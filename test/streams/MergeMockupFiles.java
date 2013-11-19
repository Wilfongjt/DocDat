/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package streams;

import docdat.io.exceptions.UnknownFileTypeException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class MergeMockupFiles {

    public static void main(String[] args) throws Exception {
        System.out.println("InputFiles_01_Test1 1");

        /// load elements


        test();
        //testBufferedReader(elems);
    }

    private static void test() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {

        System.out.println("mem 1=" + Runtime.getRuntime().freeMemory());
        String project_path = "C:\\00-Code\\00-Projects\\SHPO\\GIS-Database\\mockups";
        //String xml_path = "C:\\00-Code\\00-Projects\\SHPO\\GIS-Database\\mockups\\xml\\mockup_merge.xml";
        String xml_path = "C:\\00-Code\\01-dev\\DesignChart\\src\\xsl\\mockups\\mockup_merge.xml";
        // the path's last folder is also the project name
        // the files can be proxys to other files in other projects


        File outfile = new File(xml_path);
        if (outfile.exists()) {
            outfile.delete();
        }

        File folder = new File(project_path);

        File[] listOfFiles = folder.listFiles();
        Writer output = new BufferedWriter(new FileWriter(xml_path));
        output.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        output.write("<mockups>");
        for (int i = 0; i < listOfFiles.length; i++) {
            System.out.println("Input File: " + listOfFiles[i]);
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().endsWith(".bmml")) {
                    System.out.println("Ouput File: " + xml_path + listOfFiles[i].getName());
                    BufferedReader input = null;
                    try {
                        input = new BufferedReader(new FileReader(listOfFiles[i]));
                        String line = "";
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                            line = line.replace("%2520", " ");

                            line = line.replace("%2527", "-");

                            line = line.replace("%2528", "(");
                            line = line.replace("%2529", ")");
                            line = line.replace("%252C", ",");
                            line = line.replace("textbox", "TextInput");
                            line = line.replace("textInput", "TextInput");
                            line = line.replace("textinput", "TextInput");
                            line = line.replace("combobox", "ComboBox");
                            line = line.replace("checkbox", "CheckBox");
                            line = line.replace("Brick", "Brick");
                            line = line.replace("help-button", "Help-Button");
                            line = line.replace("label", "Label");

                            line = line.replace("commit-button", "Commit-Button");
                            line = line.replace("radio-button", "Radio-Button");
                            line = line.replace("delete-button", "Delete-Button");
                            line = line.replace("edit-button", "Edit-Button");
                            line = line.replace("paragraph", "Paragraph");

                            output.write(line);
                        }


                    } catch (IOException ioe) {
                    }
                    if (input != null) {
                        input.close();
                    }
                }
            }
        }
        output.write("</mockups>");
        output.close();


        System.out.println("out ");

    }
}
