/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.calculate.Coordinates_Hierarchical_Horizontal;
import docdat.calculate.Dimensions;
import docdat.convert.ConvertPseudoElementsToChartXML;
import docdat.io.names.InFileName;
import docdat.io.names.OutFileName;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.load.LoadPseudoElements;
import docdat.io.inputstreams.ChartByteArrayInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class ODT04_02PseudoElementConvertToChartXML_Test {

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
        filename = "sample.flowchart.odt";
        
        String outpath = "C:\\00-Code\\01-dev\\DesignChart\\test\\samples\\";
        
        path = "C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\";
        filename = "SHPO-GIS-Database.registration.layout.odt";
        filename ="SHPO-GIS-Database.registration.database.pluck.odt";

        String odtFileName = path + filename;
        InFileName fileName = new InFileName(path, filename);
        OutFileName outFileName = new OutFileName(fileName, "xml"); // output
        /*
         *
         * ODT read
         *
         */
        //ODTTextInputStreamReader odtReader = new ODTTextInputStreamReader(new ODTContentInputStreamReader(odtFileName));
        InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(fileName);


        /*
         * Import
         */
        PseudoElements elements = new PseudoElements();  // elements
        elements.setFileName(fileName);

        LoadPseudoElements importer = new LoadPseudoElements();
        importer.Import(elements, (InputStreamReader) odtReader);
        
        Dimensions dim = new Dimensions(elements);
        dim.calculate();
        
        Coordinates_Hierarchical_Horizontal coord =new Coordinates_Hierarchical_Horizontal(elements) ;
        coord.calculate();
        /*
         * Covert PseudoElements to XML
         *
         */

        ConvertPseudoElementsToChartXML charter = new ConvertPseudoElementsToChartXML();

        ChartByteArrayInputStream chartIS = charter.convertToInputStream(elements);

        System.out.println(" chartIS " + chartIS);


        /*
         * Output
         */
        File mfFile = new File(outFileName.getPathAndFileName());
        if (mfFile.exists()) {
            mfFile.delete();
        }
        BufferedReader bf = new BufferedReader(new InputStreamReader(chartIS));  // prepart chart for writing
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
