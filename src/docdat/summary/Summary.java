/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.summary;

import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import docdat.io.exceptions.UnknownFileTypeException;
import docdat.utils.Attribute;
import docdat.utils.Configuration;
import docdat.utils.Constants;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.transform.TransformerException;

/**
 *
 * @author wilfongj
 */
public class Summary extends PseudoElements {

    public Summary(Configuration config) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        super();
        setConfig(config);
    }
    private Configuration Config = null;

    public Configuration getConfig() {
        return Config;
    }

    public void setConfig(Configuration Config) {
        this.Config = Config;
    }

    public PseudoElement getLastMatchingElement(PseudoElement e) {

        // need check the incomming element for matching attributes
        // if config available then
        //   if group by then match by groupby
        // empty list
        //////System.out.println("        getLastMatchingElement 1");
        if (size() == 0) {
            return null;
        }
        //////System.out.println("        getLastMatchingElement 2");
        if (e == null) {
            return null;
        }
////System.out.println("        getLastMatchingElement 3");
        for (int i = size() - 1; i >= 0; i--) {
            PseudoElement le = this.getElement(i);
            // check for match

            if (le.equivalent(e, getConfig())) {
                // //System.out.println("        getLastMatchingElement 4");
                return le;
            }

            // stop if a nest is found 
            if (getConfig().isNestBy(le)) {
                ////System.out.println("        getLastMatchingElement 5");
                return null;
            }
        }
////System.out.println("        getLastMatchingElement out");
        return null;
    }

    /*
     * add all elements defined in summary
     * add zero if not in
     * add nest if is part of summary
     * add group if is part of summary
     */
    public void addPseudoElement(PseudoElement e) {

        if (e == null) {
            return;
        }

        // identify the incomming element .... zero, nest, group ,

        int isA = getConfig().isA(e);
        //System.out.println("    isA: " + isA);
        PseudoElement clone_e = null;
        /*
         * Add the incoming to summary
         *
         */
        switch (isA) {
            case Constants.AttributeTypes.ZERO:
                //System.out.println("      add zero");
                // add the zero
                clone_e = getConfig().getMinimumFunctionCopy(e);
                getConfig().addCommandFunctions(clone_e);
                super.addPseudoElement(clone_e);
                break;
            case Constants.AttributeTypes.NEST_BY:
                //System.out.println("      add nest by");
                clone_e = getConfig().getMinimumFunctionCopy(e);
                super.addPseudoElement(clone_e);
                break;
            case Constants.AttributeTypes.GROUP_BY:
                //System.out.println("      group by");
                PseudoElement found_e = getLastMatchingElement(e);

                if (found_e == null) {
                    found_e = getConfig().getMinimumFunctionCopy(e); // min copy and initialize counts, sums, avg
                    super.addPseudoElement(found_e);
                } else {
                    //System.out.println("       found ");
                }
                //System.out.println("     1 group by found_e.getName(): " + found_e);
                processCount(found_e, e);
                //System.out.println("   2   group by found_e.getName(): " + found_e);
                processSum(found_e, e);
                //System.out.println("   3   group by found_e.getName(): " + found_e);
                processAvg(found_e, e);
                //System.out.println("    4  group by found_e.getName(): " + found_e);

                break;
            case Constants.AttributeTypes.FUNCTION:
                /////////////////
                // handle the situation when no groupby and no nests
                //////////

                if (getConfig().isCount(e)) {
                    //System.out.println("      add count");
                    /*
                     * count to zero element
                     */
                    // take all the e counts and add them to the zero element counts
                    PseudoElement z = getZeroElement();
                    processCount(z, e);
                }


                if (getConfig().isSum(e)) {
                    //System.out.println("      add sum");
                    /*
                     * sum to zero element
                     */
                    // take all the e counts and add them to the zero element counts
                    PseudoElement z = getZeroElement();
                    processSum(z, e);
                }

                if (getConfig().isAvg(e)) {
                    //System.out.println("      add avg xxxx");

                    /*
                     * avg to zero element
                     */

                    // take all the e avg and add them to the zero element counts
                    PseudoElement z = getZeroElement();

                    processAvg(z, e);


                }

                break;
            default:
                //System.out.println("      skip");
        }

        //  //System.out.println("    addPseudoElement out");
    }

   /*    public void processCount(PseudoElement eTo, PseudoElement eFrom) {

        ////System.out.println("        processCount avg= " + avg);
        for (int i = 0; i < getConfig().size(); i++) {
            /////////////////
            // get the configs function
            //////
            Attribute configAttDef = getConfig().getAttribute(i);  // expect several counts

            if (configAttDef.getValue().equals(Constants.AttributeValues.COUNT)) {
                ////System.out.println("        --------------- ");
                ////System.out.println("        configAttDef " + configAttDef.toString());
                //////////////
                // this search is straigh name
                //////
                //Attribute eFrom_avgAtt = eFrom.getAttributes().getAttribute(configAttDef.getName());
                //if (eFrom_avgAtt != null && !eFrom_avgAtt.getValue().equals("0")) {// no value to sum



             String ctoName = getConfig().getFunctionFormattedName(configAttDef);
                Attribute cto = eTo.getAttributes().getAttribute(ctoName);  // has count(xxxx)
                Attribute cfrom = eFrom.getAttributes().getAttribute(configAttDef.getName());  // has value the attribute name xxxx
//System.out.println( "   cto: " + cto + "  cfrom: " + cfrom );
                if (cfrom != null && !cfrom.getValue().equals("0")  ) {// if no from value then skip the count
                    
                    if (cto == null) { // add empty counter
                        cto = new Attribute(ctoName, "0"); // count(xxxx)
                        eTo.getAttributes().setAttribute(cto);
                    }

                    try {
                        Double cfTo = new Double(cto.getValue());
                        Double cfFrom = new Double(1);
                        // Double cfFrom = new Double( eFrom_avgAtt.getValue() );
                        double count = (cfTo.doubleValue() + cfFrom.doubleValue());//+ count_correction;

                        cto.setValue(Double.toString(count));
                        //System.out.println("     count: " + count + " = " + cfFrom + " + " + cfTo);

                    } catch (Exception ex) {
                        //System.err.println(" processAvg " + ex.toString());
                    }

                }



                Attribute eFuncAtt = eFrom.getAttributes().getAttribute(configAttDef.getName());  // has value
                String cName = eFuncAtt.getName();
                // check for missing count
                if (eFuncAtt == null) {
                    //  //System.out.println("        initialize ");
                    eFuncAtt = new Attribute(configAttDef.getName(), "0");
                    eFrom.getAttributes().setAttribute(eFuncAtt);
                }

                ////System.out.println("          eFuncAtt " + eFuncAtt.toString());
                String zName = getConfig().getFunctionFormattedName(configAttDef);

                // add the counter for avg

                ////System.out.println("          zName = " + zName);
                Attribute zFuncAtt = eTo.getAttributes().getAttribute(zName);
                ////System.out.println("          zFuncAtt: " + zFuncAtt);

                try {
                    //  //System.out.println("        A");
                    double d = new Double(eFuncAtt.getValue()).doubleValue();
                    ////System.out.println("        B ");
                    if (zFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
                        d = 1;
                    } else {
                        //  //System.out.println("        C");
                        d += new Double(zFuncAtt.getValue()).doubleValue();
                    }
                    ////System.out.println("        D d=" + d);
                    // //System.out.println("      zd:" + Double.toString(d));
                    zFuncAtt.setValue(Double.toString(d));
                } catch (Exception ex) {
                    //System.out.println("count error: " + eFrom.toString());

                }

            } 
 
            //}
        }
    }*/
  public void processCount(PseudoElement eTo, PseudoElement eFrom) {
        for (int i = 0; i < getConfig().size(); i++) {
            /////////////////
            // get the configs function
            //////
            Attribute funcAttDef = getConfig().getAttribute(i);  // expect several counts

            if (funcAttDef.getValue().equals(Constants.AttributeValues.COUNT)) {
                //  //System.out.println("        --------------- ");
                //   //System.out.println("        configAttDef " + configAttDef.toString());
                // this search is straigh name
                Attribute eFuncAtt = eFrom.getAttributes().getAttribute(funcAttDef.getName());  // has value
                // check for missing count
                if (eFuncAtt == null) {
                    eFuncAtt = new Attribute(funcAttDef.getName(), "");
                    eFrom.getAttributes().setAttribute(eFuncAtt);
                }
                // //System.out.println("          eFuncAtt " + eFuncAtt.toString());
                String zName = getConfig().getFunctionFormattedName(funcAttDef);// sum(hours)

                // //System.out.println("          zName = " + zName);
                Attribute zFuncAtt = eTo.getAttributes().getAttribute(zName);

                ////System.out.println("          zFuncAtt: " + zFuncAtt);
                try {
                    double d = new Double(eFuncAtt.getValue()).doubleValue();
                    if (zFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
                        d += 0;
                    } else {
                        d += new Double(zFuncAtt.getValue()).doubleValue();
                    }
                    //  //System.out.println("      zd:" + Double.toString(d));
                    zFuncAtt.setValue(Double.toString(d));
                } catch (Exception ex) {
                    //System.out.println("sum error: " + eFrom.toString());
                    // zFuncAtt.setValue(Constants.ErrorMsgs.ERR);
                }
            }
        }
    }
    public void processAvg(PseudoElement eTo, PseudoElement eFrom) {
        // look at all configs
        for (int i = 0; i < getConfig().size(); i++) {
            Attribute configAttDef = getConfig().getAttribute(i);  // expect several counts from configuration

            if (configAttDef.getValue().equals(Constants.AttributeValues.AVG)) {
              //  //System.out.println("        --------------- ");
               // //System.out.println("        configAttDef " + configAttDef);
                Attribute eFrom_avgAtt = eFrom.getAttributes().getAttribute(configAttDef.getName());
                if (  eFrom_avgAtt != null && !eFrom_avgAtt.getValue().equals("0")  ) {// no value to sum
                   // //System.out.println("        eFrom_avgAtt " + eFrom_avgAtt);
                    /*
                     * get  sum
                     */
                    // double count_correction = 0;
                    String sName = Constants.AttributesTemp.SumPrefix + configAttDef.getName();
                    Attribute sto = eTo.getAttributes().getAttribute(sName);

                    if (sto == null) {
                        sto = new Attribute(sName, "0");
                        eTo.getAttributes().setAttribute(sto);
                        ////System.out.println("          add sum  to " + eTo.toString());
                        //   count_correction = 0; // the sum value is really missing and not zero and the count needs to be correcte so we hedge here
                    }

                    /*
                     * update counter
                     */
                    String cName = Constants.AttributesTemp.CounterPrefix + configAttDef.getName();
                    Attribute cto = eTo.getAttributes().getAttribute(cName);

                    if (cto == null) {
                        cto = new Attribute(cName, "0");
                        eTo.getAttributes().setAttribute(cto);
                        // //System.out.println("          add count  to " + eTo.toString());
                    }
                    try {
                        Double cfTo = new Double(cto.getValue());

                        Double cfFrom = new Double(0);
/*                        if(     ){
                            cfFrom = 
                        }
   */                     // Double cfFrom = new Double( eFrom_avgAtt.getValue() );
                        double count = (cfTo.doubleValue() + cfFrom.doubleValue());//+ count_correction;

                        cto.setValue(Double.toString(count));
                       // //System.out.println("     count: " + count + " = " + cfFrom + " + " + cfTo);

                    } catch (Exception ex) {
                        //System.err.println(" processAvg " + ex.toString());
                    }
                    /*
                     * update sum
                     */
                    Attribute sfr = eFrom.getAttributes().getAttribute(configAttDef.getName());
                    try {
                        Double sfTo = new Double(sto.getValue());
                        Double sfFrom = new Double(sfr.getValue());
                        double sum = sfTo.doubleValue() + sfFrom.doubleValue();
                        sto.setValue(Double.toString(sum));
                    } catch (Exception ex) {
                        //System.err.println(" processAvg " + ex.toString());

                    }
                    /*
                     * calculate avg
                     */
                    String aName = getConfig().getFunctionFormattedName(configAttDef);
                    Attribute ato = eTo.getAttributes().getAttribute(aName);
                    Double cd = new Double(cto.getValue());
                    Double sd = new Double(sto.getValue());
                    double ad = sd.doubleValue() / cd.doubleValue();
                    ato.setValue(Double.toString(ad));
                } else {
                    //System.out.println("      skip " + eFrom_avgAtt);
                }
            }
        }
    }

    public void processSum(PseudoElement eTo, PseudoElement eFrom) {
        processSum(eTo, eFrom, false);
    }

    public void processSum(PseudoElement eTo, PseudoElement eFrom, boolean avg) {
        for (int i = 0; i < getConfig().size(); i++) {
            /////////////////
            // get the configs function
            //////
            Attribute funcAttDef = getConfig().getAttribute(i);  // expect several counts

            if (funcAttDef.getValue().equals(Constants.AttributeValues.SUM)) {
                //  //System.out.println("        --------------- ");
                //   //System.out.println("        configAttDef " + configAttDef.toString());
                // this search is straigh name
                Attribute eFuncAtt = eFrom.getAttributes().getAttribute(funcAttDef.getName());  // has value
                // check for missing count
                if (eFuncAtt == null) {
                    eFuncAtt = new Attribute(funcAttDef.getName(), "");
                    eFrom.getAttributes().setAttribute(eFuncAtt);
                }
                // //System.out.println("          eFuncAtt " + eFuncAtt.toString());
                String zName = getConfig().getFunctionFormattedName(funcAttDef);// sum(hours)

                // //System.out.println("          zName = " + zName);
                Attribute zFuncAtt = eTo.getAttributes().getAttribute(zName);

                ////System.out.println("          zFuncAtt: " + zFuncAtt);
                try {
                    double d = new Double(eFuncAtt.getValue()).doubleValue();
                    if (zFuncAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
                        d += 0;
                    } else {
                        d += new Double(zFuncAtt.getValue()).doubleValue();
                    }
                    //  //System.out.println("      zd:" + Double.toString(d));
                    zFuncAtt.setValue(Double.toString(d));
                } catch (Exception ex) {
                    //System.out.println("sum error: " + eFrom.toString());
                    // zFuncAtt.setValue(Constants.ErrorMsgs.ERR);
                }
            }
        }
    }
}
