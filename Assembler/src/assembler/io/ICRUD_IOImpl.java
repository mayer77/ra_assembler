package assembler.io;

import assembler.grenz.CodeGrenz;
import java.nio.file.Path;

/**
 * see https://github.com/esposem/MifGenerator
 * @author mmaye
 */
public class ICRUD_IOImpl implements ICRUD_IO {

    @Override
    public int saveCode(CodeGrenz cg, Path cpath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CodeGrenz loadCode(Path cpath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int exportCode(CodeGrenz cg, Path epath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
