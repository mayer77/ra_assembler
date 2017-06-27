/*
 * 
 * @author Maximilian Mayer
 *  m.mayer77@web.de
 * 
 */
package assembler.io;

import assembler.grenz.CodeGrenz;
import java.io.File;


/**
 *
 * @author mmaye
 */
public interface ICRUD_IO {

    public int saveCode(CodeGrenz cg, File cpath);

    public CodeGrenz loadCode(File cpath);

    public int exportCode(CodeGrenz cg, File epath, boolean isSim);
}
