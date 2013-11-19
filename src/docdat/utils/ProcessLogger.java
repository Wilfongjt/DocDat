package docdat.utils;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ProcessLogger{
  String LogName = "";
  String LogPath = "";
  
  private Date _lasttime= new Date();
  protected long getTimeDifference(){
    // calculate time since last call  
    Date _newtime = new Date();  
    long diff =_newtime.getTime() - _lasttime.getTime(); 
    _lasttime = _newtime;
    return diff;
  }
  
  public synchronized void WriteNoDate(String MFStr){
      try{  
        //MFStr = MFStr;

        File mfFile = new File(LogPath,LogName);  
        if(mfFile != null){
          //BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile));  
          //DataOutputStream mf = new DataOutput(mfFile,"rw");
          //mf.seek(mf.length());
          //mf.write(MFStr.getBytes());
          RandomAccessFile mf = new RandomAccessFile(mfFile,"rw");
          mf.seek(mf.length());
          mf.write(MFStr.getBytes());
          mf.write('\r');
          mf.write('\n');
          mf.close();
          
        }  
      }
      
      catch(IOException ioe){
     //   WriteErrorMessage("Unable to write malfunction to " + mfFileName); 
       // throw new IOException(ioe.toString() + "Unable to write malfunction to " + mfFileName);   
      }
      catch(Exception e){
       //  WriteErrorMessage("Unable to write malfunction to " + mfFileName); 
         //throw new IOException(e.toString() + "Unable to write malfunction to " + mfFileName);   
      }  
  }
    
  public synchronized void Write(String MFStr){
      try{
        String rc = "";
        rc = new Date() + "-"+ getTimeDifference() +"-"+ MFStr;

        File mfFile = new File(LogPath,LogName);  
        if(mfFile != null){
          //BufferedWriter mf = new BufferedWriter(new FileWriter(mfFile));  
          //DataOutputStream mf = new DataOutput(mfFile,"rw");
          //mf.seek(mf.length());
          //mf.write(MFStr.getBytes());
          RandomAccessFile mf = new RandomAccessFile(mfFile,"rw");
          //System.out.println(rc);
          mf.seek(mf.length());
          mf.write(rc.getBytes());
          mf.write('\r');
          mf.write('\n');
          mf.close();

        }  
      }
      
      catch(IOException ioe){
     //   WriteErrorMessage("Unable to write malfunction to " + mfFileName); 
       // throw new IOException(ioe.toString() + "Unable to write malfunction to " + mfFileName);   
      }
      catch(Exception e){
       //  WriteErrorMessage("Unable to write malfunction to " + mfFileName); 
         //throw new IOException(e.toString() + "Unable to write malfunction to " + mfFileName);   
      }  
    }
    
    public String getFileName(){return LogPath + "\\" + LogName;}
    
    public void KillLog(){
      try{  
        File mfFile = new File(LogPath,LogName);  
        if(mfFile != null){
          mfFile.delete();
        }  
      }
      catch(Exception e){
       //  WriteErrorMessage("Unable to write malfunction to " + mfFileName); 
         //throw new IOException(e.toString() + "Unable to write malfunction to " + mfFileName);   
      }  
  
    }   
    public ProcessLogger(){
      LogName = "ProcessLogger.log";
      LogPath = System.getProperty("user.dir");
      
    }  
    public ProcessLogger(File aFile){
      LogName = aFile.getName()  ;  
      LogPath = aFile.getParent() ;   
    }    
    public ProcessLogger(String mfFileName){
      LogName = mfFileName;
      LogPath = System.getProperty("user.dir");

    }  
    public ProcessLogger(String FilePath,String mfFileName){
      LogName = mfFileName ;  
      LogPath = FilePath ;   
    }    
    
}     