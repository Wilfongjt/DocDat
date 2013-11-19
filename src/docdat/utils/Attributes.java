package docdat.utils;

import java.util.ArrayList;



public class Attributes extends ArrayList {
    
  public Attributes(){}
  
 
  public String getClassName(){return "Attributes";}
  

  
  public void setAttribute(String name,String value){
      Attribute bp = new Attribute();
      bp.setName(name);   
      bp.setValue(value); 
      setAttribute(bp);
  }  

    public void setAttribute(Attribute bp){

      // check for parameter if in list
      Attribute lookup = getAttribute(bp.getName());

      if(lookup == null){
        add(bp);
      }else{
        lookup.setValue(bp.getValue());
      }

  }
    /*
     * set attribute only if it is not in the list already
     * this is intended to collect list of all attriubtes
     * used for header / column titles
     */
    public void setUniqueAttribute(Attribute bp, String skipAttribs){

      // check for parameter if in list
      Attribute lookup = getAttribute(bp.getName());

      if(lookup == null ){
        if(isShow(bp,skipAttribs)){ // some attributes are suberted and push into element so skip them
          add(bp);
        }
      } 


  }

/*
  private String SkipAttributes=""; // formated

  public String getSkipAttributes() {
    return SkipAttributes;
  }

  public void setSkipAttributes(String SkipAttributes) {
    this.SkipAttributes += "[" + SkipAttributes + "]";
  }
*/


  public boolean isShow(Attribute att, String skipAttribs){
    String name = "";

    if(att == null){
         return false;
    }

    name = att.getName().toLowerCase();
    if(name == null){
      return false;
    }
    if(name.length()==0){
      return false; // empty name field
    }
    
   /* if(Constants.SKIP_ATTRIBUTES.toLowerCase().contains("["+name.trim()+"]") ){
      return false;
    }*/
   
    if(skipAttribs.toLowerCase().contains("["+name.trim()+"]") ){
      return false;
    }



    return true;
  }
  /*public void setAttribute(Attribute bp){
     
      // check for parameter if in list then delete it.
      int i = 0;
      int found = 0;
      
      for(i=0;i < size();i++){
        Attribute p = getAttribute(i);
        if(p.getName().toLowerCase().equals(bp.getName().toLowerCase()) ){
          found = i ;  
        }           
        //check for 
      } 
      
      if(found == 0){ // new value
        // add new parameter value
        add(bp);
      }else{ // replace the old value using the same key
        remove(new Integer(found));
        add(bp);
      }  

  }   */

  
  /* */
  public Attribute getAttribute(int i){
      if(i<0 || i>=size()){return null;}
      return (Attribute) get(new Integer(i));
  } 
  
  public Attribute getAttribute(String paramname){

      for(int i = 0; i < size();i++){

        Attribute bp = (Attribute)get(new Integer(i));
        
        if(bp.getName().toLowerCase().equals(paramname.trim().toLowerCase())){
            //System.out.println(     "     compare ["+paramname+"] to  [" +bp.getName() +"] found");

          return bp;  
        }

      }
      
      return null;
  } 
 

  
  public String getAttributes(){
      String rc = "";
      //getAD().WriteDebug("getAttributes 1");
      for(int i = 0;i < size();i++){
        if(rc.length()>0){rc = rc + " ";}
        Attribute bp = (Attribute)get(new Integer(i));
        rc += bp.getName() + "=\"" + bp.getValue() +"\"";
      }    
      //getAD().WriteDebug("getAttributes rc= "+ rc);
      return rc;
  } 
 
  public String getName(int i){
    if(i<0 || i>=size()){return null;}
    Attribute bp = (Attribute)get(new Integer(i));
    return bp.getName();
  }
  public String getValue(int i){
    if(i<0 || i>=size()){return null;}

    Attribute bp = (Attribute)get(new Integer(i));
    return bp.getValue();
  }
  
  
  
  
  public Attributes getClone(){
      // make an exact copy including the class type
      Attributes  a = new Attributes();
      a.Assign(this);
      return a;
  } 
  
  public void Assign(Attributes cps){
    //setAD(cps.getAD());
    for(int i = 0; i<cps.size();i++){
      setAttribute(cps.getAttribute(i));
    }  
  }  
  
}    

