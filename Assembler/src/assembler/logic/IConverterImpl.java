package assembler.logic;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import static com.sun.media.jfxmediaimpl.MediaUtils.error;
import java.util.ArrayList;
import java.util.BitSet;

/**
 *
 * @author mmaye
 */
public class IConverterImpl implements IConverter
{

    @Override
    public CodeGrenz convert(String[] rows)
    {
        int error = -1;
        String[] tmpSatz = new String[4];
        ArrayList<WordGrenz> wordList = new ArrayList<>();
        ArrayList<VarGrenz> varList = new ArrayList<>();
        for (String tmpRow : rows)
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

        }

        CodeGrenz retCG = new CodeGrenz(row, wordList, varList, error);
        return retCG;
    }

    private VarGrenz search_Var(String tmpWord)
    {
        VarGrenz variable = null;
        if (tmpWord.contains("%"))
        {
            variable.setLabel(tmpWord);
            BitSet bs = new BitSet();

            variable.setMa(bs); //Variablen Logik
        }
        return variable;
    }

    private WordGrenz searchWord(String tmpWord)
    {
        WordGrenz word = new WordGrenz();
        BitSet bs = new BitSet() ;
        switch(tmpWord)
        {
            case "st" : bs = 00001;
                break;
            case "ld" : bs = 00010;
                break;
            case "sti" : bs = ;
                break;
            case "ldi" : bs = ;
                break;
            case "lc" : bs = ;
            break;
            case "add" : bs = ;
            break;
            case "sub" : bs = ;
            break;
            case "mul" : bs = ;
            break;
            case "div" : bs = 00011;
            break;
            case "and" : bs = 00011;
            break;
            case "or" : bs = 00011;
            break;
            case "xor" : bs = 00011;
            break;
            case "sll" : bs = 00011;
            break;
            case "srl" : bs = 00011;
            break;
            case "sla" : bs = 00011;
            break;
            case "sra" : bs = 00011;
            break;
            case "not" : bs = 00011;
            break;
            case "inc" : bs = 00011;
            break;
            case "dec" : bs = 00011;
            break;
            case "jz" : bs = 00011;
            break;
            case "jne" : bs = 00011;
            break;
            case "jmp" : bs = 00011;
            break;
            case "call" : bs = 00011;
            break;
            case "ret" : bs = ;
            break;
            case "push" : bs = ;
            break;
            case "pop" : bs = ;
            break;
            case "nop" : bs = ;
            break;
            
        }

        return word;
    }

}
