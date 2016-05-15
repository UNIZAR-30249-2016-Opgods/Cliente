package opgods.opcampus;

import android.util.Log;

import com.google.android.gms.maps.model.TileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class TileProviderFactory {
    private static String GEOSEVER_FORMAT = "http://geoserver-naxsel.rhcloud.com/labis/wms" +
            "?service=WMS" +
            "&version=1.1.0" +
            "&request=GetMap" +
            "&layers=labis:%s" +
            "&styles=" +
            "&bbox=%f,%f,%f,%f" +
            "&width=512" +
            "&height=512" +
            "&srs=EPSG:900913" +
            "&format=image/png" +
            "&transparent=true";

    // return a geoserver wms tile layer
    public static TileProvider getTileProvider(final String capa) {
        return new WMSTileProvider(256, 256) {
            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, GEOSEVER_FORMAT, capa, bbox[MINX],
                        bbox[MINY], bbox[MAXX], bbox[MAXY]);
                Log.v("URL WMS", s);
                URL url;
                try {
                    url = new URL(s);
                } catch (MalformedURLException e) {
                    throw new AssertionError(e);
                }
                return url;
            }
        };
    }
}