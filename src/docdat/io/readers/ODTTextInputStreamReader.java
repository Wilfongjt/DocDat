/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.io.readers;

import docdat.transforms.TransformISR_ODTToText;
import java.io.IOException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;


/**
 *
 * @author wilfongj
 */
public class ODTTextInputStreamReader extends InputStreamReaderBridge {

    public ODTTextInputStreamReader(ODTContentInputStreamReader in) throws TransformerException, TransformerConfigurationException ,IOException {
        super(  (new TransformISR_ODTToText()).transformToInputStream(in)  );
    }

}
