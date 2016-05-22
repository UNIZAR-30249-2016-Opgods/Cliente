package opgods.opcampus.parking;

/**
 * Created by URZU on 22/05/2016.
 */
public class Slot {
    int id;
    int nPlazas;

    public Slot(int id, int nPlazas) {
        this.id = id;
        this.nPlazas = nPlazas;
    }

    public int getId() {
        return id;
    }

    public int getnPlazas() {
        return nPlazas;
    }
}
