package opgods.opcampus;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import org.junit.Test;

import opgods.opcampus.maps.TileProviderFactory;

import static junit.framework.Assert.*;

/**
 * Created by URZU on 24/05/2016.
 */
public class TileProviderFactoryTest {
    @Test
    public void getTiteProvider() {
        TileProvider tileProvider = TileProviderFactory.getTileProvider("foo", "bar");
        Tile tile = tileProvider.getTile(32606, 24405, 16);
        assertEquals(256, tile.height);
        assertEquals(256, tile.width);
    }
}
