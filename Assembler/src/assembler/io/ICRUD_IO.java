/*
 * 
 * @author Maximilian Mayer
 *  m.mayer77@web.de
 * 
 */
package assembler.io;

import assembler.grenz.CodeGrenz;
import java.nio.file.Path;

/**
 *
 * @author mmaye
 */
public interface ICRUD_IO {

    public int saveCode(CodeGrenz cg, Path cpath);

    public CodeGrenz loadCode(Path cpath);

    public int exportCode(CodeGrenz cg, Path epath);
}
