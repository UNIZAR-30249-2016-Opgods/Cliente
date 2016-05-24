package opgods.opcampus;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import opgods.opcampus.parking.Slot;

import static junit.framework.Assert.*;

/**
 * Created by URZU on 24/05/2016.
 */
public class SlotTest {
    private static final int id = 6;
    private static final LatLng localizacion = new LatLng(0.3, 7.0);
    private static final int nPlazas = 90;
    private static final int libres = 56;

    @Test
    public void newSlot() {
        Slot slot = new Slot(id,localizacion, nPlazas, libres);
        assertEquals(slot.getId(), id);
        assertEquals(slot.getLocalizacion(), localizacion);
        assertEquals(slot.getnPlazas(), nPlazas);
        assertEquals(slot.getLibres(), libres);
    }
}
