package opgods.opcampus.teachers;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by URZU on 22/05/2016.
 */
public class Teacher {
    private int id;
    private String nombre;
    private boolean disponibilidad;
    private String info;
    private LatLng localización;
    private String despacho;

    public Teacher(int id, String info, LatLng localización,
                   String nombre, String despacho, boolean disponibilidad) {
        this.despacho = despacho;
        this.disponibilidad = disponibilidad;
        this.id = id;
        this.info = info;
        this.localización = localización;
        this.nombre = nombre;
    }

    public String getDespacho() {
        return despacho;
    }

    public boolean estaDisponible() {
        return disponibilidad;
    }

    public int getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public LatLng getLocalización() {
        return localización;
    }

    public String getNombre() {
        return nombre;
    }
}
