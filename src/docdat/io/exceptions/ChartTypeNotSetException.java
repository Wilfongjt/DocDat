/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docdat.io.exceptions;

/**
 *
 * @author wilfongj
 */
public class ChartTypeNotSetException extends Exception{

    public ChartTypeNotSetException(){super();}
    public ChartTypeNotSetException(String str){super(str);}
    public ChartTypeNotSetException(String message, Throwable cause ){super(message, cause);}
    public ChartTypeNotSetException(Throwable cause){super(cause);}
}
