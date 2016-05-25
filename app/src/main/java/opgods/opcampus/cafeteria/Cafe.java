package opgods.opcampus.cafeteria;

import com.google.android.gms.maps.model.LatLng;

/**
 * Clase que representa una cafetería
 */
public class Cafe {
    private int id;
    private LatLng localizacion;
    private int nPlazas;
    private int libres;

    public Cafe(int id, LatLng localizacion, int nPlazas, int libres) {
        this.id = id;
        this.libres = libres;
        this.localizacion = localizacion;
        this.nPlazas = nPlazas;
    }

    public int getId() {
        return id;
    }

    public int getLibres() {
        return libres;
    }

    public LatLng getLocalizacion() {
        return localizacion;
    }

    public int getnPlazas() {
        return nPlazas;
    }
}
