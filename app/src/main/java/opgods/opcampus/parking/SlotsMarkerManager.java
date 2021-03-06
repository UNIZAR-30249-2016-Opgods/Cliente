package opgods.opcampus.parking;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.MainActivity;
import opgods.opcampus.R;
import opgods.opcampus.util.AsyncTaskCompleteListener;
import opgods.opcampus.util.Constants;
import opgods.opcampus.util.GetAdapter;

/**
 * Clase encargada de mostrar los marcadores del parking
 */
public class SlotsMarkerManager implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener,
        AsyncTaskCompleteListener<String> {
    private GoogleMap map;
    private MainActivity mainActivity;
    private Context context;
    private List<Marker> slots;
    private List<Marker> access;
    private Polyline route;
    private Marker lastClicked;
    private DirectionCallback callback;

    public SlotsMarkerManager(MainActivity mainActivity) {
        this.map = mainActivity.getMap();
        this.context = mainActivity.getApplicationContext();
        this.mainActivity = mainActivity;
        this.slots = new ArrayList<>();
        this.access = new ArrayList<>();
        this.map.setOnInfoWindowClickListener(this);
        this.map.setOnMarkerClickListener(this);
        setCallback();
    }

    private void setCallback() {
        this.callback = new DirectionCallback() {
            @Override
            public void onDirectionSuccess(Direction direction, String rawBody) {
                if (direction.isOK()) {
                    Route route = direction.getRouteList().get(0);
                    Leg leg = route.getLegList().get(0);
                    setRoute(leg.getDirectionPoint());
                }
            }

            @Override
            public void onDirectionFailure(Throwable t) {

            }
        };
    }


    /**
     * Muestra en el mapa todas las secciones de parking
     *
     * @param slots con las secciones de parking
     */
    private void loadSlotsMarkers(List<Slot> slots) {
        this.map.setInfoWindowAdapter(new SlotInfoWindow(this.context));
        this.slots.clear();
        for (Slot slot : slots) {
            this.slots.add(map.addMarker(new MarkerOptions()
                    .position(slot.getLocalizacion())
                    .title(slot.getLibres() + "/" + slot.getnPlazas())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_slots))));
        }
        showMarkersIfZoom();
        setMarkersVisibility();
    }


    /**
     * Muesra en el mapa todos los puntos de acceso
     *
     * @param access con los puntos de acceso
     */
    public void loadAccessMarkers(List<LatLng> access) {
        for (LatLng a : access) {
            this.access.add(map.addMarker(new MarkerOptions().position(a)));
        }
    }


    /**
     * Muestra los marcadores si el nivel de zoom actual lo permite
     */
    private void showMarkersIfZoom() {
        if (map.getCameraPosition().zoom < Constants.ZOOM_MIN_PARKING) {
            for (Marker marker : slots) {
                marker.setVisible(false);
            }
        }
    }


    /**
     * Oculta los marcadores de los profesores en tiempo de ejecución según el nivel de zoom
     */
    private void setMarkersVisibility() {
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                for (Marker marker : slots) {
                    marker.setVisible(cameraPosition.zoom > Constants.ZOOM_MIN_PARKING);
                }
            }
        });
    }


    /**
     * Muestra una ruta en el mapa
     *
     * @param route ruta a mostrat
     */
    public void setRoute(ArrayList<LatLng> route) {
        PolylineOptions polylineOptions = DirectionConverter.createPolyline(context,
                route, 5, ContextCompat.getColor(context, R.color.routes));
        this.route = map.addPolyline(polylineOptions);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        // comprueba que haya pulsado en la ventana de información de una plaza
        if (slots.contains(marker)) {
            lastClicked = marker;
            new GetAdapter(this).execute(Constants.ACCESOS);
            View parentLayout = mainActivity.findViewById(R.id.drawer_layout);
            if (parentLayout != null) {
                // borra una ruta si existe
                if (route != null) {
                    route.remove();
                }
                Snackbar.make(parentLayout, "Escoge tu entrada", Snackbar.LENGTH_LONG).show();
                CameraPosition to =  new CameraPosition.Builder().target(new LatLng(41.683662, -0.886500))
                        .zoom(15.6f)
                        .bearing(0)
                        .tilt(0)
                        .build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(to));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // comprueba que haya pulsado en un punto de acceso
        if (access.contains(marker)) {
            for (Marker a : access) {
                // borra todos los puntos de acceso menos el pulsado
                if (!a.equals(marker)) {
                    a.remove();
                }
            }
            // calcula la ruta
            LatLng from = marker.getPosition();
            LatLng to = lastClicked.getPosition();
            RoutesCalculator routesCalculator = new RoutesCalculator(context.getString(R.string.google_maps_server_key));
            routesCalculator.paintRoute(from, to, callback);
            marker.hideInfoWindow();

            return true;
        }
        // sino comportamiento por defecto
        return false;
    }

    @Override
    public void onTaskComplete(String result) {
        JsonParserSlots parserSlots = new JsonParserSlots();
        List<Slot> slots = parserSlots.getDataFromJson(result);
        if (!slots.isEmpty()) {
            loadSlotsMarkers(slots);
        } else {
            JsonParserAccess parserAccess = new JsonParserAccess();
            List<LatLng> access = parserAccess.getDataFromJson(result);
            loadAccessMarkers(access);
        }
    }
}