/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.load;

import java.io.BufferedReader;


/**
 *
 * @author wilfongj
 */
public class Load {

  public Load() {
    //setFiles(fs);
    // no need for a buffered reader
    //setInBuffer(new BufferedReader(new FileReader( new File(fs) )));
  }
  /*
  public Load(Files fs)  throws FileNotFoundException{
  setFiles(fs);
  setInBuffer(new BufferedReader(new FileReader(getFiles().getInFile())));
  }
   */
  /*
  public Load(String fs)  throws FileNotFoundException{
  //setFiles(fs);

  setInBuffer(new BufferedReader(new FileReader( new File(fs) )));
  }
   *
   */

  /* 
   *
   * Name of the enclosing tag for group
   * i.e.  <resources><resource/><resource></resources>
   * where recources tag bounds a set of resource tags
   *
   */
  private String _BlockTagName = "";

  public String getBlockTagName() {
    return _BlockTagName;
  }

  public void setBlockTagName(String _BlockName) {
    this._BlockTagName = _BlockName;
  }
  /*
   * the tag name enclosed by BlockTagName or by itself
   *
   */
  private String _ItemTagName = "";

  public String getItemTagName() {
    return _ItemTagName;
  }

  public void setItemTagName(String _ItemTagName) {
    this._ItemTagName = _ItemTagName;
  }

  /*
   * an alternate tag for the ItemTagName
   * this is used to specify a mixed list of significant tags i.e., functional-requirement and nonfunctional-requirement
   *
   */
  private String _ItemTagNameAlternate = "";

  public String getItemTagNameAlternate() {
    return _ItemTagNameAlternate;
  }

  public void setItemTagNameAlternate(String _ItemTagNameAlternate) {
    this._ItemTagNameAlternate = _ItemTagNameAlternate;
  }

  /*
   * the text found just after the ID... 1.1.1 DocumentElementName
   */
  /*
  private String DocumentElementName = "";

  public String getDocumentElementName() {
  return DocumentElementName;
  }

  public void setDocumentElementName(String DocumentElementName) {
  this.DocumentElementName = DocumentElementName;
  }
   */
  /*
  private Files _files = null;

  public Files getFiles() {
    return _files;
  }

  public void setFiles(Files _files) {
    this._files = _files;
  }
   * 
   */
  private BufferedReader inBuffer = null;

  public BufferedReader getInBuffer() {
    return inBuffer;
  }

  public void setInBuffer(BufferedReader inBuffer) {
    this.inBuffer = inBuffer;
  }

  public void postProcess() {
  }
}
