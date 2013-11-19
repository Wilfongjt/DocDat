/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.convert.ConvertPseudoElementsToChartXML;
import docdat.io.names.InFileName;
import docdat.io.names.InputFileNames;
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
public class ODT04_02_04ODTToSVG_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("ODT04_02_03ODTToFlowChartSVG_Test 1");

        /// load elements


        testImportText();
        //testBufferedReader(elems);
    }

    private static void testImportText() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        /*
         * load list of ODT files from a given folder
         * process each file to a chart, orgchart or flowchart
         *
         */
        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        String project_path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";

        // the path's last folder is also the project name
        InputFileNames inputFiles = new InputFileNames(project_path);
        for (int i = 0; i < inputFiles.size(); i++) {
            System.out.println("filename: " + inputFiles.getFileName(i).getPathAndFileName());
            try {
                writeSVG(inputFiles.getFileName(i));
            } catch (Exception e) {
                System.out.println("e " + e.toString());
            }
        }

        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");
    }

    public static void writeSVG(InFileName inFileName) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        OutFileName outFileName = new OutFileName(inFileName, "svg"); // output

        /*
         *
         * ODT read
         *
         */
        //  ODTTextInputStreamReader odtReader = new ODTTextInputStreamReader(new ODTContentInputStreamReader(inFileName.getPathAndFileName()));
        InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(inFileName);


        /*
         * Import
         */
        PseudoElements elements = new PseudoElements();
        elements.setFileName(inFileName);

        LoadPseudoElements importer = new LoadPseudoElements();
        importer.Import(elements, (InputStreamReader) odtReader);


        /*
         * Covert PseudoElements to XML
         *
         */
        ConvertPseudoElementsToChartXML charter = new ConvertPseudoElementsToChartXML();
        ChartByteArrayInputStream chartIS = charter.convertToInputStream(elements);


        /*
         * Transform to SVG
         *
         */

        TransformIS_ChartToSVG transSVG = new TransformIS_ChartToSVG((InputStream) chartIS);
        InputStream svgIS = transSVG.transformToInputStream();

        /*
         *
         * out puts
         *
         */
        /*
         *
         * kill existing output file
         */
        File mfFile = new File(outFileName.getPathAndFileName());
        if (mfFile.exists()) {
            mfFile.delete();
        }

        /*
         * 
         * output to disk
         *
         */

        BufferedReader bf = new BufferedReader(new InputStreamReader(svgIS));
        BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile));

        System.out.println("file = " + outFileName.getPathAndFileName());

        String line = "";
        while ((line = bf.readLine()) != null) {

            mf.write(line);
        }

        odtReader.close();
        bf.close();
        mf.close();
    }
}
