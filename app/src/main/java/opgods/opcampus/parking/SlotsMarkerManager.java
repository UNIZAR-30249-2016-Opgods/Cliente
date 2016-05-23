package opgods.opcampus.parking;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.R;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class SlotsMarkerManager {
    private GoogleMap map;
    private Context context;
    private List<Marker> markers;

    public SlotsMarkerManager(GoogleMap map, Context context) {
        this.map = map;
        this.context = context;
        this.markers = new ArrayList<>();
    }

    public void loadMarkers(List<Slot> slots) {
        for (Slot slot : slots) {
            markers.add(map.addMarker(new MarkerOptions()
                    .position(slot.getLocalizacion())
                    .title(slot.getLibres() + "/" + slot.getnPlazas())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_slots))));
        }
        showMarkersIfZoom();
        setMarkersVisibility();
        setMarkerClicked();
    }

    /**
     * Muestra los marcadores si el nivel de zoom actual lo permite
     */
    private void showMarkersIfZoom() {
        if (map.getCameraPosition().zoom < Constants.ZOOM_MIN_PARKING) {
            for (Marker marker : markers) {
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
                for (Marker marker : markers) {
                    marker.setVisible(cameraPosition.zoom > Constants.ZOOM_MIN_PARKING);
                }
            }
        });
    }

    public boolean setMarkerClicked() {
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                RoutesCalculator routesCalculator = new RoutesCalculator(context, map);
                LatLng from = new LatLng(41.688768, -0.875018);
                LatLng to = marker.getPosition();
                routesCalculator.paintRoute(from, to);
                marker.hideInfoWindow();
            }
        });
        return false;
    }
}
