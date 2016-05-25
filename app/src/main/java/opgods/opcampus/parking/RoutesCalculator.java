package opgods.opcampus.parking;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.google.android.gms.maps.model.LatLng;

/**
 * Clase que calcula las rutas mediante Google Maps API
 */
public class RoutesCalculator {
    private String key;

    public RoutesCalculator(String key) {
        this.key = key;
    }

    /**
     * Calcula una ruta
     *
     * @param from lugar de origen
     * @param to lugar de destino
     * @param callback función de callback
     */
    public void paintRoute(LatLng from, LatLng to, DirectionCallback callback) {
        GoogleDirection.withServerKey(key)
                .from(from)
                .to(to)
                .transitMode(TransportMode.DRIVING)
                .execute(callback);
    }
}
