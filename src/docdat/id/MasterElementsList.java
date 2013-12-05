/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.id;

import docdat.utils.Attribute;
import docdat.utils.Constants;
import java.util.ArrayList;

/**
 *
 * @author wilfongj
 */
public class MasterElementsList extends ArrayList {

    public MasterElementsList() {
    }

    public PseudoElements getElements(int i) {
        if (i < 0 || i > size()) {
            return null;
        }
        return (PseudoElements) get(i);
    }
    PseudoElements currentElements = null;

    public PseudoElements getCurrentElements() {
        return currentElements;
    }

    public void setCurrentElements(PseudoElements currentElements) {
        this.currentElements = currentElements;
    }

    public void postProcess() {

        if (size() == 0) {
            return;
        }

        // loop through all the Element Listsin the Master
        for (int i = 0; i < size(); i++) {

            setCurrentElements(getElements(i));

            // loop through each element 
            for (int j = 0; j < getCurrentElements().size(); j++) {

                PseudoElement el = getCurrentElements().getElement(j);

                // look for elements with replace-property, replace-component, replace-function, replace-api, replace-screen 
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_PROPERTY.toLowerCase())) {
                    //System.out.print("[" + Constants.Replace.REPLACE_PROPERTY + "] ");

                    replaceAttribute(el, Constants.Attributes.PROPERTY, Constants.Replace.REPLACE_PROPERTY);

                }
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_COMPONENT.toLowerCase())) {
                    //System.out.print("[" + Constants.Replace.REPLACE_COMPONENT + "] ");

                    replaceAttribute(el, Constants.Attributes.COMPONENT, Constants.Replace.REPLACE_COMPONENT);

                }
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_FUNCTION.toLowerCase())) {
                    //System.out.print("[" + Constants.Replace.REPLACE_FUNCTION + "] ");

                    replaceAttribute(el, Constants.Attributes.FUNCTION, Constants.Replace.REPLACE_FUNCTION);

                }
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_SCREEN.toLowerCase())) {
                    //System.out.print("[" + Constants.Replace.REPLACE_SCREEN + "] ");

                    replaceAttribute(el, Constants.Attributes.SCREEN, Constants.Replace.REPLACE_SCREEN);

                }
                if (el.getName().toLowerCase().contains(Constants.Replace.REPLACE_API.toLowerCase())) {

                    //System.out.print("[" + Constants.Replace.REPLACE_API + "] ");
                    replaceAttribute(el, Constants.Attributes.API, Constants.Replace.REPLACE_API);

                }

            } // j for
        } // i for

    }

    protected void replaceAttribute(PseudoElement el, String attName, String replaceName) {

        // find the parent with the proper attribute property, component, function, api, screen
        PseudoElement parent = null; //getCurrentElements().getParentFromAttribute(el, attName);

        if (el.getAttributes().getAttribute(attName) != null) {
            parent = el;
        } else {
            parent = getCurrentElements().getParentFromAttribute(el, attName);
        }

        // get the proper attribute value from property, component, function, api, or screen
        if (parent != null) {

            Attribute att = parent.getAttributes().getAttribute(attName);  // this should have the look up value we are looking for

            if (att != null) {

                // go find the element that has an attribute with the value found in the parent
                PseudoElement foundElem = getElementByAttribute(new Attribute(Constants.Attributes.NO, att.getValue()));

                if (foundElem != null) {

                    // find occurances of the replace-xxxx name in the element (el) 
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
                        el.setName(one + foundElem.getName() + two);
                        //replaced_cnt++;
                    }

                }
            }
            // do the replace on this element 
        }

    }

    protected PseudoElement getElementByAttribute(Attribute attToFind) {

        // loop through all the master elements ... brute force search
        for (int i = 0; i < size(); i++) {
            PseudoElements elems = getElements(i);
            // loop through all element
            for (int j = 0; j < elems.size(); j++) {
                // look for element with attribute with a value of attToFind.getValue

                PseudoElement el = elems.getElementFromAttribute(attToFind);
                if (el != null) {
                    return el;
                }
            }
            // return 
        }
        // not found then return null 
        return null;

    }
}
