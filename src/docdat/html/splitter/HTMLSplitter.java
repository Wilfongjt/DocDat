/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.html.splitter;

import docdat.io.exceptions.UnknownFileTypeException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class HTMLSplitter {

    public HTMLSplitter(String in_path, String in_file, String out_path) {
        setIn_file(in_file);
        setIn_path(in_path);
        setOut_path(out_path);

    }
    private String breaker = "[Page-Break:";
    String in_path = "";
    String in_file = "";
    String out_path = "";

    public String getBreaker() {
        return breaker;
    }

    public void setBreaker(String breaker) {
        this.breaker = breaker;
    }

    public String getIn_file() {
        return in_file;
    }

    public void setIn_file(String in_file) {
        this.in_file = in_file;
    }

    public String getIn_path() {
        return in_path;
    }

    public void setIn_path(String in_path) {
        this.in_path = in_path;
    }

    public String getOut_path() {
        return out_path;
    }

    public void setOut_path(String out_path) {
        this.out_path = out_path;
    }

    public void split() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        // Stream  - use the InputStreamReaderFactory to initiate the ODTContentInputStreamReader
        //System.out.println("input: " + getIn_path() + getIn_file()) ;
        //System.out.println("output: " + getOut_path() ) ;
        
        File inFile = new File(getIn_path() + getIn_file());

        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        //System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());

        String line = "";
        int line_cnt = 0;
        BufferedWriter mf = null; //new BufferedWriter(new FileWriter(mfFile)); 
        while ((line = bf.readLine()) != null) {
              line_cnt++;
            if (line.indexOf(getBreaker()) > -1) {
                String fn = getOutFileName(line);
               
                if (mf != null) {
                    //System.out.println("close lines: "+line_cnt);
                    mf.close();
                    line_cnt = 0;
                    mf = null;
                } 
                //System.out.println("FileName: " + fn);
                File oFile = new File(getOut_path(), fn);
                if (oFile.exists()) {
                    oFile.delete();
                }
                mf = new BufferedWriter(new FileWriter(oFile));
                line = "";
            }
            
            
            if (mf != null) {
              //  //System.out.println("line: " + line );
                mf.write(line);
            }
        }
        if (mf != null) {
            mf.close();
        }

        //System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());

        bf.close();
        //System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        //System.out.println("testOdtReader out");

        //System.out.println("out ");

    }

    private String getOutFileName(String line) {
        int i = 0;
        for (i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == ':') {
                break;
            }
        }
        i++;
        String rc = "";
        for (; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (ch == ']') {
                break;
            }
            rc += ch;
        }
        return rc;
    }
}
