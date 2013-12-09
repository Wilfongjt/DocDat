/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docdat.utils;

import docdat.io.names.ProcessLogger;




/**
 *
 * @author James
 */
public class Attribute {
  public Attribute(){}

  public Attribute(String name,String value){
    setName(name);
    setValue(value);
  }
  
  public boolean equals(Attribute e){
     if(!e.getName().equalsIgnoreCase(this.getName()) ){return false;}
     if(!e.getValue().equalsIgnoreCase(this.getValue())){return false;}
     return true;
 }

  private String _Name = "";
  public String getName(){return _Name;}
  public void setName(String nm){_Name=nm;}
 
  private String _Value = "";
  public String getValue(){
    return _Value;
  }
  
  public void setValue(String Val){
      String val = Val;
      while(  val.indexOf("\"") >=0){  
          val = val.replace("\"", "'"); 
      }
    _Value=val;
    
  }

      /*public String getFunctionName(){
        return "";
    }*/


  private String _Color = Constants.Colors.COLOR_DEFAULT;

  public String getColor() {
    return _Color;
  }

  public void setColor(String _Color) {
    this._Color = _Color;
  }
  public String toString(){
      //return "name: " + getName() + "  value: "+ getValue();
       return  "["+getName() + ": "+ getValue() +"]";
  }
  
    public Attribute getClone(){
      // make an exact copy including the class type
      Attribute  a = new Attribute();
      a.Assign(this);
      return a;
    } 
    
    public void Assign(Attribute wp){

      setName(wp.getName());
      setValue(wp.getValue());
     
      
      
    }  
  public static synchronized void WriteDebug(String aLine){
       ProcessLogger dbug = new ProcessLogger("debug" +".log");  
       dbug.Write( aLine);
       dbug = null;
  }  
  
}
