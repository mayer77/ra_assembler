package assembler.grenz;

/**
 *
 * @author mmaye
 */
public class WordGrenz {

    private String opCode, optionA, optionB, optionC, address, ma;

    public WordGrenz() {
    }

    public WordGrenz(String opCode, String optionA, String optionB, String optionC, String address, String ma) {
        this.opCode = opCode;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.address = address;
        this.ma = ma;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

}
