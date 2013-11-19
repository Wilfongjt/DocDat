/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docdat.io.exceptions;

/**
 *
 * @author wilfongj
 */
public class UnknownFileTypeException extends Exception{

    public UnknownFileTypeException(){super();}
    public UnknownFileTypeException(String str){super(str);}
    public UnknownFileTypeException(String message, Throwable cause ){super(message, cause);}
    public UnknownFileTypeException(Throwable cause){super(cause);}
}
