package assembler.logic;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import java.util.ArrayList;

/**
 *
 * @author mmaye
 */
public class IConverterImpl implements IConverter
{

    @Override
    public CodeGrenz convert(CodeGrenz codeGrenz)
    {
        ArrayList<WordGrenz> wordList = new ArrayList<>();
        ArrayList<String> row;
        row = codeGrenz.getCtxt();
        WordGrenz wordGrenz = new WordGrenz();
        String[] column = new String[5];
        int line = 0;
        for(String tmpColumn : row)
        {
            column = tmpColumn.split(" ");
            VarGrenz variable = search_Var(column[0]);
            if(variable == null)
            {
                wordGrenz = searchWord(column[0]);
                if(wordGrenz == null)
                {
                    codeGrenz.setError(line);
                }
                else
                {
                    wordGrenz.setAddress(Integer.toString(line));
                }
            }
            else
            {
                wordGrenz = searchWord(column[1]);
                if(wordGrenz == null)
                {
                    codeGrenz.setError(line);
                }
                else
                {
                    wordGrenz.setAddress(Integer.toString(line));
                }
                
            }
            
            
        }
                
                
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

    private VarGrenz search_Var(String tmpWord)
    {
        VarGrenz variable = null;
        if (tmpWord.contains(":"))
        {
            variable.setLabel(tmpWord);
            String bs = "";

            variable.setMa(bs); //Variablen Logik
        }
        return variable;
    }

    private WordGrenz searchWord(String tmpWord)
    {
        WordGrenz word = new WordGrenz();
        String bs = "";
        switch (tmpWord)
        {
            case "st":
                bs = "00001";
                break;
            case "ld":
                bs = "00010";
                break;
            case "sti":
                bs = "00011";
                break;
            case "ldi":
                bs = "00100";
                break;
            case "lc":
                bs = "00101";
                break;
            case "add":
                bs = "00110";
                break;
            case "sub":
                bs = "00111";
                break;
            case "mul":
                bs = "01000";
                break;
            case "div":
                bs = "01001";
                break;
            case "and":
                bs = "01010";
                break;
            case "or":
                bs = "01011";
                break;
            case "xor":
                bs = "01100";
                break;
            case "sll":
                bs = "11000";
                break;
            case "srl":
                bs = "11001";
                break;
            case "sla":
                bs = "11010";
                break;
            case "sra":
                bs = "11011";
                break;
            case "not":
                bs = "01101";
                break;
            case "inc":
                bs = "01110";
                break;
            case "dec":
                bs = "01111";
                break;
            case "jz":
                bs = "10000";
                break;
            case "jne":
                bs = "10001";
                break;
            case "jmp":
                bs = "10010";
                break;
            case "call":
                bs = "10011";
                break;
            case "ret":
                bs = "10100";
                break;
            case "push":
                bs = "10101";
                break;
            case "pop":
                bs = "10110";
                break;
            case "nop":
                bs = "10111";
                break;
            default:
                word = null;
                break;
        }
        word.setOpCode(bs);
        return word;
    }

    private String searchReg()
    {
        
        String retString = new String();
        
        return retString;
    }
}
