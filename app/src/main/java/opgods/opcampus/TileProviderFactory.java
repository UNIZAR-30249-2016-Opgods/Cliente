package opgods.opcampus;

import com.google.android.gms.maps.model.TileProvider;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class TileProviderFactory {
    private static final String GEOSERVER_FORMAT = "http://geoserver-naxsel.rhcloud.com/labis/wms" +
            "?service=WMS" +
            "&version=1.1.0" +
            "&request=GetMap" +
            "&layers=labis:prueba_ada" +
            "&styles=" +
            "&bbox=%f,%f,%f,%f" +
            "&width=768" +
            "&height=654" +
            "&srs=EPSG:3857" +
            "&format=image/png" +
            "&transparent=true";

    // return a geoserver wms tile layer
    public static TileProvider getTileProvider() {
        return new WMSTileProvider(768, 654) {

            @Override
            public synchronized URL getTileUrl(int x, int y, int zoom) {
                double[] bbox = getBoundingBox(x, y, zoom);
                String s = String.format(Locale.US, GEOSERVER_FORMAT, bbox[MINX],
                        bbox[MINY], bbox[MAXX], bbox[MAXY]);
                URL url = null;
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