/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.convert;

import docdat.calculate.Coordinates_Flow;
import docdat.calculate.Coordinates_Hierarchical_Horizontal;
import docdat.calculate.Coordinates_Hierarchical_Vertical;
import docdat.calculate.Dimensions;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.inputstreams.ChartByteArrayInputStream;
import docdat.utils.Attribute;
import docdat.utils.Constants;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 *
 * @author wilfongj
 */
public class ConvertPseudoElementsToChartXML extends Converter {

    public ConvertPseudoElementsToChartXML() {
        super();
    }
    /*
     * Used to buffer the transform output
     *
     */
    private ByteArrayOutputStream transBAOS = null; // buffer output to memory

    protected ByteArrayOutputStream getTransBAOS() {
        if (transBAOS == null) {
            transBAOS = new ByteArrayOutputStream();
        }
        return transBAOS;
    }

    protected void setTransBAOS(ByteArrayOutputStream tBAOS) {
        transBAOS = tBAOS;
    }



    public ChartByteArrayInputStream convertToInputStream(PseudoElements list) throws IOException {

        //dimensionsAndCoordinates(list);

        ////////////////////
        String line = "";
        line = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        line += "<" + Constants.Element.Chart + ">";
        //line += "<workbreakdownstructures>";

        getTransBAOS().write(line.getBytes());

        /*
         * set the closing tags
         */
        getStack().push("</" + Constants.Element.Chart + ">");
        //getStack().push("</workbreakdownstructures>");



        int lastlevel = -1;


       /*
         * loop through elements and format the orgchart xml
         */
        for (int k = 0; k < list.size(); k++) {

            PseudoElement element = (PseudoElement) list.get(k);


            switch (element.getType()) {

                case 0: // comment

                    break;
                case Constants.ElementTypes.FIRST: // top element

                    // last level could be 0 or greater than zero
                    // when the current element has a level less than the  last level
                    // then this means that we need to terminate tag some of the open elements
                    // popping the stack until  the current level is equal to the last level will do this

                    for (int i = lastlevel; i > 0 && element.getLevel() <= i; i--) {
                        line = (String) getStack().pop();
                        getTransBAOS().write(line.getBytes());

                    }

                    getStack().push("</" + Constants.Element.Leaf + ">");

                    line = getElement(element);
                    getTransBAOS().write(line.getBytes());

                    lastlevel = element.getLevel();
                    break;
                case Constants.ElementTypes.REGULAR: // regular element command and no id

                    // last level could be 0 or greater than zero
                    // when the current element has a level less than the  last level
                    // then this means that we need to terminate tag some of the open elements
                    // popping the stack until  the current level is equal to the last level will do this

                    for (int i = lastlevel; i > 0 && element.getLevel() <= i; i--) {
                        line = (String) getStack().pop();
                        getTransBAOS().write(line.getBytes());

                    }

                    // push the terminating tag for this element
                    getStack().push("</" + Constants.Element.Leaf + ">");

                    // format the element and attributes and children elements
                    // leaving off the terminating tag
                    line = getElement(element);
                    getTransBAOS().write(line.getBytes());

                    lastlevel = element.getLevel();
                    break;

                default:

                    line = "<!-- " + "[" + element.getType() + "] should never see this" + " >";


            }


        }

        /*
         * pop the closing tags
         */

        while (getStack().size() > 0) { // pop the last one cause it will not go throught the switch
            // writeXML((String) getStack().pop());

            line = (String) getStack().pop();
            getTransBAOS().write(line.getBytes());
        }

        ChartByteArrayInputStream chartIS = null;
        if(list.getChartType().equalsIgnoreCase( Constants.ChartTypeValues.ORCHART ) ||
                list.getChartType().equalsIgnoreCase( Constants.ChartTypeValues.FLOWCHART )  ){
          
            chartIS = new ChartByteArrayInputStream(getTransBAOS().toByteArray());
            chartIS.setChartType(list.getChartType()); 
        }

        return chartIS;

    }

    public String getElement(PseudoElement element) {
        String st = "";
        switch (element.getType()) {
            case 0: // comment
                // //System.out.println("writeXML 1a");
                //writeComment(element.getDescription());
                st = element.getDescription();
                break;
            case 1: // top
                // //System.out.println("writeXML 1b");
                st += "<";
                st += Constants.Element.Leaf;
                st += " ";
               // st += "id=\"" + element.getId() + "\" ";
                st += " ";
                //st += element.getAttributes();
                for (int i = 0; i < element.getAttributes().size(); i++) {
                    Attribute att = element.getAttributes().getAttribute(i);
                    st += att.getName() + "=\"" + att.getValue() + "\" ";
                }
                st += " ";
                st += ">";
                break;
            case 2: // regular
                // //System.out.println("writeXML 1c");
                st = "<";
                st += Constants.Element.Leaf;
                st += " ";
               // st += " id=\"" + element.getId().trim() + "\"";
                st += " ";
                for (int i = 0; i < element.getAttributes().size(); i++) {
                    Attribute att = element.getAttributes().getAttribute(i);
                    st += att.getName() + "=\"" + att.getValue() + "\" ";
                }
                st += ">";

                break;
            default:

                st = "[" + element.getType() + "] should never see this";

        }
        if (element.getType() != 0) {
            if (element.getDescription().trim().length() > 0) {
                int pixelsPerLine = 50;

                int rows = getNumberOfLines(element.getDescription());//getHeight(txt) / pixelsPerLine;

                if (rows == 1) {
                    st += "<" + Constants.Element.DESCRIPTION + "><line><![CDATA[" + element.getDescription() + "]]></line></" + Constants.Element.DESCRIPTION + ">";
                } else {
                    st += "<" + Constants.Element.DESCRIPTION + ">";
                    for (int i = 0; i < rows; i++) {
                        String subtxt = element.getDescription();
                        st += "<line><![CDATA[" + getLine(i, element.getDescription()) + "]]></line>";
                    }
                    st += "</" + Constants.Element.DESCRIPTION + ">";
                }
            }
        }
        return st;
    }
    /*
    private PseudoElement currentPseudoElement = null;

    public PseudoElement getCurrentPseudoElement() {
    return currentPseudoElement;
    }

    public void setCurrentPseudoElement(PseudoElement currentPseudoElement) {
    this.currentPseudoElement = currentPseudoElement;
    }
     * 
     */
}
