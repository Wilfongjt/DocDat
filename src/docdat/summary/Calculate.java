/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.summary;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.utils.Attribute;
import docdat.utils.Configuration;
import docdat.utils.Constants;
import java.util.Stack;

/**
 *
 * @author wilfongj
 */
public class Calculate {

    public Calculate(PseudoElements ems, Configuration config) {
        setElements(ems);
        setConfig(config);
    }
    Configuration Config = null;

    public void setConfig(Configuration Config) {
        this.Config = Config;
    }

    public Configuration getConfig() {
        return Config;
    }
    PseudoElements _Elements = null;

    public PseudoElements getElements() {
        return _Elements;
    }

    public void setElements(PseudoElements _Elements) {
        this._Elements = _Elements;
    }

    protected String formatFunctionName(Attribute att) {
        return att.getValue().replace("()", "(" + att.getName() + ")");
    }

    public void calculate() {
        /////////////////////
        // look through nests and functions
        /////////
        for (int configNest_idx = 0; configNest_idx < getConfig().size(); configNest_idx++) {
            Attribute nestTypeAtt = getConfig().getAttribute(configNest_idx);

            for (int nest_idx = 0; nest_idx < getElements().size(); nest_idx++) {
                PseudoElement nest = getElements().getElement(nest_idx);

                if (nest.getName().equalsIgnoreCase(nestTypeAtt.getName())) {

                    //System.out.println("  nest:" + "[" + nest_idx + "]" + nest.getName());
                    double count = 0;
                    double sum = 0;
                    nest_idx++;
                    boolean done = false;

                    while (nest_idx < getElements().size() && !done) {

                        PseudoElement processElm = getElements().getElement(nest_idx);
                        ////System.out.println("   next: " + "[" + nest_idx + "]" + processElm.getName());

                        if (!getConfig().isNestBy(processElm)) { //skip other nests
                            // do some summing
                            //System.out.println("   process: " + "[" + nest_idx + "]" + processElm.getName());

                            for (int j = 0; j < getConfig().size(); j++) {
                                Attribute configFuncAtt = getConfig().getAttribute(j);

                                if (configFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
                                    ////System.out.println("        count " + "[" + j + "]");
                                    Attribute foundAtt = processElm.getAttributes().getAttribute(formatFunctionName(configFuncAtt));

                                    //  //System.out.println("        lookat: " + processElm.getName() + " lookfor: " + configFuncAtt.getName() + "      found: " + foundAtt.getName());

                                    count += new Double(foundAtt.getValue()).doubleValue();

                                    // find and put the value in the nest
                                    Attribute updateAtt = nest.getAttributes().getAttribute(formatFunctionName(configFuncAtt));

                                    updateAtt.setValue(Double.toString(count));

                                }

                                if (configFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
                                    //System.out.println("        sum " + "[" + j + "]");
                                    Attribute foundAtt = processElm.getAttributes().getAttribute(formatFunctionName(configFuncAtt));

                                    sum += new Double(foundAtt.getValue()).doubleValue();
                                    Attribute updateAtt = nest.getAttributes().getAttribute(formatFunctionName(configFuncAtt));
                                    updateAtt.setValue(Double.toString(sum));
                                }
                            }



                        }


                        if (nestTypeAtt.getName().equalsIgnoreCase(getElements().getElement(nest_idx).getName())) {
                            done = true;
                            //System.out.println("   done: " + "[" + nest_idx + "]" + processElm.getName());
                            nest_idx--;
                        } else {
                            nest_idx++;
                        }


                    }// while

                    ////System.out.println("  nest_idx:" + nest_idx);

                }
            }
        }
    }

    public void calculate3() {
        //System.out.println(" caluculate 1");
        // start at bottom elements
        // while not first element

        // first pass to do non group
        //Double[] count = new Double[getConfig().getArraySize(Constants.AttributeValues.COUNT)];
        //Double[] sum = new Double[getConfig().getArraySize(Constants.AttributeValues.SUM)];
        // double sum = 0;
        //double avg = 0;


        /////////////////////
        // look through nests and functions
        /////////
        for (int i = 0; i < getConfig().size(); i++) {

            Attribute att = getConfig().getAttribute(i);

            if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {

                //System.out.println("  " + "[" + i + "]" + att.getName());

                String curNestName = att.getName(); // name of nest cat

                // go through the elements
                for (int k = 0; k < getElements().size(); k++) { // all elements

                    PseudoElement el = getElements().getElement(k);

                    //System.out.println("    " + "[" + k + "]" + el.getName());

                    // process those element that are nests by name
                    if (curNestName.equalsIgnoreCase(el.getName())) {

                        PseudoElement nest = el;
                        double count = 0;
                        double sum = 0;


                        //loop till el is EOL or e.name = curNestname
                        k++; // move to the next element
                        PseudoElement next = getElements().getElement(k);
                        while (next != null && !next.getName().equalsIgnoreCase(curNestName)) {
                            //System.out.println("      " + "[" + k + "]" + next.getName());
                            // loop through all the functions
                            //int c = 0;
                            //int s = 0;
                            for (int j = 0; j < getConfig().size(); j++) {
                                Attribute configFuncAtt = getConfig().getAttribute(j);

                                if (configFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {

                                    Attribute foundAtt = next.getAttributes().getAttribute(formatFunctionName(configFuncAtt));

                                    //System.out.println("      lookat: " + next.getName() + " lookfor: " + configFuncAtt.getName() + "      found: " + foundAtt.getName());

                                    count += new Double(foundAtt.getValue()).doubleValue();

                                    // find and put the value in the nest
                                    Attribute updateAtt = nest.getAttributes().getAttribute(formatFunctionName(configFuncAtt));

                                    updateAtt.setValue(Double.toString(count));


                                }
                                if (configFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
                                    //System.out.println("        sum");
                                    Attribute foundAtt = next.getAttributes().getAttribute(formatFunctionName(configFuncAtt));

                                    //System.out.println("      lookat: " + next.getName() + " lookfor: " + configFuncAtt.getName() + "      found: " + foundAtt.getName());

                                    //sum[s++] += new Double(foundAtt.getValue()).doubleValue();
                                    sum += new Double(foundAtt.getValue()).doubleValue();
                                    Attribute updateAtt = nest.getAttributes().getAttribute(formatFunctionName(configFuncAtt));
                                    updateAtt.setValue(Double.toString(sum));
                                    //s++;
                                }
                            }

                            next = getElements().getElement(k++);
                        }

                        // add result sum count avg to nest

                    } // 

                }// for  elements

            } //for nest by
            /*
            if (nestTypeAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
            String functionName = nestTypeAtt.getValue();
            String functionAttName = Constants.AttributeValues.COUNT.replace("()", "(" + nestTypeAtt.getName() + ")");

            calculateNestByFunctions(functionName, functionAttName);


            }

            if (nestTypeAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
            String functionName = nestTypeAtt.getValue();
            String functionAttName = Constants.AttributeValues.SUM.replace("()", "(" + nestTypeAtt.getName() + ")");
            calculateNestByFunctions(functionName, functionAttName);
            }
             */
        }

        //System.out.println(" caluculate 1");
    }
    /*
     * functionName is count(), sum(), avg()
     * functionAttName is count(<attName>),sum(<attName>) , avg(<attName>)
     *
     * this one doesnt stop properly
     */

    protected double calculateNestByFunctions(String nest, String functionName, String functionAttName) {
        double rc = 0;

        Stack stk1 = new Stack();


        // summ up count, sum, avg

        /*
        Attribute[] nestByArray = getConfig().getNestByArray();
        /// process the stack of all nestbys
        for (int i = nestByArray.length; i > 0; i--) {
        while (stk1.size() > 0) {

        PseudoElement nb = (PseudoElement) stk1.pop();
        // get last nest by from config
        Attribute foundAtt = nb.getAttributes().getAttribute(functionAttName);
        if () {
        }

        }
        }
         */



        return rc;


    }

    public void calculate2() {

        if (getConfig().isEmpty()) { // no summary
            return;


        }

        if (!getConfig().hasCommands()) { // no commands then no summary
            return;


        } // for (int i = 0; i < getElements().size(); i++) {}
        PseudoElement e = getElements().getZeroElement();


        if (e != null) {
            for (int i = 0; i
                    < getConfig().size(); i++) {

                Attribute att = getConfig().getAttribute(i);


                if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
                    String functionName = att.getValue();
                    String functionAttName = Constants.AttributeValues.COUNT.replace("()", "(" + att.getName() + ")");


                    double rc = calculateNestByFunctions2(e.getIdx() + 1, functionName, functionAttName);
                    att.setValue(Double.toString(rc));


                }

                if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
                    String functionName = att.getValue();
                    String functionAttName = Constants.AttributeValues.SUM.replace("()", "(" + att.getName() + ")");


                    double rc = calculateNestByFunctions2(e.getIdx() + 1, functionName, functionAttName);
                    att.setValue(Double.toString(rc));


                }

            }
        }

    }

    /*
     * functionName is count(), sum(), avg()
     * functionAttName is count(<attName>),sum(<attName>) , avg(<attName>)
     *
     * this one doesnt stop properly
     */
    protected double calculateNestByFunctions2(int i, String functionName, String functionAttName) {
        // //System.out.println("calculateNestByFunctions2 1");
        double rc = 0;
        PseudoElement e = getElements().getElement(i);



        if (e == null) {
            return rc;


        }

        // check for last nest

        //     if(getConfig().isLastNest()){

        //  }



        if (getConfig().isNestBy(e)) {
            //System.out.println("    nestby  " + e.getName());
            rc = calculateNestByFunctions2(i + 1, functionName, functionAttName); // move on to the next element
            // update this elements count
            Attribute foundAtt = e.getAttributes().getAttribute(functionAttName);


            try {
                if (foundAtt != null) {

                    foundAtt.setValue(Double.toString(rc));



                }
            } catch (Exception ex) {
                //System.out.println("1 e " + e.getName() + "  error: " + ex.toString());


            }
        } else {

            if (getConfig().hasFunction(functionName, e)) {
                //System.out.println("    function " + functionName);
                Attribute foundAtt = e.getAttributes().getAttribute(functionAttName);


                try {
                    if (foundAtt != null) {

                        rc = new Double(foundAtt.getValue()).doubleValue();
                        rc += calculateNestByFunctions2(i + 1, functionName, functionAttName);



                    }
                } catch (Exception ex) {
                    //System.out.println("2 e " + e.getName() + "  error: " + ex.toString());


                }
            }
        }

        return rc;

    }
}
