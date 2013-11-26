/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.assembler.bmml;

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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class BMML_Assembler {

    public BMML_Assembler(String project_path) {

        setProject_Path(project_path);
         setAssembler_helper(new BMML_Assembler_Helper( project_path  ) ) ;

    }
    String Project_Path = "";

    public String getProject_Path() {
        return Project_Path;
    }

    public void setProject_Path(String Project_Path) {
        this.Project_Path = Project_Path;
    }

    public void assemble() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        InputFileNames inputFiles = new InputFileNames(getProject_Path());
        //String outfilename = getProject_Path() + "\\merge\\" + "merge.xml";

        String outfilepath = getProject_Path() + "\\temp\\";

        /*
         * Output folder for intermediate xml 
         */

        File mfFile = new File(outfilepath);
        mfFile.mkdirs();
        /*
        if (mfFile.exists()) {
        mfFile.delete();
        }
         */
        // add xml header to all project files in project folder

        for (int i = 0; i < inputFiles.size(); i++) {
            // open the bmml file 
            BufferedReader bf = new BufferedReader(new FileReader(inputFiles.getFileName(i).getPathAndFileName()));

            mfFile = new File(outfilepath + inputFiles.getFileName(i).getFileName() + ".xml");
            if (mfFile.exists()) {
                mfFile.delete();
            }
            BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile)); // prepare output file

            String rc = "";
            rc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
            //rc = "<project>" + "\n";
            //System.out.println(rc);
            mf.write(rc);
            String line = "";
            while ((line = bf.readLine()) != null) {
                mf.write(line);
            }
            //rc = "</project>" + "\n";
            mf.close();
            bf.close();
        }


        //File mfFile = new File(infilepathassets);
        outfilepath += "assets\\";
        File mfAssetFile = new File(outfilepath);
        mfAssetFile.mkdirs();
        //inputFiles = null;
        InputFileNames inputAssetFiles = new InputFileNames(getProject_Path() + "assets\\");
        for (int i = 0; i < inputAssetFiles.size(); i++) {
            // open the bmml file 
            BufferedReader bf = new BufferedReader(new FileReader(inputAssetFiles.getFileName(i).getPathAndFileName()));

            mfFile = new File(outfilepath + inputAssetFiles.getFileName(i).getFileName() + ".xml");
            if (mfFile.exists()) {
                mfFile.delete();
            }
            BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile)); // prepare output file

            String rc = "";
            rc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
            //rc = "<project>" + "\n";
            //System.out.println(rc);
            mf.write(rc);
            String line = "";
            while ((line = bf.readLine()) != null) {
                mf.write(line);
            }
            //rc = "</project>" + "\n";
            mf.close();
            bf.close();
        }

        /*
         * Merge files
         * 
         */

        inputFiles = null;
        inputFiles = new InputFileNames(getProject_Path() + "\\temp\\");
        inputAssetFiles = null;
        inputAssetFiles = new InputFileNames(getProject_Path() + "\\temp\\assets\\");

        if (inputFiles.size() > 0) {

            File mgFile = new File(getProject_Path() + "\\merge\\", "merge.xml");
            mgFile.mkdirs();
            if (mgFile.exists()) {
                mgFile.delete();
            }

            InputStream source_XMLInputStream = getDummyXMLInputStream();
            InputStream transform_XSLInputStream = getXSLInputStream( getAssembler_helper()  ,  inputFiles, inputAssetFiles);
            OutputStream XML_OutputOutputStream = new FileOutputStream(mgFile);
            TransformInputStream TIS = new TransformInputStream(
                    source_XMLInputStream,
                    transform_XSLInputStream,
                    XML_OutputOutputStream);

            TIS.transformToInputStream();// returns null when output stream is set

            source_XMLInputStream.close();
            transform_XSLInputStream.close();
            XML_OutputOutputStream.close();
        } else {
            //System.out.println("  No files found in [" + getProject_Path() + "]");
        }
        /*
         *
         *  prefix bmml with xml bmml
         *
         */
        /*
        for (int i = 0; i < inputFiles.size(); i++) {
        //System.out.println("-------------------------------------------------");
        InFileName ipn = inputFiles.getFileName(i);
        //System.out.println(" path and filename : " + ipn.getPathAndFileName());
        //System.out.println(" source : " + ipn.getSourceName());
        //System.out.println(" type: " + ipn.getType());
        BMML_Assembler_Helper assembler_helper = new BMML_Assembler_Helper(getProject_Path());
        
        // open the bmml file 
        BufferedReader bf = new BufferedReader(new FileReader(inputFiles.getFileName(i).getPathAndFileName()));
        
        //System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());
        
        String line = "";
        while ((line = bf.readLine()) != null) {
        //System.out.println(line);
        // add src name 
        if (assembler_helper.isMockup(line)) {
        line = line.replace("<mockup", "<mockup src=\"" + ipn.getPathAndFileName() + "\"");
        line = line.replace("<mockup", " <mockup tblname=\"" + ipn.getFileNameWithNoExtension().replace(".bmml", "") + "\"");
        line = line.replace("<mockup", "<mockup prjname=\"" + assembler_helper.getProjectName() + "\"");
        }
        
        if (assembler_helper.isAsset(line)) {
        line = line.replace("<src", "<src control=\"" + assembler_helper.getControName(line) + "\"");
        line = line.replace("<src", "<src file=\"" + getProject_Path() + assembler_helper.getControlFileName(line) + "\"");
        }
        
        if (assembler_helper.isSymbol(line)) {
        line = line.replace("<src", "<src symbol=\"" + assembler_helper.getControName(line) + "\"");
        line = line.replace("<src", "<src file=\"" + getProject_Path() + assembler_helper.getControlFileName(line) + "\"");
        
        }
        mf.write(line);
        }
        
        
        //System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());
        
        //odtReader.close();
        bf.close();
        
        }
        
        rc = "</mockups>" + "\n";
        //System.out.println(rc);
        mf.write(rc);
        
        
        mf.close();
        
         */
        //System.out.println("out: " + "file:/" + getProject_Path().replace("\\", "/") + "merge/" + "merge.xml");
        //System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
        //System.out.println("testOdtReader out");
    }

    private static InputStream getDummyXMLInputStream() {
        String rc = "";
        rc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
        rc += "  <dummy></dummy>";

        return new ByteArrayInputStream(rc.getBytes());
    }
  
    
    private BMML_Assembler_Helper assembler_helper=null;

    public BMML_Assembler_Helper getAssembler_helper() {
        return assembler_helper;
    }

    public void setAssembler_helper(BMML_Assembler_Helper assembler_helper) {
        this.assembler_helper = assembler_helper;
    }
  
  
    private static InputStream getXSLInputStream(BMML_Assembler_Helper assemb_helper, InputFileNames inFileNames, InputFileNames assetFileNames) {
        
       
        String rc = "";
        rc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";



        rc += "<xsl:stylesheet " + "\n";
        rc += "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" " + "\n";
        rc += "xmlns:exsl=\"http://exslt.org/common\"" + "\n";
        rc += "extension-element-prefixes=\"exsl\"" + "\n";
        rc += "version=\"1.0\">" + "\n";
        rc += "    <xsl:output method=\"xml\" indent=\"yes\" cdata-section-elements=\"description\"/>" + "\n";
        rc += "    <xsl:template match=\"/\">" + "\n";

        rc += "       <project name=\""  +  assemb_helper.getProjectName()  +  "\" ";
        rc += "       src=\""  +  assemb_helper.getProjectPath()  +  "\" "; 
        rc += ">" + "\n";
        rc += "           <mockups>" + "\n";
        for (int i = 0; i < inFileNames.size(); i++) {
            rc += "           <source  src=\"" +  inFileNames.getFileName(i).getFileName().replace(".xml"  ,"")   +"\"" ;
            rc += " tblname=\"" +  inFileNames.getFileName(i).getFileName().replace(".bmml.xml"  ,"")    +  "\">" +"\n"; 
            rc += "             <xsl:copy-of select=\"document('" + inFileNames.getFileName(i).getPathAndFileName() + "',/)/mockup\"/>" + "\n";
            rc += "           </source>" + "\n";
        }
        rc += "           </mockups>" + "\n";
        rc += "           <assets>" + "\n";

        for (int i = 0; i < assetFileNames.size(); i++) {

            if (!assetFileNames.getFileName(i).getFileName().contains("symbols")) {
                rc += "           <xsl:copy-of select=\"document('" + assetFileNames.getFileName(i).getPathAndFileName() + "',/)/mockup\"/>" + "\n";
            }
        }
        rc += "           </assets>" + "\n";
        rc += "           <symbols>" + "\n";

        for (int i = 0; i < assetFileNames.size(); i++) {
            if (assetFileNames.getFileName(i).getFileName().contains("symbols")) {
                rc += "           <xsl:copy-of select=\"document('" + assetFileNames.getFileName(i).getPathAndFileName() + "',/)/mockup\"/>" + "\n";
            }
        }
        rc += "           </symbols>" + "\n";
        rc += "       </project>" + "\n";
        rc += "    </xsl:template>" + "\n";
        rc += "</xsl:stylesheet>" + "\n";

        //System.out.println(rc);

        return new ByteArrayInputStream(rc.getBytes());

    }
    /*   private static InputStream getXSLInputStream(Project inputFiles) {
    
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
    for (int i = 0; i < inputFiles.size(); i++) {
    OutFileName outFileName = new OutFileName(inputFiles.getFileName(i), "xml"); // output
    rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/setting/Settings\"/>" + "\n";
    }
    
    
    for (int i = 0; i < inputFiles.size(); i++) {
    OutFileName outFileName = new OutFileName(inputFiles.getFileName(i), "xml"); // output
    rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/layout/Screen\"/>" + "\n";
    }
    
    // rc += "           <xsl:copy-of select=\"document('C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\xml\\SHPO-GIS-Database.terms-of-use.layout.xml',/)/layout/Screen\"/>" + "\n";
    
    
    rc += "           <Databases>" + "\n";
    
    for (int i = 0; i < inputFiles.size(); i++) {
    OutFileName outFileName = new OutFileName(inputFiles.getFileName(i), "xml"); // output
    rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)/database/Database\"/>" + "\n";
    }
    rc += "           </Databases>" + "\n";
    
    
    
    rc += "           <Requirements>" + "\n";
    
    rc += "           <Indirect-Requirements>" + "\n";
    for (int i = 0; i < inputFiles.size(); i++) {
    OutFileName outFileName = new OutFileName(inputFiles.getFileName(i), "xml"); // output
    
    rc += "<xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//requirement[@name='Indirect-Requirements']/*" + "\"/>" + "\n";
    }
    rc += "           </Indirect-Requirements>" + "\n";
    
    
    for (int i = 0; i < inputFiles.size(); i++) {
    
    // rc += "           <Source>" + inputFiles.getFileName(i) .getFileName()+ "</Source>" + "\n";
    
    OutFileName outFileName = new OutFileName(inputFiles.getFileName(i), "xml"); // output
    
    
    
    rc += "           <xsl:copy-of select=\"document('" + outFileName.getPathAndFileName() + "',/)//*[name()='BR' or name()='Non-Functional-Requirement' or name()='Functional-Requirement'  or name()='Assumption' or name()='Constraint'  ]\"/>" + "\n";
    
    }
    
    
    rc += "           </Requirements>" + "\n";
    
    rc += "        </layout>" + "\n";
    rc += "    </xsl:template>" + "\n";
    
    //rc += "    <xsl:variable name=\"setting\">" + "\n";
    //  rc += "        <xsl:copy-of select=\"document('C:\\00-Code\\01-dev\\DocDat\\test\\shpo_gis_database\\xml\\SHPO-GIS-Database.setting.xml',/)/setting[@id='0']\"/>" + "\n";
    
    //rc += "    </xsl:variable>" + "\n";
    
    rc += "</xsl:stylesheet>" + "\n";
    
    //System.out.println(rc);
    
    return new ByteArrayInputStream(rc.getBytes());
    
    }         
     */
}
