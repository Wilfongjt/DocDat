/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package docdat.io.readers;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 *
 * @author wilfongj
 */
public class ODTContentInputStreamReader extends InputStreamReaderBridge {

    public ODTContentInputStreamReader(String odtUrl) throws ZipException, IOException {
        super( new ZipFile(new File(odtUrl)) );
        //super( (new ZipFile(new File(odtUrl))).getInputStream( new ZipEntry(Constants.XML.ODTContent)) );
    }


}
