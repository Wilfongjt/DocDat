/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.transforms;

import docdat.io.exceptions.ChartTypeNotSetException;
import docdat.io.inputstreams.ChartByteArrayInputStream;
import docdat.utils.Constants;
import docdat.utils.FileUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 *
 * @author wilfongj
 */
public class TransformIS_ChartToSVG extends TransformInputStream {
    /*
     * InputStream source_XMLInputStream - stream to ODT file
     * OutputStream XML_OutputOutputStream - name of xml output file
     */

    public TransformIS_ChartToSVG(InputStream source_XMLInputStream) throws IOException {
        super(source_XMLInputStream);

    }
    /**/

    /*
     * @param source_XMLInputStream - content.xml of an ODT zip file
     * @param XML_OutputOutputStream -  output stream
     */
    
    public TransformIS_ChartToSVG(InputStream source_XMLInputStream, OutputStream XML_OutputOutputStream) throws IOException {
        super(source_XMLInputStream /* source file */
                , new FileUtil().getInputStreamFromJAR((new FileUtil().getHomeFolder() + Constants.DiagramsJAR), Constants.XSL.OrgChartXSL ) /* transform xsl file */
                , XML_OutputOutputStream);
    }

    @Override
    protected InputStream getTransformXSLInputStream() throws IOException {
        if (super.getTransformXSLInputStream() == null) {
           String charttype = ((ChartByteArrayInputStream) getSourceXMLInputStream()).getChartType();
           if(charttype.equalsIgnoreCase(Constants.ChartTypeValues.FLOWCHART  )){
              // System.out.println("flowchart");
               setTransformXSLInputStream( this.getClass().getResourceAsStream("/DocDat/transforms/xsl/flowchart.step.02.xsl") );
            }else{
                if(charttype.equalsIgnoreCase(Constants.ChartTypeValues.ORCHART )){
                  //System.out.println("Orgchart");
                   setTransformXSLInputStream( this.getClass().getResourceAsStream("/DocDat/transforms/xsl/orgchart.step.02.xsl") );
                }else{
                    throw new IOException("charttype not set " );
                    
                }
            }

        }
        return super.getTransformXSLInputStream();
    }

}
