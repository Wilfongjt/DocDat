/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.utils;

import docdat.id.PseudoElement;
import docdat.id.PseudoElement;
import docdat.id.PseudoElements;
import java.util.ArrayList;

/**
 *
 * @author wilfongj
 */
public class Configuration extends ArrayList {

  public Configuration() {
  }

  public boolean isEmpty() {
    if (size() == 0) {
      return true;
    }
    return false;
  }
  /*
   * counts the number attributes with a specifc name
   */

  public int size(String nme) {
    int rc = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      if(att != null && att.getName().equalsIgnoreCase(nme)){
        rc ++;
      }
    }
    return rc;
  }

  /*
   * 
   */
  public void showConfiguration() {
    System.out.println("------------------");
    System.out.println("Configuration");
    System.out.println("------------------");
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      System.out.println("    name: " + att.getName() + "  value: " + att.getValue());
    }
    System.out.println("------------------");
  }

  /*
   * get function formated name
   */
  public String getFunctionFormattedName(Attribute att) {
    return att.getValue().replace("()", "(" + att.getName() + ")");
  }

  /*
   *
   * get the last nest att
   */
  public Attribute getLastNestAttribute() {
    Attribute att = null;
    for (int i = 0; i < size(); i++) {
      Attribute fa = (Attribute) get(i);
      if (fa.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
        att = fa;
      }
    }
    return att;
  }
  /*
   * isA zero, nest, group, or function
   *
   */

  public int isA(PseudoElement e) {
    int rc = -1;
    if (isZeroElement(e)) {
      rc = Constants.AttributeTypes.ZERO;
    }
    if (isNestBy(e) && rc == -1) {
      rc = Constants.AttributeTypes.NEST_BY;
    }
    if (isGroupBy(e) && rc == -1) {
      System.out.println("  isA groupby");
      rc = Constants.AttributeTypes.GROUP_BY;
    }
    if ((isCount(e) || isSum(e) || isAvg(e)) && rc == -1) {
      System.out.println("  isA function");
      rc = Constants.AttributeTypes.FUNCTION;
    }
    return rc;
  }
  /*
   * does configuration have count
   */

  public boolean hasFunction(String functionName) {

    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      if (att.getValue().equalsIgnoreCase(functionName)) {
        return true;
      }
    }
    return false;
  }
  /*
   * does element have any counters
   */

  public boolean hasFunction(String functionName, PseudoElement element) {

    int count_sz = Constants.AttributeValues.COUNT.length() - 2;
    String count_name = Constants.AttributeValues.COUNT.substring(0, count_sz);

    // look in nest

    // look in element attributes
    for (int i = 0; i < element.getAttributes().size(); i++) {
      Attribute att = element.getAttributes().getAttribute(i);
      if (att.getName().toLowerCase().contains(count_name)) {
        return true;
      }
    }

    return false;
  }
  /*
   * does element have specific count
   */

  public boolean hasFunction(String functionName, PseudoElement element, String attName) {

    String count_name = functionName.replace("()", "(" + attName + ")");

    // look in nest

    // look in element attributes
    for (int i = 0; i < element.getAttributes().size(); i++) {
      Attribute att = element.getAttributes().getAttribute(i);
      if (att.getName().toLowerCase().contains(count_name)) {
        return true;
      }
    }

    return false;
  }

  public boolean isZeroElement(PseudoElement element) {
    if (element == null) {
      return false;
    }
    if (element.getId().equals("0")) {
      return true;
    }
    return false;
  }

  public PseudoElement getNextNestUpFrom(int startingElementPos, PseudoElements elements) {
    PseudoElement e = null;
    if (startingElementPos - 1 < 0 || startingElementPos - 1 > elements.size()) {
      return null;
    }

    for (int i = startingElementPos - 1; i > 0; i--) {
      e = elements.getElement(i);
      if (isNestBy(e)) {
        break;
      }
    }
    return e;
  }

  public PseudoElement[] getEmptyNestElementsArray() {
    int cnt = 0;
    PseudoElement[] rc = null;

    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
        cnt++;
      }
    }
    if (cnt > 0) {
      rc = new PseudoElement[cnt];
    }
    return rc;
  }

  /*
   * commands are attributes with nest-by, group-by, count(), sum(), avg() in value
   */
  public boolean hasCommands() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        rc = true;
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        rc = true;
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        rc = true;
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.GROUP_BY)) {
        rc = true;
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
        rc = true;
      }
    }
    return rc;
  }

  public boolean hasNests() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
        rc = true;
      }
    }
    return rc;
  }

  public boolean hasGroups() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.GROUP_BY)) {
        rc = true;
      }
    }
    return rc;
  }

  public boolean hasCommandFunctions() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        rc = true;
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        rc = true;
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        rc = true;
      }
    }
    return rc;
  }

  public boolean hasSumCommandFunctions() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        rc = true;
      }
    }
    return rc;
  }

  public boolean hasCountCommandFunctions() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        rc = true;
      }

    }
    return rc;
  }

  public boolean hasAvgCommandFunctions() {
    boolean rc = false;
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        rc = true;
      }

    }
    return rc;
  }

  /*
   * add id
   * add name
   * add element
   * add nests
   * if has function add all commands
   */
  public PseudoElement getMinimumFunctionCopy(PseudoElement e) {
    PseudoElement clone_e = new PseudoElement();
    clone_e.setId(e.getId());
    
    clone_e.setName(e.getName());
    clone_e.setElement(e.getElement());

    // add nest from config
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {  // is nest

        Attribute foundAtt = null;
        if ((foundAtt = e.getAttributes().getAttribute(att.getName())) != null) { // find in elem

          clone_e.getAttributes().setAttribute(foundAtt.getClone()); // add to new elem
        }
      }

    }

    if (hasCommandFunctions()) {
      // System.out.println("    hascommandfuncs");
      // xxxxx
      Attribute[] atts = getCommandFunctionArray();
      for (int i = 0; atts != null && i < atts.length; i++) {
        clone_e.getAttributes().setAttribute(atts[i]);
      }
    }
    return clone_e;
  }

  /*
   * adds command functions to the element and sets defaults
   * 
   */
  public void addNestByCommands(PseudoElement e) {
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
        String valuename = "(" + att.getName() + ")";
        e.getAttributes().setAttribute(Constants.AttributeValues.NEST_BY, att.getName());
      }

    }
  }
  /*
  public void addCountCommandFunctions(PseudoElement e) {
  for (int i = 0; i < size(); i++) {
  Attribute att = getAttribute(i);
  
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
  //String name = "(" + att.getName() + ")";
  // name = Constants.AttributeValues.COUNT.replace("()", name);
  CountAttribute newAtt = new CountAttribute(att.getName(), "0");
  if (e.getAttributes().getAttribute(newAtt.getName()) == null) {
  e.getAttributes().setAttribute(newAtt);
  }
  }
  
  
  }
  }
  
  public void addSumCommandFunctions(PseudoElement e) {
  for (int i = 0; i < size(); i++) {
  Attribute att = getAttribute(i);
  
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
  String name = "(" + att.getName() + ")";
  name = Constants.AttributeValues.SUM.replace("()", name);
  if (e.getAttributes().getAttribute(name) == null) {
  e.getAttributes().setAttribute(name, "0");
  }
  }
  
  
  }
  }
  
  public void addAvgCommandFunctions(PseudoElement e) {
  for (int i = 0; i < size(); i++) {
  Attribute att = getAttribute(i);
  
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
  String name = "(" + att.getName() + ")";
  name = Constants.AttributeValues.AVG.replace("()", name);
  if (e.getAttributes().getAttribute(name) == null) {
  e.getAttributes().setAttribute(name, "0");
  }
  }
  
  
  }
  }
   */

  public void addCommandFunctions(PseudoElement e) {
    for (int i = 0; i < size(); i++) {
      Attribute att = getAttribute(i);
      /*
       * add Average function name and default value
       */
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        String valuename = "(" + att.getName() + ")";
        //System.out.println("  set AVG 1 "+att.getName());
        e.getAttributes().setAttribute(Constants.AttributeValues.AVG.replace("()", valuename), "0");
      }
      /*
       * add Count function name and default value
       */
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        //  String valuename = "(" + att.getName() + ")";
        //e.getAttributes().setAttribute(Constants.AttributeValues.COUNT.replace("()", valuename), "0");
        CountAttribute newAtt = new CountAttribute(att.getName(), "0");
        if (e.getAttributes().getAttribute(newAtt.getName()) == null) {
          e.getAttributes().setAttribute(newAtt);
        }
      }
      /*
       * add Sum function name and default value
       */
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        String valuename = "(" + att.getName() + ")";
        //   System.out.println("  set SUM 1 "+valuename);
        e.getAttributes().setAttribute(Constants.AttributeValues.SUM.replace("()", valuename), "0");
      }
    }
  }

  public void addAttribute(Attribute att) {
    // stop duplicate entries
    for (int i = 0; i < size(); i++) {
      Attribute chkatt = (Attribute) get(i);
      // if already in list then ignore
      if (att.getName().equalsIgnoreCase(chkatt.getName()) && att.getValue().equalsIgnoreCase(chkatt.getValue())) {
        return;
      }
    }
    add(att);
  }

  public Attribute getAttribute(int i) {
    if (i < 0 || i >= size()) {
      return null;
    }
    return (Attribute) get(i);
  }

  public Attribute getAttribute(String name) {
    for (int i = 0; i < size(); i++) {

      Attribute att = getAttribute(i);
      System.out.println("getAttribute name:" + att.getName());
      if (att != null && att.getName().equalsIgnoreCase(name)) {
        return att;
      }
    }
    return null;
  }

  public Attribute getAttribute(String name, int idx) {
    Attribute rc = null;
    int cnt = 0;
    for (int i = 0; i < size(); i++) {

      Attribute att = getAttribute(i);
      //System.out.println("getAttribute name:"+att.getName());
      if (att != null && att.getName().equalsIgnoreCase(name)) {
        
        if (cnt == idx) {
          return att;
        }
        cnt++;
      }
    }
    return rc;
  }

  public Attribute[] getNestByArray() {

    int sz = getArraySize(Constants.AttributeValues.NEST_BY);

    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    // give values
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.NEST_BY);
      }
    }

    return atts;
  }
  /*
  public Attribute[] getNestByArray() {
  //System.out.println("  getGroupByArray 1 sz:" + size());
  int sz = 0;
  // calc array size
  for (int i = 0; i < size(); i++) {
  Attribute att = (Attribute) get(i);
  //  System.out.println("    getGroupByArray 1a");
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
  // System.out.println("      getGroupByArray 1b ");
  sz++;
  }
  }
  //System.out.println("  getGroupByArray 2 sz: "+sz);
  if (sz == 0) {
  return null;
  }
  
  Attribute[] atts = new Attribute[sz];
  int k = 0;
  for (int i = 0; i < size(); i++) {
  Attribute att = (Attribute) get(i);
  //System.out.println("  getGroupByArray 2a");
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
  //System.out.println("      getGroupByArray 2b");
  atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.NEST_BY);
  }
  }
  
  return atts;
  }
   */

  public Attribute[] getGroupByArray() {

    // calc array size
    int sz = getArraySize(Constants.AttributeValues.GROUP_BY);

    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.GROUP_BY)) {
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.GROUP_BY);
      }
    }

    return atts;
  }
  /*
   * allocate array and fill with e values
   */

  public Attribute[] getGroupByArray(PseudoElement e) {

    // calc array size
    int sz = getArraySize(Constants.AttributeValues.GROUP_BY);

    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.GROUP_BY)) {
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.GROUP_BY);
      }
    }
    for (int i = 0; i < atts.length; i++) {
      Attribute att = atts[i];
      String name = att.getValue().replace("()", "(" + att.getName() + ")");
      Attribute foundAtt = e.getAttributes().getAttribute(name);
      if (foundAtt != null) {
        att.setValue(foundAtt.getValue());
      }
    }

    return atts;
  }

  public int getCommandFunctionSize() {
    int sz = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      // xxx
      //  System.out.println("    getGroupByArray 1a");
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)
              || att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)
              || att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        // System.out.println("      getGroupByArray 1b ");
        sz++;
      }
    }
    return sz;
  }

  public Attribute[] getCommandFunctionArray() {
    //System.out.println("  getGroupByArray 1 sz:" + size());
    // name: count(xxx)
    // name: xxx
    // name: xxx
    int sz = 0;
    // calc array size
    sz = getCommandFunctionSize();
    /*
    for (int i = 0; i < size(); i++) {
    Attribute att = (Attribute) get(i);
    // xxx
    //  System.out.println("    getGroupByArray 1a");
    if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)
    || att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)
    || att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
    // System.out.println("      getGroupByArray 1b ");
    sz++;
    }
    }*/
    //System.out.println("  getGroupByArray 2 sz: "+sz);
    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      String name = "";
      //System.out.println("  getGroupByArray 2a");
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        //System.out.println("      getGroupByArray 2b");
        name = Constants.AttributeValues.COUNT.replace("()", "(" + att.getName() + ")");
        atts[k++] = new Attribute(name, Constants.AttributeValues.COUNT);
        //atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.COUNT);
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        //System.out.println("      getGroupByArray 2b");
        //atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.SUM);

        name = Constants.AttributeValues.SUM.replace("()", "(" + att.getName() + ")");
        //System.out.println("  set SUM 3 "+name);
        atts[k++] = new Attribute(name, Constants.AttributeValues.SUM);
      }

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        //atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.AVG);
        // System.out.println("  set AVG 2 " +att.getName());
        name = Constants.AttributeValues.AVG.replace("()", "(" + att.getName() + ")");
        //System.out.println("  set AVG 3 "+att.getName());
        atts[k++] = new Attribute(name, Constants.AttributeValues.AVG);
      }

    }

    return atts;
  }

  public Attribute[] getSourceCommandFuncitonArray() {
    //System.out.println("  getGroupByArray 1 sz:" + size());
    // straight name
    int sz = 0;
    // calc array size
    sz = getCommandFunctionSize();
    /*
    for (int i = 0; i < size(); i++) {
    Attribute att = (Attribute) get(i);
    //  System.out.println("    getGroupByArray 1a");
    if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
    // System.out.println("      getGroupByArray 1b ");
    sz++;
    }
    }
     * 
     */
    //System.out.println("  getGroupByArray 2 sz: "+sz);
    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.COUNT);
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        //   System.out.println("  set SUM 4 "+att.getName());
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.SUM);
      }
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        //System.out.println("  set AVG 4 "+ att.getName());
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.AVG);
      }
    }

    return atts;
  }

  public Attribute[] getCountSourceArray() {
    //System.out.println("  getGroupByArray 1 sz:" + size());
    // straight name
    int sz = 0;
    // calc array size
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      //  System.out.println("    getGroupByArray 1a");
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        // System.out.println("      getGroupByArray 1b ");
        sz++;
      }
    }
    //System.out.println("  getGroupByArray 2 sz: "+sz);
    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      //System.out.println("  getGroupByArray 2a");
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        //System.out.println("      getGroupByArray 2b");
        //atts[k++] = new CountAttribute(att.getName(), Constants.AttributeValues.COUNT);
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.COUNT);
      }
    }

    return atts;
  }

  public Attribute[] getCountTargetArray(Attribute[] sourceArray) {
    //System.out.println("  getGroupByArray 1 sz:" + size());
    int sz = 0;
    // calc array size

    if (sourceArray == null) {
      return null;
    }

    Attribute[] atts = new Attribute[sourceArray.length];

    for (int i = 0; i < sourceArray.length; i++) {
      Attribute att = (Attribute) sourceArray[i].getClone();
      String newName = Constants.AttributeValues.COUNT.replace("()", "(" + att.getName() + ")");
      att.setName(newName);
      att.setValue("0");
      atts[i] = att;
      // System.out.println("   getCountTargetArray  name: " + att.getName() + "  value: " + att.getValue() );
    }

    return atts;
  }

  /*
  public Attribute[] getCountSourceArray() {
  //System.out.println("  getGroupByArray 1 sz:" + size());
  int sz = 0;
  // calc array size
  for (int i = 0; i < size(); i++) {
  Attribute att = (Attribute) get(i);
  //  System.out.println("    getGroupByArray 1a");
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
  // System.out.println("      getGroupByArray 1b ");
  sz++;
  }
  }
  //System.out.println("  getGroupByArray 2 sz: "+sz);
  if (sz == 0) {
  return null;
  }
  
  Attribute[] atts = new Attribute[sz];
  int k = 0;
  for (int i = 0; i < size(); i++) {
  Attribute att = (Attribute) get(i);
  //System.out.println("  getGroupByArray 2a");
  if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
  //System.out.println("      getGroupByArray 2b");
  atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.COUNT);
  }
  }
  
  return atts;
  }
   */
  public int getArraySize(String constValue) {
    int rc = 0;

    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      if (att.getValue().equalsIgnoreCase(constValue)) {
        rc++;
      }
    }
    return rc;
  }

  public Attribute[] getSumArray() {
    //System.out.println("  getGroupByArray 1 sz:" + size());
    int sz = 0;
    // calc array size
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      //  System.out.println("    getGroupByArray 1a");
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        // System.out.println("      getGroupByArray 1b ");
        sz++;
      }
    }
    //System.out.println("  getGroupByArray 2 sz: "+sz);
    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      //System.out.println("  getGroupByArray 2a");
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        //System.out.println("      getGroupByArray 2b");
        //     System.out.println("  set SUM 5 "+att.getName());
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.SUM);
      }
    }

    return atts;
  }

  public Attribute[] getAvgArray() {

    int sz = 0;
    // calc array size
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);

      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        sz++;
      }
    }

    if (sz == 0) {
      return null;
    }

    Attribute[] atts = new Attribute[sz];
    int k = 0;
    for (int i = 0; i < size(); i++) {
      Attribute att = (Attribute) get(i);
      if (att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        // System.out.println("  set AVG 5  " + att.getName());
        atts[k++] = new Attribute(att.getName(), Constants.AttributeValues.AVG);
      }
    }

    return atts;
  }
  /*
  public boolean hasAttributeName(PseudoElement e, String searchname) {
  Attribute se = e.getAttributes().getAttribute(searchname);
  if (se == null) {
  return false;
  }
  return true;
  }
   */

  public boolean isNestBy(PseudoElement e) {
    boolean rc = false;
    // nest without count, sum, or avg
    // nest with count, sum, or avg

    for (int k = 0; k < size(); k++) { // loop through attributes
      Attribute nester = (Attribute) get(k);
      if (nester.getValue().equalsIgnoreCase(Constants.AttributeValues.NEST_BY)) {
//System.out.println(" search for:"+nester.getName());
        if (e.hasAttribute(nester.getName())) {
          //System.out.println("  found ");
          rc = true;
        }
      }
    }
    return rc;
  }
  /*
  public boolean isNestBy(PseudoElement e) {
  boolean rc = false;
  // nest without count, sum, or avg
  // nest with count, sum, or avg
  
  Attribute[] arry = getNestByArray();
  if (arry != null) {
  // name of the nest-by att needs to match the name in the element
  for (int i = 0; i < arry.length; i++) {
  // System.out.println("isNestBy lookfor:" + arry[i].getName() + "  cur:" + e.getName() +"  " + hasAttributeName(e, arry[i].getName()) );
  if (e.hasAttribute(arry[i].getName())) {
  //System.out.println(" found: " + arry[i].getName());
  rc = true;
  }
  }
  }
  return rc;
  }
   */

  public boolean isCount(PseudoElement e) {
    boolean rc = false;

    Attribute[] arryCount = getCountSourceArray();
    if (arryCount == null) {
      return false;
    }
    if (arryCount.length == 0) {
      return false;
    }

    // must have at least one count
    for (int i = 0; i < arryCount.length; i++) {
      if (e.hasAttribute(arryCount[i].getName())) {
        rc = true;
      }

    }

    return rc;
  }
  /* public boolean isCount(PseudoElement e) {
  boolean rc = true;
  
  Attribute[] arryCount = getCountSourceArray();
  if (arryCount == null) {
  return false;
  }
  if (arryCount.length == 0) {
  return false;
  }
  
  // must have group names
  for (int i = 0; i < arryCount.length; i++) {
  if (!e.hasAttribute(arryCount[i].getName())) {
  rc = false;
  }
  
  }
  
  return rc;
  }
   */

  public boolean isSum(PseudoElement e) {
    boolean rc = true;

    Attribute[] arry = getSumArray();
    if (arry == null) {
      return false;
    }
    if (arry.length == 0) {
      return false;
    }

    // must have group names
    for (int i = 0; i < arry.length; i++) {
      if (!e.hasAttribute(arry[i].getName())) {
        rc = false;
      }

    }

    return rc;
  }

  public boolean isAvg(PseudoElement e) {
    boolean rc = true;

    Attribute[] arry = getAvgArray();
    if (arry == null) {
      return false;
    }
    if (arry.length == 0) {
      return false;
    }

    // must have group names
    for (int i = 0; i < arry.length; i++) {
      if (!e.hasAttribute(arry[i].getName())) {
        rc = false;
      }

    }

    return rc;
  }

  /*
   * must have all the group by elements to be a group by
   */
  public boolean isGroupBy(PseudoElement e) {
    boolean rc = true;
    Attribute[] arryGroup = getGroupByArray();

    if (arryGroup == null) {
      return false;
    }

    if (arryGroup.length == 0) {
      return false;
    }

    // and element must have all the group by attributes
    for (int idx_c = 0; idx_c < size(); idx_c++) {
      Attribute c_att = (Attribute) get(idx_c);
      if (c_att.getValue().equalsIgnoreCase(Constants.AttributeValues.GROUP_BY)) {
        Attribute e_att = e.getAttributes().getAttribute(c_att.getName());
        if (e_att == null) {
          return false;
        }
      }
    }


    // and element must have one or more abc:count(), abc:sum(), abc:avg()
    boolean hasCount = false;
    boolean hasSum = false;
    boolean hasAvg = false;
    boolean hasConfig = false;
    for (int idx_c = 0; idx_c < size(); idx_c++) {
      Attribute c_att = (Attribute) get(idx_c);
      if (c_att.getValue().equalsIgnoreCase(Constants.AttributeValues.COUNT)) {
        hasConfig = true;
        Attribute e_att = e.getAttributes().getAttribute(c_att.getName());
        if (e_att != null) {
          hasCount = true;
        }
      }
      if (c_att.getValue().equalsIgnoreCase(Constants.AttributeValues.SUM)) {
        hasConfig = true;
        Attribute e_att = e.getAttributes().getAttribute(c_att.getName());
        if (e_att != null) {
          hasSum = true;
        }
      }
      if (c_att.getValue().equalsIgnoreCase(Constants.AttributeValues.AVG)) {
        hasConfig = true;
        Attribute e_att = e.getAttributes().getAttribute(c_att.getName());
        if (e_att != null) {
          hasAvg = true;
        }
      }
    }

    if (hasConfig) {
      if (!hasCount && !hasSum && !hasAvg) {
        rc = false;
      }
    }
    return rc;
  }
  /*
  public boolean isGroupBy(PseudoElement e) {
  boolean rc = true;
  Attribute[] arryGroup = getGroupByArray();
  
  if (arryGroup == null) {
  return false;
  }
  
  if (arryGroup.length == 0) {
  return false;
  }
  
  // must have all group names
  for (int i = 0; i < arryGroup.length; i++) {
  if (!e.hasAttribute(arryGroup[i].getName())) {
  rc = false;
  }
  }
  
  if (!rc) {
  return rc;
  }
  
  
  // must also have function names if there are function defined
  // xxxx what is in the getName
  //Attribute[] arryFunctions = getCommandFunctionArray();
  Attribute[] arryFunctions = getSourceCommandFuncitonArray();
  
  if (arryFunctions == null) {
  return true;
  }
  
  for (int i = 0; i < arryFunctions.length; i++) {
  //  System.out.println("  search: " + arryFunctions[i].getName());
  if (!e.hasAttribute(arryFunctions[i].getName())) {
  rc = false;
  }
  }
  
  return rc;
  }
   */
}
