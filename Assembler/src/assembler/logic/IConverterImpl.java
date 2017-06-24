package assembler.logic;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mmaye
 */
public class IConverterImpl implements IConverter {

    /**
     *
     * @param codeGrenz
     * @return null If Ctxt is null
     */
    @Override
    public CodeGrenz convert(CodeGrenz codeGrenz) {

        codeGrenz.setCc(new ArrayList<>());
        codeGrenz.setError(null);
        codeGrenz.setVarlist(new ArrayList<>());
        codeGrenz.setLabelList(new HashMap<>());
        if (codeGrenz.getCtxt() == null) {
            return null;
        }

        //First Run
        int status = 0;
        int varSize = 1;
        Integer lineNR = 1;

        for (String line : codeGrenz.getCtxt()) {
            //Komentare entfernen
            if (line.contains(";")) {
                line = line.replaceAll(";[\\S\\s]*", "");
            }

            //Code interpretieren
            //Variablen
            if (status == 1) {
                Integer varValue = null;
                if (line.contains(":")) {
                    if (!codeGrenz.getVarlist().isEmpty()) {
                        codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).countMA();
                    }
                    varSize = 1;
                    String[] tmp = line.split(":");

                    String[] codeOnly = tmp[1].split(" ");
                    codeOnly[0] = "";
                    for (String tmp3 : codeOnly) {
                        if (!tmp3.isEmpty()) {
                            if (tmp[1].contains(".ds")) {
                                varSize = Integer.parseInt(tmp3);
                            } else if (tmp[1].contains(".dc")) {
                                varValue = Integer.parseInt(tmp3);
                            }
                        }
                    }
                    codeGrenz.getVarlist().add(new VarGrenz(tmp[0], new ArrayList<>()));
                    codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).getValues().add(varValue);
                } else {
                    varSize++;
                    String[] tmp = line.split(":");

                    String[] codeOnly = tmp[1].split(" ");
                    codeOnly[0] = "";
                    for (String tmp3 : codeOnly) {
                        if (!tmp3.isEmpty()) {
                            if (tmp[1].contains(".ds")) {
                                codeGrenz.setError(lineNR);
                                return codeGrenz;
                            } else if (tmp[1].contains(".dc")) {
                                varValue = Integer.parseInt(tmp3);
                            }
                        }
                    }
                    codeGrenz.getVarlist().get(codeGrenz.getVarlist().size() - 1).getValues().add(varValue);
                }

            }

            //Code
            if (status == 2) {
                WordGrenz wordGrenz;

                if (line.contains(":")) {

                    String[] tmp = line.split(":");

                    String[] codeOnly = tmp[1].split(" ");
                    wordGrenz = searchWord(codeOnly, codeGrenz.getVarlist());
                    if (wordGrenz == null) {
                        codeGrenz.setError(lineNR);
                        return codeGrenz;
                    }

                    if (!codeGrenz.getLabelList().containsKey(tmp[0])) {
                        codeGrenz.getLabelList().put(tmp[0], new ArrayList<>());
                        codeGrenz.getLabelList().get(tmp[0]).add(wordGrenz);

                    } else {
                        codeGrenz.getLabelList().get(tmp[0]).add(wordGrenz);
                    }

                    wordGrenz.setLabel(tmp[0]);

                } else {
                    String[] codeOnly = line.split(" ");
                    wordGrenz = searchWord(codeOnly, codeGrenz.getVarlist());
                    if (wordGrenz == null) {
                        codeGrenz.setError(lineNR);
                        return codeGrenz;
                    }
                }
            }

            //Programmbereiche werchseln
            if (status == 2 && line.contains(".end")) {
                status = 3;
            }

            if (status == 1 && line.contains(".text")) {
                status = 2;
            }

            if (status == 0 && line.contains(".data")) {
                status = 1;
            } else if (status == 0) {
                codeGrenz.getCtxt().remove(line);
            }
            lineNR++;
        }
        /*
        
         ArrayList<WordGrenz> wordList = new ArrayList<>();
         ArrayList<String> row;
         row = codeGrenz.getCtxt();
         WordGrenz wordGrenz = new WordGrenz();
         String[] column = new String[5];
         int line = 0;
         for (String tmpColumn : row) {
         column = tmpColumn.split(" ");
         VarGrenz variable = search_Var(column[0]);
         if (variable == null) {
         wordGrenz = searchWord(column[0]);
         if (wordGrenz == null) {
         codeGrenz.setError(line);
         } else {
         wordGrenz.setAddress(Integer.toString(line));
         }
         } else {
         wordGrenz = searchWord(column[1]);
         if (wordGrenz == null) {
         codeGrenz.setError(line);
         } else {
         wordGrenz.setAddress(Integer.toString(line));
         }

         }

         } */

        /*
         int error = -1;
         int line = 0;
         String[] tmpSatz = new String[4];
         ArrayList<WordGrenz> wordList = new ArrayList<>();
         ArrayList<VarGrenz> varList = new ArrayList<>();
         for (String tmpRow : codeGrenz.getCtxt())
         {
         tmpSatz = tmpRow.split(" ");
         }
        
         for (String tmpWord : tmpSatz)
         {
            
         VarGrenz variable = search_Var(tmpWord);
         if (variable != null)
         {
         varList.add(variable);
         }
         WordGrenz word = searchWord(tmpWord);
         if (word != null) //Falscher Ansatz es sollten ganze Sätze geprüft werden.
         {
         wordList.add(word);
         }
         else
         {
         codeGrenz.setError(line);
         }
         line++;
         }
         codeGrenz.setCc(wordList);
         codeGrenz.setError(error);
         codeGrenz.setVarlist(varList);
         */
        return codeGrenz;
    }

    private WordGrenz searchWord(String[] words, ArrayList<VarGrenz> varList) {
        WordGrenz wordGrenz = new WordGrenz();
        String opCode = "";
        int opCodeLevel = 0;
        int opCodeType = 0;
        String[] tmpStrings;

        /**
         * Table of OpCodeTypes NO 1 R 2 RR 3 RRR 4 RImm 5 Imm 6
         */
        for (String word : words) {
            if ((!(word == null)) && (!word.isEmpty())) {

                if (opCodeLevel == 0) {

                    switch (word) {
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
                }

                if (opCodeLevel == 1) {
                    switch (opCodeType) {
                        case 1:
                            break;
                        case 6:
                            break;
                        default:
                            tmpStrings = word.split("r");
                            wordGrenz.setOptionA(int2String(Integer.parseInt(tmpStrings[1]), 5));
                            break;
                    }
                }

                if (opCodeLevel == 2) {
                    switch (opCodeType) {
                        case 3:
                            tmpStrings = word.split("r");
                            wordGrenz.setOptionB(int2String(Integer.parseInt(tmpStrings[1]), 5));
                            break;
                        case 4:
                            tmpStrings = word.split("r");
                            wordGrenz.setOptionB(int2String(Integer.parseInt(tmpStrings[1]), 5));
                            break;
                        case 5:
                            if (Character.isDigit(word.charAt(0))) {
                                wordGrenz.setOptionB(int2String(Integer.parseInt(word), 21));
                            } else {
                                if (word.contains("[")) {
                                    for (VarGrenz varGrenz : varList) {
                                        if (varGrenz.getLabel().equals(word.substring(0, word.indexOf("[")))) {
                                            wordGrenz.setOptionB(int2String(varGrenz.getMa() + Integer.parseInt(word.substring(word.indexOf("[") + 1, word.indexOf("]"))), 21));
                                        }
                                    }

                                } else {
                                    for (VarGrenz varGrenz : varList) {
                                        if (varGrenz.getLabel().equals(word)) {
                                            wordGrenz.setOptionB(int2String(varGrenz.getMa(), 21));
                                        }
                                    }
                                }
                            }
                            break;
                        case 6:
                            
                            break;

                    }

                }

            }
        }

        return wordGrenz;
    }

    private String int2String(int i, int size) {
        String result;
        result = Integer.toBinaryString(i);
        if (result.length() < size) {
            for (int n = 0; n < size - result.length(); n++) {
                result = "0".concat(result);
            }
        }
        if (result.length() > size) {
            return null;
        }

        return result;

    }
}
