/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.utils;

/**
 *
 * @author wilfongj
 */
public class CountAttribute extends Attribute {

    public CountAttribute() {
      super();
    }

    public CountAttribute(String name, String value) {
        super(  Constants.AttributeValues.COUNT.replace("()",  "("+ name +")" ), value );
    }

 /*   public String getFunctionName(){
        return Constants.AttributeValues.COUNT.replace("()",  "("+ getName() +")"  ) ;
    }
*/
}
