/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.io.readers.ODTContentInputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;


/**
 *
 * @author wilfongj
 */
public class ODT01ContextInputStreamReader_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("PseudoElementsReaderTest 1");

        /// load elements


        testODTContextInputStreamReader();
        //testBufferedReader(elems);
    }

    private static void testODTContextInputStreamReader() throws IOException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
 String path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";
        String filename = "sample.log.odt";
        String odtFileName = path + filename;
        
        
        // Stream  - use the InputStreamReaderFactory to initiate the ODTContentInputStreamReader

        ODTContentInputStreamReader odtReader = new ODTContentInputStreamReader(odtFileName);

        BufferedReader bf = new BufferedReader(odtReader);
        System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());
        String line = "";
        while ((line = bf.readLine()) != null) {
            System.out.println(line);
        }

        System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());

        bf.close();
        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");
    }
}
