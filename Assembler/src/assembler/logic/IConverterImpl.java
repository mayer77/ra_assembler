package assembler.logic;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Maximilian Mayer
 * @author Phillip Braun
 */
public class IConverterImpl implements IConverter
{

    private CodeGrenz codeGrenz;

    /**
     *
     * @param cg CodeGrenz
     * @return null If Ctxt is null
     */
    @Override
    public CodeGrenz convert(CodeGrenz cg)
    {
        
        try
        {
            codeGrenz = cg;
            VarGrenz.resetMA();
            WordGrenz.resetMA();
            codeGrenz.setCc(new ArrayList<>());
            codeGrenz.setError(null);
            codeGrenz.setVarlist(new ArrayList<>());
            codeGrenz.setLabelList(new HashMap<>());
            if (codeGrenz.getCtxt() == null || codeGrenz == null)
            {
                return null;
            }

            //First Run
            int status = 0;
            int varSize = 1;
            Integer lineNR = 1;

            for (String line : codeGrenz.getCtxt())
            {
                //Kommentare entfernen
                if (line.contains(";"))
                {
                    line = line.replaceAll(";[\\S\\s]*", "");
                }

                //Programmbereiche wechseln
                if (status == 0 && line.contains(".data"))
                {
                    status = 1;
                } else if (status == 0)
                {
                    codeGrenz.getCtxt().remove(line);
                } else if (status == 1 && line.contains(".text"))
                {
                    status = 2;
                } else if (status == 2 && line.contains(".end"))
                {
                    status = 3;
                } //Code interpretieren 
                else if (status == 1)//Variablen
                {
                    Integer varValue = 0;
                    if (line.contains(":"))
                    {
                        varSize = 1;
                        String[] tmp = line.split(":");

                        String[] codeOnly = tmp[1].split(" ");

                        for (String tmp3 : codeOnly)
                        {
                            if (tmp3.matches("\\d+"))
                            {
                                if (tmp[1].contains(".ds"))
                                {
                                    varSize = Integer.parseInt(tmp3);
                                } else if (tmp[1].contains(".dc"))
                                {
                                    varValue = Integer.parseInt(tmp3);
                                }
                            }
                        }

                        if (codeGrenz.getVarlist().add(new VarGrenz(tmp[0], new ArrayList<>())))
                        {
                            codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).getValues().add(varValue);
                        }
                        if (!codeGrenz.getVarlist().isEmpty())
                        {
                            codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).countMA();
                        }
                    } else
                    {
                        varSize++;

                        String[] codeOnly = line.split(" ");
                        codeOnly[0] = "";
                        for (String tmp3 : codeOnly)
                        {
                            if (tmp3.matches("\\d+"))
                            {
                                if (line.contains(".ds"))
                                {
                                    codeGrenz.setError(lineNR);
                                    return codeGrenz;
                                } else if (line.contains(".dc"))
                                {
                                    varValue = Integer.parseInt(tmp3);
                                }

                                codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).getValues().add(varValue);
                                if (!codeGrenz.getVarlist().isEmpty())
                                {
                                    codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).countMA();
                                }
                            }
                        }
                    }
                } else if (status == 2) //Code
                {
                    WordGrenz wordGrenz;

                    if (line.contains(":"))
                    {
                        String[] tmp = line.split(":");

                        String[] codeOnly = tmp[1].split(" ");
                        wordGrenz = searchWord(codeOnly, codeGrenz.getVarlist());
                        if (wordGrenz == null)
                        {
                            codeGrenz.setError(lineNR);
                            return codeGrenz;
                        }
                        codeGrenz.getLabelList().put(tmp[0], wordGrenz.getMa());
                    } else
                    {
                        String[] codeOnly = line.split(" ");
                        wordGrenz = searchWord(codeOnly, codeGrenz.getVarlist());
                        if (wordGrenz == null)
                        {
                            codeGrenz.setError(lineNR);
                            return codeGrenz;
                        }
                    }
                    codeGrenz.getCc().add(wordGrenz);
                }
                lineNR++;
            }
            return codeGrenz;
        } catch (Exception e)
        {
            return null;
        }
    }

    private WordGrenz searchWord(String[] words, ArrayList<VarGrenz> varList)
    {
        try
        {
            WordGrenz wordGrenz = new WordGrenz("", "", "", "", "", "");
            String opCode = "";
            int opCodeLevel = 0;
            int opCodeType = 0;
            String[] tmpStrings;

            /**
             * Table of OpCodeTypes NO 1 R 2 RR 3 RRR 4 RImm 5 Imm 6
             */
            for (String word : words)
            {
                if ((!(word == null)) && (!word.isEmpty()))
                {
                    if (opCodeLevel == 0)
                    {
                        switch (word)
                        {
                            case "st":
                                opCode = "00001";
                                opCodeType = 5;
                                break;
                            case "ld":
                                opCode = "00010";
                                opCodeType = 5;
                                break;
                            case "sti":
                                opCode = "00011";
                                opCodeType = 3;
                                break;
                            case "ldi":
                                opCode = "00100";
                                opCodeType = 3;
                                break;
                            case "lc":
                                opCode = "00101";
                                opCodeType = 5;
                                break;
                            case "add":
                                opCode = "00110";
                                opCodeType = 4;
                                break;
                            case "sub":
                                opCode = "00111";
                                opCodeType = 4;
                                break;
                            case "mul":
                                opCode = "01000";
                                opCodeType = 4;
                                break;
                            case "div":
                                opCode = "01001";
                                opCodeType = 4;
                                break;
                            case "and":
                                opCode = "01010";
                                opCodeType = 4;
                                break;
                            case "or":
                                opCode = "01011";
                                opCodeType = 4;
                                break;
                            case "xor":
                                opCode = "01100";
                                opCodeType = 4;
                                break;
                            case "sll":
                                opCode = "11000";
                                opCodeType = 3;
                                break;
                            case "srl":
                                opCode = "11001";
                                opCodeType = 3;
                                break;
                            case "sla":
                                opCode = "11010";
                                opCodeType = 3;
                                break;
                            case "sra":
                                opCode = "11011";
                                opCodeType = 3;
                                break;
                            case "not":
                                opCode = "01101";
                                opCodeType = 2;
                                break;
                            case "inc":
                                opCode = "01110";
                                opCodeType = 2;
                                break;
                            case "dec":
                                opCode = "01111";
                                opCodeType = 2;
                                break;
                            case "jz":
                                opCode = "10000";
                                opCodeType = 6;
                                break;
                            case "jne":
                                opCode = "10001";
                                opCodeType = 6;
                                break;
                            case "jmp":
                                opCode = "10010";
                                opCodeType = 6;
                                break;
                            case "call":
                                opCode = "10011";
                                opCodeType = 6;
                                break;
                            case "ret":
                                opCode = "10100";
                                opCodeType = 1;
                                break;
                            case "push":
                                opCode = "10101";
                                opCodeType = 2;
                                break;
                            case "pop":
                                opCode = "10110";
                                opCodeType = 2;
                                break;
                            case "nop":
                                opCode = "10111";
                                opCodeType = 1;
                                break;
                            default:
                                return null;
                        }
                        opCodeLevel++;
                        wordGrenz.setOpCode(opCode);
                    } else if (opCodeLevel == 1)
                    {
                        switch (opCodeType)
                        {
                            case 1:
                                break;
                            case 6:
                                wordGrenz.setOptionA("00000");
                                if (!codeGrenz.getLabelList().containsKey(word))
                                {
                                    codeGrenz.getLabelList().put(word, -1);
                                }
                                wordGrenz.setLabel(word);
                                break;
                            case 2:
                                tmpStrings = word.split("r");
                                tmpStrings = tmpStrings[1].split(",");
                                wordGrenz.setOptionA(int2String(Integer.parseInt(tmpStrings[0]), 5));
                                break;
                            case 3:
                                tmpStrings = word.split("r");
                                tmpStrings = tmpStrings[1].split(",");
                                wordGrenz.setOptionA(int2String(Integer.parseInt(tmpStrings[0]), 5));
                                break;
                            case 4:
                                tmpStrings = word.split("r");
                                tmpStrings = tmpStrings[1].split(",");
                                wordGrenz.setOptionA(int2String(Integer.parseInt(tmpStrings[0]), 5));
                                break;
                            case 5:
                                tmpStrings = word.split("r");
                                tmpStrings = tmpStrings[1].split(",");
                                wordGrenz.setOptionA(int2String(Integer.parseInt(tmpStrings[0]), 5));
                                break;
                            default:

                        }
                        opCodeLevel++;
                    } else if (opCodeLevel == 2)
                    {
                        switch (opCodeType)
                        {
                            case 3:
                                tmpStrings = word.split("r");
                                tmpStrings = tmpStrings[1].split(",");
                                wordGrenz.setOptionB(int2String(Integer.parseInt(tmpStrings[0]), 5));
                                break;
                            case 4:
                                tmpStrings = word.split("r");
                                tmpStrings = tmpStrings[1].split(",");
                                wordGrenz.setOptionB(int2String(Integer.parseInt(tmpStrings[0]), 5));
                                break;
                            case 5:
                                if (Character.isDigit(word.charAt(0)))
                                {
                                    wordGrenz.setOptionB(int2String(Integer.parseInt(word), 21));
                                } else
                                {
                                    if (word.contains("["))
                                    {
                                        for (VarGrenz varGrenz : varList)
                                        {
                                            if (varGrenz.getLabel().equals(word.substring(0, word.indexOf("["))))
                                            {
                                                wordGrenz.setOptionB(int2String(varGrenz.getMa() + Integer.parseInt(word.substring(word.indexOf("[") + 1, word.indexOf("]"))), 21));
                                            }
                                        }
                                    } else
                                    {
                                        for (VarGrenz varGrenz : varList)
                                        {
                                            if (varGrenz.getLabel().equals(word))
                                            {
                                                wordGrenz.setOptionB(int2String(varGrenz.getMa(), 21));
                                            }
                                        }
                                    }
                                }
                                break;
                            case 6:

                                break;
                            default:

                        }
                        opCodeLevel++;
                    } else if (opCodeLevel == 3)
                    {
                        switch (opCodeType)
                        {
                            case 4:
                                tmpStrings = word.split("r");
                                wordGrenz.setOptionC(int2String(Integer.parseInt(tmpStrings[1]), 5));
                                break;
                            default:
                        }
                    }

                }
            }
            return wordGrenz;
        } catch (Exception e)
        {
            return null;
        }
    }

    private String int2String(int i, int size)
    {
        try
        {
            String result;
            result = Integer.toBinaryString(i);
            if (result.length() < size)
            {
                int l = result.length();
                for (int n = 0; n < size - l; n++)
                {
                    result = "0".concat(result);
                }
            }
            if (result.length() > size)
            {
                return null;
            }

            return result;
        } catch (Exception e)
        {
            return null;
        }
    }
}
