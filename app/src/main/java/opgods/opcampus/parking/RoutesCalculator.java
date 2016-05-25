package opgods.opcampus.parking;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.model.LatLng;

/**
 * Clase que calcula las rutas mediante Google Maps API
 */
public class RoutesCalculator {
    private SlotsMarkerManager manager;

    public RoutesCalculator(SlotsMarkerManager manager) {
        this.manager = manager;
    }


    /**
     * Calcula una ruta
     *
     * @param from lugar de origen
     * @param to lugar de destino
     * @param key clave de servidor para realizar la petición
     */
    public void paintRoute(LatLng from, LatLng to, String key) {
        GoogleDirection.withServerKey(key)
                .from(from)
                .to(to)
                .transitMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            manager.setRoute(leg.getDirectionPoint());
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
    }
}
