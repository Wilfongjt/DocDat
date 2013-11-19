/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.io.names;

import java.io.File;

/**
 *
 * the file name can be encoded with information
 * A filename usually has two parts the  1) project name 2) content type
 * if a file has three parts then 1) project name 2) content type 3) ext
 * if a file has four parts the 1) project name 2) subpart1 3) content type 3) ext
 * if a file has five parts then 1) project name 2) subpart1 3) subpart2 4) ext
 * @author wilfongj
 */
public class OutFileName extends InFileName {
    public OutFileName() {
       super();
    }
/*
 * path to output folder
 * file name with proper extension
 *
 */
    public OutFileName(InFileName inFile,String newExt) {
       
       setPath(inFile.getPath().trim()+newExt); // use ext to create new output folder
       for(int i =0 ; i<inFile.size(); i++){
           add((String) inFile.get(i)) ;
       }
       setExtension(newExt);
       makeFolder();
    }
    public OutFileName(InFileName inFile,String newfolder,String newExt) {
       
       setPath(inFile.getPath().trim()+newfolder); // use ext to create new output folder
       for(int i =0 ; i<inFile.size(); i++){
           add((String) inFile.get(i)) ;
       }
       setExtension(newExt);
       makeFolder();
    }


   public void makeFolder(){
       System.out.println("getPath: "+getPath() );
       new File( getPath() ).mkdirs() ;
   }



}
