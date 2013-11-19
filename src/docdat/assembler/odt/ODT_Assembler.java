/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.assembler.odt;

import docdat.convert.ConvertPseudoElementsToXML;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.names.InFileName;
import docdat.io.names.InputFileNames;
import docdat.io.names.OutFileName;
import docdat.project.Project;
import docdat.transforms.TransformInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class ODT_Assembler {

    public ODT_Assembler(String project_path) {

        setProject_Path(project_path);

    }
    String Project_Path = "";

    public String getProject_Path() {
        return Project_Path;
    }

    public void setProject_Path(String Project_Path) {
        this.Project_Path = Project_Path;
    }

    private String outFileName ="base-merge.xml";

    public String getOutFileName() {
        return outFileName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }
    
    public void assemble() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        // the files can be proxys to other files in other projects
        //System.out.println(  "assemble 1 getProject_Path():  " +  getProject_Path()  );
        InputFileNames inputFiles = new InputFileNames(getProject_Path());
         System.out.println(  "assemble 2 " );
        
        Project project = new Project(inputFiles);
        
         System.out.println(  "assemble 3"  );

        /*
         *
         * remove Merge.xml fileODT read
         *
         */




        /*
         *
         * ODT read
         *
         */
        for (int i = 0; i < project.size(); i++) {
            System.out.println("-------------------------------------------------");
            InFileName ipn = project.getInFileNames().getFileName(i);
            System.out.println(" path and filename : " + ipn.getPathAndFileName());
            System.out.println(" source : " + ipn.getSourceName());
            System.out.println(" type: " + ipn.getType());

            System.out.println(" elements: " + project.getElements(i).size());




            /*
             * Covert PseudoElements to XML
             *
             */

            ConvertPseudoElementsToXML exporter = new ConvertPseudoElementsToXML();
            InputStream exportIS = exporter.convertToInputStream(project.getElements(i));

            BufferedReader bf = new BufferedReader(new InputStreamReader(exportIS));



            /*
             * Output
             */
            OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output

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

            //odtReader.close();
            bf.close();
            mf.close();
        }

        /*
         * Merge definitions 
         */
        /*
         * Translate and Output
         */
        if (project.size() > 0) {

            //String outfilename = project.getElements(0).getFileName().getProjectName() + ".merging.xml";
            //InFileName fileName = new InFileName(project_path, outfilename);
            //OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(0), "merge", "xml"); // output
            //File mfFile = new File(outFileName.getPathAndFileName());
            File mfFile = new File(getProject_Path() + "\\merge\\", getOutFileName());
            mfFile.mkdirs();
            if (mfFile.exists()) {
                mfFile.delete();
            }

            InputStream source_XMLInputStream = getDummyXMLInputStream();
            InputStream transform_XSLInputStream = getXSLInputStream(project);
            OutputStream XML_OutputOutputStream = new FileOutputStream(mfFile);
            TransformInputStream TIS = new TransformInputStream(
                    source_XMLInputStream,
                    transform_XSLInputStream,
                    XML_OutputOutputStream);

            TIS.transformToInputStream();// returns null when output stream is set

            source_XMLInputStream.close();
            transform_XSLInputStream.close();
            XML_OutputOutputStream.close();
        } else {
            System.out.println("  No files found in [" + getProject_Path() + "]");
        }
        System.out.println("out: " + "file:/" + getProject_Path().replace("\\", "/") + "merge/" + getOutFileName());
        System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        System.out.println("testOdtReader out");
    }

    private static InputStream getDummyXMLInputStream() {
        String rc = "";
        rc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
        rc += "  <dummy></dummy>";

        return new ByteArrayInputStream(rc.getBytes());
    }

    private static InputStream getXSLInputStream(Project project) {
        String rc = "";
        rc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";



        rc += "<xsl:stylesheet " + "\n";
        rc += "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" " + "\n";
        rc += "xmlns:exsl=\"http://exslt.org/common\"" + "\n";
        rc += "extension-element-prefixes=\"exsl\"" + "\n";
        rc += "version=\"1.0\">" + "\n";
        rc += "    <xsl:output method=\"xml\" indent=\"yes\" cdata-section-elements=\"description\"/>" + "\n";
        rc += "    <xsl:template match=\"/\">" + "\n";

        rc += "       <layout>" + "\n";
        for (int i = 0; i < project.size(); i++) {
            OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
            rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/setting/Settings\"/>" + "\n";
        }


        for (int i = 0; i < project.size(); i++) {
            OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
            rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/layout/Screen\"/>" + "\n";
        }

        // rc += "           <xsl:copy-of select=\"document('C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\xml\\SHPO-GIS-Database.terms-of-use.layout.xml',/)/layout/Screen\"/>" + "\n";


        /*
         * rc += "           <Database>" + "\n";
        for (int i = 0; i < project.size(); i++) {
        OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
        rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/database/Database/Table\"/>" + "\n";
        }
        rc += "           </Database>" + "\n";
         */
        rc += "           <Databases>" + "\n";
        
        for (int i = 0; i < project.size(); i++) {
            OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
            rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/database/Database\"/>" + "\n";
        }
        rc += "           </Databases>" + "\n";



        rc += "           <Requirements>" + "\n";

        rc += "           <Indirect-Requirements>" + "\n";
        for (int i = 0; i < project.size(); i++) {
            OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output

            rc += "<xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//requirement[@name='Indirect-Requirements']/*" + "\"/>" + "\n";
        }
        rc += "           </Indirect-Requirements>" + "\n";


        for (int i = 0; i < project.size(); i++) {

            // rc += "           <Source>" + project.getInFileNames().getFileName(i) .getFileName()+ "</Source>" + "\n";

            OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output



            rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//*[name()='BR' or name()='Non-Functional-Requirement' or name()='Functional-Requirement'  or name()='Assumption' or name()='Constraint'  ]\"/>" + "\n";

        }
        /*
        for (int i = 0; i < project.size(); i++) {
        
        // rc += "           <Source>" + project.getInFileNames().getFileName(i) .getFileName()+ "</Source>" + "\n";
        
        OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
        rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//BR\"/>" + "\n";
        
        }
        
        for (int i = 0; i < project.size(); i++) {
        // rc += "           <Source>" + project.getInFileNames().getFileName(i) + "</Source>" + "\n";
        OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
        rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//Non-Functional-Requirement\"/>" + "\n";
        }
        for (int i = 0; i < project.size(); i++) {
        //  rc += "           <Source>" + project.getInFileNames().getFileName(i) + "</Source>" + "\n";
        OutFileName outFileName = new OutFileName(project.getInFileNames().getFileName(i), "xml"); // output
        rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//Functional-Requirement\"/>" + "\n";
        }
         */

        rc += "           </Requirements>" + "\n";

        rc += "        </layout>" + "\n";
        rc += "    </xsl:template>" + "\n";

        //rc += "    <xsl:variable name=\"setting\">" + "\n";
        //  rc += "        <xsl:copy-of select=\"document('C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\xml\\SHPO-GIS-Database.setting.xml',/)/setting[@id='0']\"/>" + "\n";

        //rc += "    </xsl:variable>" + "\n";

        rc += "</xsl:stylesheet>" + "\n";

        System.out.println(rc);
        return new ByteArrayInputStream(rc.getBytes());

    }
}
