/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.io.names.InFileName;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.load.LoadPseudoElements;
import docdat.utils.Attribute;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class PE_01Import_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("PseudoElementsReaderTest 1");

        /// load elements


        testImportText();
        //testBufferedReader(elems);
    }

    private static void testImportText() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        /*
         * split up path and file name
         */
        String path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";
        String filename = "sample.log.odt";
        String odtFileName = path + filename;
        InFileName fileName = new InFileName(path, filename);


        /*
         * open ODT and transform to text stream
         *
         */
        InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(fileName);

        /*
         *
         * Parse Text stream and store in elements
         */
        PseudoElements elements = new PseudoElements();
        elements.setFileName(fileName);

        LoadPseudoElements importer = new LoadPseudoElements();

        importer.Import(elements, (InputStreamReader) odtReader);

        /*
         *
         * Output
         */
        System.out.println("--------------------------------------");
        if (elements.size() <= 0) {
            System.out.println(" Empty Elements ");
        }
        for (int i = 0; i < elements.size(); i++) {
            PseudoElement elm = elements.getElement(i);
            System.out.println("--------------------------------------");
            for (int k = 0; k < elm.getAttributes().size(); k++) {
                Attribute att = elm.getAttributes().getAttribute(k);
                System.out.println("name: " + att.getName() + " value: " + att.getValue());
            }
            System.out.println("type: " + elm.getType());
        }
        System.out.println("--------------------------------------");
        System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());

        odtReader.close();

        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");
    }
}
