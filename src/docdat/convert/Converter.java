/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.convert;

import docdat.id.PseudoElements;
import docdat.utils.Constants;
import java.io.IOException;
import java.util.Stack;

/**
 *
 * @author wilfongj
 */
public class Converter {

    public Converter() {
    }

    private int charPerLine = Constants.CharPerLine;
    private Stack stack = new Stack();

    public void convert(PseudoElements list) throws IOException {
    }



    protected String getLine(int line, String txt) {
        String rc = "";
        // chars per row
        //getCharPerRow()
        if (txt.length() == 0) {
            return "";
        }
        int start = line * getCharPerLine();
        int end = start + getCharPerLine();
        if (end > txt.length()) {
            end = txt.length();
        }
        rc = txt.substring(start, end);
        return rc;
    }

    public int getNumberOfLines(String ln) {
        int lines = ln.length() / getCharPerLine();
        if ((ln.length() % getCharPerLine()) > 0) {
            lines++;
        }
        return lines;
    }

    public int getCharPerLine() {
        return charPerLine;
    }

    public Stack getStack() {
        return stack;
    }
/*
    public void writeComment(String comm) {

        ProcessLogger pl = new ProcessLogger(new File(getExportRootName()));
        pl.WriteNoDate("<!-- " + comm + " -->");
    }

    public String getWriteComment(String comm) {

        return "<!-- " + comm + " -->";
    }

    public void writeXML(String cmd) {

        ProcessLogger pl = new ProcessLogger(new File(getExportRootName()));

        pl.WriteNoDate("</" + cmd + ">");
    }

    public String getWriteXML(String cmd) {

        return "</" + cmd + ">";
    }
    */
    /*
     * @deprecated
     */
/*
    protected void writeXMLHeader() throws IOException {

        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        ProcessLogger pl = new ProcessLogger(new File(getExportRootName()));
        //pl.KillLog();
        //System.out.println("writeXMLHeader " + header);
        pl.WriteNoDate(header);
        //setInBuffer(new BufferedReader(new FileReader(getFiles().)));

    }
    */

}
