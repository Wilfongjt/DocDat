/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.io.names;

import docdat.io.exceptions.UnknownFileTypeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.transform.TransformerException;



/**
 *
 * @author wilfongj
 */
public class InputFileNames extends ArrayList  {

    public InputFileNames(String infolder) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        setInFolder(infolder);

        loadFileNamesFromDisk();
        
    }

    public void loadFileNamesFromDisk() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        //new InputFileStrings(cmdType, inFolder, prjName, sources, alternateLogFile);
        // find all files in the home folder
        // create import for each file
        // tab names a project + "." + task + "." + Source + "." + NN

        // get list of files
        // for each file
        //   determine the project
        //   determine the extention
        //   determine the task

System.out.println(" getInFolder: " + getInFolder());
        File dir = new File(getInFolder());
        String[] children = null;

        FilenameFilter filter = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                boolean rc = true;
                if (name.trim().length() < 1) {
                    rc = false;
                }
                if (name.indexOf('.') < 0) {
                    rc = false;
                }

                if (name.toLowerCase().startsWith(".")) {
                    rc = false;
                }

                if (name.toLowerCase().endsWith(".")) {
                    rc = false;
                }

                if (name.toLowerCase().endsWith(".svg")) {
                    rc = false;
                }

                if (name.toLowerCase().endsWith(".pdf")) {
                    rc = false;
                }

                if (name.toLowerCase().endsWith(".doc")) {
                    rc = false;
                }

                if (name.toLowerCase().endsWith(".txt")) {
                    rc = false;
                }
                if (name.toLowerCase().endsWith(".xls")) {
                    rc = false;
                }
                if (name.toLowerCase().endsWith(".zip")) {
                    rc = false;
                }
                /*
                if (name.toLowerCase().endsWith(".odt")) {
                rc = false;
                }
                 *
                 */
                return rc;
            }
        };
System.out.println("  dir: " + dir );
        children = dir.list(filter);

        for (int i = 0; i < children.length; i++) {
            add(  new InFileName(getInFolder(), children[i])  );
        }

    }

    public void showFileNames() {
        System.out.println("-------------------- Files");
        for (int i = 0; i < this.size(); i++) {
            System.out.println("    file: [" + i + "] " + ((InFileName) get(i)).getPathAndFileName() );

        }
        System.out.println("--------------------");
    }

    public InFileName getFileName(int i) {
        if(i < 0 || i >= size()){ return null;}
        return (InFileName) get(i);
    }
    

    private String infolder = "";
    public String getInFolder() {
        return infolder;
    }

    public void setInFolder(String infolder) {
        this.infolder = infolder;
    }


}
