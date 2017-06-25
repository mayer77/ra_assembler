package assembler.grenz;

/**
 *
 * @author mmaye
 */
public class WordGrenz
{
    private static int nextMA = 0;
    private String opCode, optionA, optionB, optionC, label;
    private int ma;

    public WordGrenz()
    {
        
    }

    public WordGrenz(String opCode, String optionA, String optionB, String optionC, String label, String ma)
    {
        this.opCode = opCode;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.label = label;
        this.ma = nextMA;
        nextMA++;
    }

    public String getOpCode()
    {
        return opCode;
    }

    public void setOpCode(String opCode)
    {
        this.opCode = opCode;
    }

    public String getOptionA()
    {
        return optionA;
    }

    public void setOptionA(String optionA)
    {
        this.optionA = optionA;
    }

    public String getOptionB()
    {
        return optionB;
    }

    public void setOptionB(String optionB)
    {
        this.optionB = optionB;
    }

    public String getOptionC()
    {
        return optionC;
    }

    public void setOptionC(String optionC)
    {
        this.optionC = optionC;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public static int getNextMA()
    {
        return nextMA;
    }

    public int getMa()
    {
        return ma;
    }

    public static void resetMA()
    {
        nextMA = 0;
    }
}
