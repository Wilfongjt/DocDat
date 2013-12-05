/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.assembler.text;

import docdat.convert.ConvertPseudoElementsToXML;
import docdat.id.MasterElementsList;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.names.InputFileNames;
import docdat.load.LoadPseudoElements;
import docdat.transforms.TransformInputStream;
import docdat.utils.Constants;
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
import java.util.Date;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class TEXT_Assembler {

  public TEXT_Assembler(String project_path) {

    setProject_Path(project_path);
    setAssembler_helper(new TEXT_Assembler_Helper(project_path));

  }

  public TEXT_Assembler(String project_path, MasterElementsList master) {

    setProject_Path(project_path);
    setAssembler_helper(new TEXT_Assembler_Helper(project_path));

    setMasterElements(master);
  }
  

  /*
   * List of all PseudoElements 
   * this is used when we need to save all teh elements for post processing
   */
  private MasterElementsList masterElements = new MasterElementsList();

  public MasterElementsList getMasterElements() {
    return masterElements;
  }

  public void setMasterElements(MasterElementsList masterElements) {
    this.masterElements = masterElements;
  }
  String Project_Path = "";

  public String getProject_Path() {
    return Project_Path;
  }

  public void setProject_Path(String Project_Path) {
    this.Project_Path = Project_Path;
  }

  public void assemble() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

    // load files
    InputFileNames inputFiles = new InputFileNames(getProject_Path());
    inputFiles.showFileNames();


    String outfilepath = getProject_Path() + "\\temp\\";
    // OutFileName outFileName = new OutFileName(  fileName  , "xml"); 
        /*
     * Output folder for intermediate xml 
     */

    File mfFile = new File(outfilepath);

    mfFile.mkdirs();

    for (int d = 0; d < mfFile.listFiles().length; d++) {
      //System.out.println("Delete temp file: " + mfFile.listFiles()[d].getName());
      mfFile.listFiles()[d].delete();
    }

    // Load the text file 
    for (int i = 0; i < inputFiles.size(); i++) {

      File inFile = new File(inputFiles.getFileName(i).getPathAndFileName());

      // set up pseudo elements 
      PseudoElements elems = new PseudoElements(inputFiles.getFileName(i));

      // load pseudo elemts
      LoadPseudoElements loader = new LoadPseudoElements(elems);
      elems = loader.Import();
      // elems.showList();

      /*
       * only save the elements when the master has been added
       * 
       */
      getMasterElements().add(elems);

    }
    //System.out.println(" getMasterElements().postProcess() 1 ");
    getMasterElements().postProcess(); // fix the cross file no references
    //System.out.println(" getMasterElements().postProcess() 2 think about multiple lines and line returns and lines that do not start with a nuember");
    // export intermediate xml files
    for (int i = 0; i < inputFiles.size(); i++) {

      PseudoElements elems = getMasterElements().getElements(i);

      // prepare input files to be read
      ConvertPseudoElementsToXML exporter = new ConvertPseudoElementsToXML();
      InputStream exportIS = exporter.convertToInputStream(elems);


      BufferedReader bf = new BufferedReader(new InputStreamReader(exportIS));

      String outFileName = outfilepath + inputFiles.getFileName(i).getFileName() + ".xml";
      File oFile = new File(outFileName);
      if (oFile.exists()) {
        oFile.delete();
      }

      BufferedWriter mf = new BufferedWriter(new FileWriter(oFile)); // prepare output file

      //System.out.println("mem 2=" + Runtime.getRuntime().freeMemory());
      String line = "";
      while ((line = bf.readLine()) != null) {
        //System.out.println(line);
        mf.write(line);
      }
      bf.close();
      mf.close();

    }

    /*
     * Merge files
     * 
     */

    //inputFiles = null;
    InputFileNames Temp_inputFiles = new InputFileNames(getProject_Path() + "\\temp\\");

    if (Temp_inputFiles.size() > 0) {

      File mgFile = new File(getProject_Path() + "\\merge\\", "mindjet-merge.xml");
      mgFile.mkdirs();
      if (mgFile.exists()) {
        mgFile.delete();
      }

      InputStream source_XMLInputStream = getDummyXMLInputStream();
      InputStream transform_XSLInputStream = getXSLInputStream(getAssembler_helper(), Temp_inputFiles, inputFiles);
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


    //System.out.println("out: " + "file:/" + getProject_Path().replace("\\", "/") + "merge/" + "mindjet-merge.xml");
    //System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
    //System.out.println("testOdtReader out");
  }

  private static InputStream getDummyXMLInputStream() {
    String rc = "";
    rc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
    rc += "  <dummy></dummy>";

    return new ByteArrayInputStream(rc.getBytes());
  }
  private TEXT_Assembler_Helper assembler_helper = null;

  public TEXT_Assembler_Helper getAssembler_helper() {
    return assembler_helper;
  }

  public void setAssembler_helper(TEXT_Assembler_Helper assembler_helper) {
    this.assembler_helper = assembler_helper;
  }

  private static InputStream getXSLInputStream(TEXT_Assembler_Helper assemb_helper, InputFileNames inFileNames, InputFileNames assetFileNames) {


    String rc = "";
    rc += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";



    rc += "<xsl:stylesheet " + "\n";
    rc += "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" " + "\n";
    rc += "xmlns:exsl=\"http://exslt.org/common\"" + "\n";
    rc += "extension-element-prefixes=\"exsl\"" + "\n";
    rc += "version=\"1.0\">" + "\n";
    rc += "    <xsl:output method=\"xml\" indent=\"yes\" cdata-section-elements=\"description\"/>" + "\n";
    rc += "    <xsl:template match=\"/\">" + "\n";

    rc += "       <project name=\"" + assemb_helper.getProjectName() + "\" ";
    rc += "       src=\"" + assemb_helper.getProjectPath() + "\" ";
    rc += ">" + "\n";

    rc += "           <Sources imported=\"" + new Date() + "\">" + "\n";
    //System.out.println("  inFileNames: " + inFileNames.size() + "  assetFileNames: " + assetFileNames.size());
    for (int i = 0; i < inFileNames.size(); i++) {
      // only allow the xml files to be processed
      if (inFileNames.getFileName(i).getExtension().equalsIgnoreCase(Constants.Extentions.XML)) {
        ////System.out.println(" assetFileNames.getFileName(i) : " +  assetFileNames.getFileName(i)  );
        ////System.out.println(" assetFileNames.getFileName(i).getFileNameWithNoExtension() : " +   );
        ////System.out.println(" assetFileNames.getFileName(i).getPathAndFileName() : " +  assetFileNames.getFileName(i).getPathAndFileName()   );
        String fn = "";
        String lmod = "";
        try {
          ////System.out.println(" assetFileNames : [" + i +"] " + assetFileNames.getFileName(i).getProjectName()  );
          String prjName = inFileNames.getFileName(i).getProjectName();

          for (int z = 0; z < assetFileNames.size(); z++) {
            String tmpName = assetFileNames.getFileName(z).getProjectName();
            if (tmpName.equalsIgnoreCase(prjName)) {
              fn = tmpName;
              lmod = new Date(new File(assetFileNames.getFileName(z).getPathAndFileName()).lastModified()).toString();
              break;
            }
          }
        } catch (Exception e) {
          //System.out.println("error here");
          e.printStackTrace();
        }




        rc += "             <source id=\"" + fn + "\" last-updated=\"" + lmod + "\">" + inFileNames.getFileName(i).getPathAndFileName() + "</source>" + "\n";
      }
    }
    rc += "           </Sources>" + "\n";
    rc += "           <Glossary>" + "\n";

    for (int i = 0; i < inFileNames.size(); i++) {
      if (inFileNames.getFileName(i).getExtension().equalsIgnoreCase(Constants.Extentions.XML)) {
        rc += "             <xsl:copy-of select=\"document('" + inFileNames.getFileName(i).getPathAndFileName() + "',/)/definition/definition\"/>" + "\n";
      }
    }
    rc += "           </Glossary>" + "\n";
    rc += "           <Agendas>" + "\n";

    for (int i = 0; i < inFileNames.size(); i++) {
      if (inFileNames.getFileName(i).getExtension().equalsIgnoreCase(Constants.Extentions.XML)) {
        rc += "             <xsl:copy-of select=\"document('" + inFileNames.getFileName(i).getPathAndFileName() + "',/)/agenda\"/>" + "\n";
      }
    }
    rc += "           </Agendas>" + "\n";
    
    rc += "           <Requirements>" + "\n";

    for (int i = 0; i < inFileNames.size(); i++) {
      if (inFileNames.getFileName(i).getExtension().equalsIgnoreCase(Constants.Extentions.XML)) {
        rc += "             <xsl:copy-of select=\"document('" + inFileNames.getFileName(i).getPathAndFileName() + "',/)/requirement\"/>" + "\n";
      }
    }
    rc += "           </Requirements>" + "\n";
    
        rc += "           <Sitemap>" + "\n";

    for (int i = 0; i < inFileNames.size(); i++) {
      if (inFileNames.getFileName(i).getExtension().equalsIgnoreCase(Constants.Extentions.XML)) {
        rc += "             <xsl:copy-of select=\"document('" + inFileNames.getFileName(i).getPathAndFileName() + "',/)/sitemap\"/>" + "\n";
      }
    }
    rc += "           </Sitemap>" + "\n";
    
    
    rc += "       </project>" + "\n";
    rc += "    </xsl:template>" + "\n";
    rc += "</xsl:stylesheet>" + "\n";

    //System.out.println(rc);


    return new ByteArrayInputStream(rc.getBytes());

  }
}
