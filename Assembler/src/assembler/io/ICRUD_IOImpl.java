package assembler.io;

import assembler.grenz.CodeGrenz;
import assembler.grenz.VarGrenz;
import assembler.grenz.WordGrenz;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
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
    public CodeGrenz loadCode(File cpath) {
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

    public int exportCode(CodeGrenz cg, File epath, boolean isSim) {
        int address = 0;
        String tString;

        if (isSim) {
            //SIM -Teil
           
            //String tString;
            ArrayList<String> txt=new ArrayList<>();

            try {

               // DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(epath)));
                
            //dos.writeInt(words.size());
              //  dos.writeBytes("1001000000" + fillLeftZero(Integer.toBinaryString(VarGrenz.getNextMA() + 1), 22));
                txt.add(("1001000000" + fillLeftZero(Integer.toBinaryString(VarGrenz.getNextMA() + 1), 22)));
                //address++;
                for (VarGrenz vg : cg.getVarlist()) {
                    int i = 0;
                    for (Integer val : vg.getValues()) {
                        //dos.writeBytes(fillRightZero("00000" + fillLeftZero(Integer.toBinaryString(val), 27)));
                        txt.add((fillRightZero("00000" + fillLeftZero(Integer.toBinaryString(val), 27))));
                        i++;
                    }
                }
                address += VarGrenz.getNextMA();
                for (WordGrenz wg : cg.getCc()) {
                    if (!wg.getLabel().isEmpty()) {

                        tString = fillLeftZero(Integer.toBinaryString(cg.getLabelList().get(wg.getLabel()) + VarGrenz.getNextMA() + 1), 22);
                    } else {
                        tString = "";
                    }

                  //  dos.writeBytes(fillRightZero(wg.getOpCode() + wg.getOptionA() + wg.getOptionB() + wg.getOptionC() + tString));
                    txt.add((fillRightZero(wg.getOpCode() + wg.getOptionA() + wg.getOptionB() + wg.getOptionC() + tString)));
                }
             //   dos.flush();
               // dos.close();
                
                OpenOption[] options = new OpenOption[] {};
                byte[] btxt=new byte[txt.size()*4];
                int j=0;
                
                for(String ints:txt){
                    btxt[j]=(byte)Integer.parseInt(ints.substring(0, 8),2);
                    btxt[j+1]=(byte)Integer.parseInt(ints.substring(8, 16),2);
                    btxt[j+2]=(byte)Integer.parseInt(ints.substring(16, 24),2);
                    btxt[j+3]=(byte)Integer.parseInt(ints.substring(24, 32),2);
                    j++;
                }
               
                Files.write(epath.toPath(),btxt,options);
               

            } catch (Exception e) {
                Logger.getLogger(ICRUD_IOImpl.class.getName()).log(Level.SEVERE, null, e);
                
                return 1;
            }

        } else {

            //MIF-File 
            PrintWriter writer;
            try {
                writer = new PrintWriter(epath, "UTF-8");
                writer.println("DEPTH = 8192;");
                writer.println("WIDTH = 32;");
                writer.println("ADDRESS_RADIX = HEX;");
                writer.println("DATA_RADIX = BIN;");
                writer.println("CONTENT");
                writer.println("BEGIN");

                writer.println(Integer.toHexString(address) + " : 1001000000" + fillLeftZero(Integer.toBinaryString(VarGrenz.getNextMA() + 1), 22));
                address++;
                for (VarGrenz vg : cg.getVarlist()) {
                    int i = 0;
                    for (Integer val : vg.getValues()) {
                        writer.println(Integer.toHexString(address + vg.getMa() + i) + " : " + fillRightZero("00000" + fillLeftZero(Integer.toBinaryString(val), 27)));
                        i++;
                    }
                }
                address += VarGrenz.getNextMA();
                for (WordGrenz wg : cg.getCc()) {
                    if (!wg.getLabel().isEmpty()) {

                        tString = fillLeftZero(Integer.toBinaryString(cg.getLabelList().get(wg.getLabel()) + VarGrenz.getNextMA() + 1), 22);
                    } else {
                        tString = "";
                    }

                    writer.println(Integer.toHexString(address + wg.getMa()) + " : " + fillRightZero(wg.getOpCode() + wg.getOptionA() + wg.getOptionB() + wg.getOptionC() + tString));

                }

                writer.println("");
                writer.println("END;");
                writer.close();

            } catch (Exception e) {
                Logger.getLogger(ICRUD_IOImpl.class.getName()).log(Level.SEVERE, null, e);
                return 1;
            }
        }

        return 0;

    }

    private String fillLeftZero(String s, int length) {
        try {
            s = s.trim();
            int n = s.length();
            for (int i = 0; i < (length - n); i++) {
                s = "0".concat(s);
            }
            return s;
        } catch (Exception e) {
            return null;
        }
    }

    private String fillRightZero(String s) {
        try {
            s = s.trim();
            int n = s.length();
            for (int i = 0; i < (32 - n); i++) {
                s = s.concat("0");
            }
            return s;
        } catch (Exception e) {
            return null;
        }
    }

}
