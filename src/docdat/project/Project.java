/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.project;

import docdat.calculate.Coordinates_Flow;
import docdat.calculate.Coordinates_Hierarchical_Horizontal;
import docdat.calculate.Coordinates_Hierarchical_Vertical;
import docdat.calculate.Dimensions;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.io.names.InFileName;
import docdat.io.names.InputFileNames;
import docdat.load.LoadPseudoElements;
import docdat.utils.Attribute;
import docdat.utils.Constants;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class Project extends ArrayList {

    public Project(InputFileNames inFileNames) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        
        System.out.println("Project 1");
        
        setInFileNames(inFileNames);
        System.out.println("Project 2");
        loadElements();
        System.out.println("Project 3");
        loadSpecialElements();
        System.out.println("Project 4");
    }

    /*
     * Access to the list of PseudoElements
     */
    public PseudoElements getElements(int i) {
        if (i < 0 || i >= size()) {
            return null;
        }
        return (PseudoElements) get(i);
    }


    public void setElements(PseudoElements elems) {
        add(elems);
    }
    /*
     * load an elements list for each file
     */
    protected void loadElements() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        /*
         * import all the files
         * load the elements
         */
        for (int i = 0; i < getInFileNames().size(); i++) {

            InFileName fileName = getInFileNames().getFileName(i);
            PseudoElements elements = new PseudoElements();
            elements.setFileName(fileName);
            /*
             *            skip the master-reference imports but push a place holder into the list
             * 
             */
            if (!fileName.isMasterReference()) {

                InputStreamReader isr = InputStreamReaderFactory.getInputStreamObject(fileName);
                LoadPseudoElements importer = new LoadPseudoElements();

                importer.Import(elements, isr);
                setDefaults(elements);
                setDimensionsAndCoordinates(elements);

            }

            setElements(elements);
        }

    }

    protected void loadSpecialElements() {
        for (int i = 0; i < size(); i++) {
            PseudoElements MRElms = getElements(i);
            if (MRElms.getFileName().isMasterReference()) {
                
                // MR Attrib value is at the size - 3
                String MRAttValue = (String) MRElms.getFileName().get(MRElms.getFileName().size() - 3);

                PseudoElement zeroElm = new PseudoElement();
                zeroElm.setId("0");
                zeroElm.setElement(MRAttValue);
                zeroElm.setName(MRElms.getFileName().getProjectName());
                zeroElm.setIdx(0);
                zeroElm.setChildNo(0);
                zeroElm.setLevel(1);
                MRElms.addPseudoElement(zeroElm);

                // look at all elements and attributes in project
                for (int k = 0; k < size(); k++) {
                    PseudoElements selms = getElements(k);
                    // skip the MRs
                    if (!selms.getFileName().isMasterReference()) {
                       
                        // add a zero elemt

                        // cycle through all the element lists skip the MRs
                        for (int j = 0; j < selms.size(); j++) {
                            PseudoElement elm = selms.getElement(j);
                            // scan the attribs
                            for (int m = 0; m < elm.getAttributes().size(); m++) {
                                Attribute MRAtt = elm.getAttributes().getAttribute(m);

                                if (MRAtt.getName().equalsIgnoreCase(MRAttValue)) {

                                    PseudoElement nelm = new PseudoElement();
                                    nelm.setId("" + MRElms.size());
                                    nelm.setElement(MRAttValue);
                                    nelm.setName(MRAtt.getValue());
                                    nelm.setIdx(MRElms.size());
                                    nelm.setChildNo(0);
                                    nelm.setLevel(1);
                                    nelm.getAttributes().setAttribute("from" , selms.getFileName().getFileName() );
                                    // put it into the MR
                                    MRElms.addPseudoElement(nelm);
                                }

                            }
                        }
                    }

                }
            }

        }
    }

    /*
     * Project Files
     * pre loaded by initiation of the InputFileNames
     */
    private InputFileNames _inFileNames = null;

    public InputFileNames getInFileNames() {
        return _inFileNames;
    }

    protected void setInFileNames(InputFileNames inFileNames) {
        this._inFileNames = inFileNames;
    }


    /*
     * set defaults
     *   Chart Type
     *   Orientation
     */
    protected void setDefaults(PseudoElements list) {
        PseudoElement zeroEl = list.getZeroElement();

        /*
         * set orientation of the graph
         */
        Attribute orientationAtt = zeroEl.getAttributes().getAttribute(Constants.Attributes.ORIENTATION);
        if (orientationAtt == null) {
            zeroEl.getAttributes().setAttribute(Constants.Attributes.ORIENTATION, Constants.OrientationValues.VERTICAL);
        }
        /*
         * set default chart type
         *
         */
        Attribute chartTypeAtt = zeroEl.getAttributes().getAttribute(Constants.Attributes.CHARTTYPE);

        if (chartTypeAtt == null) {
            zeroEl.getAttributes().setAttribute(Constants.Attributes.CHARTTYPE, Constants.ChartTypeValues.ORCHART);
        }
    }
    /*
     * calculate the dimensions (h and w) from the amount of text
     * calculate the position of each text block with regards to orientation and chart type
     */

    private void setDimensionsAndCoordinates(PseudoElements list) {

        new Dimensions().calculate(list);// calculate the size of the box around the text

        //setDefaults(list); // orientation and charttype

        /* Coordinates  by chart type */

        PseudoElement zeroEl = list.getZeroElement();

        Attribute chartType = zeroEl.getAttributes().getAttribute(Constants.Attributes.CHARTTYPE);

        /*
         *
         * OrgChart
         * Default chart type
         */
        if (chartType.getValue().equalsIgnoreCase(Constants.ChartTypes.ORGCHART)) {
            Attribute orientationAtt = zeroEl.getAttributes().getAttribute(Constants.Attributes.ORIENTATION);
            System.out.println("orientationAtt : " + orientationAtt);
            if (orientationAtt == null || orientationAtt.getValue().equalsIgnoreCase(Constants.OrientationValues.VERTICAL)) {



                Coordinates_Hierarchical_Vertical coords = new Coordinates_Hierarchical_Vertical(list);
                coords.calculate();
            } else {

                Coordinates_Hierarchical_Horizontal coords = new Coordinates_Hierarchical_Horizontal(list);
                coords.calculate();

            }
        }

        /*
         *
         * FlowChart
         */
        if (chartType.getValue().equalsIgnoreCase(Constants.ChartTypes.FLOWCHART)) {
            Coordinates_Flow coords = new Coordinates_Flow(list);
            coords.calculate();

        }
    }
}
