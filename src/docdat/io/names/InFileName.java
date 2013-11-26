/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.io.names;

import docdat.io.exceptions.UnknownFileTypeException;
import docdat.io.factory.InputStreamReaderFactory;
import docdat.utils.Attribute;
import docdat.utils.Configuration;
import docdat.utils.Constants;
import docdat.utils.StringParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.transform.TransformerException;

/**
 *
 * the file name can be encoded with information
 * A filename usually has two parts the  1) project name 2) content type
 * if a file has three parts then 1) project name 2) content type 3) ext
 * if a file has four parts the 1) project name 2) subpart1 3) content type 3) ext
 * if a file has five parts then 1) project name 2) subpart1 3) subpart2 4) ext
 * @author wilfongj
 */
public class InFileName extends ArrayList {

    public InFileName() {
    }

    public InFileName(String path, String prjname, String sourceExt) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        setPath(path);
        add(prjname);
        add(sourceExt);
        // setProjectName(prjname);
        // setSourceName(sourceExt);
        setDefaults();

        parseConfiguration();
        replaceProxy();
    }

    public InFileName(String path, String filename) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
       
      //System.out.println("InFileName 1");
      setPath(path);
        StringParser SP = new StringParser();
        int cnt = SP.ColumnCount(filename, '.');
        ////System.out.println("cnt: "+cnt);
        for (int i = 1; i <= cnt; i++) {
            add(SP.getValue(filename, i, '.'));
        }
        setDefaults();
    //System.out.println("InFileName 2");
        parseConfiguration();
    
        replaceProxy();
        //System.out.println("InFileName out");
    }
    private Configuration configuration = new Configuration();

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
   /* public String getURL() {
        return "http:////" +  getPathAndFileName().substring(3).replace("\\", "/"); 
    }*/
    public void setPathAndFileName(String pathandfilename) throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException{
        //System.out.println("setPathAndFileName :" + pathandfilename);
        clear();// remove all objects
        String path = "";
        String filename = "";
        int index = pathandfilename.lastIndexOf("\\");
        path = pathandfilename.substring(0, index);
        filename = pathandfilename.substring(index + 1);

        setPath(path);

        StringParser SP = new StringParser();
        int cnt = SP.ColumnCount(filename, '.');
        ////System.out.println("cnt: "+cnt);
        for (int i = 1; i <= cnt; i++) {
            add(SP.getValue(filename, i, '.'));
        }
        setDefaults();

        //System.out.println("setPathAndFileName : out " + getFileName());
    }
    private String _Path = "";

    public String getPath() {
        return _Path;
    }

    public void setPath(String path) {

        path = path.trim();

        if (path.length() > 0 && path.charAt(path.length() - 1) != '\\') {
            path += "\\";
        }
        this._Path = path;

    }

    public String getType() {
        String rc = Constants.Type.TEXT;
        String t = (String) get(size() - 1);

        if (t.equalsIgnoreCase(Constants.Extentions.ODT)) {
            rc = Constants.Type.ODT;
        }

        return rc;
    }

    public String getSubName() {
        String rc = "";
        int sz = size();
        switch (sz) {
            case 0:
            case 1:
            case 2:
                // project name, source
                break;
            case 3: // project, sub, source 
                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    rc = (String) get(1); // subname
                }
                break;

            default: // project, sub, source, type
                rc = (String) get(1); // subname
                break;

        }
        return rc;
    }

    public void setSubName(String SubName) {
        int sz = size();
        switch (sz) {
            case 0:
            case 1:
                // throw new IOException(Constants.ErrorMsgs.MISSING_SOURCE + " " + getFileName());
                break;
            case 2:
                // project name, , source,  assume text type

                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    // make room for subname
                    add((String) get(size() - 1)); // add to end
                    for (int i = size() - 2; i > 1; i--) {
                        set(i, (String) get(i - 1));
                    }
                    set(1, SubName);
                } else {
                    // this is an error
                    // throw new IOException(Constants.ErrorMsgs.MISSING_SOURCE + " " + getFileName());
                }
                break;
            case 3:// project name, subName, source, assum text
                // project name, , source,  assume text type

                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    // has subname update it
                    set(1, SubName);
                } else {
                    // make room for subname
                    add((String) get(size() - 1)); // add to end
                    for (int i = size() - 2; i > 1; i--) {
                        set(i, (String) get(i - 1));
                    }
                    set(1, SubName);
                }
                break;

            default: // prj name, subName, source, ext

                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    // has subname update it
                    set(1, SubName);
                } else {
                    set(1, SubName);
                }


        }
    }

    public String getProjectName() {
        //return ProjectName;
        return (String) get(0);
    }


    /*
    public void setProjectName(String ProjectName) {
    this.ProjectName = ProjectName;
    }
     *
     */
    protected void setDefaults() {
        if (getSourceName().equalsIgnoreCase(Constants.Source.PROXY)) {
            setProxy(true);
        }
        
        if (getSourceName().equalsIgnoreCase(Constants.Source.PLUCK)) {
            setPluck(true);
        }
        
        if (getSourceName().equalsIgnoreCase(Constants.Source.SUMMARY)) {
            setSummary(true);
        }

        if (getSourceName().equalsIgnoreCase(Constants.Source.MASTER_REFERENCE)) {
            setMasterReference(true);
        }
    }
    private boolean proxy = false;

    public boolean isProxy() {
        return proxy;
    }

    public void setProxy(boolean proxy) {
        this.proxy = proxy;
    }
    /*
     *  pluck subset elements
     */
    private boolean Pluck = false;

    public boolean isPluck() {
        return Pluck;

    }

    public void setPluck(boolean IsPluck) {
        //System.out.println("setSummary "+IsPluck);
        this.Pluck = IsPluck;
    }
    /*
     *  summary reference sources get skipped in the intial load process and are loaded later in the special load process
     */
    private boolean Summary = false;

    public boolean isSummary() {
        return Summary;

    }

    public void setSummary(boolean IsSummary) {
        //System.out.println("setSummary "+IsSummary);
        this.Summary = IsSummary;
    }

    /*
    public boolean isSummary() {
    if (getSourceName().equalsIgnoreCase(Constants.Source.SUMMARY)) {
    return true;
    }
    return false;
    }
     */
    /*
     *  master reference sources get skipped in the intial load process and are loaded later in the special load process
     */
    private boolean MasterReference = false;

    public boolean isMasterReference() {
        return MasterReference;
    }

    public void setMasterReference(boolean MasterReference) {
        //System.out.println("setMasterReference "+MasterReference);
        this.MasterReference = MasterReference;
    }

    /*
    public boolean isMasterReference() {
    if (getSourceName().equalsIgnoreCase(Constants.Source.MASTER_REFERENCE)) {
    return true;
    }
    return false;
    }*/
    public String getSourceName() {

        String rc = "NoSource";


        if (getType().equals(Constants.Type.TEXT)) {
            rc = (String) get(size() - 1);


        } else {
            rc = "";


            if (size() > 2) {
                rc = (String) get(size() - 2);


            }

        }
        return rc;


    }
    /* */

    public void setSourceName(String SourceName) {
        int sz = size();


        switch (sz) {
            case 0: // nothin
                break;


            case 1: //
                add(SourceName);


                break;


            case 2: // project , source
                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    set(1, SourceName);


                } else {
                    // project, type
                    add((String) get(1));
                    set(
                            1, SourceName);


                }
                break;


            case 3:
                // proj, sub, source
                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    set(2, SourceName);


                } else {
                    // proj , source , type
                    set(1, SourceName);


                }
                break;


            case 4:
                // proj, sub, extrs, source
                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    set(3, SourceName);


                } else {
                    // proj , sub, source , type
                    set(2, SourceName);


                }
                break;


            default:
                if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
                    set(size() - 1, SourceName);


                } else {
                    // proj , sub, extras , source , type
                    set(size() - 2, SourceName);


                }
        }
    }

    public String getExtension() {
        String ext = "";


        if (size() > 2) {
            ext = (String) get(size() - 1);


        }
        return ext;


    }

    public String setExtension(String ext) {

        if (size() < 2) {
            add(ext);


        } else {
            set(size() - 1, ext);


        }
        return ext;


    }

    public String getFileName() {
        String rc = "";
        ////System.out.println("size: "+size());


        for (int i = 0; i
                < size(); i++) {
            if (rc.length() > 0) {
                rc += ".";


            }
            rc += (String) get(i);


        }
        return rc;


    }

    public String getFileNameWithNoExtension() {
        String rc = "";
        ////System.out.println("size: "+size());


        if (getType().equalsIgnoreCase(Constants.Type.TEXT)) {
            for (int i = 0; i
                    < size(); i++) {
                if (rc.length() > 0) {
                    rc += ".";


                }
                rc += (String) get(i);


            }
        } else { // has extention so stop one short to exclued ext
            for (int i = 0; i
                    < size() - 1; i++) {

                if (rc.length() > 0) {
                    rc += ".";


                }

                rc += (String) get(i);



            }

        }

        return rc;


    }

    public String getPathAndFileName() {
        return getPath() + getFileName();


    }

    public void assign(InFileName in) {
        setPath(in.getPath());


        for (int i = 0; i
                < in.size(); i++) {
            add((String) in.get(i));


        }
    }

    protected void parseConfiguration() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
        // open the file read the lines
        // parse using [name:value] pair 
        // [proxied:][name:group-by][day:group-by][hours: evaluate(end-time - start-time )] [hours:sum()]
        // [proxied:][element-name:pluck]
        // order doesnt matter
////System.out.println(" parseConfiguration 1i sMasterReference(): " + isMasterReference() + "  isSummary(): " + isSummary() );

        // skip all but
 //System.out.println("parseConfiguration 1");
        if (!isMasterReference() && !isSummary() && !isPluck()) {
            return;

        }
//System.out.println(" parseConfiguration 2");

        BufferedReader bfr = new BufferedReader(InputStreamReaderFactory.getInputStreamObject(this));
        String line = ""; // expect a single line of text

        // read lines
        String ln;


        while ((ln = bfr.readLine()) != null) {
            line += " " + ln;
        }
//System.out.println(" parseConfiguration 3");
        bfr.close();

        if (line.trim().length() == 0) {
            return;
        }

        int sz = line.length();


        for (int i = 0; i < sz; i++) {
            // parse the line

            String name = "";
            String value = "";


            for (; i < sz && line.charAt(i) != '['; i++); // skip blanks
            i++;

            for (; i < sz && line.charAt(i) != ':'; i++) {
                if (line.charAt(i) != ':') {
                    name += line.charAt(i);
                }
            }
            i++;


            for (; i < sz && line.charAt(i) != ']'; i++) {
                if (line.charAt(i) != ']') {
                    value += line.charAt(i);
                }
            }

            if (name.length() > 0 && value.length() > 0) {
                  //System.out.println("add configuration  name: " + name + " value: " + value);
                getConfiguration().addAttribute(new Attribute(name.trim(), value.trim()));
                ////System.out.println("  getConfiguration().size()" + getConfiguration().size() + "  getConfiguration()." + getConfiguration().getAttribute(getConfiguration().size() - 1).getValue());


            }

        }
//System.out.println(" parseConfiguration out");
    }
    /*
     * replaces a the file to open with the one in the proxy file
     */

    public void replaceProxy() throws FileNotFoundException, IOException, UnknownFileTypeException, TransformerException {
 //System.out.println("replaceProxy 1");
        // do only for files with source equal to proxy or the configuration contains the attribute proxy
        Attribute att = getConfiguration().getAttribute(Constants.Source.PROXY);
 //System.out.println("replaceProxy 2 att: "+att);
        if (att == null) {
            return;
        }

        //System.out.println("proxy values: " + att.getValue());
        // overide infiles values and effectively point it to actual file

        this.setPathAndFileName(att.getValue());

    }
}
