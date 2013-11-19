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
import docdat.utils.Constants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class PE_03Pluck_Test {

  public static void main(String[] args) throws Exception {
    System.out.println("PE_03Pluck_Test");

    /// load elements


    testImportText();
    //testBufferedReader(elems);
  }

  private static void testImportText() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

    System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
    ///////////////////////////////////////////////////////// Inputs
        /*
     * split up path and file name
     */
    String path = "C://00-Code//01-dev//DocDat//test//shpo_gis_database//";
    //String filename = "sample.year.log.summary.odt";
    String filename = "SHPO-GIS-Database.registration.database.pluck.odt";
    String odtFileName = path + filename;
    InFileName fileName = new InFileName(path, filename);



    /*
     * open  and transform to text stream
     *
     */
    InputStreamReader odtReader = InputStreamReaderFactory.getInputStreamObject(fileName);

    /////////////////////////////////////////////////////// Work
        /*
     *
     * Parse Text stream and store in elements
     */
    PseudoElements elements = new PseudoElements();
    elements.setFileName(fileName);

    /*
     * Summaries are handled in the importer
     */
    LoadPseudoElements importer = new LoadPseudoElements();

    elements = importer.Import(elements, (InputStreamReader) odtReader);

    System.out.println("--------------------------------------");
    System.out.println("  filename: " + elements.getFileName().getFileName());

    /*
    
    //////////////////////////////////////////////////////////////////////////////// Display below
    System.out.println("--------------------------------------");
    
    System.out.println(" Elements sz:" + elements.size());
    /*
     *
     * Output
     */
    // System.out.println("--------------------------------------");
    //System.out.println(" Summary sz:" + elements.size());

    /*
     * show Element LIsts one at a time
     */
    boolean pluck_toggle = false;
    System.out.println("  1  ");
    elements.getFileName().getConfiguration().showConfiguration();
    System.out.println("  2  ");
    int sz = elements.getFileName().getConfiguration().size(Constants.Source.PLUCK);
    System.out.println("  3  sz: " + sz);
    
    for (int i = 0; i < elements.size(); i++) {
      PseudoElement elm = elements.getElement(i);
      elm.setIdx(i);
      System.out.println(" element: " + elm.getElement() + " name: " + elm.getName());
    }

    // System.out.println("--------------------------------------");
    System.out.println("mem 3=" + Runtime.getRuntime().freeMemory());

    odtReader.close();
    System.out.println("mem 4=" + Runtime.getRuntime().freeMemory());
    System.out.println("testOdtReader out");
  }
}
