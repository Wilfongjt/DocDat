/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.convert;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Attribute;
import docdat.utils.Constants;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author wilfongj
 */
public class ConvertPseudoElementsToXML extends Converter {

    public ConvertPseudoElementsToXML() {
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

    public InputStream convertToInputStream(PseudoElements list) throws IOException {
        String line = "";
        line = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" ;
        getTransBAOS().write(line.getBytes());
        int lastlevel = -1;

        if (list.size() == 0) {
            line = "<empty-pseudoelements/>";
            getTransBAOS().write(line.getBytes() );
                    
        }
        for (int k = 0; k < list.size(); k++) {

            PseudoElement element = (PseudoElement) list.get(k);


            switch (element.getType()) {

                case 0: // comment

                    break;
                case 1: // top element

                    for (int i = lastlevel; i > 0 && element.getLevel() <= i; i--) {
                        line = (String) getStack().pop();
                        getTransBAOS().write(line.getBytes());

                    }

                    getStack().push("</" + element.getElement() + ">" +"\n");

                    line = getElement(element);
                    getTransBAOS().write(line.getBytes());
                    //pl.WriteNoDate(getElement(element));
                    lastlevel = element.getLevel();
                    break;
                case 2: // regular element command and no id

                    for (int i = lastlevel; i > 0 && element.getLevel() <= i; i--) {
                        line = (String) getStack().pop();
                        getTransBAOS().write(line.getBytes());

                    }

                    getStack().push("</" + element.getElement() + ">" +"\n");

                    line = getElement(element);
                    getTransBAOS().write(line.getBytes());

                    lastlevel = element.getLevel();
                    break;

                default:

                    line = "<!-- " + "[" + element.getType() + "] should never see this" + " >";


            }


        }

        while (getStack().size() > 0) { // pop the last one cause it will not go throught the switch
            // writeXML((String) getStack().pop());
            line = (String) getStack().pop();
            getTransBAOS().write(line.getBytes());
        }


        return new ByteArrayInputStream(getTransBAOS().toByteArray());

    }

    public String getElement(PseudoElement element) {
        String st = "";
        switch (element.getType()) {
            case 0: // comment
                // System.out.println("writeXML 1a");
                //writeComment(element.getDescription());
                st = element.getDescription();
                break;
            case 1: // top
                // System.out.println("writeXML 1b");
                st += "<";
                st += element.getElement();
                st += " ";
                // st += "id=\"" + element.getId() + "\" ";
                st += " ";
                //st += element.getAttributes();
                for (int i = 0; i < element.getAttributes().size(); i++) {
                    Attribute att = element.getAttributes().getAttribute(i);
                    
                    if(  att.getName().trim().length() > 0  ){
                      st += att.getName() + "=\"" + att.getValue() + "\" ";
                    }else{
                        st += "x-" + i + "=\"" + att.getValue() + "\" ";
                        
                    }
                    
                }
                st += " ";
                st += ">";
                break;
            case 2: // regular
                // System.out.println("writeXML 1c");
                st = "<";
                st += element.getElement();
                st += " ";
                // st += " id=\"" + element.getId().trim() + "\"";
                st += " ";
                for (int i = 0; i < element.getAttributes().size(); i++) {
                    Attribute att = element.getAttributes().getAttribute(i);
                    if(  att.getName().trim().length() > 0  ){
                      st += att.getName() + "=\"" + att.getValue() + "\" ";
                    }else{
                        st += "x-" + i + "=\"" + att.getValue() + "\" ";
                        
                    }
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
                    st += "<" + Constants.Element.DESCRIPTION + "><line><![CDATA[" + element.getDescription() + "]]></line></" + Constants.Element.DESCRIPTION + ">" +"\n";
                } else {
                    st += "<" + Constants.Element.DESCRIPTION + ">";
                    for (int i = 0; i < rows; i++) {
                        String subtxt = element.getDescription();
                        st += "<line><![CDATA[" + getLine(i, element.getDescription()) + "]]></line>"+"\n";
                    }
                    st += "</" + Constants.Element.DESCRIPTION + ">" +"\n";
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
