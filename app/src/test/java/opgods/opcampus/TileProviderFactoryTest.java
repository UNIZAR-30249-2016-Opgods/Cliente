package opgods.opcampus;

import com.google.android.gms.maps.model.TileProvider;

import org.junit.Test;

import opgods.opcampus.maps.TileProviderFactory;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 24/05/2016.
 */
public class TileProviderFactoryTest {
    @Test
    public void getTileProviderTest() {
        TileProvider tileProvider = TileProviderFactory.getTileProvider(Constants.PLANTA_0);
    }
}
