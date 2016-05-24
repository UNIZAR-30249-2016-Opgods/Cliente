package opgods.opcampus.maps;

import com.google.android.gms.maps.model.UrlTileProvider;


/**
 * Clase que calcula el área de la tesela en función de Web Mercator
 */
public abstract class WMSTileProvider extends UrlTileProvider {
    // Web Mercator n/w corner of the map.
    private static final double[] TILE_ORIGIN = {-20037508.34789244, 20037508.34789244};

    private static final int ORIG_X = 0;
    private static final int ORIG_Y = 1;

    // Size of square world map in meters, using WebMerc projection.
    private static final double MAP_SIZE = 20037508.34789244 * 2;

    protected static final int MINX = 0;
    protected static final int MAXX = 1;
    protected static final int MINY = 2;
    protected static final int MAXY = 3;

    // Construct with tile size in pixels, normally 256
    public WMSTileProvider(int x, int y) {
        super(x, y);
    }

    /**
     * Calcula el área de la tesela
     *
     * @param x coordenada x
     * @param y coordenada y
     * @param zoom nivel de zoom
     *
     * @return cuatro esquinas de la tesela
     */
    protected double[] getBoundingBox(int x, int y, int zoom) {
        double tileSize = MAP_SIZE / Math.pow(2, zoom);
        double minx = TILE_ORIGIN[ORIG_X] + x * tileSize;
        double maxx = TILE_ORIGIN[ORIG_X] + (x+1) * tileSize;
        double miny = TILE_ORIGIN[ORIG_Y] - (y+1) * tileSize;
        double maxy = TILE_ORIGIN[ORIG_Y] - y * tileSize;

        double[] bbox = new double[4];
        bbox[MINX] = minx;
        bbox[MINY] = miny;
        bbox[MAXX] = maxx;
        bbox[MAXY] = maxy;

        return bbox;
    }
}