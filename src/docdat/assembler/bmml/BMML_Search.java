/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.assembler.bmml;

/**
 *
 * @author wilfongj
 */
public class BMML_Search {

    public BMML_Search(String controlfile_pathandname) {
        setPathAndName(controlfile_pathandname);
    }
    
    private String pathAndName = "";

    public String getPathAndName() {
        return pathAndName;
    }

    public void setPathAndName(String pathAndName) {
        this.pathAndName = pathAndName;
    }
    
    
    
    
    
}
