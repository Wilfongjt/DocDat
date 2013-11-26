/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.transforms;

import docdat.id.PseudoElements;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;


/**
 *
 * @author wilfongj
 *
 */
public class TransformInputStream {

    public TransformInputStream(){}
    
    public TransformInputStream(PseudoElements elements ){
        // need to convert elements to an input stream
       setSourceXMLInputStream(PseudoElementsToStream(elements) );
    }

    public TransformInputStream(InputStream source_XMLInputStream) {
        setSourceXMLInputStream(source_XMLInputStream);
        // TransformInputStream XSL is defined in an overload of child class
        // Output is going to ByteArrayOutputStream
    }
    public TransformInputStream(InputStream source_XMLInputStream, InputStream transform_XSLInputStream, OutputStream XML_OutputOutputStream) {

        setSourceXMLInputStream(source_XMLInputStream);
        setTransformXSLInputStream(transform_XSLInputStream);
        // out put is going to an output stream directly ... a file
        setXMLOutputOutputStream(XML_OutputOutputStream);

    }

    /*
    public TransformInputStream(InputStream source_XMLInputStream, InputStream transform_XSLInputStream) {

    setSourceXMLInputStream(source_XMLInputStream);
    setTransformXSLInputStream(transform_XSLInputStream);

    // Output is going to ByteArrayOutputStream
    }
     */

    protected InputStream PseudoElementsToStream(PseudoElements elements){
        // over load
        return null;
    }


    /*
    private javax.xml.transform.Result resultOutput = null;

    protected Result getResultOutput() {
    return resultOutput;
    }

    protected void setResultOutput(Result resultOutput) {
    this.resultOutput = resultOutput;
    }
     * 
     */
    private InputStream SourceXMLInputStream = null;
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
     * the user supplied incomming xml file
     *
     */

    protected InputStream getSourceXMLInputStream() {
        return SourceXMLInputStream;
    }

    protected void setSourceXMLInputStream(InputStream SourceXMLInputStream) {
        this.SourceXMLInputStream = SourceXMLInputStream;

    }

    /*
     * the user or class supplied transforming xsl file
     *
     */
    protected InputStream getTransformXSLInputStream()  throws IOException {
        return TransformXSLInputStream;
    }

    protected void setTransformXSLInputStream(InputStream TransformXSLInputStream) {
        this.TransformXSLInputStream = TransformXSLInputStream;
    }
    private InputStream resultsAsInputStream = null; // sometime want data base to feed next proces

    protected InputStream getResultsAsInputStream() {
        return resultsAsInputStream;
    }

    protected void setResultsAsInputStream(InputStream resultsAsInputStream) {
        this.resultsAsInputStream = resultsAsInputStream;
    }

    public void closeFiles() throws IOException {

        if (getXMLOutputOutputStream() != null) {
            getXMLOutputOutputStream().close();
        }
        if (getSourceXMLInputStream() != null) {
            getSourceXMLInputStream().close();
        }

        if (getTransformXSLInputStream() != null) {
            getTransformXSLInputStream().close();
        }

        if (getTransBAOS() != null) {
            getTransBAOS().close();
        }

        if (getResultsAsInputStream() != null) {
            getResultsAsInputStream().close();
        }

    }


    /*
     * transform xml with xsl  into buffer
     */
    private void transformJAXPToBAOS() throws TransformerConfigurationException, TransformerException,IOException {
      //  System.out.println("transformJAXPToBAOS 1");
        // XML
        javax.xml.transform.Source xmlSource =
                new javax.xml.transform.stream.StreamSource(getSourceXMLInputStream());
//System.out.println("transformJAXPToBAOS 2");
        // XSL
        javax.xml.transform.Source xsltSource =
                new javax.xml.transform.stream.StreamSource(getTransformXSLInputStream());
//System.out.println("transformJAXPToBAOS 3");
        //javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(getXMLOutputOutputStream());

        javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(getTransBAOS());  // buffered
//System.out.println("transformJAXPToBAOS 4");
        // create an instance of TransformerFactory
        javax.xml.transform.TransformerFactory transFact = javax.xml.transform.TransformerFactory.newInstance();
//System.out.println("transformJAXPToBAOS 5");
        javax.xml.transform.Transformer trans = transFact.newTransformer(xsltSource);
//System.out.println("transformJAXPToBAOS 6");
        // transformToInputStream
        trans.transform(xmlSource, result);
        //System.out.println("transformJAXPToBAOS out");
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
    public InputStream transformToInputStream() throws IOException, TransformerConfigurationException, TransformerException {
        //System.out.println("transformToInputStream a");
        transformJAXPToBAOS(); // transform to buffer
        //write directly to output
        if (getXMLOutputOutputStream() != null) {
           // System.out.println("transformToInputStream b");
            getTransBAOS().writeTo(getXMLOutputOutputStream()); // dump contents of buffer to output stream
            getTransBAOS().close();
            setTransBAOS(null);
        } else {
            //System.out.println("transformToInputStream c");
            // assume that not out put stream means that we are converting to input stream instead
            setResultsAsInputStream(  new ByteArrayInputStream(getTransBAOS().toByteArray())  ); // grab byte array output and convert to input stream
        }
        // System.out.println("transformToInputStream d "+getResultsAsInputStream());
        return getResultsAsInputStream();
    }

}
