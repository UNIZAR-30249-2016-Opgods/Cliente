package opgods.opcampus.parking;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import opgods.opcampus.R;

/**
 * Created by URZU on 21/05/2016.
 */
public class RoutesCalculator {
    private GoogleMap map;
    private Context context;
    private SlotsMarkerManager manager;

    public RoutesCalculator(Context contex, GoogleMap map, SlotsMarkerManager manager) {
        this.map = map;
        this.context = contex;
        this.manager = manager;
    }

    public void paintRoute(LatLng from, LatLng to) {
        GoogleDirection.withServerKey(context.getString(R.string.google_maps_server_key))
                .from(from)
                .to(to)
                .transitMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(context,
                                    directionPositionList, 5, ContextCompat.getColor(context, R.color.routes));
                            manager.setRoute(map.addPolyline(polylineOptions));
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                    }
                });
    }
}
