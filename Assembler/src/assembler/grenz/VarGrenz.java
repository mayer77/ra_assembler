package assembler.grenz;

import java.util.ArrayList;

/**
 *
 * @author mmaye
 */
public class VarGrenz {

    private static int nextMA = 1;
    private int ma;
    private String label;
    private ArrayList<Integer> values;

    public VarGrenz(String label, ArrayList<Integer> value) {
        this.label = label;
        this.values = value;
        this.ma = nextMA;
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public void setValues(ArrayList<Integer> values) {
        this.values = values;
    }

    public static int getNextMA() {
        return nextMA;
    }

    public int getMa() {
        return ma;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public void countMA(){
        nextMA+=values.size();
    }

}
