/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.convert.ConvertPseudoElementsToXML;
import docdat.io.names.InFileName;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.io.names.OutFileName;
import docdat.load.LoadPseudoElements;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class ODT04_01PseudoElementConvertToXML_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("PseudoElementsReaderTest 1");

        /// load elements


        testImportText();
        //testBufferedReader(elems);
    }

    private static void testImportText() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        String path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";
        String filename = "HIT_Implementation_Manager.02.flashcards.odt";
        String outpath = "C:\\00-Code\\01-dev\\DesignChart\\test\\samples\\";
        //String outfilename = "SHPO-GIS-Database.registration.layout.xml";

        filename = "sample.flowchart.odt";
        filename = "sample.layout.odt";
        path = "C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\";
        filename = "SHPO-GIS-Database.registration.layout.odt";
        String odtFileName = path + filename;
        InFileName fileName = new InFileName(path, filename);
        OutFileName outFileName = new OutFileName(fileName, "xml"); // output
        outFileName.setPath(outpath);


        /*
         *
         * ODT read
         *
         */
        //ODTTextInputStreamReader odtReader = new ODTTextInputStreamReader(new ODTContentInputStreamReader(odtFileName));
        InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(fileName);
        System.out.println("read " + odtReader.toString());
        //BufferedReader bf = new BufferedReader(odtReader);
        System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());


        /*
         * Import
         */
        PseudoElements elements = new PseudoElements();
        elements.setFileName(fileName);

        LoadPseudoElements importer = new LoadPseudoElements();
        importer.Import(elements, (InputStreamReader) odtReader);


        /*
         * Covert PseudoElements to XML
         *
         */
        ConvertPseudoElementsToXML exporter = new ConvertPseudoElementsToXML();
        InputStream exportIS = exporter.convertToInputStream(elements);

        BufferedReader bf = new BufferedReader(new InputStreamReader(exportIS));

        /*
         * Output
         */
        File mfFile = new File(outFileName.getPathAndFileName());
        if (mfFile.exists()) {
            mfFile.delete();
        }
        BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile)); // prepare output file

        System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());
        String line = "";
        while ((line = bf.readLine()) != null) {
            System.out.println(line);
            mf.write(line);
        }


        System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());

        odtReader.close();
        bf.close();
        mf.close();

        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");
    }
}
