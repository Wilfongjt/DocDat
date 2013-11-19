/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package docdat.io.inputstreams;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 *
 * @author wilfongj
 */
public class ChartByteArrayInputStream extends ByteArrayInputStream {

    public ChartByteArrayInputStream(byte[] bytearray) {
        super( bytearray );
    }
/*
    public synchronized int read() throws IOException {

        return read();
    }
*/

    private String ChartType ="";

    public String getChartType() {
        return ChartType;
    }

    public void setChartType(String ChartType) {
        this.ChartType = ChartType;
    }

    

}
