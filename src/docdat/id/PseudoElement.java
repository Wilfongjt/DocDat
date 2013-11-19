/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.id;

import docdat.utils.Attribute;
import docdat.utils.Attributes;
import docdat.utils.Configuration;
import docdat.utils.Constants;

/**
 *
 * @author wilfongj
 */
public class PseudoElement extends Id {

  public PseudoElement() {
  }

  /*
   * test to see if this is the zero element
   */
  public boolean isZeroElement() {
    if (getId().equalsIgnoreCase("0")) {
      return true;
    }
    return false;
  }
  /*
   * this one needs to have all the same attributes as the one being compared
   * looks for only the atts in this one to make a match
   * ignores any extra attributes
   */

  public boolean equivalent(PseudoElement e, Configuration config) {

    // look for matches to config groupby
    // match e and this for equiv
    for (int c = 0; c < config.size(); c++) {
      Attribute cAtt = config.getAttribute(c);
      if (cAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.GROUP_BY)) {
        Attribute eAtt = e.getAttributes().getAttribute(cAtt.getName());
        Attribute thisAtt = getAttributes().getAttribute(cAtt.getName());
        if (eAtt == null) { // not found then not equiv
          return false;
        }
        if (thisAtt == null) { // not foud then not equiv
          return false;
        }
        if (!thisAtt.equals(eAtt)) { // not match
          return false;
        }
      }
    }
    return true;
  }

// does this element represent a conditional element
  public boolean isCondition() {
    boolean isCondition = false;
    Attribute symbolAtt = getAttributes().getAttribute(Constants.Attributes.SYMBOL_TYPE);
    if (symbolAtt != null) {
      if (symbolAtt.getValue().equalsIgnoreCase(Constants.SymbolTypeValues.CONDITION)) {
        isCondition = true;
      }
    }
    return isCondition;
  }
  

    public void setRunningIdx(String value) {
    super.setId(value);
    Attribute id = getAttributes().getAttribute(Constants.RUNNINGIDX);
    if (id != null) {
      id.setValue(value);
    } else {
      id = new Attribute(Constants.RUNNINGIDX, value);
      getAttributes().setAttribute(id);
    }
  }

  public String getRunningIdx() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.RUNNINGIDX);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }

  public void setId(String value) {
    super.setId(value);
    Attribute id = getAttributes().getAttribute(Constants.ID);
    if (id != null) {
      id.setValue(value);
    } else {
      id = new Attribute(Constants.ID, value);
      getAttributes().setAttribute(id);
    }
  }

  public String getId() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.ID);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }
  /*
  public void setChartType(String value) {
  
  Attribute name = getAttributes().getAttribute(Constants.Attributes.CHARTTYPE );
  if (name != null) {
  name.setValue(value);
  } else {
  name = new Attribute(Constants.Attributes.CHARTTYPE, value);
  getAttributes().setAttribute(name);
  }
  }
  
  public String getChartType() {
  // store name in the attributes list rather than the bases name
  String rc = "";
  Attribute name = getAttributes().getAttribute(Constants.Attributes.CHARTTYPE);
  if (name != null) {
  rc = name.getValue();
  }
  return rc;
  }
   * 
   */

  public void setDescription(String value) {
    super.setDescription(value);
    Attribute name = getAttributes().getAttribute(Constants.Attributes.ATT_DESCRIPTION);
    if (name != null) {
      name.setValue(value);
    } else {
      name = new Attribute(Constants.Attributes.ATT_DESCRIPTION, value);
      getAttributes().setAttribute(name);
    }
  }

  public String getDescription() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.Attributes.ATT_DESCRIPTION);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }

  public void setElement(String value) {
    super.setId(value);
    Attribute name = getAttributes().getAttribute(Constants.Attributes.ELEMENT_TYPE);
    if (name != null) {
      name.setValue(value);
    } else {
      name = new Attribute(Constants.Attributes.ELEMENT_TYPE, value);
      getAttributes().setAttribute(name);
    }
  }

  public String getElement() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.Attributes.ELEMENT_TYPE);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }

  public void setName(String Value) {
    String value = Value ;
    while(value.indexOf("\"") >= 0 ){ value = value.replace("\"" , "'" );}
    
    Attribute name = getAttributes().getAttribute(Constants.Attributes.NAME);
    if (name != null) {
      name.setValue(value.trim()); // update name
    } else {
      name = new Attribute(Constants.Attributes.NAME, value.trim()); // add new name
      getAttributes().setAttribute(name);
    }
  }

  public String getName() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.Attributes.NAME);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }

  public void setTitle(String value) {
    Attribute name = getAttributes().getAttribute(Constants.Attributes.TITLE);
    if (name != null) {
      name.setValue(value);
    } else {
      name = new Attribute(Constants.Attributes.TITLE, value);
      getAttributes().setAttribute(name);
    }
  }

  public String getTitle() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.Attributes.TITLE);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }

  public void setRole(String value) {
    Attribute name = getAttributes().getAttribute(Constants.Attributes.ROLE);
    if (name != null) {
      name.setValue(value);
    } else {
      name = new Attribute(Constants.Attributes.ROLE, value);
      getAttributes().setAttribute(name);
    }
  }

  public String getRole() {
    // store name in the attributes list rather than the bases name
    String rc = "";
    Attribute name = getAttributes().getAttribute(Constants.Attributes.ROLE);
    if (name != null) {
      rc = name.getValue();
    }
    return rc;
  }
  // attibutes
  private Attributes _attributes = new Attributes();

  public Attributes getAttributes() {
    return _attributes;
  }

  public int getX() {
    //return x;
    int rc = Constants.XDefault;
    Attribute att = getAttributes().getAttribute(Constants.XName);
    if (att != null) {
      rc = new Integer(att.getValue()).intValue();
    }
    return rc;
  }

  public void setX(int xv) {
    //this.minXOffset = minXOffset;
    Attribute att = getAttributes().getAttribute(Constants.XName);
    if (att == null) {
      att = new Attribute(Constants.XName, new Integer(xv).toString());
      getAttributes().setAttribute(att);
    }
    att.setValue(new Integer(xv).toString());
  }

  public int getY() {
    //return x;
    int rc = Constants.YDefault;
    Attribute att = getAttributes().getAttribute(Constants.YName);
    if (att != null) {
      rc = new Integer(att.getValue()).intValue();
    }
    return rc;
  }

  public void setY(int yv) {
    //this.minXOffset = minXOffset;
    Attribute att = getAttributes().getAttribute(Constants.YName);
    if (att == null) {
      att = new Attribute(Constants.YName, new Integer(yv).toString());
      getAttributes().setAttribute(att);
    }
    //System.out.println("setY " +yv);
    att.setValue(new Integer(yv).toString());
  }

  public int getH() {
    //return x;
    int rc = Constants.HeightDefault;
    Attribute att = getAttributes().getAttribute(Constants.HeightName);
    if (att != null) {
      rc = new Integer(att.getValue()).intValue();
    }
    return rc;
  }

  public void setH(int hv) {

    Attribute att = getAttributes().getAttribute(Constants.HeightDefault);
    if (att == null) {
      att = new Attribute(Constants.HeightName, new Integer(hv).toString());
      getAttributes().setAttribute(att);
    }
    att.setValue(new Integer(hv).toString());
  }

  public int getW() {
    //return x;
    int rc = Constants.WidthDefault;
    Attribute att = getAttributes().getAttribute(Constants.WidthName);
    if (att != null) {
      rc = new Integer(att.getValue()).intValue();
    }
    return rc;
  }

  public void setW(int wv) {
    Attribute att = getAttributes().getAttribute(Constants.WidthDefault);
    if (att == null) {
      att = new Attribute(Constants.WidthName, new Integer(wv).toString());
      getAttributes().setAttribute(att);
    }
    att.setValue(new Integer(wv).toString());
  }

  public boolean hasAttribute(String attName) {
    Attribute se = getAttributes().getAttribute(attName);
    if (se == null) {
      return false;
    }
    return true;
  }

  /*
   * hasMatchingAttributesAndValues
   */
  public boolean hasMatchingAttributesAndValues(Attribute[] sumByAtts) {
    // search for each sumByAtts
    // name and value must match
    //int match_cnd=0;
        /*
     * missing attributes indicate that the array has not been loaded properly
     */


    for (int j = 0; j < sumByAtts.length; j++) {
      //missing attributes indicate that the array has not been loaded properly
      if (sumByAtts[j] == null) {
        return false;
      }
      // find existing attribute by sumbyAtt
      //  System.out.println("  sumByAtts ["+j+"]'"+ sumByAtts[j]);
      Attribute att = getAttributes().getAttribute(sumByAtts[j].getName());
      if (att == null) { // match on name
        return false;// if fail att search by name then not a match
      }
      // check match on value
      if (!att.getValue().equalsIgnoreCase(sumByAtts[j].getValue())) {
        return false; // found name but no value match then cannot be match
      }

    } // end of sumByAtts
    return true;
  }

  public String toString() {
    String rc = "element ";
    for (int i = 0; i < getAttributes().size(); i++) {
      Attribute att = getAttributes().getAttribute(i);
      rc += " " + att.toString();
    }
    return rc;
  }

  public PseudoElement clone() {
    PseudoElement el = new PseudoElement();
    el.assign(this);
    return el;
  }

  public void assign(PseudoElement el) {
    for (int i = 0; i < el.getAttributes().size(); i++) {
      Attribute att = new Attribute(el.getAttributes().getAttribute(i).getName(), el.getAttributes().getAttribute(i).getValue());
      att.setColor(el.getAttributes().getAttribute(i).getColor());
      getAttributes().setAttribute(att);
    }
  }

  /*
   *  get array of specific functions
   */
  protected void getByValues(Attribute[] Atts) {
    // move this elments count, sum vlaues in to
    if (Atts == null) {
      return;
    }
    for (int i = 0; i < Atts.length; i++) {
      Attribute att = (Attribute) Atts[i];
      if (att == null) {
        return;
      }



      att = getAttributes().getAttribute(((Attribute) Atts[i]).getName());

      if (att != null) {
        ((Attribute) Atts[i]).setValue(att.getValue());
      } else {
        ((Attribute) Atts[i]).setValue(null); // attribute not found
      }

    }
  }

  public void getNestByValues(Attribute[] Atts) {
    getByValues(Atts);
  }

  public void getGroupByValues(Attribute[] Atts) {
    getByValues(Atts);
  }

  public void getSumValues(Attribute[] Atts) {
    getByValues(Atts);
  }

  public void getAvgValues(Attribute[] Atts) {
    getByValues(Atts);
  }

  public void getCountValues(Attribute[] Atts) {
    getByValues(Atts);
  }
  /*
   * attsCountTargets has name and value
   */

  public void showAttribute() {
  }

  public void countAttributes(Attribute[] attsCountTargets) {
    // this moves attCountTarget values into this element's count values
    if (attsCountTargets == null) {
      // System.out.println(" countsAttributes return");
      return;
    }

    for (int i = 0; i < attsCountTargets.length; i++) {
      // this name should be count(nnn)
      // System.out.println("  search for: " + attsCountTargets[i].getName());
      Attribute foundAtt = this.getAttributes().getAttribute(attsCountTargets[i].getName()); // search for function name if
      // System.out.println("    foundAtt " + foundAtt);
      try {
        if (foundAtt != null) {

          if (foundAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
            foundAtt.setValue("0");
          }
          //System.out.println("  1 found name: "+foundAtt.getName() + "  " + foundAtt.getValue());
          Double targetNumber = new Double(foundAtt.getValue());
          Double addNumber = new Double("1");
          //Double addNumber = new Double(attsCountTargets[i].getValue());

          Double newValue = new Double(targetNumber.doubleValue() + addNumber.doubleValue());
          foundAtt.setValue(newValue.toString());
          //System.out.println(" countAttributes  name: " + foundAtt.getName() + "  value: " + foundAtt.getValue());
        }
      } catch (Exception e) {
        System.out.println("  error: " + e.toString());

      }
    }
  }

  /*
  public void countAttributes(Attribute[] attsCount) {
  System.out.println("countAttributes 1 add counts to " + getName());
  if (attsCount == null) { // may not have sums if so then attsSum will be null
  return;
  }
  System.out.println("  countAttributes 2 ");
  int cnt = 0;
  for (int i = 0; i < attsCount.length; i++) {
  if (attsCount[i] != null) {
  cnt++;
  }
  }
  System.out.println("  countAttributes 3 ");
  if (cnt != attsCount.length) {// no sum atts
  return;
  }
  System.out.println("  countAttributes 4 ");
  for (int i = 0; i < attsCount.length; i++) {
  
  if (attsCount[i] != null) { // some count atts may be null but not all
  System.out.println("  countAttributes 5 ");
  // String countName = Constants.AttributeValues.COUNT.replace("()", "(" + attsCount[i].getName() + ")"); //"sum(" + attsSum[i].getName() + ")";
  
  System.out.println("    serarch for: " + attsCount[i].getName() );
  
  //Attribute foundAtt = this.getAttributes().getAttribute(attsCount[i].getName() );
  // search for the function name  and then
  Attribute foundAtt = this.getAttributes().getAttribute(attsCount[i].getName()); // search for function name if
  if (foundAtt == null) {
  
  
  System.out.println("  countAttributes 6 not found add new a");
  //foundAtt = new Attribute(countName, "0");
  //this.getAttributes().setAttribute(foundAtt);
  foundAtt = new CountAttribute(attsCount[i].getName(), "0");
  System.out.println("  countAttributes 6 add new b");
  this.getAttributes().setAttribute(foundAtt);
  System.out.println("  countAttributes 6 add new  c " + " name: " + foundAtt.getName() + "   value: " + foundAtt.getValue());
  // foundAtt = foundAtt;
  
  
  }
  System.out.println("  countAttributes 7 name: " + getName());
  try {
  // System.out.print("  add count ");
  Integer curval = new Integer(foundAtt.getValue());
  System.out.println("  countAttributes 8 " + attsCount[i]);
  System.out.println("  countAttributes 8 " + attsCount[i].getValue());
  // new value will be null when count has not started ... need to patch it up
  Integer newval = new Integer(attsCount[i].getValue());
  if (foundAtt.getValue().equals("0")) {
  newval = new Integer(1);
  
  }
  
  System.out.println("  countAttributes 9 ");
  
  int count = curval.intValue() + newval.intValue();
  
  System.out.println("   add " + count + " to " + foundAtt.getName());
  foundAtt.setValue(Integer.toString(count));
  } catch (Exception e) { // display err
  foundAtt.setValue(Constants.ErrorMsgs.ERR);
  }
  
  }
  }
  System.out.println("  ");
  }
   */
  public void sumAttributes(Attribute[] attsSum) {
    // System.out.println("sumAttributes 1");
    // check attsSum for null if null the return
    // check attsSum for all null atts if null then return ... means
    // for all the sum atts
    // reformat the sum attribute into search-name sum(attsSum[i])
    // check for search-name
    // if found the add to it sum-attribute to it
    // if not found then add attribute with search-name and attsSum[i].getValue()

    // may not have sums if so then attsSum will be null
    if (attsSum == null) { // may not have sums if so then attsSum will be null
      return;


    } //System.out.println("sumAttributes 2");
    int cnt = 0;


    for (int i = 0; i
            < attsSum.length; i++) {
      if (attsSum[i] != null) {
        cnt++;
      }
    }
    //System.out.println("sumAttributes 3 cnt: " + cnt + " attsSum.length :" + attsSum.length);
    if (cnt != attsSum.length) {// no sum atts
      return;
    }
    //System.out.println("sumAttributes 4");
    for (int i = 0; i < attsSum.length; i++) {
      //System.out.println("sumAttributes 5");
      if (attsSum[i] != null) { // some sum atts may be null but not all
        // System.out.println("sumAttributes 6");
        String sumName = "sum(" + attsSum[i].getName() + ")";
        // search for sum name if
        Attribute foundAtt = this.getAttributes().getAttribute(sumName);

        if (foundAtt == null) { // format attribute and add
          //System.out.println("sumAttributes 7");
          this.getAttributes().setAttribute(sumName, attsSum[i].getValue());


        } else { // update the sum
          //System.out.println("sumAttributes 8");
          if (foundAtt.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
            foundAtt.setValue("0");
          }
          try {

            Double curval = new Double(foundAtt.getValue());
            Double newval = new Double(attsSum[i].getValue());

            double sum = curval.doubleValue() + newval.doubleValue();
            foundAtt.setValue(Double.toString(sum));

          } catch (Exception e) { // display err
            System.out.println("sumAttributes ERR " + e.toString());
            foundAtt.setValue(Constants.ErrorMsgs.ERR);

          }
        }
      }
    }
    //  System.out.println("sumAttributes out");

    // reformat the sum attribute into search-name sum(attsSum[i])
    // check for search-name
    // if found the add to it sum-attribute to it
    // if not found then add attribute with search-name and attsSum[i].getValue()

  }

  public void avgAttributes(Attribute[] attsAvg) {
    // update sum calc
    // then calc avg every time

    // may not have sums if so then attsAvg will be null
    if (attsAvg == null) { // may not have sums if so then attsAvg will be null
      return;
    }

    //////////////////////////////////////////////////////////
    // calc sum
    ////////////////////////
    Attribute[] attsSum = new Attribute[attsAvg.length];
    Attribute[] attsCount = new Attribute[attsAvg.length];
    // load up the necessary sum attrib values

    //getCountValues(attsCount);

    // need sum and counts for avg
    for (int i = 0; i < attsAvg.length; i++) {
      if (attsAvg[i] != null) {
        attsSum[i] = new Attribute(attsAvg[i].getName(), attsAvg[i].getValue());
        attsCount[i] = new Attribute(attsAvg[i].getName(), attsAvg[i].getValue());
      } else {
        attsSum[i] = null;
        attsCount[i] = null;
      }
    }

    // this.sumAttributes(attsSum); // load values
    if (attsSum == null) {
      return;
    } // check for competely empty array
    int cnt = 0;

    for (int i = 0; i < attsSum.length; i++) {
      if (attsSum[i] != null) {
        cnt++;
      }
    }

    if (cnt != attsSum.length) {// no sum atts
      return;
    }

    for (int i = 0; i < attsCount.length; i++) {

      if (attsCount[i] != null) { // some sum atts may be null but not all

        // make unique name to store sum for this var
        String Name = Constants.AttributesTemp.CounterPrefix + attsCount[i].getName();
        // search for count name if
        Attribute foundAtt = this.getAttributes().getAttribute(Name);

        if (foundAtt == null) { // format attribute and add


          this.getAttributes().setAttribute(Name, Double.toString(1.0));
        } else { // update the sum

          try {

            Double curval = new Double(foundAtt.getValue());
            Double newval = new Double(1);
            double count = curval.doubleValue() + newval.doubleValue();

            foundAtt.setValue(Double.toString(count));
          } catch (Exception e) { // display err
            foundAtt.setValue(Constants.ErrorMsgs.ERR);
          }
        }
      }
    }

    for (int i = 0; i < attsSum.length; i++) {

      if (attsSum[i] != null) { // some sum atts may be null but not all

        // make unique name to store sum for this var
        String sumName = Constants.AttributesTemp.SumPrefix + attsSum[i].getName();
        // search for sum name if
        Attribute foundAtt = this.getAttributes().getAttribute(sumName);

        if (foundAtt == null) { // format attribute and add

          this.getAttributes().setAttribute(sumName, attsSum[i].getValue());


        } else { // update the sum

          try {

            Double curval = new Double(foundAtt.getValue()); // from this element
            Double newval = new Double(attsSum[i].getValue());

            double sum = curval.doubleValue() + newval.doubleValue();

            foundAtt.setValue(Double.toString(sum));
          } catch (Exception e) { // display err
            foundAtt.setValue(Constants.ErrorMsgs.ERR);
          }
        }
      }
    }

    /////////////////////////////////
    // Avg
    ////////////////
    // get count
    // get element-sum-xxx
    // get avg(x)
    // update avg(x) = element-suml-xxx/count
    //////////////////////////




    // if (count != null) {
    for (int i = 0; i < attsAvg.length; i++) {
      Attribute count = this.getAttributes().getAttribute(Constants.AttributesTemp.CounterPrefix + attsAvg[i].getName());
      Attribute sum = this.getAttributes().getAttribute(Constants.AttributesTemp.SumPrefix + attsAvg[i].getName());

      if (sum != null) {
        String avgName = "avg(" + attsAvg[i].getName() + ")";
        Attribute avg = this.getAttributes().getAttribute(avgName);

        if (avg == null) {
          avg = new Attribute(avgName, "0");
          this.getAttributes().setAttribute(avg);
        }
        try {
          Double dcnt = new Double(count.getValue());
          Double dsum = new Double(sum.getValue());
          double calc = dsum / dcnt;
          avg.setValue(Double.toString(calc));
        } catch (Exception e) {
          avg.setValue(Constants.ErrorMsgs.ERR);
        }
      }

    }
    //}

  }

  public void clearTempAttributes() {
    for (int i = getAttributes().size() - 1; i > 0; i--) {
      Attribute att = getAttributes().getAttribute(i);
      //System.out.println("element " + att);
      if (att.getName().contains(Constants.AttributesTemp.CounterPrefix)) {
        //               System.out.println("  clear " +att.getName());
        getAttributes().remove(i);
      }


      if (att.getName().contains(Constants.AttributesTemp.SumPrefix)) {
        //            System.out.println("  clear " +att.getName());
        getAttributes().remove(i);
      }

    }
  }
}
