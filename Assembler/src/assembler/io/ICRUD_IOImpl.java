package assembler.io;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maximilian Mayer
 * @author Phillip Braun
 */
public class ICRUD_IOImpl implements ICRUD_IO {

    @Override
    public int saveCode(CodeGrenz codeGrenz, File cpath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(cpath))) {
            bw.write("");
            for (String line : codeGrenz.getCtxt()) {
                bw.append(line);
                bw.newLine();
                //System.out.println("Line:" + line);
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
        CodeGrenz codeGrenz = new CodeGrenz(null, null, null, null);
        codeGrenz.setCtxt(new ArrayList<>());
        try {
            BufferedReader br = new BufferedReader(new FileReader(cpath));
            String thisLine;
            while ((thisLine = br.readLine()) != null) {
                if (!thisLine.isEmpty()) {
                    codeGrenz.getCtxt().add(thisLine);
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
    public int exportCode(CodeGrenz cg, File epath) {
        //der mif Teil
        int address = 0;
        String tString;

        PrintWriter writer;
        try {
            writer = new PrintWriter(epath, "UTF-8");
            writer.println("DEPTH = 8192;");
            writer.println("WIDTH = 32;");
            writer.println("ADDRESS_RADIX = HEX;");
            writer.println("DATA_RADIX = BIN;");
            writer.println("CONTENT");
            writer.println("BEGIN");

            writer.println(Integer.toHexString(address) + " : 1001000000" + fillLeftZero(Integer.toBinaryString(VarGrenz.getNextMA()+1), 22));
            address++;
            for (VarGrenz vg : cg.getVarlist()) {
                int i=0;
                for (Integer val : vg.getValues()) {
                    writer.println(Integer.toHexString(address + vg.getMa()+i) + " : "+fillRightZero("00000" + fillLeftZero(Integer.toBinaryString(val), 27)));                 
                    i++;
                }
            }
            address+=VarGrenz.getNextMA();
            for (WordGrenz wg : cg.getCc()) {
                if (!wg.getLabel().isEmpty()) {
                    
                    tString = fillLeftZero(Integer.toBinaryString(cg.getLabelList().get(wg.getLabel())+VarGrenz.getNextMA()+1), 22);
                } else {
                    tString = "";
                }

                writer.println(Integer.toHexString(address + wg.getMa()) + " : " + fillRightZero( wg.getOpCode() + wg.getOptionA() + wg.getOptionB() + wg.getOptionC() + tString));
                
            }

            writer.println("");
            writer.println("END;");
            writer.close();

        } catch (Exception e) {
            Logger.getLogger(ICRUD_IOImpl.class.getName()).log(Level.SEVERE, null, e);
            return 1;
        }

        return 0;
    }

    private String fillLeftZero(String s, int length) {
        s=s.trim();
        int n=s.length();
        for (int i = 0; i < (length - n); i++) {
            s = "0".concat(s);
        }
        return s;
    }

    private String fillRightZero(String s) {
        s=s.trim();
        int n=s.length();
        for (int i = 0; i < (32 - n); i++) {
            s = s.concat("0");
        }
        return s;
    }

}
