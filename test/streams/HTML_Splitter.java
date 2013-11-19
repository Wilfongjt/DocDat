/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

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
public class HTML_Splitter {

    public static void main(String[] args) throws Exception {
        System.out.println("InputFiles_01_Test1 1");

        /// load elements


        test();
        //testBufferedReader(elems);
    }

    private static void test() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());

        //String in_path = "C:\\00-Code\\01-dev\\DesignChart\\src\\xsl\\std\\layout\\page\\perspectives_v5\\";
        // String out_path = "C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\html\\";
        //String in_file = "merge.html";

        String in_path = "C:\\00-Code\\01-dev\\DesignChart\\src\\xsl\\std\\layout\\page\\perspectives_v5\\";
        String out_path = "C:\\00-Code\\00-Projects\\SHPO\\GIS-Database\\06-Output\\";

        String in_file = "mockup_text.html";

        //in_file = "perspectives.html";
        String breaker = "[Page-Break:";

        // Stream  - use the InputStreamReaderFactory to initiate the ODTContentInputStreamReader
        File inFile = new File(in_path + in_file);
        //   if(inFile.exists()){
        //          inFile.delete();
        //   }
        // File outFile = new File(out_path + );

        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(inFile)));
        System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());

        String line = "";
        BufferedWriter mf = null; //new BufferedWriter(new FileWriter(mfFile)); 
        while ((line = bf.readLine()) != null) {

            if (line.indexOf(breaker) > -1) {
                String fn = getOutFileName(line);
                System.out.println(fn);
                if (mf != null) {
                    mf.close();
                    mf = null;
                }
                File oFile = new File(out_path, fn);
                if (oFile.exists()) {
                    oFile.delete();
                }
                mf = new BufferedWriter(new FileWriter(oFile));
                line="";
            }
            if (mf != null) {
                mf.write(line);
            }
        }
        if (mf != null) {
            mf.close();
        }

        System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());

        bf.close();
        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");

        System.out.println("out ");

    }

    private static String getOutFileName(String line) {
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
