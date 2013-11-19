/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.names.InFileName;
import docdat.io.names.InputFileNames;
import docdat.project.Project;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.transform.TransformerException;


/**
 *
 * @author wilfongj
 */
public class Project_01_Test {

    public static void main(String[] args) throws Exception {
        System.out.println("InputFiles_01_Test1 1");

        /// load elements


        test();
        //testBufferedReader(elems);
    }

    private static void test() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        String project_path = "C:\\00-Code\\01-dev\\DocDat\\test\\samples\\";

        // the path's last folder is also the project name
        // the files can be proxys to other files in other projects
        InputFileNames inputFiles = new InputFileNames(project_path);
        Project project = new Project(inputFiles);

        for( int i = 0 ; i < inputFiles.size() ; i++ ){
            System.out.println("-------------------------------------------------");
            InFileName ipn = project.getInFileNames().getFileName(i) ;
            System.out.println(" path and filename : " +ipn.getPathAndFileName() );
            System.out.println(" source : " + ipn.getSourceName());
            System.out.println(" type: " + ipn.getType());
        }

        System.out.println("out ");

    }
}
