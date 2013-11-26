/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.io.factory;

import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.names.InFileName;
import docdat.io.readers.ODTContentInputStreamReader;
import docdat.io.readers.ODTTextInputStreamReader;
import docdat.utils.Constants;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 * open proper input stream 
 * Text, or ODT
 */
public class InputStreamReaderFactory {

    public static InputStreamReader getInputStreamObject(InFileName infile) throws UnknownFileTypeException, TransformerException, IOException {

        InputStreamReader is = null;

        if (infile.getType().equalsIgnoreCase(Constants.Type.TEXT)) {
            //System.out.println("filename: "+infile.getPathAndFileName());
            //System.out.println("filename: "+infile.getPathAndFileName());
            is = new InputStreamReader(new FileInputStream(infile.getPathAndFileName()));
            //System.out.println("InputStreamReaderFactory 2");
        } else {

            if (infile.getType().equalsIgnoreCase(Constants.Type.ODT)) {
                is = new ODTTextInputStreamReader(new ODTContentInputStreamReader(infile.getPathAndFileName()));
            } else {
                throw new UnknownFileTypeException(infile.getFileName());
            }
        }
       
        return is;
    }

}
