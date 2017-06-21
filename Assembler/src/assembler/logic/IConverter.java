/*
 * 
 * @author Maximilian Mayer
 *  m.mayer77@web.de
 * 
 */
package assembler.logic;

import assembler.grenz.CodeGrenz;

/**
 *
 * @author mmaye
 */
public interface IConverter
{

    public CodeGrenz convert(CodeGrenz codeGrenz); //ArrayList<String> besser anstatt String[]?

}
