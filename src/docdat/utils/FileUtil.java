/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author wilfongj
 */
public class FileUtil {

    public FileUtil() {
    }

    private InputStream getXSL(String resourcename) {
        //"dev_requirements_short.xsl"
        return getClass().getResourceAsStream(resourcename);
    }

    public String getHomeFolder() {
        String APP_ROOT = System.getProperty("user.dir");
        String home = "";

        home = APP_ROOT.replace("batch", "");

        URL myurl = this.getClass().getResource("/DocDat/transforms/xsl/trans.odt.text.xsl");
        //System.out.println(" url " + myurl);


        return home;
    }

    /*
     * jarUrl is 
     * 
     */
    public InputStream getInputStreamFromJAR(String jarUrl, String filename) throws IOException {
        //System.out.println("getInputStreamFromJAR 1 jarUrl:" + jarUrl);

        JarFile jarFile = new JarFile(new File(jarUrl));
        //System.out.println("getInputStreamFromJAR 3 entry:" + odtFile);

        ////////////////////////////////
        //System.out.println("getInputStreamFromJAR 2 filename: " + filename);

        JarEntry entry = jarFile.getJarEntry(filename);
        //System.out.println("getInputStreamFromJAR 4 entry:" + entry);
        InputStream inputStream = jarFile.getInputStream(entry);
        //System.out.println("getInputStreamFromJAR 5");

        return inputStream;
    }
    /*
     * input stream from ODT
     */

    public InputStream getInputStreamFromODT(String odtUrl) throws IOException {
        //System.out.println("getInputStreamFromJAR 1 odtUrl:" + odtUrl);
        String filename = "context.xml";
        ZipFile odtFile = new ZipFile(new File(odtUrl));
        InputStream inputStream = null;
        //System.out.println("getInputStreamFromJAR 3 entry:" + odtFile);
        try {

            /*ZipEntry zipEntry = new ZipEntry(Constants.XML.ODTContent) ;

             inputStream = odtFile.getInputStream(zipEntry);
             */


            inputStream = odtFile.getInputStream(new ZipEntry(Constants.XML.ODTContent));

        } catch (Exception e) {
            odtFile.close();
        }
        ////////////////////////////////
        //System.out.println("getInputStreamFromJAR 2 filename: " + filename);

        //JarEntry entry = odtFile.getJarEntry(filename);
        //System.out.println("getInputStreamFromJAR 4 entry:" + entry);
        // InputStream inputStream = odtFile.getInputStream(entry);
        //System.out.println("getInputStreamFromJ
        return inputStream;
    }
    /*
     * ODT to Text
     *
     */

    public void convertODTtoText(String fullPath) {
        // ODT are jars, and jars are zips

        int lastPeriod = fullPath.lastIndexOf('.');
        String textFileName = fullPath.substring(0, lastPeriod);
        //System.out.println("convert word: " + fullPath);
        //System.out.println("to text: " + textFileName);

        //finish writing odt import

        // open odt file
        // transform context.xml with odttotext.xsl

    }
}
