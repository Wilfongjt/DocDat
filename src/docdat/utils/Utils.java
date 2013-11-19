/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.utils;

/**
 *
 * @author wilfongj
 */
public class Utils {
    public Utils(){}
    
    public static String padFront(String value, String ch , int width){
        String rc = value ;
        while( rc.length() <= width   ){
            rc = ch + rc;
        }
        return rc;
    }
    
}
