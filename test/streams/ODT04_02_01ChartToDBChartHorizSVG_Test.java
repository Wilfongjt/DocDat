/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.convert.ConvertPseudoElementsToChartXML;
import docdat.io.names.OutFileName;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.io.inputstreams.ChartByteArrayInputStream;
import docdat.io.names.InputFileNames;
import docdat.project.Project;
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
public class ODT04_02_01ChartToDBChartHorizSVG_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("ODT04_02_01ChartToOrgChartSVG_Test 1");

        /// load elements


        testImportText();
        //testBufferedReader(elems);
    }

    private static void testImportText() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        String project_path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";
        String filename = "HIT_Implementation_Manager.02.horizontal.flashcards.odt";
        String outpath = "C:\\00-Code\\01-dev\\DesignChart\\test\\samples\\";

        project_path = "C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\";
        // filename = "SHPO-GIS-Database.registration.layout.odt";

        //String odtFileName = path + filename;
        //InFileName fileName = new InFileName(path, filename);
        //System.out.println("path 1 : " + fileName.getPathAndFileName());
        // OutFileName outFileName = new OutFileName(fileName, "svg");
        //outFileName.setPath(outpath);
        // System.out.println("path 1 : " + outFileName.getPathAndFileName());





        /*
         *
         * ODT read 
         *
         */
        //ODTTextInputStreamReader odtReader = new ODTTextInputStreamReader(new ODTContentInputStreamReader(odtFileName));
       /* InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(fileName);
        System.out.println("read " + odtReader.toString());
         * 
         */
        //BufferedReader bf = new BufferedReader(odtReader);
        System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());




        /*
         * Import
         */

        InputFileNames inputFiles = new InputFileNames(project_path);
        Project project = new Project(inputFiles);

        
        inputFiles.showFileNames();
        
        
        /*PseudoElements elements = new PseudoElements();
        elements.setFileName(fileName);
        
        LoadPseudoElements importer = new LoadPseudoElements();
        importer.Import(elements, (InputStreamReader) odtReader);
         */

        /*
         * Covert PseudoElements to XML
         *
         */

        PseudoElements elements = project.getElements(1);
        ConvertPseudoElementsToChartXML charter = new ConvertPseudoElementsToChartXML();
        ChartByteArrayInputStream chartIS = charter.convertToInputStream(elements);


        // Transform to SVG
        //the transform xsl is not working giving NaN for the x values on the

        TransformIS_ChartToSVG transSVG = new TransformIS_ChartToSVG((InputStream) chartIS);
        InputStream svgIS = transSVG.transformToInputStream();

        OutFileName outFileName = new OutFileName(elements.getFileName(), "svg");

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

        //  odtReader.close();
        svgIS.close();
        chartIS.close();

        bf.close();
        mf.close();



        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");
    }
}
