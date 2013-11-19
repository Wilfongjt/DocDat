/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.names.InputFileNames;
import docdat.project.Project;
import docdat.utils.Attribute;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class Project_02_Test {

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

        for (int i = 0; i < project.size(); i++) {
            System.out.println("-------------------------------------------------");
            PseudoElements elms = project.getElements(i);
            System.out.println("fileName: " + elms.getFileName().getFileName());
            System.out.println("  charttype: " + elms.getChartType());
            System.out.println("  proxy: " + elms.isProxy());
            System.out.println("  master-reference: " + elms.isMasterReference());
            System.out.println("  summary: " + elms.isSummary());
            /*
            for (int k = 0; k < elms.size(); k++) {
            PseudoElement elm = elms.getElement(k);
            for (int j = 0; j < elm.getAttributes().size(); j++) {
            Attribute att = elm.getAttributes().getAttribute(j);
            System.out.println("att: "+att.getName() + " : " + att.getValue());
            }

            }
             */
            for (int j = 0; j < elms.getFileName().getConfiguration().size(); j++) {
                Attribute att = elms.getFileName().getConfiguration().getAttribute(j);
                System.out.println("  config: " + att.getName() + " : " + att.getValue());
            }

        }


        System.out.println("out ");

    }
}
