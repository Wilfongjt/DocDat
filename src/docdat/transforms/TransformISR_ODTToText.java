/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.transforms;

import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author wilfongj
 */
public class TransformISR_ODTToText extends TransformInputStreamReader {
    /*
     * Convert an ODT Context.xml file to a Text Stream
     * InputStream source_XMLInputStream - stream to ODT file
     * OutputStream XML_OutputOutputStream - name of xml output file
     */

    public TransformISR_ODTToText() throws IOException {

    }


    /*
     *
     * get the XSL transform for ODT Context to Text from the jar file
     */
    protected InputStream getTransformXSLInputStream() {
     //   System.out.println("getTransformXSLInputStream 1");
        if (super.getTransformXSLInputStream() == null) {
         
                 setTransformXSLInputStream(this.getClass().getResourceAsStream("/DocDat/transforms/xsl/trans.odt.text.xsl") );
              //  System.out.println("getTransformXSLInputStream 3");

        }
        //System.out.println("getTransformXSLInputStream out");
        return super.getTransformXSLInputStream();
    }

}
