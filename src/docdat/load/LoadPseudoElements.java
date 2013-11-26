/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.load;

import docdat.id.PseudoComment;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Attribute;
import docdat.utils.Attributes;
import docdat.summary.Calculate;
import docdat.summary.Summary;
import docdat.utils.Configuration;
import docdat.utils.Constants;
import docdat.utils.Utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author wilfongj
 */
public class LoadPseudoElements extends Load {

    public LoadPseudoElements() throws FileNotFoundException {
        super();
    }

    public LoadPseudoElements(PseudoElements elements) throws FileNotFoundException {
        super();

        //something is wront with getPathAnd FileName

        if (elements.getFileName() != null
                && !elements.getFileName().getProjectName().equalsIgnoreCase(Constants.DUMMYFILENAME)) {
            //////System.out.println(" LoadPseudoElements 2");
            setInBuffer(new BufferedReader(new FileReader(elements.getFileName().getPathAndFileName())));
        }

        setElements(elements);

    }

    public LoadPseudoElements(PseudoElements elements, String singleLine) throws FileNotFoundException {
        super();

        setElements(elements);
        loadLine(singleLine);

    }

    public LoadPseudoElements(PseudoElements elements, InputStream inStream) throws FileNotFoundException {
        super();


        /*
         if (elements.getFileName() != null
         && !elements.getFileName().getProjectName().equalsIgnoreCase(Constants.DUMMYFILENAME)) {
         setInBuffer(new BufferedReader(new FileReader(elements.getFileName().getPathAndFileName())));
         }
         */

        setInBuffer(new BufferedReader(new InputStreamReader(inStream)));
        setElements(elements);

    }
    private int _level = 0;
    private PseudoElements List = null;
    private String _prefix = "";  // prefix for unique wbs id names

    /*
     * get the PseudoElements List
     *
     */
    public PseudoElements getElements() {
        return List;
    }

    public void setElements(PseudoElements _PseudoElements) {
        this.List = _PseudoElements;
    }

    public void preProcess() throws IOException {
    }

    public void postProcess() {
        buildPluckElements();
        // buildSums();
        buildSummaryElements();

        //getElements().clearTempAttributes();

        pruneIgnore();
        patchRootNumber();  // use the root-id to update the root id number
        patchRunningNumber(); // use the root-id to updat the running-idx number
        patchMEO(); // used for setting manditory required or option
        patchInvitationTab(); // this depends on  specific parent element
        patchBadSpelling(); // just plan mis named or spelling error
        patchAPITab();
        patchRefs();
        patchAddHyphens();
        patchReplaceProperty(); // replace all the replace-property instances
        patchEventRef();// ensure that each child of an event has a [ref:true] tag


    }

    protected void pruneIgnore() {

        if (getElements().size() == 0) {
            return;
        }

        if (!getElements().getElement(0).hasAttribute(Constants.Attributes.ROOTID)) {
            return;
        }

        for (int i = 0; i < getElements().size(); i++) {
            PseudoElement el = getElements().getElement(i);
            Attribute ignore = el.getAttributes().getAttribute(Constants.Attributes.IGNORE ) ;
            if (el.getName().equalsIgnoreCase(Constants.Element.IGNORE) || ignore != null ) {
                // el.setName(el.getName().replaceAll(" ", "-"));
                String ignoreVal = el.getId();
                int k = i;
                for (k = i; k < getElements().size() && getElements().getElement(k).getId().startsWith(ignoreVal); k++);
                k--;
               // ////System.out.println("sz =" + getElements().size() + " i = " + i + "  k = " + k);
                for (int j = k; j >= i; j--) {
                   // ////System.out.println("prune i = " + i + "  k = " + k + " j = " + j);
                    getElements().remove(j);
                }



            }


        }
        
        // reindex objects 
        for(int i = 0 ; i < getElements().size() ; i ++ ){
            getElements().getElement( i  ).setPosition( i ); 
            getElements().getElement( i  ).setIdx(  i  ) ; 
            
        }
        
        
    }

    protected void patchAddHyphens() {

        if (getElements().size() == 0) {
            return;
        }

        if (!getElements().getElement(0).hasAttribute(Constants.Attributes.ROOTID)) {
            return;
        }

        for (int i = 0; i < getElements().size(); i++) {
            PseudoElement el = getElements().getElement(i);
            if (el.getAttributes().getAttribute(Constants.Attributes.NO) != null) {
                el.setName(el.getName().replaceAll(" ", "-"));
            }


        }

    }

    protected void patchRootNumber() {
        if (getElements().size() == 0) {
            return;
        }

        if (!getElements().getElement(0).hasAttribute(Constants.Attributes.ROOTID)) {
            return;
        }

        Attribute att = getElements().getElement(0).getAttributes().getAttribute(Constants.Attributes.ROOTID);
        String rootId = att.getValue();
        rootId = Utils.padFront(rootId, "0", Constants.Padding.PAD_ATT);

        att.setValue(rootId);
        // replace the first or root id number with the root-id
        for (int i = 0; i < getElements().size(); i++) {
            // get current number
            PseudoElement el = getElements().getElement(i);
            String tempIdx = el.getId();
            String idx = "";
            if (tempIdx.indexOf('.') > -1) {
                idx = tempIdx.substring(0, tempIdx.indexOf('.'));
            } else {
                idx = tempIdx;
            }
            // get chars up to first period (.) or end of string

            tempIdx = tempIdx.replaceFirst(idx, rootId);

            // replace id
            el.setId(tempIdx);
        }

    }

    protected void patchRunningNumber() {
        ////System.out.println("");
        ////System.out.println("patch Running Number: ");
        if (getElements().size() == 0) {
            ////System.out.println(" Missing Elements ");
            return;
        }

        if (!getElements().getElement(0).hasAttribute(Constants.Attributes.ROOTID)) {
            ////System.out.println(" No root-id for " + getElements().getElement(0).getName());
            return;
        }
        try {
            Attribute att = getElements().getElement(0).getAttributes().getAttribute(Constants.Attributes.ROOTID);

            String rootId = att.getValue();
            // replace the first or running-idx number with the root-id + "." + running-idx
            for (int i = 0; i < getElements().size(); i++) {
//////System.out.println("patchRunningNumber 1 " );
                /*
                 * Running Number
                 * 
                 */
                // get current number
                PseudoElement el = getElements().getElement(i);
                String tempId = el.getRunningIdx();
                // replace id
                el.setRunningIdx(rootId + "." + Utils.padFront(tempId, "0", Constants.Padding.PAD_ATT));
//////System.out.println("patchRunningNumber 2 " );
                /*
                 * 
                 *  conver function: attrib to rooted number to match the no:
                 * 
                 * 
                 */
                Attribute att_function = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.FUNCTION);
                if (att_function != null) {
                    // ////System.out.println("function: " + att_function.getValue());

                    if (!att_function.getValue().contains(".")) {  // relative reference    
                        att_function.setValue(rootId + "." + Constants.Ids.FUNICTIONS + "." + Utils.padFront(att_function.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                //////System.out.println("patchRunningNumber 3 " );
                /*
                 * 
                 * Property Number
                 * 
                 * 
                 */
                Attribute att_property = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.PROPERTY);
                if (att_property != null) {
                    //////System.out.println("Property: " + att_property.getValue());
                    if (!att_property.getValue().contains(".")) {  // relative reference    
                        att_property.setValue(rootId + "." + Constants.Ids.PROPERTIES + "." + Utils.padFront(att_property.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                // ////System.out.println("patchRunningNumber 4 " );
                /*
                 * 
                 *component Number
                 * 
                 * 
                 */
                Attribute att_component = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.COMPONENT);
                if (att_component != null) {
                    //  ////System.out.println("Component: " + att_component.getValue());

                    if (!att_component.getValue().contains(".")) {  // relative reference    
                        att_component.setValue(rootId + "." + Constants.Ids.COMPONENTS + "." + Utils.padFront(att_component.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                // ////System.out.println("patchRunningNumber 5 " );
                /*
                 * 
                 *screen Number
                 * 
                 * 
                 */
                Attribute att_screen = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.SCREEN);
                if (att_screen != null) {
                    //      ////System.out.println("Screen: " + att_screen.getValue());

                    if (!att_screen.getValue().contains(".")) {  // relative reference    
                        att_screen.setValue(rootId + "." + Constants.Ids.SCREENS + "." + Utils.padFront(att_screen.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                //  ////System.out.println("patchRunningNumber 6 " );
                /*
                 * 
                 *api Number
                 * 
                 * 
                 */
                Attribute att_api = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.API);
                if (att_api != null) {
                    // ////System.out.println("Api: " + att_api.getValue());

                    if (!att_api.getValue().contains(".")) {  // relative reference    
                        att_api.setValue(rootId + "." + Constants.Ids.APIs + "." + Utils.padFront(att_api.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                // ////System.out.println("patchRunningNumber 7 " );
                /*
                 * 
                 *role Number
                 * 
                 * 
                 */
                Attribute att_role = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.ROLE);
                if (att_role != null) {
                    //////System.out.println("Role: " + att_role.getValue());

                    if (!att_role.getValue().contains(".")) {  // relative reference    
                        att_role.setValue(rootId + "." + Constants.Ids.ROLES + "." + Utils.padFront(att_role.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
//////System.out.println("patchRunningNumber 8 " );
                /*
                 * 
                 * Activity Number
                 * 
                 * 
                 */
                Attribute att_activity = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.ACTIVITY);
                if (att_activity != null) {
                    //////System.out.println("Activity: " + att_activity.getValue());
                    if (!att_activity.getValue().contains(".")) {  // relative reference    
                        att_activity.setValue(rootId + "." + Constants.Ids.ACTIVITIES + "." + Utils.padFront(att_activity.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
//////System.out.println("patchRunningNumber 9 " );
                /*
                 * 
                 * slider number
                 * 
                 * 
                 */
                Attribute att_slider = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.SLIDER);
                if (att_slider != null) {
                    // ////System.out.println("Slider: " + att_slider.getValue());
                    if (!att_slider.getValue().contains(".")) {  // relative reference    
                        att_slider.setValue(rootId + "." + Constants.Ids.SLIDERS + "." + Utils.padFront(att_slider.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                    // ////System.out.println("Slider: out " );
                }
                //////System.out.println("patchRunningNumber 10 " );
                /*
                 * 
                 * ui  number
                 * 
                 * 
                 */
                Attribute att_ui = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.UI);
                if (att_ui != null) {
                    // ////System.out.println("intent-bar: " + att_intentbar.getValue());
                    if (!att_ui.getValue().contains(".")) {  // relative reference   

                        att_ui.setValue(rootId + "." + Constants.Ids.UI + "." + Utils.padFront(att_ui.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                /*
                 * 
                 * intent bar  number
                 * 
                 * 
                 */
                Attribute att_intent = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.INTENT);
                if (att_intent != null) {
                    // ////System.out.println("intent-bar: " + att_intentbar.getValue());
                    if (!att_intent.getValue().contains(".")) {  // relative reference   

                        att_intent.setValue(rootId + "." + Constants.Ids.INTENT + "." + Utils.padFront(att_intent.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                /*
                 * 
                 * intent bar  number
                 * 
                 * 
                 */
                Attribute att_intentbar = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.INTENTBAR);
                if (att_intentbar != null) {
                    // ////System.out.println("intent-bar: " + att_intentbar.getValue());
                    if (!att_intentbar.getValue().contains(".")) {  // relative reference   

                        att_intentbar.setValue(rootId + "." + Constants.Ids.INTENTBAR + "." + Utils.padFront(att_intentbar.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }
                /*
                 * 
                 * config number
                 * 
                 * 
                 */
                Attribute att_config = getElements().getElement(i).getAttributes().getAttribute(Constants.Attributes.CONFIG);
                if (att_config != null) {
                    // ////System.out.println("config: " + att_config.getValue());
                    if (!att_config.getValue().contains(".")) {  // relative reference   

                        att_config.setValue(rootId + "." + Constants.Ids.SCREENCONFIGURATIONS + "." + Utils.padFront(att_config.getValue(), "0", Constants.Padding.PAD_ATT));
                    } else {
                        // absolute reference dont change the number
                    }
                }


                //////System.out.println("patchRunningNumber 11 " );
                /*
                 * 
                 * Id Number
                 * 
                 * 
                 */
                //PseudoElement elem = getElements().getElement( i  );
                Attribute att_no = getElements().getElement( i  ).getAttributes().getAttribute(Constants.Attributes.NO);
                if (att_no != null) {
                    try {
                        //////System.out.println(" ------------------ ") ;
                        //////System.out.println("patchRunningNumber A1 " + getElements().getElement( i  ).getName()  + " i: " + i  + "  pos: " + getElements().getElement( i  ).getPosition() + " size: "+ getElements().size() );
                        PseudoElement parent = getElements().getParent(  getElements().getElement( i  )  );
                        PseudoElement parentparent = getElements().getParent(  parent );
                        //////System.out.println("patchRunningNumber A2");
                        // determine parenet of the element and fix the number 
                        //////System.err.println("No: " + att_no.getValue());
                        if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.FUNCTIONS)) {
                            //////System.out.println("patchRunningNumber B3");
                            att_no.setValue(rootId + "." + Constants.Ids.FUNICTIONS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                          //  ////System.out.println("patchRunningNumber B31");
                        } else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.PROPERTIES)) {
                            // ////System.out.println("patchRunningNumber B4");
                            att_no.setValue(rootId + "." + Constants.Ids.PROPERTIES + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.SCREENS)) {
                          //   ////System.out.println("patchRunningNumber B5");
                            att_no.setValue(rootId + "." + Constants.Ids.SCREENS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.APIs)) {
                           //  ////System.out.println("patchRunningNumber B6");
                            att_no.setValue(rootId + "." + Constants.Ids.APIs + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.COMPONENTS)) {
                           //  ////System.out.println("patchRunningNumber B7");
                            att_no.setValue(rootId + "." + Constants.Ids.COMPONENTS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.ROLES)) {
                           //  ////System.out.println("patchRunningNumber B8");
                            att_no.setValue(rootId + "." + Constants.Ids.ROLES + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.ACTIVITIES)) {
                          //   ////System.out.println("patchRunningNumber B9");
                            att_no.setValue(rootId + "." + Constants.Ids.ACTIVITIES + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } 
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.SLIDERS)) {
                            // ////System.out.println("patchRunningNumber B10");
                            att_no.setValue(rootId + "." + Constants.Ids.SLIDERS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } 
                        // parent version
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.SHAREDSLIDERS)) {
                          //   ////System.out.println("patchRunningNumber B11");
                            att_no.setValue(rootId + "." + Constants.Ids.SLIDERS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                           //  ////System.out.println("patchRunningNumber B12");
                        } 
                        // PARENT'S PARENT
                        else if (parentparent != null && parentparent.getName().equalsIgnoreCase(Constants.Element.SHAREDSLIDERS)) {
                          //   ////System.out.println("patchRunningNumber B11");
                            att_no.setValue(rootId + "." + Constants.Ids.SLIDERS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                           //  ////System.out.println("patchRunningNumber B12");
                        }                         
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.SCREENCONFIGURATIONS)) {
                         //    ////System.out.println("patchRunningNumber B13");
                            att_no.setValue(rootId + "." + Constants.Ids.SCREENCONFIGURATIONS + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } 
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.INTENTBARS )) {
                         //    ////System.out.println("patchRunningNumber B13");
                            att_no.setValue(rootId + "." + Constants.Ids.INTENTBAR + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        } 
                        else if (parent != null && getElements().getElement( i  ).getName().equalsIgnoreCase(Constants.Element.INTENTBAR)) {
                          //  ////System.out.println("patchRunningNumber B1");
                            att_no.setValue(rootId + "." + Constants.Ids.INTENTBAR + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                           // ////System.out.println("patchRunningNumber B2");
                        } 
                        // parent version
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.SHAREDUIS)) {
                         //    ////System.out.println("patchRunningNumber B13");
                            att_no.setValue(rootId + "." + Constants.Ids.UI + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        }                       
                        // PARENT'S PARENT
                        else if (parentparent != null && parentparent.getName().equalsIgnoreCase(Constants.Element.SHAREDUIS)) {
                         //    ////System.out.println("patchRunningNumber B13");
                            att_no.setValue(rootId + "." + Constants.Ids.UI + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        }           
                        // parent version
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.INTENTS)) {
                         //    ////System.out.println("patchRunningNumber B13");
                            att_no.setValue(rootId + "." + Constants.Ids.INTENT + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        }  
                        // parent version
                        else if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.SLIDERINTENTS)) {
                         //    ////System.out.println("patchRunningNumber B13");
                            att_no.setValue(rootId + "." + Constants.Ids.INTENT + "." + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        }                        
                        else {
                            att_no.setValue(rootId + "-" + Utils.padFront(att_no.getValue(), "0", Constants.Padding.PAD_ATT));
                        }
                    } catch (Exception e) {
                        ////System.out.println("patchRunningNumber B " + e.toString());
                    }
                    
                    
                }



            }
        } catch (Exception e) {
            ////System.out.println("patchRunningNumber A " + e.toString());
        }
    }

    protected void patchReplaceProperty() {
        ////System.out.println("");
        ////System.out.println("patch Replace Property: ");
        int replace_cnt = 0;
        int replaced_cnt = 0;
        if (getElements().size() == 0) {
            ////System.out.println(" Missing Elements ");
            return;
        }
        try {

            for (int i = 0; i < getElements().size(); i++) {
                //  ////System.out.println("Replace Property 1");
                PseudoElement el = getElements().getElement(i);
                ////System.out.println(" -----------------------------------------------------");
                ////System.out.println("  Element:  " + el.getName() + " id:" + el.getId());
                ////System.out.println(" -----------------------------------------------------");

                // replace-function
                // find string in text
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_FUNCTION.toLowerCase())) {
                    ////System.out.println(" 1) Replace Function");
                    replace_cnt++;
                    replaceAttribute(el, Constants.Attributes.FUNCTION, Constants.Replace.REPLACE_FUNCTION);
                }

                // replace-property
                // find string in text
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_PROPERTY.toLowerCase())) {
                    ////System.out.println("   2) Replace Property");
                    replace_cnt++;

                    replaceAttribute(el, Constants.Attributes.PROPERTY, Constants.Replace.REPLACE_PROPERTY);
                }


                // replace-function
                // find string in text
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_SCREEN.toLowerCase())) {
                    ////System.out.println("   3) Replace Screen");
                    replace_cnt++;

                    replaceAttribute(el, Constants.Attributes.SCREEN, Constants.Replace.REPLACE_SCREEN);
                }

                // find string in text
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_COMPONENT.toLowerCase())) {
                    ////System.out.println("  4) Replace Component : " + el.getName() + "    " + el.getRunningIdx());
                    replace_cnt++;

                    replaceAttribute(el, Constants.Attributes.COMPONENT, Constants.Replace.REPLACE_COMPONENT);
                }

                // find string in text
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_API.toLowerCase())) {
                    ////System.out.println("  5 Replace API");
                    replace_cnt++;

                    replaceAttribute(el, Constants.Attributes.API, Constants.Replace.REPLACE_API);
                }
                // find string in text
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_ROLE.toLowerCase())) {
                    ////System.out.println("  6 Replace Role");
                    replace_cnt++;

                    replaceAttribute(el, Constants.Attributes.ROLE, Constants.Replace.REPLACE_ROLE);
                }

                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_WORD.toLowerCase())) {
                    ////System.out.println("  7 Replace Word");
                    replace_cnt++;

                    replaceWord(el, Constants.Attributes.WORD, Constants.Replace.REPLACE_WORD);
                }


            } // for


        } catch (Exception e) {
            ////System.out.println(e.toString());
        }

        ////System.out.println("Replace Property found: " + replace_cnt);
        ////System.out.println("Replace Property replaced: " + replaced_cnt);
    }

    protected void replaceWord(PseudoElement el, String attName, String replaceName) {
        // find the parent of the element
        Attribute parent_prop = null;

        // look in self before searching for parent
        if (el.getAttributes().getAttribute(attName) != null) {
            parent_prop = el.getAttributes().getAttribute(attName);
        } else {
            // look in parents
            parent_prop = getParentAncestor(el, attName);
        }

        if (parent_prop != null) {

            // go get the referenced element
            Attribute word = new Attribute(Constants.Attributes.WORD, parent_prop.getValue());

            // ////System.out.println("Replace Property 4 no name: " + no.getName() + "  value: " + no.getValue());
            // PseudoElement prop_el = getElements().getElementFromAttribute(no);

            //if (prop_el != null) {
            // ////System.out.println("       Replace Property 5 " + prop_el.getName());
            while (el.getName().toLowerCase().indexOf(replaceName.toLowerCase()) >= 0) {
                int pos1 = el.getName().toLowerCase().indexOf(replaceName.toLowerCase());

                if (pos1 == -1) {
                    break;
                }
                int pos2 = pos1 + (replaceName.length() - 1);
                String one = el.getName().substring(0, pos1);  // get front part of string
                String two = "";
                if (el.getName().length() > pos2 + 1) {
                    two = el.getName().substring(pos2 + 1);  // get back end of string
                }
                el.setName(one + word.getValue() + two);
                //replaced_cnt++;
            }
            //}
        }

    }

    protected void replaceAttribute(PseudoElement el, String attName, String replaceName) {
        // find the parent of the element
        Attribute parent_prop = null;

        // look in self before searching for parent
        if (el.getAttributes().getAttribute(attName) != null) {
            parent_prop = el.getAttributes().getAttribute(attName);
        } else {
            // look in parents
            parent_prop = getParentAncestor(el, attName);
        }

        // ////System.out.println("replaceAttribute 1 " + el.getName());

        if (parent_prop != null) {

            // go get the referenced element
            Attribute no = new Attribute(Constants.Attributes.NO, parent_prop.getValue());

            // ////System.out.println("Replace Property 4 no name: " + no.getName() + "  value: " + no.getValue());
            PseudoElement prop_el = getElements().getElementFromAttribute(no);

            if (prop_el != null) {
                // ////System.out.println("       Replace Property 5 " + prop_el.getName());
                while (el.getName().toLowerCase().indexOf(replaceName.toLowerCase()) >= 0) {
                    int pos1 = el.getName().toLowerCase().indexOf(replaceName.toLowerCase());

                    if (pos1 == -1) {
                        break;
                    }
                    int pos2 = pos1 + (replaceName.length() - 1);
                    String one = el.getName().substring(0, pos1);  // get front part of string
                    String two = "";
                    if (el.getName().length() > pos2 + 1) {
                        two = el.getName().substring(pos2 + 1);  // get back end of string
                    }
                    el.setName(one + prop_el.getName() + two);
                    //replaced_cnt++;
                }
            }
        }
    }

    protected Attribute getParentAncestor(PseudoElement start_el, String attName) {
        String rc = "";
        PseudoElement pel = null;

        /*
         * 
         * Find parent with property attribute
         * 
         */

        pel = getElements().getParent(start_el);
        // hunt for parent with a specific property 
        // such as
        // property
        // screen
        // function
        // api
        while (pel != null && pel.getPosition() > 0) {

            if (pel != null) {
                //////System.out.println("getParentAncestor 4 " + attName);
                // Attribute att_property = pel.getAttributes().getAttribute(Constants.Attributes.PROPERTY);
                Attribute att_property = pel.getAttributes().getAttribute(attName);
                if (att_property != null) {
                    //////System.out.println("getParentAncestor 5 " + att_property.getValue());
                    return att_property;
                }
            }

            pel = getElements().getParent(pel);
        }

        return null;
    }

    protected void patchBadSpelling() {
        if (getElements().size() == 0) {
            return;
        }

        FixerHelper fx = new FixerHelper(getElements());
        fx.fix();



    }

    protected void patchInvitationTab() {
        if (getElements().size() == 0) {
            return;
        }


        try {
            //Attribute att = getElements().getElement(0).getAttributes().getAttribute(Constants.Attributes.NAME);

            // replace the first or running-idx number with the root-id + "." + running-idx
            for (int i = 0; i < getElements().size(); i++) {
                // get current number
                PseudoElement el = getElements().getElement(i);
                if (el.getName().equalsIgnoreCase(Constants.Element.InvitationsScreenTab)
                        || el.getName().equalsIgnoreCase(Constants.Element.InvitationScreenTab)
                        || el.getName().equalsIgnoreCase(Constants.Element.InvitationTab)
                        || el.getName().equalsIgnoreCase(Constants.Element.InvitationsTab)
                        || el.getName().equalsIgnoreCase(Constants.Element.InvitationScreenTab)) {



                    PseudoElement parent = getElements().getParent(el);



                    if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.Extends)) {
                        el.setName(Constants.Element.InvitationsScreenTab);  // correct case
                        if (el.getAttributes().getAttribute(Constants.Attributes.REF) == null) {
                            Attribute att = new Attribute(Constants.Attributes.REF, Constants.TRUE);
                            el.getAttributes().setAttribute(att);
                        }
                    }
                }



            }
        } catch (Exception e) {
            ////System.out.println(e.toString());
        }
    }

    protected void patchRefs() {
        /*
         * 
         */
        if (getElements().size() == 0) {
            return;
        }


        try {

            for (int i = 0; i < getElements().size(); i++) {
                // get current number
                PseudoElement el = getElements().getElement(i);
                PseudoElement parent = getElements().getParent(el);

                if (!getElements().hasChild(el)) {
                    if (el.getAttributes().getAttribute(Constants.Attributes.REF) == null) {

                        if (el.getAttributes().getAttribute(Constants.Attributes.PROPERTY) != null
                                || el.getAttributes().getAttribute(Constants.Attributes.COMPONENT) != null) {

                            if (el.getName().equalsIgnoreCase(Constants.Replace.REPLACE_PROPERTY)
                                    || el.getName().equalsIgnoreCase(Constants.Replace.REPLACE_COMPONENT)) {
                                Attribute att = new Attribute(Constants.Attributes.REF, Constants.TRUE);
                                el.getAttributes().setAttribute(att);
                            }
                        }



                    }
                }


                if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.Extends)) {

                    if (el.getAttributes().getAttribute(Constants.Attributes.REF) == null) {
                        Attribute att = new Attribute(Constants.Attributes.REF, Constants.TRUE);
                        el.getAttributes().setAttribute(att);
                    }
                }

            }
        } catch (Exception e) {
            ////System.out.println(e.toString());
        }
    }

    protected void patchEventRef() {
        if (getElements().size() == 0) {
            return;
        }


        try {

            for (int i = 0; i < getElements().size(); i++) {
                // get current number
                PseudoElement el = getElements().getElement(i);
                PseudoElement parent = getElements().getParent(el);


                if (parent != null && parent.getName().equalsIgnoreCase(Constants.Element.Extends)) {

                    if (el.getAttributes().getAttribute(Constants.Attributes.REF) == null) {
                        Attribute att = new Attribute(Constants.Attributes.REF, Constants.TRUE);
                        el.getAttributes().setAttribute(att);
                    }
                }

            }
        } catch (Exception e) {
            ////System.out.println(e.toString());
        }
    }

    protected void patchAPITab() {
        if (getElements().size() == 0) {
            return;
        }


        try {

            // replace the first or running-idx number with the root-id + "." + running-idx
            for (int i = 0; i < getElements().size(); i++) {
                // get current number
                PseudoElement el = getElements().getElement(i);
                if (el.getName().equalsIgnoreCase(Constants.Element.API)
                        || el.getName().equalsIgnoreCase(Constants.Element.APIs)) {

                    el.setName(Constants.Element.APIs);  // correct case
                }



            }
        } catch (Exception e) {
            ////System.out.println(e.toString());
        }
    }

    protected void patchMEO() {
        if (getElements().size() == 0) {
            return;
        }

        for (int i = 0; i < getElements().size(); i++) {
            PseudoElement el = getElements().getElement(i);
            if (el.getElement().equals(Constants.Element.REQUIREMENT)) {
                if (el.getAttributes().getAttribute(Constants.Attributes.MEO) == null) {
                    Attribute att = new Attribute(Constants.Attributes.MEO, Constants.Default.MEO);
                    // get current number
                    el.getAttributes().setAttribute(att);
                }
            }
        }
    }

    protected boolean validateAtts(Attribute[] atts) {
        boolean rc = true;
        // is array proper
        if (atts == null) {
            ////System.out.println("validateAtts  atts  are  null ");
            return false;
        }
        // are array values not null
        for (int i = 0; i < atts.length; i++) {

            // are array values not nul
            if (atts[i] == null) {
                rc = false;
                ////System.out.println("validateAtts  atts[" + i + "]: null ");
            } else {
                if (atts[i].getValue() == null) {
                    rc = false;
                    ////System.out.println("validateAtts  atts[" + i + "]: " + atts[i].getName() + "  value: " + atts[i].getValue());
                }
            }
        }

        return rc;
    }

    protected void buildPluckElements() {

        // loop through elements
        int sz = getElements().getFileName().getConfiguration().size(Constants.Source.PLUCK);

        if (sz == 0) {
            return;
        }// no [pluck: ]


        for (int i = getElements().size() - 1; i > 0; i--) {
            PseudoElement elm = getElements().getElement(i);
            boolean found = false;
            // loop through configuration 
            for (int k = 0; k < sz; k++) {
                Attribute pluckatt = getElements().getFileName().getConfiguration().getAttribute(Constants.Source.PLUCK, k);
                // compare config and the element         
                if (pluckatt != null && elm.getElement().equalsIgnoreCase(pluckatt.getValue())) {
                    found = true;
                }
            }
            if (!found) {
                // remove it
                getElements().remove(i);
            }
        }

        for (int i = 0; i < getElements().size(); i++) {
            PseudoElement elm = getElements().getElement(i);
            elm.setIdx(i);
            //////System.out.println(" element: " + elm.getElement() + " name: " + elm.getName());

        }
    }

    protected void buildSummaryElements() {
        PseudoElements elements = getElements(); // get loaded elements
        Configuration config = elements.getFileName().getConfiguration();

        if (config.isEmpty()) { // no summary
            return;
        }
        if (!config.hasCommands()) { // no commands then no summary
            return;
        }

        Summary summaryElements = null; // target of summary outputs
        try {
            summaryElements = new Summary(getElements().getFileName().getConfiguration());
        } catch (Exception e) {
        }

        for (int i = 0; i < elements.size(); i++) {

            PseudoElement e = elements.getElement(i);
            ////System.out.println(" e: " + e.getName());
            summaryElements.addPseudoElement(e);
            ////System.out.println("---------------------------");
        }

        setElements(summaryElements);

//        Calculate calc = new Calculate(summaryElements, config);
        //      calc.calculate();
        // summaryElements.clearTempAttributes(); // get rid of temp data holders
        //summaryElements.clearTempAttributes(); // get rid of temp data holders
    }

    protected void buildSummaryElements2() {
        PseudoElements elements = getElements(); // get loaded elements
        Configuration config = elements.getFileName().getConfiguration();

        if (config.isEmpty()) { // no summary
            return;
        }
        if (!config.hasCommands()) { // no commands then no summary
            return;
        }

        boolean newNest = true;

        boolean hasFunctions = config.hasCommandFunctions();
        boolean hasNests = config.hasNests();
        boolean hasGroups = config.hasGroups();
        boolean hasSums = config.hasSumCommandFunctions();
        boolean hasCounts = config.hasCountCommandFunctions();
        boolean hasAvgs = config.hasAvgCommandFunctions();
        // get all the summary info


        Attribute[] attsNestBy = elements.getFileName().getConfiguration().getNestByArray();
        Attribute[] attsCountValues = elements.getFileName().getConfiguration().getCountSourceArray();
        // showAttributes(attsCountValues, "show attsCountValues 1");
        //Attribute[] attsCount = elements.getFileName().getConfiguration().getCountSourceArray();
        Attribute[] attsGroupBy = elements.getFileName().getConfiguration().getGroupByArray();
        Attribute[] attsSum = elements.getFileName().getConfiguration().getSumArray();
        Attribute[] attsAvg = elements.getFileName().getConfiguration().getAvgArray();

        PseudoElements summaryElements = null; // target of summary outputs
        try {
            summaryElements = new PseudoElements();
        } catch (Exception e) {
        }


        for (int i = 0; i < elements.size(); i++) {
            PseudoElement e = elements.getElement(i);
            PseudoElement clone_e = null;
            //////System.out.println("------------------------------------------------------------------------------------------- ");
            //////System.out.println("Element: " + e.getName());
            // showElement(e);

            if (config.isZeroElement(e)) {
                // clone
                // add as is
                clone_e = config.getMinimumFunctionCopy(e);

                //clone_e.setIdx(summaryElements.size()); // update numbers
                summaryElements.addPseudoElement(clone_e);
                // elements.showElement(clone_e, "clone_e:");

                // config.addCountCommandFunctions(clone_e);
                // config.addSumCommandFunctions(clone_e);
                // config.addAvgCommandFunctions(clone_e);
                config.addCommandFunctions(clone_e);
            }

            if (hasNests) {
                // is this a nest
                // //////System.out.println("  hasNests ");
                if (config.isNestBy(e)) {
                    e.getNestByValues(attsNestBy);// get values from e to use in search
                    ///////////////////////////
                    // attempt to find
                    clone_e = summaryElements.getLastElementByAttriubutes(attsNestBy);
                    //////////////////////////////////
                    //  add a nest if not found
                    ///////////
                    if (clone_e == null) {
                        //clone_e = e.clone();
                        //clone_e.setIdx(summaryElements.size() + 1); // update numbers
                        clone_e = config.getMinimumFunctionCopy(e);
                        ////////////
                        //  add the functions sum, count, avg
                        ////////
                        if (config.hasCommandFunctions()) {
                            config.addCommandFunctions(clone_e);
                        }
                        summaryElements.addPseudoElement(clone_e); // add element
                    }
                    e.getGroupByValues(attsGroupBy);// get values from e to use in search
                }
            }


            // add groups with no nests
            if (hasGroups) {
                ////////System.out.println("");
                //////System.out.println("  hasGroups - 1 " + config.isGroupBy(e) + " " + config.isNestBy(e));
                if (config.isGroupBy(e) && !config.isNestBy(e)) {
                    //////System.out.println("  isGroup ");
                    // clone
                    // add atts: any count, sum, avg atts with defaults of 0
                    // add to summary
                    // //////System.out.println("is GroupBy name: " + e.getName());
                    //////System.out.println("  hasGroups 2 1");
                    e.getGroupByValues(attsGroupBy);// get values from e to use in search
                    // attempt to find in the summary
                    Attribute lastNestByAtt = null;
                    if (hasNests) {// when nest by then get the last att to limit search
                        lastNestByAtt = attsNestBy[attsNestBy.length - 1];
                    }
                    //////System.out.println("  hasGroups - 3");
                    clone_e = summaryElements.getLastElementByAttributes(attsGroupBy, lastNestByAtt);
                    //////System.out.println("  hasGroups - 4");
                    /////////////////////////
                    // add a group if not found
                    /////////////////
                    if (clone_e == null) {
                        //////System.out.println("  hasGroups - 5");
                        //clone_e = e.clone();
                        //clone_e.setIdx(summaryElements.size() + 1); // update numbers
                        clone_e = config.getMinimumFunctionCopy(e); // min copy and initialize counts, sums, avg
                        summaryElements.addPseudoElement(clone_e);
                    }

                    //////System.out.println("  hasGroups - 6");

                    e.getCountValues(attsCountValues);
                    clone_e.countAttributes(config.getCountTargetArray(attsCountValues));

                    e.getSumValues(attsSum);
                    //  clone_e.sumAttributes( config.getSumTargetArray( attsSum) );
                    clone_e.sumAttributes(attsSum);
                    e.getAvgValues(attsAvg);
                    clone_e.avgAttributes(attsAvg);

                }

            }

            /////////////////
            // handle the situation when no groupby and no nests
            //////////
            if (hasCounts && !hasGroups && !hasNests) {
                //     //////System.out.println("  isGroupby "+config);
                if (config.isCount(e) && !config.isGroupBy(e) && !config.isNestBy(e)) {
                    e.getCountValues(attsCountValues);
                    clone_e = summaryElements.getZeroElement();
                    clone_e.countAttributes(attsCountValues);
                }
            }
            if (hasSums) {
                if (config.isSum(e) && !config.isGroupBy(e) && !config.isNestBy(e)) {
                    e.getSumValues(attsSum);
                    clone_e = summaryElements.getZeroElement();
                    clone_e.sumAttributes(attsSum);
                }
            }
            if (hasAvgs) {
                if (config.isAvg(e) && !config.isGroupBy(e) && !config.isNestBy(e)) {
                    e.getAvgValues(attsAvg);
                    clone_e = summaryElements.getZeroElement();
                    clone_e.avgAttributes(attsAvg);
                }
            }

        }


        setElements(summaryElements);
        //backwardPass(config,summaryElements);
        //recursiveTotals( summaryElements,   config );
        Calculate calc = new Calculate(summaryElements, config);
        calc.calculate();
        //  summaryElements.clearTempAttributes(); // get rid of temp data holders
    }

    public void showElement(PseudoElement e) {
        ////System.out.println("   Element:" + e.getName());
        for (int i = 0; i < e.getAttributes().size(); i++) {
            Attribute att = e.getAttributes().getAttribute(i);
            ////System.out.println("      name: " + att.getName() + "  value: " + att.getValue());
        }
    }

    public void showAttributes(Attribute[] atts, String title) {
        ////System.out.println("showAttibutes " + title);
        if (atts == null) {
            ////System.out.println(" showAttributes  null");
        }
        for (int i = 0; i < atts.length; i++) {
            if (atts[i] != null) {
                ////System.out.println("  showAttributes  name: " + atts[i].getName() + "  value: " + atts[i].getValue());
            } else {
                ////System.out.println(" showAttributes  name: atts[" + i + "]=null");
            }
        }
    }

    protected void recursiveTotals(PseudoElements elements, Configuration config) {
        Calculate calc = new Calculate(elements, config);
        calc.calculate();
    }

    protected void backwardPass(Configuration config, PseudoElements elements) {
        ////System.out.println("backwardPass 1");
        //   PseudoElement[] nestElems = config.getEmptyNestElmentsArray();
        //PseudoElements elements = getElements(); // get loaded elements
        //Configuration config = elements.getFileName().getConfiguration();

        if (config.isEmpty()) { // no summary
            return;
        }

        if (!config.hasCommands()) { // no commands then no summary
            return;
        }

        boolean hasNests = config.hasNests();
        boolean hasGroups = config.hasGroups();
        boolean hasSums = config.hasSumCommandFunctions();
        boolean hasCounts = config.hasCountCommandFunctions();
        boolean hasAvgs = config.hasAvgCommandFunctions();

        // get array of all count to store sums
        Attribute[] attsCount = config.getCountSourceArray();
        Attribute[] attsSum = config.getSumArray();

        // showAttributes(attsCount, "Counts");
        // showAttributes(attsSum, "Sum");
        // cruz throug elements back ward
        for (int i = elements.size() - 1; i >= 0; i--) {

            ////System.out.println("--------------------------------------------");
            PseudoElement e = elements.getElement(i);

            if (config.isZeroElement(e)) {
                // skip for now
            }
            //
            if (hasNests) {


                if (e.getName().trim().length() > 0 && !config.isNestBy(e)) {
                    ////System.out.println(" sum, count, avg  " + e.getName() + "  value: ");
                    // add to nests

                    PseudoElement nest_e = config.getNextNestUpFrom(i, elements);// get the next level up

                    if (nest_e != null) {// when null then done
                        ////System.out.println("  count, sum, avg to " + nest_e.getName());
                        /*        ////System.out.println("backwardPass 10 nest_e: " + nest_e.getName());
                         ////System.out.println("backwardPass 10 e: " + e.getName());
                         // loop through all the counts
                         //count are not good
                         e.getCountValues(attsCount); // get count values from current element
                         for (int k = 0; k < attsCount.length; k++) {
                         ////System.out.println("    name: " + attsCount[k].getName() + "  value: " + attsCount[k].getValue());
                         }
            
                         nest_e.countAttributes(attsCount); // add counts to nest element
                         */
                    }
                } else {
                    ////System.out.println(" nest  " + e.getName());
                }

            }
        }

        ////System.out.println("backwardPass out");
    }

    protected void buildSums() {

        PseudoElements elements = getElements();
        Attribute[] attsNestBy = elements.getFileName().getConfiguration().getNestByArray();
        Attribute[] attsGroupBy = elements.getFileName().getConfiguration().getGroupByArray();
        Attribute[] attsSum = elements.getFileName().getConfiguration().getSumArray();
        Attribute[] attsAvg = elements.getFileName().getConfiguration().getAvgArray();

        //  validateAtts(attsNestBy);
        PseudoElements summaryElements = null;

        if (attsGroupBy != null) { // stop if no group-by atts

            try {
                summaryElements = new PseudoElements();
            } catch (Exception e) {
            }

            for (int i = 0; i < elements.size(); i++) {
                ////System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx--------------------------------------");
                PseudoElement el = elements.getElement(i);
                el.getNestByValues(attsNestBy);
                el.getGroupByValues(attsGroupBy);
                el.getSumValues(attsSum); // get current values from elements
                el.getAvgValues(attsAvg); // get current values from elements
                el.getSumValues(attsAvg); // get current values from elements
                //validateAtts(attsAvg);

                if (attsGroupBy == null) {
                    ////System.out.println(" null attsFind");
                    break;
                }


                /*
                 * move the group by elements these are sperarte from nests
                 */
                // is element already in list if true then skip it... avoid duplicates
                // stop the unmatched group bys from going in
                //       validate the current elements attributs
                ////System.out.println("buildSums 3");
                PseudoElement chkGroupBy_el = summaryElements.getElementByAttriubutes(attsGroupBy);
                ////System.out.println("buildSums 4");

                if (chkGroupBy_el == null) {// not in summary so attempt to add
                    ////System.out.println("buildSums 4a");
                    // add new element
                    if (validateAtts(attsGroupBy) && validateAtts(attsSum)) { // check for null atts
                        PseudoElement sum_el = elements.summerizeByAttributes(attsGroupBy);
                        if (sum_el != null) {
                            ////System.out.println("buildSums 4c");
                            summaryElements.addPseudoElement(sum_el);
                            sum_el.sumAttributes(attsSum);  // add values to sum element
                            sum_el.avgAttributes(attsAvg);
                        }
                    }
                } else {

                    chkGroupBy_el.sumAttributes(attsSum); // add values to sum element
                    chkGroupBy_el.avgAttributes(attsAvg); // add avg value to avg element
                }

            }
            // summaryElements.clearTempAttributes(); // get rid of temp data holders
            setElements(summaryElements);
        }

    }
    /*
     * reads, parses, and loads Elements from stream
     *
     */

    public PseudoElements Import() throws IOException {
        String line = null;

        // load lines from text
        if (getElements().size() == 0) { // when not zero then the list has already been read just need to pre and post process



            while ((line = readLine()) != null) {
                loadLine(line);

            }
            getInBuffer().close();

        }

        postProcess();

        return getElements();




    }

    /*
     *
     * import from another list and make a copy
     *
     */
    //public List Import(PseudoElements elements) throws IOException {
    public PseudoElements Import(PseudoElements elements) throws IOException {

        String line = null;

        preProcess();


        for (int i = 0; i
                < elements.size(); i++) {
            PseudoElement el = (PseudoElement) elements.get(i);
            getElements().add(el);

        }

        postProcess();

        return getElements(); // return new list




    }
    // public List Import(PseudoElements elements, InputStreamReader inStreamReader) throws IOException {

    public PseudoElements Import(PseudoElements elements, InputStreamReader inStreamReader) throws IOException {

        setInBuffer(new BufferedReader(inStreamReader));
        setElements(elements);
        return Import();

    }


    /*
     *
     */
    protected String readLine() throws IOException {
        return getInBuffer().readLine();




    }
    private String mindjetPrefix = "";

    public String getMindjetPrefix() {
        return mindjetPrefix;
    }

    public void setMindjetPrefix(String mindjetPrefix) {
        this.mindjetPrefix = mindjetPrefix;
    }
    private int lineCount = 0; // counter for alternate numbering

    protected void loadLine(String line) {
        //////System.out.println("loadLine: " + line);
        // get id
        // get element name
        // get attributes
        // push element name
        String ln = "";

        //ProcessLogger pl = new ProcessLogger(getOutFileName());
        //////System.out.println("loadLine 1 " + line);


//////System.out.println("mindjet prefix: [" + getMindjetPrefix() +"]" );

        if (line == null) {
            return;
        }



        String line2 = line.trim();



        /*
         int firstDigit = 0;
         for (firstDigit = 0; firstDigit < line2.length(); firstDigit++) {
         char ch = line2.charAt(firstDigit);
         if (ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
         break;
         }

         }

         line2 = line2.substring(firstDigit, line2.length());
         */
        // clean out mindjets extar chars 0
        for (int t = 0; t < line2.length(); t++) {
            char c = line2.charAt(t);
            if ((int) c > 31 && (int) c < 127) {
                // ////System.out.println("add c: " + c );
                ln += c;
            }

        }

        //////System.out.println("loadLine A  [" + ln + "]");
        if (ln.trim().length() == 0) {
            return;
        }

        ln = ln.trim();

        if (ln.contains(Constants.MindJet.TitleBreak)) {
            ////System.out.println("set mindjet prefix");
            setMindjetPrefix(Constants.MindJet.Prefix);
            return;
        }

        if (ln.startsWith(Constants.MindJet.SectionBreak)) {

            return;
        }

        lineCount++; // counter for alternate numbering
        if (getMindjetPrefix().length() > 0) {
            if (lineCount == 1) {

                ln = "1 " + ln; // patch mindjets missing numbers

            } else {

                ln = getMindjetPrefix() + ln; // patch mindjets missing numbers


            }
        }
        //////System.out.println("loadLine B  [" + ln + "]");

        /*
         // test code
         {
         for (int t = 0; t < ln.length(); t++) {
         ////System.out.println(" char: " + ln.charAt(t) + " acsii:" + (int)ln.charAt(t) + "  no: " + Integer.toHexString(ln.charAt(t)));
         }
         }
         */


        //  ////System.out.println("loadLine 2 "+ ln);
        /////////////////////////
        // deal with comments
        ////////////////

        if (ln.length() == 0 || ln.charAt(0) == '#') {
            // getTopStack().push("<!-- " + ln);
            // getBottomStack().push(" -->");
            // pl.WriteNoDate("<!-- " + ln + " -->");
            PseudoComment comment = new PseudoComment(ln);
            getElements().addPseudoElement(comment);

            return;
        }
        // ////System.out.println("loadLine 3 ");
        ////////////////////////////////
        // must start with a number
        //////////////////
        if (ln.charAt(0) != '0' && ln.charAt(0) != '1' && ln.charAt(0) != '2' && ln.charAt(0) != '3' && ln.charAt(0) != '4' && ln.charAt(0) != '5' && ln.charAt(0) != '6' && ln.charAt(0) != '7' && ln.charAt(0) != '8' && ln.charAt(0) != '9') {
            PseudoComment comment = new PseudoComment(ln);
            getElements().addPseudoElement(comment);
            return;
        }
        //////System.out.println("loadLine 4");
        ////////////////////////////////////////////
        // get ID,  the level and the child position
        ///////////////////////
        int periods = 0;
        String child = "";
        int childIndex = 0;
        int type = -1; // unassigned
        String id = "";
        int i = 0;
        // get id
        // get the level and the child position
        String numeric = "0123456789.,";
        //////System.out.println("loadLine 5");


        for (i = 0; ln != null && i < ln.length() && numeric.indexOf(ln.charAt(i)) >= 0; i++) {

            char ch = ln.charAt(i);

            if (ch == '.') {
                periods++;
                child = "";
            }
            if (ch != '.') {
                child += ch;
            }

            id += ch; // add to id

        } // remove tailing periods ... word does this
        if (id.endsWith(",")) {
            id = id.substring(0, id.lastIndexOf(','));

        }
        if (id.endsWith(".")) {
            id = id.substring(0, id.lastIndexOf('.'));

        } //id = getPrefix() + id; // makes consecutive wbs unique
        // finsh with ID
        // Load the  child to a number
        try {
            childIndex = new Integer(child).intValue();

            if (childIndex > 0) {
                //////System.out.println("child a");
                childIndex = childIndex - 1;
            }
        } catch (Exception e) {
            //  ////System.out.println("child b");
            childIndex = 0;

        } // set level

        //  ////System.out.println("id = " + id);
        if (id.equals("0")) {
            setLevel(0);
        } else {
            setLevel(periods + 1);

        } // determine type:
        //  # = comment
        //  0-9 = start of id
        //  if not one of the above the type = 0
        //  if id is not blank then look
        //
        //////////////////////////////////////////////////////////////////////////
        // move on to the element name
        // boolean foundFirstBlank = false;
        // boolean isComment = false;
        String elementName = ""; // default is blank
        //////System.out.println("elementname: " + elementName );

        // handle [definition: asdkf]
        boolean useFirstAttributeAsElementName = false; // flag to indicate that the first attributes name should be used for the element name

        String unqNameValue = "";
        Attributes attribs = new Attributes(); // store text bracked with [ ]


        char ch = ' ';

        if (i < ln.length()) {
            ch = ln.charAt(i);

            if (i < ln.length()) {
                ch = ln.charAt(i);
            }
        }

        // if the elementName is not defined
        //
        // use text after ID for element name
        //   when getItemTagName() = blank, then use text after ID for element name
        //   when getItemTagName() = blank and text after ID = blank, then use text from first attribute for element name
        // use getItemTagName() for element name
        //   when getItemTagName() <> blank, then use getItemTagName() for element name


        // get the text after ID
        boolean isAttribute = false;
        for (; i < ln.length() && ch != '[' && ch != ']'; i++) {  //loop til [
            ch = ln.charAt(i);
            switch (ch) {
                case '[': // skip
                    isAttribute = true;
                    break;

                case ']': // skip, missing first [ when this happens
                    //isAttribute = true;
                    break;

                case '\t': // skip tabs
                    break;

                default:
                    elementName += ch;

            }
        }

        elementName = elementName.trim();






        ///////////////////////////////////////////////////////////////////////////////////////////
        // get any attributes
        ///////////////
        //String attribs = "";
        boolean isValue = false;
        //String unqNameValue = "" ;
        String attName = "";
        String attValue = "";
        boolean hasAttribs = false;


        for (; i < ln.length(); i++) {
            //////System.out.println("ch: " + ch);
            ch = ln.charAt(i);
            switch (ch) {
                case '[':
                    attName = "";
                    attValue = "";
                    hasAttribs = true;
                    isValue = false;
                    //isAttribute = true;
                    break;
                case ']':
                    isValue = false;  // turn off value collect and turn on name collect
                    //isAttribute = false;
                    if (attValue.trim().length() == 0) {
                        attValue = Constants.TBD;
                    } // check if attribute value ends with a delimiter.  these get special treatment
                    if (attValue.trim().length() > 0) {
                        String lch = "" + attValue.trim().charAt(attValue.trim().length() - 1);
                        if (Constants.DELIMITERS.contains(lch)) {
                            attValue += Constants.TBD;
                        }
                    }
                    //attribs += " " + name.trim() + "=\"" + value.trim() + "\" ";
                    if (attName.toLowerCase().equals(Constants.Attributes.ATT_NAME)) {
                        unqNameValue = attValue;
                    }
                    Attribute att = new Attribute(attName.trim(), attValue.trim());
                    attribs.setAttribute(att);
                    break;
                case '|':
                    isValue = true;
                    break;
                case ':':
                    if (isValue == true) {
                        attValue += ch;
                    }// this alows : in value
                    isValue = true;
                    break;
                case '\t': // skip tabs
                    break;
                default:
                    if (isValue) {
                        attValue += ch;
                    } else {
                        attName += ch;
                    }
            }
        }

        //////////////////////
        // sort out the naming of element
        //////////////

        //////System.out.println("");
//////System.out.print("elementName:"+elementName);
        if (getItemTagName().trim().length() == 0) {
            // this code is used with adhoc imports files to provide an element name
            setItemTagName(getElements().getSource());
            // ////System.out.print(" A");
        }

        if (getItemTagName().trim().length() == 0) {
            if (elementName.length() == 0) {
                // ////System.out.print(" B");
                // elementName is set to the first attribute'firstDigit name
                useFirstAttributeAsElementName = true;

            } // elementName is already set to the text after the ID
        } else {

            if (elementName.length() == 0) {
                // set the element name to the ItemTagName
                //elementName = getItemTagName();
//////System.out.print(" C");
                useFirstAttributeAsElementName = true;
            } else {
                //////System.out.print(" D");
                // use the elementName as the value of the name attribute

                if (attribs.getAttribute(Constants.Attributes.NAME) == null) { // no name so need one
                    attribs.setAttribute(new Attribute(Constants.Attributes.NAME, elementName));
                    unqNameValue = elementName;
                } else {
                    attribs.setAttribute(new Attribute(Constants.Attributes.ELEMENT_TYPE, elementName));
                    //unqNameValue = elementName;
                }

                // set the element name to the ItemTagName
                elementName = getItemTagName();

            }

        }



        // if name and value are not blank the there was a problem closing the last attribute
    /*if (hasAttribs && name.trim().length() == 0 && value.trim().length() == 0) {
    
         //pl.WriteNoDate("<!-- ERROR with attribute syntax in: " + ln + " -->");
         PseudoComment comment = new PseudoComment(" ERROR with attribute syntax in: " + ln);
         getElements().add(comment);
         return;
         }*/
        /////////////////////////////////////////////////////////////////
        // add the element and attribs to the Lines
        // we can have elements with out attribs
        // but we have to have elements
        // a test for element was done above so no need repeat
        // bad attribute formating should be taken care of already so no need to check those

        PseudoElement pe = new PseudoElement();
        // we have already defined some attributes for book keeping
        if (useFirstAttributeAsElementName) {
            Attribute FirstAttribute = attribs.getAttribute(0);
            if (FirstAttribute == null) {
                //elementName = "MISSING-FIRST-ATTRIBUTE-NAME";
                elementName = "";
            } else {
                elementName = FirstAttribute.getName();
                //elementName = getElements().getSource();
                unqNameValue = FirstAttribute.getValue();
                getElements().setSkipAttributes(elementName);
            }
        }

        pe.setId(id.trim());
//////System.out.println("ProperCase: " + getProperCase(   unqNameValue.trim()     ));
        //while(elementName.contains("\"")){  elementName = elementName.replace("\"", "'") ;  }
        while (unqNameValue.indexOf("\"") >= 0) {
            unqNameValue = unqNameValue.replace("\"", "'");
            ////System.out.println("unqNameValue: replace: quot");
        }

        pe.setElement(elementName.trim());
        //  if(pe.getAttributes().getAttribute(Constants.Attributes.NAME) == null){   }// name att not already added
        pe.setName(unqNameValue.trim());
//////System.out.println("unqNameValue: replace: "+ pe.getName()  );   

        if (id.equals("0")) {
            pe.setType(Constants.ElementTypes.FIRST); // top
            //setPrefix(getPrefix() + "0");
        } else {
            pe.setType(Constants.ElementTypes.REGULAR); // regular element
        }
        if (unqNameValue.length() == 0) {
            pe.setType(Constants.ElementTypes.COMMENT);
        }
        // add attributes to element

        //add attribs from attrib list
        for (int k = 0; k < attribs.size(); k++) {
            Attribute att = attribs.getAttribute(k);
            pe.getAttributes().setAttribute(att);
        }

        pe.setIdx(getElements().size());
        pe.setRunningIdx("" + lineCount);
        pe.setChildNo(childIndex);
        pe.setLevel(getLevel());

        if (pe.getElement().equalsIgnoreCase("Indirect-Requirement") || pe.getElement().equalsIgnoreCase("BR") || pe.getElement().equalsIgnoreCase("Functional-Requirement") || pe.getElement().equalsIgnoreCase("Non-Functional-Requirement")) {
            pe.getAttributes().setAttribute(new Attribute("filename", getElements().getFileName().getFileName()));
            pe.getAttributes().setAttribute(new Attribute("source", getElements().getFileName().getPathAndFileName()));

        }



        getElements().addPseudoElement(pe);
//////System.out.println("name: " + pe.getName() + "   child: " + pe.getChildNo() );
        // getStack().push("</" + element + ">"); // push closing element for later
        //   ////System.out.println("loadLine out");
        if (pe.getAttributes().getAttribute(Constants.Attributes.CHARTTYPE) != null) {
            ////System.out.println("  charttype: " + pe.getAttributes().getAttribute(Constants.Attributes.CHARTTYPE).getValue());
        }
    }

    private String getProperCase(String str) {
        String tmp = str.trim().toLowerCase();
        String rc = "";
        for (int i = 0; i < tmp.length(); i++) {
            if (i == 0) {

                rc += tmp.toUpperCase().charAt(i);
            } else {
                rc += tmp.charAt(i);
            }

        }
        return rc;
    }

    public int getLevel() {
        return _level;
    }

    public void setLevel(int _level) {
        this._level = _level;
    }

    public void showList() {
        ////System.out.println(".........................");


        ((PseudoElements) getElements()).showList();
        ////System.out.println(".........................");
    }

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
}
