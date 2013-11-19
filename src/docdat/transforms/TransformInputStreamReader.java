/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.transforms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 *
 */
public class TransformInputStreamReader {

    public TransformInputStreamReader(){}
    
   // private InputStream SourceXMLInputStream = null;
    private InputStream TransformXSLInputStream = null;
    private OutputStream XMLOutputOutputStream = null;
    private ByteArrayOutputStream transBAOS = null; // buffer output to memory

    /*
     * Used to buffer the transform output
     *
     */
    protected ByteArrayOutputStream getTransBAOS() {
        if (transBAOS == null) {
            transBAOS = new ByteArrayOutputStream();
        }
        return transBAOS;
    }

    protected void setTransBAOS(ByteArrayOutputStream tBAOS) {

        transBAOS = tBAOS;
    }
    /*
     * the user supplied output - not alway used
     *
     */
    
    protected OutputStream getXMLOutputOutputStream() {
        return XMLOutputOutputStream;
    }

    protected void setXMLOutputOutputStream(OutputStream XMLOutputOutputStream) {
        this.XMLOutputOutputStream = XMLOutputOutputStream;
    }

    /*
     * the user or class supplied transforming xsl file
     *
     */
    protected InputStream getTransformXSLInputStream() {
        return TransformXSLInputStream;
    }

    protected void setTransformXSLInputStream(InputStream TransformXSLInputStream) {
        this.TransformXSLInputStream = TransformXSLInputStream;
    }
    private InputStream resultsAsInputStream = null; // sometime want data base to feed next proces

    protected InputStream getResultsAsInputStreamReader() {
        return resultsAsInputStream;
    }

    protected void setResultsAsInputStream(InputStream resultsAsInputStream) {
        this.resultsAsInputStream = resultsAsInputStream ;
    }

    public void closeFiles() throws IOException {

        if (getXMLOutputOutputStream() != null) {
            getXMLOutputOutputStream().close();
        }
     /*   if (getSourceXMLInputStream() != null) {
            getSourceXMLInputStream().close();
        }
*/
        if (getTransformXSLInputStream() != null) {
            getTransformXSLInputStream().close();
        }

        if (getTransBAOS() != null) {
            getTransBAOS().close();
        }

        if (getResultsAsInputStreamReader() != null) {
            getResultsAsInputStreamReader().close();
        }



    }


    /*
     * transform xml with xsl  into buffer
     */
    private void transformJAXPToBAOS(InputStreamReader xmlInputStreamReader) throws TransformerConfigurationException, TransformerException {
        // XML
        javax.xml.transform.Source xmlSource =
                new javax.xml.transform.stream.StreamSource( xmlInputStreamReader );
//System.out.println("transformJAXPToBAOS "+  getTransformXSLInputStream() );
        // XSL
        javax.xml.transform.Source xsltSource =
                new javax.xml.transform.stream.StreamSource(  getTransformXSLInputStream()  );

        //javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(getXMLOutputOutputStream());

        javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(getTransBAOS());  // buffered

        // create an instance of TransformerFactory
        javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();

        javax.xml.transform.Transformer trans = transFact.newTransformer(xsltSource);

        // transformToInputStream
        trans.transform(xmlSource, result);
    }

    /*
     * does transform and sends it to an outputstream
     */
   /*  public InputStream transformToInputStream() throws IOException, TransformerConfigurationException, TransformerException {


     }
*/

    /*
     * does transform and sends it to an input stream
     */
    public InputStream transformToInputStream( InputStreamReader xmlInputStreamReader ) throws IOException, TransformerConfigurationException, TransformerException {
        //System.out.println("transformToInputStream a");
        transformJAXPToBAOS(xmlInputStreamReader); // transform to buffer
        //write directly to output
        if (getXMLOutputOutputStream() != null) {
            //System.out.println("transformToInputStream b");
            getTransBAOS().writeTo(  getXMLOutputOutputStream()); // dump contents of buffer to output stream
            getTransBAOS().close();
            setTransBAOS(null);
        } else {
            //System.out.println("transformToInputStream c");
            // assume that not out put stream means that we are converting to input stream instead
            setResultsAsInputStream(   new ByteArrayInputStream(getTransBAOS().toByteArray())  ); // grab byte array output and convert to input stream
        }
        // System.out.println("transformToInputStream d "+getResultsAsInputStreamReader());
        return getResultsAsInputStreamReader();
    }


}
