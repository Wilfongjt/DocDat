/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.utils;

/**
 *
 * @author wilfongj
 */
public class Typer {

  public int getType(String val) {
    int rc = Constants.STRING;
    if (isNumeric(val)) {
      if (isInteger(val)) {
        rc = Constants.INTEGER;
      } else {
        rc = Constants.DOUBLE;
      }
    }
    return rc;
  }

  public boolean isNumeric(String val) {
    boolean rc = true;
    for (int i = 0; i < val.length(); i++) {
      char ch = val.charAt(i);
      if (Constants.Filters.NUMERICS.indexOf(ch) < 0) {
        rc = false;
      }
    }
    return rc;
  }

  public boolean isDouble(String val) {
    boolean rc = true;
    for (int i = 0; i < val.length(); i++) {
      char ch = val.charAt(i);
      if (Constants.Filters.NUMERICDOUBLE.indexOf(ch) < 0) {
        rc = false;
      }
    }
    return rc;
  }

  public boolean isInteger(String val) {
    boolean rc = true;
    for (int i = 0; i < val.length(); i++) {
      char ch = val.charAt(i);
      if (Constants.Filters.NUMERICINTEGER.indexOf(ch) < 0) {
        rc = false;
      }
    }
    return rc;
  }
  /*public boolean isString(String val){
  boolean rc = false;

  return rc;
  }*/
}
