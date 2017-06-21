package assembler.io;

import assembler.grenz.CodeGrenz;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * see https://github.com/esposem/MifGenerator
 *
 * @author mmaye
 */
public class ICRUD_IOImpl implements ICRUD_IO {

    @Override
    public int saveCode(CodeGrenz codeGrenz, File cpath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(cpath))) {
            bw.write("");
            for (String line : codeGrenz.getCtxt()) {
                bw.append(line);
                bw.newLine();
                System.out.println("Line:" + line);
            }
            bw.close();
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            return 1;
        }
        return 0;
    }

    @Override
    public CodeGrenz loadCode(File cpath
    ) {
        CodeGrenz codeGrenz = new CodeGrenz();
        codeGrenz.setCtxt(new ArrayList<>());
        try {
            BufferedReader br = new BufferedReader(new FileReader(cpath));
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                if(!thisLine.isEmpty()){
                codeGrenz.getCtxt().add(thisLine);
                System.out.println("Line:" + thisLine);
                }
            }
            br.close();
        } catch (java.io.IOException e) {
            System.out.println(e.getMessage());
            codeGrenz = null;
        }
        return codeGrenz;
    }

    @Override
    public int exportCode(CodeGrenz cg, File epath
    ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
