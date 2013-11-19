package docdat.utils;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*; 

public class StringParser{
  int decimalPlaces = 0;
  
  public StringParser(){  }  
  public int itemWidth(String DelimStr, int ColIndex, int Delimiter){
    // delimiters '\t' = TAB, '\'' = single quote... See Character Literals 
    int i = 1;
    int startpos = 0;
    int delpos = 0;
    int len = 0;
    
    String blankstr = " ";
    String rcstr = " ";
    
    //ProcessLogger ELog = new ProcessLogger(System.getProperty("user.dir"),"Error.log") ;
  
    // find find ColIndex - 1 occurance of the delimiter
    while(i <= ColIndex){
      delpos = DelimStr.indexOf(Delimiter,startpos) ;
      
      if(i == ColIndex){
        if(startpos == delpos){ 
          if(startpos + 1 == DelimStr.length()){// end of string
            return 0;
          } 
          return 0;   
        }    
        if(startpos > delpos) {// del not found ... end of string
          delpos = DelimStr.length();   
        }    
        rcstr = DelimStr.substring(startpos,delpos);
        len = rcstr.length();
        return len;
      }
      // set start to next possible position
      startpos = delpos + 1;
      if(startpos == 0){ // read past end of string
        return 0;
      }  
      // inc the col counter
      i = i + 1;
    } 
    
    return 0; 
  }  
  public String getValue(String DelimStr, int ColIndex, int Delimiter){
    String aValue = "";
    String str2pass = DelimStr;
    if(getIgnoreRepeatingDelimeters()){
      str2pass = removeRepeatingDelimeters(str2pass,Delimiter);   
    }    
    aValue = itemValue(str2pass, ColIndex, Delimiter);
    return aValue;
  }  
  public String itemValue(String DelimStr, int ColIndex, int Delimiter){
    // delimiters '\t' = TAB, '\'' = single quote... See Character Literals 
    int i = 1;
    int startpos = 0;
    int delpos = 0;
    int len = 0;
    String Nostr = "";
    String blankstr = " ";
    String rcstr = " ";
    
    //ProcessLogger ELog = new ProcessLogger(System.getProperty("user.dir"),"Error.log") ;
  
    // find find ColIndex - 1 occurance of the delimiter
    while(i <= ColIndex){
      delpos = DelimStr.indexOf(Delimiter,startpos) ;
      
      if(i == ColIndex){
        if(startpos == delpos){ // end of string
          if(startpos + 1 == DelimStr.length()){// end of string
            return Nostr; 
          } 
          return blankstr;   // no data
        }    
        if(startpos > delpos) {// del not found ... end of string
          delpos = DelimStr.length();   
        }    
        rcstr = DelimStr.substring(startpos,delpos);
        return rcstr;
      }
      // set start to next possible position
      startpos = delpos + 1;
      if(startpos == 0){ // read past end of string
        return Nostr;
      }  
      // inc the col counter
      i = i + 1;
    } 
    
    return Nostr; 
  }  
  private boolean ignorerepeatingdelimeters = false;
  public boolean getIgnoreRepeatingDelimeters(){return ignorerepeatingdelimeters;}
  public void setIgnoreRepeatingDelimeters(boolean setting){  }  
  

  public String removeRepeatingDelimeters(String astr,int Delim){
    String rc = "";
    String ch = "";
	String lastch = "a";
    int i = 0;
    int len = astr.length();
    // grab each char one at a time
    while(i < len){
      ch = astr.substring(i,i+1);
      // compare to delemiter 
     if(ch != null && ch.length() > 0 && ch.charAt(0) == Delim && lastch.charAt(0) == Delim){ // ch is a delimeter
	   // do nothing
     }else{
        rc = rc +  ch;
      }
      lastch = ch;   
      i++;  
    }    
    return rc; 
  }
  
  public String itemType(String DelimStr, int ColIndex, int Delimiter){
    String item = "";
    String rcstr = "";
    int w = 0;
    
    item = itemValue(DelimStr, ColIndex, Delimiter);
    if(item.length() == 0 ) {return item;}
    w = item.length();
    if(isInteger(item)){
      rcstr = "Integer";    
      return rcstr;
    }
    if(isDecimal(item)){  
      rcstr = "Decimal(" + w + "," + decimalPlaces + ")";    
      return rcstr;
    }
    rcstr = "Char(" + w + ")";
    return rcstr;
  } 
  
  public int ColumnCount(String DelimStr, int Delimiter){
    // delimiters '\t' = TAB, '\'' = single quote... See Character Literals 
    int i = 0;
    int startpos = 0;
    int delpos = 0;
    int len = 0;
    String blankstr = "";
    String rcstr = " ";
    int ColIndex = 300;
    int tempstartpos = 0;
    
    
    if(DelimStr.length() == 0) return 0;
    for(int k = 0 ; k<DelimStr.length();k++){
      char ch = DelimStr.charAt(k);
      if(ch == Delimiter){
        i++;
      }
    }
    i++; // always one extra
/*
    delpos = DelimStr.indexOf(Delimiter,startpos) ;

    while(delpos > -1){
        
      delpos = DelimStr.indexOf(Delimiter,startpos) ;
      startpos = delpos + 1;
      i = i + 1;
    }
 *
 */
    return i;
    
  }  
  
  public boolean isInteger(String item){
    int i = 0;
    boolean digit = true;
    while(i < item.length()){
      digit = false;  
      switch(item.charAt(i)){
         case '0': digit = true; break;
         case '1': digit = true; break;
         case '2': digit = true; break;
         case '3': digit = true; break;        
         case '4': digit = true; break;
         case '5': digit = true; break;
         case '6': digit = true; break;
         case '7': digit = true; break;
         case '8': digit = true; break;
         case '9': digit = true; break;
      }  
      if(!digit){  return false; }  
      i = i + 1;
    }
    return true;
  }  
  
  public boolean isDecimal(String item){
    int i = 0;
    boolean digit = true;
    int decPointCount = 0;
    decimalPlaces = 0;
    while(i < item.length()){
      digit = false;  
      switch(item.charAt(i)){
         case '0': digit = true; break;
         case '1': digit = true; break;
         case '2': digit = true; break;
         case '3': digit = true; break;        
         case '4': digit = true; break;
         case '5': digit = true; break;
         case '6': digit = true; break;
         case '7': digit = true; break;
         case '8': digit = true; break;
         case '9': digit = true; break;
         case '.': 
           digit = true; 
           decPointCount = decPointCount + 1;
           decimalPlaces = item.length() - (i + 1);
           break;
      }  
      
      if(decPointCount > 1){ return false;}
      if(!digit){  return false; }  
      i = i + 1;
    }
    return true;
  }  
      
}    