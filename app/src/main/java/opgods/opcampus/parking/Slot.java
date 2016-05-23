package opgods.opcampus.parking;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by URZU on 22/05/2016.
 */
public class Slot {
    private int id;
    private LatLng localizacion;
    private int nPlazas;
    private int libres;

    public Slot(int id, LatLng localizacion, int nPlazas, int libres) {
        this.id = id;
        this.localizacion = localizacion;
        this.nPlazas = nPlazas;
        this.libres = libres;
    }

    public int getId() {
        return id;
    }

    public int getnPlazas() {
        return nPlazas;
    }

    public LatLng getLocalizacion() {
        return localizacion;
    }

    public int getLibres() {
        return libres;
    }
}
