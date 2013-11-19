package docdat.utils;

// HTMLValidation

//import java.sql.*;
//import java.io.*;
//import javax.servlet.*;
//import javax.servlet.http.*;
//import bpm.v12.utilities.StringParser;

public class DataFixer{
  String value = "";  
  public DataFixer(){
    
  }
 
  public String getClassName(){return "DataFixer";}
  
  public String getInteger(String aval){
    if(aval == null){return "";}
    String tmp = "";
    boolean stop = false;
    for(int i = 0; i < aval.length() && !stop;i++){
      switch(aval.charAt(i)){
         case '0': tmp = tmp + aval.charAt(i); break;
         case '1': tmp = tmp + aval.charAt(i); break;
         case '2': tmp = tmp + aval.charAt(i); break;
         case '3': tmp = tmp + aval.charAt(i); break;        
         case '4': tmp = tmp + aval.charAt(i); break;
         case '5': tmp = tmp + aval.charAt(i); break;
         case '6': tmp = tmp + aval.charAt(i); break;
         case '7': tmp = tmp + aval.charAt(i); break;
         case '8': tmp = tmp + aval.charAt(i); break;
         case '9': tmp = tmp + aval.charAt(i); break;
         case '-': tmp = tmp + aval.charAt(i); break;
         case '.': stop = true; break; // truncates anything after the . and including the . 
         //default: tmp = tmp + aval.charAt(i);
      } 
    }
    return tmp;
  }
  public String getDouble(String aval){
    if(aval == null){return "";}
    String tmp = "";
    boolean stop = false;
    for(int i = 0; i < aval.length() && !stop;i++){
      switch(aval.charAt(i)){
         case '0': tmp = tmp + aval.charAt(i); break;
         case '1': tmp = tmp + aval.charAt(i); break;
         case '2': tmp = tmp + aval.charAt(i); break;
         case '3': tmp = tmp + aval.charAt(i); break;        
         case '4': tmp = tmp + aval.charAt(i); break;
         case '5': tmp = tmp + aval.charAt(i); break;
         case '6': tmp = tmp + aval.charAt(i); break;
         case '7': tmp = tmp + aval.charAt(i); break;
         case '8': tmp = tmp + aval.charAt(i); break;
         case '9': tmp = tmp + aval.charAt(i); break;
         case '-': tmp = tmp + aval.charAt(i); break;
         case '.': tmp += aval.charAt(i);break; 
         //default: tmp = tmp + aval.charAt(i);
      } 
    }
    return tmp;
  }  
  public String getTextXML(String aval){
    if(aval == null){return "";}
    String tmp = "";
    //System.err.println("getText aval = "+aval);
    for(int i = 0; i < aval.length();i++){
      switch(aval.charAt(i)){
         case ' ': tmp = tmp + aval.charAt(i); break;
         case 'A': tmp = tmp + aval.charAt(i); break;
         case 'B': tmp = tmp + aval.charAt(i); break;
         case 'C': tmp = tmp + aval.charAt(i); break;
         case 'D': tmp = tmp + aval.charAt(i); break;
         case 'E': tmp = tmp + aval.charAt(i); break;
         case 'F': tmp = tmp + aval.charAt(i); break;
         case 'G': tmp = tmp + aval.charAt(i); break;
         case 'H': tmp = tmp + aval.charAt(i); break;
         case 'I': tmp = tmp + aval.charAt(i); break;
         case 'J': tmp = tmp + aval.charAt(i); break;
         case 'K': tmp = tmp + aval.charAt(i); break;
         case 'L': tmp = tmp + aval.charAt(i); break;
         case 'M': tmp = tmp + aval.charAt(i); break;
         case 'N': tmp = tmp + aval.charAt(i); break;
         case 'O': tmp = tmp + aval.charAt(i); break;
         case 'P': tmp = tmp + aval.charAt(i); break;
         case 'Q': tmp = tmp + aval.charAt(i); break;
         case 'R': tmp = tmp + aval.charAt(i); break;
         case 'S': tmp = tmp + aval.charAt(i); break;
         case 'T': tmp = tmp + aval.charAt(i); break;
         case 'U': tmp = tmp + aval.charAt(i); break;
         case 'V': tmp = tmp + aval.charAt(i); break;
         case 'W': tmp = tmp + aval.charAt(i); break;
         case 'X': tmp = tmp + aval.charAt(i); break;
         case 'Y': tmp = tmp + aval.charAt(i); break;
         case 'Z': tmp = tmp + aval.charAt(i); break;

         case 'a': tmp = tmp + aval.charAt(i); break;
         case 'b': tmp = tmp + aval.charAt(i); break;
         case 'c': tmp = tmp + aval.charAt(i); break;
         case 'd': tmp = tmp + aval.charAt(i); break;
         case 'e': tmp = tmp + aval.charAt(i); break;
         case 'f': tmp = tmp + aval.charAt(i); break;
         case 'g': tmp = tmp + aval.charAt(i); break;
         case 'h': tmp = tmp + aval.charAt(i); break;
         case 'i': tmp = tmp + aval.charAt(i); break;
         case 'j': tmp = tmp + aval.charAt(i); break;
         case 'k': tmp = tmp + aval.charAt(i); break;
         case 'l': tmp = tmp + aval.charAt(i); break;
         case 'm': tmp = tmp + aval.charAt(i); break;
         case 'n': tmp = tmp + aval.charAt(i); break;
         case 'o': tmp = tmp + aval.charAt(i); break;
         case 'p': tmp = tmp + aval.charAt(i); break;
         case 'q': tmp = tmp + aval.charAt(i); break;
         case 'r': tmp = tmp + aval.charAt(i); break;
         case 's': tmp = tmp + aval.charAt(i); break;
         case 't': tmp = tmp + aval.charAt(i); break;
         case 'u': tmp = tmp + aval.charAt(i); break;
         case 'v': tmp = tmp + aval.charAt(i); break;
         case 'w': tmp = tmp + aval.charAt(i); break;
         case 'x': tmp = tmp + aval.charAt(i); break;
         case 'y': tmp = tmp + aval.charAt(i); break;
         case 'z': tmp = tmp + aval.charAt(i); break;
         
         case '0': tmp = tmp + aval.charAt(i); break;
         case '1': tmp = tmp + aval.charAt(i); break;
         case '2': tmp = tmp + aval.charAt(i); break;
         case '3': tmp = tmp + aval.charAt(i); break;        
         case '4': tmp = tmp + aval.charAt(i); break;
         case '5': tmp = tmp + aval.charAt(i); break;
         case '6': tmp = tmp + aval.charAt(i); break;
         case '7': tmp = tmp + aval.charAt(i); break;
         case '8': tmp = tmp + aval.charAt(i); break;
         case '9': tmp = tmp + aval.charAt(i); break;
         
         case '.': tmp = tmp + aval.charAt(i); break;
         case '-': tmp = tmp + aval.charAt(i); break;
         case ',': tmp = tmp + aval.charAt(i); break;
         case '!': tmp = tmp + aval.charAt(i); break;
         case '@': tmp = tmp + aval.charAt(i); break;
         case '#': tmp = tmp + aval.charAt(i); break;
         case '$': tmp = tmp + aval.charAt(i); break;
         case '%': tmp = tmp + aval.charAt(i); break;
         case '^': tmp = tmp + aval.charAt(i); break;
         case '&': tmp = tmp + "+"; break;
         case '*': tmp = tmp + aval.charAt(i); break;
         case '(': tmp = tmp + aval.charAt(i); break;
         case ')': tmp = tmp + aval.charAt(i); break;
         
         case '_': tmp = tmp + aval.charAt(i); break;
         case '+': tmp = tmp + aval.charAt(i); break;
         case '=': tmp = tmp + aval.charAt(i); break;
         
         case '{': tmp = tmp + aval.charAt(i); break;
         case '}': tmp = tmp + aval.charAt(i); break;
         case '[': tmp = tmp + aval.charAt(i); break;
         case ']': tmp = tmp + aval.charAt(i); break;
         
         case ':': tmp = tmp + aval.charAt(i); break;
         case ';': tmp = tmp + aval.charAt(i); break;
         case '?': tmp = tmp + aval.charAt(i); break;
         case '~': tmp = tmp + aval.charAt(i); break;
         case '|': tmp = tmp + aval.charAt(i); break;
         
         // escape some characters
         case '\r': tmp = tmp + " "; break;
         case '\n': tmp = tmp + " "; break;  
         
         case '`': tmp = tmp + "\'"; break;
         case '\'': tmp = tmp + "\'"; break;
         case '\\': tmp = tmp + "/"; break;
         case '/': tmp = tmp + "/"; break;
         case '<': tmp = tmp + "-"; break; // script breaker
         case '>': tmp = tmp + "-"; break; // script breaker
         case '\"': tmp = tmp + "-"; break;

      } 
    }

    //System.err.println("tmp = "+tmp);
    return tmp;
          
    
    //return getCharacter(aval); 
  }  
  public String getCharacterXML(String aval){
    if(aval == null){return "";}
    //WriteDebug("getCharacter lenght()=" + aval.length());
    String tmp = "";
    for(int i = 0; i < aval.length();i++){
      switch(aval.charAt(i)){
         case ' ': tmp = tmp + aval.charAt(i); break;
         case 'A': tmp = tmp + aval.charAt(i); break;
         case 'B': tmp = tmp + aval.charAt(i); break;
         case 'C': tmp = tmp + aval.charAt(i); break;
         case 'D': tmp = tmp + aval.charAt(i); break;
         case 'E': tmp = tmp + aval.charAt(i); break;
         case 'F': tmp = tmp + aval.charAt(i); break;
         case 'G': tmp = tmp + aval.charAt(i); break;
         case 'H': tmp = tmp + aval.charAt(i); break;
         case 'I': tmp = tmp + aval.charAt(i); break;
         case 'J': tmp = tmp + aval.charAt(i); break;
         case 'K': tmp = tmp + aval.charAt(i); break;
         case 'L': tmp = tmp + aval.charAt(i); break;
         case 'M': tmp = tmp + aval.charAt(i); break;
         case 'N': tmp = tmp + aval.charAt(i); break;
         case 'O': tmp = tmp + aval.charAt(i); break;
         case 'P': tmp = tmp + aval.charAt(i); break;
         case 'Q': tmp = tmp + aval.charAt(i); break;
         case 'R': tmp = tmp + aval.charAt(i); break;
         case 'S': tmp = tmp + aval.charAt(i); break;
         case 'T': tmp = tmp + aval.charAt(i); break;
         case 'U': tmp = tmp + aval.charAt(i); break;
         case 'V': tmp = tmp + aval.charAt(i); break;
         case 'W': tmp = tmp + aval.charAt(i); break;
         case 'X': tmp = tmp + aval.charAt(i); break;
         case 'Y': tmp = tmp + aval.charAt(i); break;
         case 'Z': tmp = tmp + aval.charAt(i); break;

         case 'a': tmp = tmp + aval.charAt(i); break;
         case 'b': tmp = tmp + aval.charAt(i); break;
         case 'c': tmp = tmp + aval.charAt(i); break;
         case 'd': tmp = tmp + aval.charAt(i); break;
         case 'e': tmp = tmp + aval.charAt(i); break;
         case 'f': tmp = tmp + aval.charAt(i); break;
         case 'g': tmp = tmp + aval.charAt(i); break;
         case 'h': tmp = tmp + aval.charAt(i); break;
         case 'i': tmp = tmp + aval.charAt(i); break;
         case 'j': tmp = tmp + aval.charAt(i); break;
         case 'k': tmp = tmp + aval.charAt(i); break;
         case 'l': tmp = tmp + aval.charAt(i); break;
         case 'm': tmp = tmp + aval.charAt(i); break;
         case 'n': tmp = tmp + aval.charAt(i); break;
         case 'o': tmp = tmp + aval.charAt(i); break;
         case 'p': tmp = tmp + aval.charAt(i); break;
         case 'q': tmp = tmp + aval.charAt(i); break;
         case 'r': tmp = tmp + aval.charAt(i); break;
         case 's': tmp = tmp + aval.charAt(i); break;
         case 't': tmp = tmp + aval.charAt(i); break;
         case 'u': tmp = tmp + aval.charAt(i); break;
         case 'v': tmp = tmp + aval.charAt(i); break;
         case 'w': tmp = tmp + aval.charAt(i); break;
         case 'x': tmp = tmp + aval.charAt(i); break;
         case 'y': tmp = tmp + aval.charAt(i); break;
         case 'z': tmp = tmp + aval.charAt(i); break;
         
         case '0': tmp = tmp + aval.charAt(i); break;
         case '1': tmp = tmp + aval.charAt(i); break;
         case '2': tmp = tmp + aval.charAt(i); break;
         case '3': tmp = tmp + aval.charAt(i); break;        
         case '4': tmp = tmp + aval.charAt(i); break;
         case '5': tmp = tmp + aval.charAt(i); break;
         case '6': tmp = tmp + aval.charAt(i); break;
         case '7': tmp = tmp + aval.charAt(i); break;
         case '8': tmp = tmp + aval.charAt(i); break;
         case '9': tmp = tmp + aval.charAt(i); break;
         
         case '.': tmp = tmp + aval.charAt(i); break;
         case '-': tmp = tmp + aval.charAt(i); break;
         case ',': tmp = tmp + aval.charAt(i); break;
         case '!': tmp = tmp + aval.charAt(i); break;
         case '@': tmp = tmp + aval.charAt(i); break;
         case '#': tmp = tmp + aval.charAt(i); break;
         case '$': tmp = tmp + aval.charAt(i); break;
         case '%': tmp = tmp + aval.charAt(i); break;
         case '^': tmp = tmp + aval.charAt(i); break;
         case '&': tmp = tmp + "&amp;"; break;
         case '*': tmp = tmp + aval.charAt(i); break;
         case '(': tmp = tmp + aval.charAt(i); break;
         case ')': tmp = tmp + aval.charAt(i); break;
         
         case '_': tmp = tmp + aval.charAt(i); break;
         case '+': tmp = tmp + aval.charAt(i); break;
         case '=': tmp = tmp + aval.charAt(i); break;
         
         case '{': tmp = tmp + aval.charAt(i); break;
         case '}': tmp = tmp + aval.charAt(i); break;
         case '[': tmp = tmp + aval.charAt(i); break;
         case ']': tmp = tmp + aval.charAt(i); break;
         
         case ':': tmp = tmp + aval.charAt(i); break;
         case ';': tmp = tmp + aval.charAt(i); break;
         case '?': tmp = tmp + aval.charAt(i); break;
         case '~': tmp = tmp + aval.charAt(i); break;
         case '|': tmp = tmp + aval.charAt(i); break;
         
         // escape some characters
         case '\r': tmp = tmp + " "; break;
         case '\n': tmp = tmp + " "; break;

         case '`': tmp = tmp + "-"; break;
         case '\'': tmp = tmp + "&apos;"; break;
         case '\\': tmp = tmp + "-"; break;
         case '/': tmp = tmp + "/"; break;
         case '<': tmp = tmp + "&lt;"; break;
         case '>': tmp = tmp + "&gt;"; break;
         case '\"': tmp = tmp + "&quot;"; break;
 
      } 
    }
    return tmp;
  }

  public String getDatetime(String aval,char sep){
      if(aval == null){return "";}
      String tmp = "";
      // the database by default stores dates as yyyy-mm-dd 00:00:00.0
      // this code checks to see if this is the case and reverses the order to US format
      if(aval != null && aval.trim().length()>0){

        if(aval.indexOf(':') > 0 && aval.indexOf('.') > 0){
          // reverse the date
          StringParser SP = new StringParser();
          tmp = SP.getValue(aval,1,' '); // truncate the time stamp
          String _Value = tmp; // do this to use the getSeperator value overwrite later
          _Value = SP.getValue(tmp,2, sep ) + sep +  SP.getValue(tmp,3, sep) + sep +  SP.getValue(tmp,1, sep);
          tmp=_Value;
        }  
      }  
    
      return tmp;
  }
  
  public String getPhone(String aval ){
    if(aval == null){return "";} 
    String tmp="";
    for(int i = 0; i < aval.length();i++){
          switch(aval.charAt(i)){
             case '0': tmp = tmp + aval.charAt(i); break;
             case '1': tmp = tmp + aval.charAt(i); break;
             case '2': tmp = tmp + aval.charAt(i); break;
             case '3': tmp = tmp + aval.charAt(i); break;        
             case '4': tmp = tmp + aval.charAt(i); break;
             case '5': tmp = tmp + aval.charAt(i); break;
             case '6': tmp = tmp + aval.charAt(i); break;
             case '7': tmp = tmp + aval.charAt(i); break;
             case '8': tmp = tmp + aval.charAt(i); break;
             case '9': tmp = tmp + aval.charAt(i); break;
          }
    }    
    return tmp;
  }
  /////////////////////
  //
  ///////////
  public String getZipcode(String aval){
    if(aval == null){return "";}

    String tmp = "";
    for(int i = 0; i < aval.length();i++){
          switch(aval.charAt(i)){
             case '0': tmp +=  aval.charAt(i); break;
             case '1': tmp +=  aval.charAt(i); break;
             case '2': tmp +=  aval.charAt(i); break;
             case '3': tmp +=  aval.charAt(i); break;
             case '4': tmp +=  aval.charAt(i); break;
             case '5': tmp +=  aval.charAt(i); break;
             case '6': tmp +=  aval.charAt(i); break;
             case '7': tmp +=  aval.charAt(i); break;
             case '8': tmp +=  aval.charAt(i); break;
             case '9': tmp +=  aval.charAt(i); break;
          }
    }       

    return tmp;
  }
  
  
}