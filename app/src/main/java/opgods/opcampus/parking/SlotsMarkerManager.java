package opgods.opcampus.parking;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

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

import opgods.opcampus.R;
import opgods.opcampus.util.AsyncTaskCompleteListener;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class SlotsMarkerManager implements GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener, AsyncTaskCompleteListener<String> {
    private static SlotsMarkerManager instance = null;
    private GoogleMap map;
    private Activity activity;
    private Context context;
    private List<Marker> slots;
    private List<Marker> access;
    private Polyline route;
    private Marker lastClicked;

    public static SlotsMarkerManager getInstance(GoogleMap map, Activity activity) {
        if (instance == null) {
            instance = new SlotsMarkerManager(map, activity);
        }

        return instance;
    }

    private SlotsMarkerManager(GoogleMap map, Activity activity) {
        this.map = map;
        this.context = activity.getApplicationContext();
        this.activity = activity;
        this.slots = new ArrayList<>();
        this.access = new ArrayList<>();
        this.map.setOnInfoWindowClickListener(this);
        this.map.setOnMarkerClickListener(this);
    }

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

    public void setRoute(ArrayList<LatLng> route) {
        PolylineOptions polylineOptions = DirectionConverter.createPolyline(context,
                route, 5, ContextCompat.getColor(context, R.color.routes));
        this.route = map.addPolyline(polylineOptions);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if (slots.contains(marker)) {
            lastClicked = marker;
            new GetAccessAdapter(this).execute();
            View parentLayout = activity.findViewById(R.id.drawer_layout);
            if (parentLayout != null) {
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
        if (access.contains(marker)) {
            for (Marker a : access) {
                if (!a.equals(marker)) {
                    a.remove();
                }
            }
            RoutesCalculator routesCalculator = new RoutesCalculator(SlotsMarkerManager.this);
            LatLng from = marker.getPosition();
            LatLng to = lastClicked.getPosition();
            routesCalculator.paintRoute(from, to, context.getString(R.string.google_maps_server_key));
            marker.hideInfoWindow();

            return true;
        }
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