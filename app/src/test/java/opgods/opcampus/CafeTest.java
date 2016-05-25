package opgods.opcampus;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import opgods.opcampus.cafeteria.Cafe;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 25/05/2016.
 */
public class CafeTest {
    private static final int id = 6;
    private static final LatLng localizacion = new LatLng(0.3, 7.0);
    private static final int nPlazas = 90;
    private static final int libres = 56;

    @Test
    public void newCafe() {
        Cafe cafe = new Cafe(id,localizacion, nPlazas, libres);
        assertEquals(cafe.getId(), id);
        assertEquals(cafe.getLocalizacion(), localizacion);
        assertEquals(cafe.getnPlazas(), nPlazas);
        assertEquals(cafe.getLibres(), libres);
    }
}
