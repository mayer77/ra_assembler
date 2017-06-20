package assembler.grenz;

import java.util.BitSet;

/**
 *
 * @author mmaye
 */
public class WordGrenz {

    private BitSet opCode, optionA, optionB, optionC, address, ma;
    
    public WordGrenz(BitSet opCode, BitSet optionA, BitSet optionB, BitSet optionC, BitSet address, BitSet ma)
    {
        this.opCode = opCode;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.address = address;
        this.ma = ma;
    }

    public BitSet getOpCode() {
        return opCode;
    }

    public void setOpCode(BitSet opCode) {
        this.opCode = opCode;
    }

    public BitSet getOptionA() {
        return optionA;
    }

    public void setOptionA(BitSet optionA) {
        this.optionA = optionA;
    }

    public BitSet getOptionB() {
        return optionB;
    }

    public void setOptionB(BitSet optionB) {
        this.optionB = optionB;
    }

    public BitSet getOptionC() {
        return optionC;
    }

    public void setOptionC(BitSet optionC) {
        this.optionC = optionC;
    }

    public BitSet getAddress() {
        return address;
    }

    public void setAddress(BitSet address) {
        this.address = address;
    }

    public BitSet getMa() {
        return ma;
    }

    public void setMa(BitSet ma) {
        this.ma = ma;
    }
    
    
}
