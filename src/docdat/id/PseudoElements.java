/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.id;

import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.names.InFileName;
import docdat.io.names.ProcessLogger;
import docdat.utils.Attribute;
import docdat.utils.Constants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class PseudoElements extends ArrayList {
    /*
     public PseudoElements() {
     }
     */

    private int Index = 0;

    public PseudoElements() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        setFileName(new InFileName(Constants.Default.Path, Constants.Default.Project, Constants.Default.Source));

    }

    public PseudoElements(InFileName fn) {
        setFileName(fn);

    }

    public String getClassName() {
        return "PseudoElements";
    }

    public void ResetIndex() {
        Index = 0;
    }

    public PseudoElement getNext() {
        if (getIndex() >= size()) {
            return null;
        }
        PseudoElement rc = getElement(getIndex());

        incrementIndex();

        return rc;
    }

    public int getIndex() {
        return Index;
    }

    public void incrementIndex() {
        this.Index = Index + 1;
    }

    public void setIndex(int Index) {
        this.Index = Index;
    }

    public boolean isProxy() {
        return getFileName().isProxy();
    }

    public boolean isMasterReference() {
        return getFileName().isMasterReference();
    }

    public boolean isSummary() {
        return getFileName().isSummary();
    }
    // top most element with an id = 0

    public PseudoElement getZeroElement() {
        PseudoElement zel = null;
        for (int i = 0; i < size(); i++) {
            zel = (PseudoElement) get(i);
            if (zel.getId().equals("0")) {
                return zel;
            }
        }
        return zel;
    }

    public void addPseudoElement(PseudoElement el) {
        if (el == null) {
            return;
        }

        el.setPosition(size());
        el.setIdx(size());
        //////System.out.println(" " + size() + "  add element " + el.getName());
        add(el);
    }
    private boolean _Graphable = false; // is set to true once coordinates are calculated

    public boolean isGraphable() {
        return _Graphable;
    }

    public String getOrientation() {
        String rc = "";
        PseudoElement zeroEl = getZeroElement();

        /*
         * set orientation of the graph
         */
        Attribute orientationAtt = zeroEl.getAttributes().getAttribute(Constants.Attributes.ORIENTATION);
        if (orientationAtt == null) {
            zeroEl.getAttributes().setAttribute(Constants.Attributes.ORIENTATION, Constants.OrientationValues.VERTICAL);
            rc = Constants.OrientationValues.VERTICAL;
        } else {
            rc = orientationAtt.getValue();
        }
        return rc;
    }

    public String getChartType() {
        String rc = "";
        PseudoElement zeroEl = getZeroElement();
        if (zeroEl == null) {
            return Constants.ChartTypeValues.ORCHART;
        }

        Attribute chartTypeAtt = zeroEl.getAttributes().getAttribute(Constants.Attributes.CHARTTYPE);

        if (chartTypeAtt == null) {
            Attribute att = new Attribute(Constants.Attributes.CHARTTYPE, Constants.ChartTypeValues.ORCHART);
            zeroEl.getAttributes().setAttribute(att);
            //zeroEl.getAttributes().setAttribute(Constants.Attributes.CHARTTYPE, Constants.ChartTypeValues.ORCHART);

            rc = att.getValue();
        } else {
            rc = chartTypeAtt.getValue();
        }
        return rc;
    }

    public void setGraphable(boolean _Graphable) {
        this._Graphable = _Graphable;
    }
    private InFileName _FileName = null;

    public InFileName getFileName() {
        return _FileName;
    }

    public void setFileName(InFileName _FileName) {
        this._FileName = _FileName;
    }

    /*
     * project name should be the first element in the list
     */
    public String getProjectName() {
        return getFileName().getProjectName();
        /*   String rc = "";
        
        
         if (size() > 0) {
         PseudoElement elPrj = (PseudoElement) get(0);
         rc = elPrj.getName();
         }
        
        
         return rc;
         */
    }

    /*
     * list of attriubtes to skip durring display
     */
    private String SkipAttributes = ""; // formated

    public String getSkipAttributes() {
        return SkipAttributes;
    }

    public void setSkipAttributes(String SkipAttributes) {
        String sa = "[" + SkipAttributes + "]";
        if (!this.SkipAttributes.contains(sa)) {
            this.SkipAttributes += "[" + SkipAttributes + "]";
        }
    }
    /*
     *
     * this will be the sources file extention
     * i.e., stakeholders, resources,...
     *
     */
    private String _ImportSource = "";

    public String getImportSource() {
        return _ImportSource;
    }

    public void setImportSource(String _ImportSource) {
        this._ImportSource = _ImportSource;
    }

    public String getSource() {
        return getFileName().getSourceName();
    }

    public void setSource(String _Source) {
        // this._Source = _Source;
        getFileName().setSourceName(_Source);
    }

    /*private String _Source = "";
    
     public String getSource() {
     return _Source;
     }
    
     public void setSource(String _Source) {
     this._Source = _Source;
     }
     */
    public Attribute getByAttribute(Attribute findAtt) {
        Attribute att = null;
        for (int i = 0; i < size(); i++) {
            PseudoElement el = (PseudoElement) get(i);
            for (int k = 0; k < el.getAttributes().size(); k++) {
                Attribute a = el.getAttributes().getAttribute(k);
                if (findAtt.getName().trim().equalsIgnoreCase(a.getName().toLowerCase())) {
                    if (findAtt.getValue().trim().equalsIgnoreCase(a.getValue())) {
                        att = a;
                    }
                }
            }
        }
        return att;
    }


    /*
     * summerized to an Element
     */
    public PseudoElement summerizeByAttributes(Attribute[] sumByAtts) {

        // create new element
        // add sumByAtts to the element
        //
        // for each element
        //   compare to list of search attributes
        //   when an element is found that matches the attribs of sumByAtts increment the counter
        //   when an element is found that matches the attribs of sumByAtts increment the counter
        // create a counter attribute that counts the number of elements that contribute the summary
        // create a sum from attributes with sum() evaluation
        // output new Element that has attribute that match the sum by atts
        // or null

        PseudoElement e = null;
        // make sure this list of element is not empty
        if (size() == 0) {
            ////System.out.println("Elements empty");
            return null;
        }
        // make sure incommning attrib have values
        if (sumByAtts.length == 0) {
            ////System.out.println("Sum by Atts array is empty");
            return null;
        }
        // make sure incommning attrib have values
        for (int i = 0; i < sumByAtts.length; i++) {
            if (sumByAtts[i] == null) {
                //////System.out.println("Sum by Atts array has missing one or more  search attributes.");
                return null;
            }
        }
        PseudoElement tmp_el = new PseudoElement();
        /*
         * load place holders for summary attributes
         */

        for (int i = 0; i < sumByAtts.length; i++) {
            tmp_el.getAttributes().setAttribute(sumByAtts[i].getName(), sumByAtts[i].getValue());
        }

        /* 
         * add a counter attribute
         *
         */
        tmp_el.getAttributes().setAttribute(Constants.AttributesTemp.CounterPrefix, "0");
        Attribute attCnt = tmp_el.getAttributes().getAttribute(Constants.AttributesTemp.CounterPrefix);

        int match_counter = 0;  // counts atts matching per element searched

        PseudoElement searched_el = null;
        /*
         * look at all elements for those that match the sumByAtts
         * update the counter, sums, avgs 
         */

        for (int i = 0; i < size(); i++) {
            searched_el = (PseudoElement) get(i); // get element from this list

            // skip those where id is blank this indicates a spacer

            //////System.out.println("sz: "+ emptytest.length() +" id: "+searched_el.getId() + " name: "+searched_el.getName());
            //////System.out.println("type: "+ searched_el.getType());
            if (searched_el.getType() > 0) {
                // search  all attributes for value matches
                if (searched_el.hasMatchingAttributesAndValues(sumByAtts)) {
                    // increment the counter
                    match_counter++;
                    attCnt.setValue(Integer.toString(match_counter));
                    // update any sums from searched_el
                    // update any avgs from_searched_el
                }
            }

        }

        // confirm matches found otherwise returns null
        if (match_counter != 0) {
            e = tmp_el;
        }

        return e;
    }

    public PseudoElement getElementByAttriubutes(Attribute[] Atts) {
        PseudoElement e = null;
        PseudoElement searched_el = null;
        // int match_counter = 0;

        if (Atts == null) {
            return null;
        }
        if (Atts.length == 0) {
            return null;
        }
        String findings = "";
        //  ////System.out.println("  getElementByAttriubutes 1 sz:" + Atts.length);
        for (int k = 0; k < Atts.length; k++) {
            if (Atts[k] == null) {
                findings += " null";
                // ////System.out.println("  getElementByAttriubutes 1a Not Found " + findings);
                return null;
            } else {
                findings += Atts[k].getValue() + " ";
            }
        }
        //////System.out.println("  getElementByAttriubutes 2");
        for (int i = 0; i < size(); i++) {
            searched_el = (PseudoElement) get(i); // get element from this list

            // search  all attributes for value matches
            if (searched_el.hasMatchingAttributesAndValues(Atts)) {
                // increment the counter
                //match_counter++;
                // ////System.out.println("    found " + findings);
                e = searched_el;
                break;
            } else {
                // ////System.out.println("    not found " + findings);
            }

        }
        /*
         if (match_counter == Atts.length) {
         ////System.out.println("  getElementByAttriubutes 3");
         e = searched_el;
         }
         *
         */
        // ////System.out.println("  getElementByAttriubutes out");
        return e;
    }

    public PseudoElement getLastElementByAttriubutes(Attribute[] Atts) {
        return getLastElementByAttributes(Atts, null);
    }

    /*
     * 
     *
     */
    public PseudoElement getLastElementByAttributes(Attribute[] Atts, Attribute stopAt) {
        PseudoElement e = null;
        PseudoElement searched_el = null;
        // int match_counter = 0;

        if (Atts == null) {
            return null;
        }
        if (Atts.length == 0) {
            return null;
        }
        String findings = "";
        //  ////System.out.println("  getElementByAttriubutes 1 sz:" + Atts.length);
        for (int k = 0; k < Atts.length; k++) {
            if (Atts[k] == null) {
                //findings += " null";
                //////System.out.println("  getElementByAttriubutes 1a Not Found " + findings);
                return null;
            } else {
                findings += Atts[k].getValue() + " ";
            }
        }

        //////System.out.println("  getElementByAttriubutes 2");
        //for (int i = 0; i < size(); i++) {
        for (int i = size() - 1; i >= 0; i--) {
            searched_el = (PseudoElement) get(i); // get element from this list

            if (stopAt != null && searched_el.getName().equalsIgnoreCase(stopAt.getName())) {
                return null;
            }
            // search  all attributes for value matches
            if (searched_el.hasMatchingAttributesAndValues(Atts)) {
                // increment the counter
                // ////System.out.println("    found " + findings);
                e = searched_el;
                break;
            } else {
                // ////System.out.println("    not found " + findings);
            }

        }

        // ////System.out.println("  getElementByAttriubutes out");
        return e;
    }

    public PseudoElement getElementFromAttribute(Attribute findAtt) {
        PseudoElement e = null;

        if (size() == 0) {
            //////System.out.println("getElementByAttribute not found");
        }

        for (int i = 0; i < size(); i++) {  // look in this list
            PseudoElement el = (PseudoElement) get(i); // get element from this list
            for (int k = 0; k < el.getAttributes().size(); k++) { // check elements atts for match
                Attribute a = el.getAttributes().getAttribute(k);

                if (findAtt.getName().equalsIgnoreCase(a.getName())
                        && findAtt.getValue().trim().equalsIgnoreCase(a.getValue())) {
                    e = el;
                }
            }
        }
        return e;
    }
    /*
     * @deprecate
     */

    public PseudoElement getElementByAttribute(Attribute findAtt) {
        PseudoElement e = null;

        if (size() == 0) {
            //////System.out.println("getElementByAttribute not found");
        }
        for (int i = 0; i < size(); i++) {  // look in this list
            PseudoElement el = (PseudoElement) get(i); // get element from this list
            for (int k = 0; k < el.getAttributes().size(); k++) { // check elements atts for match
                Attribute a = el.getAttributes().getAttribute(k);

                if (findAtt.getValue().trim().equalsIgnoreCase(a.getValue())) {
                    e = el;
                }
            }
        }
        return e;
    }

    public PseudoElement getElementByName(String name) {
        PseudoElement el = null;
        for (int i = 0; i < size(); i++) {
            PseudoElement tmp_el = (PseudoElement) get(i);

            if (tmp_el.getName().equalsIgnoreCase(name)) {
                el = tmp_el;
                break;
            }

        }
        return el;
    }


    /*
     * 0 = not used in this list
     * 1 = found once in this list
     * >1 = found more than once in this list
     */
    /* public int occuranceId(PseudoElement testElement){
     int match_counter = 0;
     for (int i = 0; i < size(); i++) {
     PseudoElement element = (PseudoElement) get(i);
     if(testElement.getId().equals(element.getId())){
     match_counter++;
     }
     }
     return match_counter;
     }
    
     public int occuranceId(String testId){
     int match_counter = 0;
     for (int i = 0; i < size(); i++) {
     PseudoElement element = (PseudoElement) get(i);
     if(testId.equals(element.getId())){
     match_counter++;
     }
     }
     return match_counter;
     }
     *
     */
    public int duplicateCountName(String testName) {
        int cnt = 0;
        for (int i = 0; i < size(); i++) {
            PseudoElement element = (PseudoElement) get(i);

            if (element.getName().trim().toLowerCase().equals(testName.trim().toLowerCase())) {
                cnt++;
            }
        }
        return cnt;
    }

    public int duplicateCountName(PseudoElement testElement) {
        int cnt = 0;

        cnt = duplicateCountName(testElement.getName());

        return cnt;
    }

    public PseudoElement getElement(int i) {
        PseudoElement rc = null;
        if (i < 0 || i >= size()) {
            return null;
        }

        rc = (PseudoElement) get(i);
        return rc;
    }

    /*
     * siblings have equal level
     *
     */
    public PseudoElement getFollowingSibling(PseudoElement el) {
        PseudoElement siblilng = null;
        if (el == null || el.getPosition() == 0) {
            return null;
        }

        for (int i = el.getPosition() + 1; i < size(); i++) {

            PseudoElement nextElem = getElement(i);
            if (nextElem == null) {
                return null;
            }
            if (el.getLevel() > nextElem.getLevel()) {
                return null;
            }
            if (el.getLevel() == nextElem.getLevel()) {
                return siblilng = nextElem;
            }

        }
        return siblilng;
    }

    public PseudoElement getParentFromAttribute(PseudoElement el, String attName) {
        PseudoElement parent = getParent(el);


        while (parent != null) {

            Attribute att = parent.getAttributes().getAttribute(attName);

            if (att != null) {
                return parent;
            }
            parent = getParent(parent);
        }

        return null;
    }

    public PseudoElement getParentFromAttribute(PseudoElement el, Attribute attSearch) {
        PseudoElement rc = null;
        if (el == null) {
            return null;
        }

        PseudoElement parent = getParent(el);
        if (parent == null) {
            return null;
        }

        Attribute att = parent.getAttributes().getAttribute(attSearch.getName());

        if (att != null) {
            if (att.getValue().equalsIgnoreCase(attSearch.getValue())) {
                rc = parent;
            } else {
                rc = getParentFromAttribute(parent, attSearch);
            }
        } else {
            rc = getParentFromAttribute(parent, attSearch);
        }

        //parent = getParent(parent);

        return rc;
    }

    public PseudoElement getParent(PseudoElement el) {
//////System.out.println("getParent 1 "  );
        if (el == null || el.getPosition() == 0) {
            return null;
        }
        //////System.out.println("getParent 2 el.getPosition() " + el.getPosition());

        int i = el.getPosition() - 1; // position at next potential parent
        // parent will have a lower Idx number preceding element may be a parent or a sibling

        //////System.out.println("getParent 21 i=" + i + "  size: " + size());

        PseudoElement elPrev = (PseudoElement) get(i--);

        // ////System.out.println("getParent 22");

        if (elPrev.getLevel() < el.getLevel()) {

            return elPrev;
        }
        //////System.out.println("getParent 3");
        // skip all the blank lines etc
        while (elPrev.getPosition() != 0 && elPrev.getIdx() == -1) {

            elPrev = (PseudoElement) get(i--);
        }
        //////System.out.println("getParent 4");
        // start at the el index and go backward
        while (elPrev.getPosition() != 0 && elPrev.getLevel() >= el.getLevel()) {

            //////System.out.println("getParent B id:"+elPrev.getId()+" Level: " + elPrev.getLevel() +" pos: "+elPrev.getPosition() );
            // skip all the blank lines etc
            elPrev = (PseudoElement) get(i--);

            while ((elPrev.getPosition() != 0 && elPrev.getIdx() == -1) || elPrev.getName().equals("")) {
                elPrev = (PseudoElement) get(i--);
            }

        }
        //////System.out.println("getParent 5");
        return elPrev;
    }

    public boolean hasChild(PseudoElement element, String childName) {
        boolean hasChild = false;
        if (element == null) {
            return hasChild;
        }

        int i = element.getIdx();
        i++;

        for (; i < size(); i++) {

            PseudoElement nextPseudoElement = (PseudoElement) get(i);
            if (nextPseudoElement.getElement().equalsIgnoreCase(Constants.Element.INPUT)) {
                hasChild = true;
                return hasChild;
            }

            if (nextPseudoElement.getLevel() <= element.getLevel()) {
                hasChild = false;
                return hasChild;
            }


        }

        return hasChild;
    }

    public boolean hasChild(PseudoElement element) {
        boolean hasChild = false;
        if (element == null) {
            return hasChild;
        }

        int i = element.getIdx();

        if (size() > i + 1) {
            PseudoElement nextPseudoElement = (PseudoElement) get(i + 1);
            if (nextPseudoElement.getLevel() > element.getLevel()) {
                hasChild = true;
            }
        }

        return hasChild;
    }

    /* public boolean isCondition(PseudoElement element) {
     boolean isCondition = false;
     Attribute symbolAtt = element.getAttributes().getAttribute(Constants.Attributes.SYMBOL_TYPE);
     if (symbolAtt != null) {
     if (symbolAtt.getValue().equalsIgnoreCase(Constants.SymbolTypeValues.CONDITION)) {
     isCondition = true;
     }
     }
     return isCondition;
     }
    
     */
    public void showList() {

        //for(int i = 0; i < getCharts().size();i++){

        ////System.out.println("-------------------------------------------------");
        if (size() == 0) {
            ////System.out.println(" PseudoElement is empty");
        }
        for (int i = 0; i < size(); i++) {
            PseudoElement element = (PseudoElement) get(i);

            showElement(element);
        }

    }

    public void showElement(PseudoElement element, String title) {
        ////System.out.println("" + title);
        showElement(element);
    }

    public void showElement(PseudoElement element) {
        if (element == null) {
            ////System.out.println(" PseudoElement element: " + element);
            return;
        }
        ////System.out.println(" PseudoElement element: " + element.getElement());
        ////System.out.println(" PseudoElement      id: " + element.getId());
        ////System.out.println(" PseudoElement     idx: " + element.getIdx());

        ////System.out.println(" PseudoElement    name: " + element.getName());

        ////System.out.println(" PseudoElement    desc: " + element.getDescription());
        ////System.out.println(" PseudoElement    type: " + element.getType());

        ////System.out.println(" PseudoElement    dups: " + element.getDuplicateCount());
        ////System.out.println(" PseudoElement   level: " + element.getLevel());
        ////System.out.println(" PseudoElement   SkipAttributes:" + getSkipAttributes());
        ////System.out.println(" PseudoElement   atts: ");
        for (int k = 0; k < element.getAttributes().size(); k++) {
            Attribute att = element.getAttributes().getAttribute(k);

            ////System.out.print("   " + att.getName() + "=" + att.getValue() + "  ");

        }
        ////System.out.println(" ");
    }

    public void dumpList() {

        ProcessLogger pl = new ProcessLogger("dump.log");
        pl.KillLog();
        pl.Write("-------------------------------------------------");

        for (int i = 0; i < size(); i++) {
            PseudoElement element = (PseudoElement) get(i);
            PseudoElement parent = getParent(element);
            PseudoElement fsib = getFollowingSibling(element);
            for (int k = 0; k < element.getAttributes().size(); k++) {
                Attribute att = element.getAttributes().getAttribute(k);
                pl.Write(" PseudoElement      " + att.getName() + ": " + att.getValue());

            }
            pl.Write(" PseudoElement      " + "Level : " + element.getLevel());
            pl.Write(" PseudoElement      " + "Child  : " + element.getChildNo());
            if (parent != null) {
                pl.Write(" PseudoElement      " + "Parent  : " + parent.getName());
            } else {
                pl.Write(" PseudoElement      " + "Parent  : none");
            }
            if (fsib != null) {
                pl.Write(" PseudoElement      " + "FollowngSib  : " + fsib.getName());
            } else {
                pl.Write(" PseudoElement      " + "FollowingSib  : none");
            }
            pl.Write(" ");
        }

    }
    //////////////////////////////////// from OrgChart
    private int minXOffset = Constants.MinXOffset;
    private int minYOffset = Constants.MinYOffset;

    public int getMinXOffset() {
        return minXOffset;
    }

    public void setMinXOffset(int minXOffset) {
        this.minXOffset = minXOffset;
    }

    public int getMinYOffset() {
        return minYOffset;
    }

    public void setMinYOffset(int minYOffset) {
        this.minYOffset = minYOffset;
    }

    /*
     public Attribute getChartType() {
     PseudoElement zeroelm = getZeroElement();
     Attribute attChartType = zeroelm.getAttributes().getAttribute(Constants.Attributes.CHARTTYPE);
     if (attChartType == null) {
     // org chart is default
     attChartType = new Attribute(Constants.Attributes.CHARTTYPE, Constants.ChartTypes.ORGCHART);
     }
     return attChartType;
     }
     */
    /*
     * Calculation methods
     * 
     */
    /*
     * format: nn.nn decimal on a 24 hour clock
     */
    protected double getTime(Attribute attTime) {
        Double tm = new Double(0);
        try {
            tm = new Double(attTime.getValue());
        } catch (Exception ex) {
            tm = new Double(0);
        }
        return tm.doubleValue();
    }

    /*
     * format: nnnn.nn.nn yyyy.mm.dd
     */
    protected Attribute getDate(PseudoElement e) {
        Attribute att = e.getAttributes().getAttribute(Constants.Attributes.DATE);
        if (att == null) {
            return null;
        }
        return att;
    }

    /*
     *
     * format: ???????
     */
    protected String getWeek(String strDate) {

        // YYYY.NN
        String[] tok = strDate.split("[.]");
        String rc = "";
        rc = Constants.TBD + tok.length;

        if (tok.length == 3) {
            Calendar ca1 = Calendar.getInstance();

            ca1.set(new Integer(tok[2]).intValue(), new Integer(tok[0]).intValue(), new Integer(tok[1]).intValue());

            int WEEK_OF_YEAR = ca1.get(Calendar.WEEK_OF_YEAR);

            //////System.out.println("WEEK OF YEAR :" + WEEK_OF_YEAR);
            rc = tok[2] + "." + WEEK_OF_YEAR;
        }
        return rc;
    }
    /*
     * removes any attributes that are used as temporay data holders
     */

    public void clearTempAttributes() {
        for (int i = 0; i < size(); i++) {
            PseudoElement pe = getElement(i);
            if (pe != null) {
                pe.clearTempAttributes();
            }
        }
    }
}
