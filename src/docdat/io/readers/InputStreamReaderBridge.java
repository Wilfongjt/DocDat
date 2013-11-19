/*
 * To change this template, choose Tools | Templates
 * and open the template inzip the editor.
 */

package docdat.io.readers;

import docdat.utils.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;



/**
 *
 * @author wilfongj
 */
public class InputStreamReaderBridge extends InputStreamReader {
   //private Reader inzip = null;
   private ZipFile inzip = null;
   private InputStream in = null;

    public InputStreamReaderBridge(ZipFile zf) throws ZipException, IOException {
        super( zf.getInputStream( new ZipEntry(Constants.XML.ODTContent)) );
        inzip = zf;
    }

    public InputStreamReaderBridge(InputStream instream ) throws  IOException {
        super(instream );
        in = instream;
    }

    public void close() throws IOException {
        if(inzip != null){
            inzip.close();
        }
        if(in != null){
            in.close();
        }

        super.close();

    }

}
