package assembler.grenz;

import java.util.ArrayList;

/**
 *
 * @author mmaye
 */
public class CodeGrenz {

    private String ctxt;
    private ArrayList<WordGrenz> cc;
    private ArrayList<VarGrenz> varlist;
    private Integer error;

    public String getCtxt() {
        return ctxt;
    }

    public void setCtxt(String ctxt) {
        this.ctxt = ctxt;
    }

    public ArrayList<WordGrenz> getCc() {
        return cc;
    }

    public void setCc(ArrayList<WordGrenz> cc) {
        this.cc = cc;
    }

    public ArrayList<VarGrenz> getVarlist() {
        return varlist;
    }

    public void setVarlist(ArrayList<VarGrenz> varlist) {
        this.varlist = varlist;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

}
