package assembler.grenz;

import java.util.BitSet;

/**
 *
 * @author mmaye
 */
public class VarGrenz {

    private BitSet ma;
    private String label;

    public VarGrenz(BitSet ma, String label)
    {
        this.ma = ma;
        this.label = label;
    }

    public BitSet getMa() {
        return ma;
    }

    public void setMa(BitSet ma) {
        this.ma = ma;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
}
