/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.calculate;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Constants;



/**
 * sets heights and widths
 * @author wilfongj
 */
public class Dimensions {

    public Dimensions() {
    }

    public Dimensions(PseudoElements chart) {
        setChart(chart);
    }
    PseudoElements chart = null;

    public PseudoElements getChart() {
        return chart;
    }

    public void setChart(PseudoElements chart) {
        this.chart = chart;
    }

    /*
     * calculate the dimensions of a rectangular box to fit around the text
     *
     *
     */
    public void calculate(PseudoElements chart) {
        setChart(chart);
        calculate();
    }

    public void calculate() {
        if (getChart() == null) {
            return;
        }
//System.out.println("    Dimension ");
        for (int i = 0; i < getChart().size(); i++) {
            //Step step = (Step) getChart().get(i);
            PseudoElement step = (PseudoElement) getChart().get(i);
            switch (step.getType()) {
                case 0: // comment
                    break;
                case 1: // top level or zero level
                case 2: // sub level 1...n
                case 3: // sub level 1...n
                    ////////// Width
                    String str = step.getDescription();

                    if (str.length() < step.getId().length()) {
                        str = step.getId();
                    }
                    if (str.length() < step.getName().length()) {
                        str = step.getName();
                    }
                    if (str.length() < step.getTitle().length()) {
                        str = step.getTitle();
                    }
                    if (str.length() < step.getRole().length()) {
                        str = step.getRole();
                    }
                    step.setW(getWidth(str));

                    /////// HEIGHT
                    int height = getHeight(step);


                    step.setH(height);
                    ////System.out.println("set W and H");
                    break;
            }
        }
    }

    protected int getWidth(String ln) {

        int line_ch = ln.trim().length() + getCharBuffer();

        int min_ch = 5; // characters
        int max_ch = getCharPerLine(); // characters
        int max_blocks = 1;
        int width = 0;
        // test below
        if (line_ch < min_ch) {
            line_ch = min_ch;
        }
        // test above
        if (line_ch > max_ch) {
            line_ch = max_ch;
        }
        // test between
        if (line_ch > min_ch && line_ch < max_ch) {
            int blks = line_ch / min_ch;
            int tln = 0;
            if (line_ch % min_ch > 0) {
                tln = min_ch;
            }
            line_ch = tln + (blks * min_ch);
        }
        ////System.out.println(" width: " +line_ch * getCharWidth());
        return line_ch * getCharWidth();

    }

    protected int getHeight(PseudoElement step) {
        //if(ln.trim().length()==0){return 0;}
        //int lines = getNumberOfLines(ln);
        int min_height = 50;
        /* if (lines > 2) {
        lines += 2;
        }*/
        int height = 0;
        height += getHeightOfLines(step.getId());
        height += getHeightOfLines(step.getName());
        height += getHeightOfLines(step.getTitle());
        height += getHeightOfLines(step.getRole());
        height += getHeightOfLines(step.getDescription());

        if (height < min_height) {
            height = min_height;
        }

        return height;
    }

    protected int getHeightOfLines(String ln) {
        if (ln.trim().length() == 0) {
            return 0;
        }
        int lines = getNumberOfLines(ln);
        if (lines > 2) {
            lines += 1;
        }
        int height = lines * getCharHeight(); // line height
        height += (lines * getCharHeight()) / 2; // spacing
        return height;

    }

    protected int getNumberOfLines(String ln) {
        int lines = ln.length() / getCharPerLine();
        if ((ln.length() % getCharPerLine()) > 0) {
            lines++;
        }
        return lines;
    }
    private int charHeight = Constants.CharHeight;
    private int charWidth = Constants.CharWidth;
    private int charPerLine = Constants.CharPerLine;
    private int charBuffer = Constants.CharBuffer; // two per side

    protected int getCharPerLine() {
        return charPerLine;
    }

    protected int getCharBuffer() {
        return charBuffer;
    }

    protected int getCharWidth() {
        return charWidth;
    }

    protected int getCharHeight() {
        return charHeight;
    }
}
