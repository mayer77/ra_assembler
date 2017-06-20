/*
 * 
 * @author Maximilian Mayer
 *  m.mayer77@web.de
 * 
 */
package assembler.logic;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import java.util.ArrayList;

/**
 *
 * @author mmaye
 */
public interface IConverter
{

    public CodeGrenz convert(String row[]); //String[] besser anstatt CodeGrenz?

}
