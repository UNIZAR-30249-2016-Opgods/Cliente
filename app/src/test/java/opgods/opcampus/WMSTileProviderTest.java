package opgods.opcampus;

import org.junit.Test;

import java.net.URL;

import opgods.opcampus.maps.WMSTileProvider;

import static junit.framework.Assert.assertEquals;
/**
 * Created by URZU on 24/05/2016.
 */
public class WMSTileProviderTest {
    @Test
    public void getBoundingBoxTest() {
        new WMSTileProvider(256, 256) {
            @Override
            public URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(32606, 24405, 16);
                assertEquals(-99062.38868281618, bbox[0]);
                assertEquals(-98450.89245638251, bbox[1]);
                assertEquals(5113331.4454674255, bbox[2]);
                assertEquals(5113942.941693863, bbox[3]);
                return null;
            }
        };
    }
}
