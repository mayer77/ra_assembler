package assembler.grenz;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mmaye
 */
public class CodeGrenz
{
    private ArrayList<String> ctxt;
    private ArrayList<WordGrenz> cc;
    private ArrayList<VarGrenz> varlist;
    private HashMap<String, Integer> labelList;
    private Integer error;

    public CodeGrenz(ArrayList<String> ctxt, ArrayList<WordGrenz> cc, ArrayList<VarGrenz> varlist, Integer error)
    {
        this.ctxt = ctxt;
        this.cc = cc;
        this.varlist = varlist;
        this.error = error;
        VarGrenz.resetMA();
        WordGrenz.resetMA();
    }

    public ArrayList<String> getCtxt()
    {
        return ctxt;
    }

    public void setCtxt(ArrayList<String> ctxt)
    {
        this.ctxt = ctxt;
    }

    public ArrayList<WordGrenz> getCc()
    {
        return cc;
    }

    public void setCc(ArrayList<WordGrenz> cc)
    {
        this.cc = cc;
    }

    public ArrayList<VarGrenz> getVarlist()
    {
        return varlist;
    }

    public void setVarlist(ArrayList<VarGrenz> varlist)
    {
        this.varlist = varlist;
    }

    public Integer getError()
    {
        return error;
    }

    public void setError(Integer error)
    {
        this.error = error;
    }

    public HashMap<String, Integer> getLabelList()
    {
        return labelList;
    }

    public void setLabelList(HashMap<String, Integer> labelList)
    {
        this.labelList = labelList;
    }
}
