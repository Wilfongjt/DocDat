/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.convert.ConvertPseudoElementsToChartXML;
import docdat.io.names.InFileName;
import docdat.io.names.OutFileName;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.load.LoadPseudoElements;
import docdat.io.inputstreams.ChartByteArrayInputStream;
import docdat.transforms.TransformIS_ChartToSVG;
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
public class ODT04_02_03ODTToFlowChartSVG_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("ODT04_02_03ODTToFlowChartSVG_Test 1");

        /// load elements


        testImportText();
        //testBufferedReader(elems);
    }

    private static void testImportText() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        String path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";
        String filename = "sample.flowchart.odt";
        String odtFileName = path + filename;
        InFileName fileName = new InFileName(path, filename);//input

        OutFileName outFileName = new OutFileName(fileName, "svg"); // output

        /*
         *
         * ODT read
         *
         */
        // ODTTextInputStreamReader odtReader = new ODTTextInputStreamReader(new ODTContentInputStreamReader(odtFileName));
        InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(fileName);


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
        ConvertPseudoElementsToChartXML charter = new ConvertPseudoElementsToChartXML();
        ChartByteArrayInputStream chartIS = charter.convertToInputStream(elements);


        // Transform to SVG
        //the transform xsl is not working giving NaN for the x values on the

        TransformIS_ChartToSVG transSVG = new TransformIS_ChartToSVG((InputStream) chartIS);
        InputStream svgIS = transSVG.transformToInputStream();


        /*
         *
         * out puts
         */
        File mfFile = new File(outFileName.getPathAndFileName());
        if (mfFile.exists()) {
            mfFile.delete();
        }
        // output
        BufferedReader bf = new BufferedReader(new InputStreamReader(svgIS));
        BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile));

        System.out.println("file = " + outFileName.getPathAndFileName());
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
