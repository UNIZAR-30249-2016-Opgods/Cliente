package opgods.opcampus;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class TeacherMarkerManager {
    private GoogleMap map;
    private String planta;
    private List<Marker> markers;

    public TeacherMarkerManager(GoogleMap map, String planta) {
        this.map = map;
        this.planta = planta;
        this.markers = new ArrayList<>();
        loadMarkers();
    }

    // TODO load teachers from server
    private void loadMarkers() {
        markers.add(map.addMarker(new MarkerOptions()
                .position(new LatLng(41.683904, -0.889389))
                .title("Rubén Béjar")
                .snippet("Email: emailr@unizar.es\nDespacho: 2.21")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_able))));
        markers.add(map.addMarker(new MarkerOptions()
                .position(new LatLng(41.683904, -0.889353))
                .title("Zarazaga")
                .snippet("Email: email@unizar.es\nDespacho: 2.20")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_disable))));
        markers.add(map.addMarker(new MarkerOptions()
                .position(new LatLng(41.683904, -0.889317))
                .title("JEJE")
                .snippet("Email: email@unizar.es\nDespacho: 2.19")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_whoknows))));

        showMarkersIfZoom();
        setMarkersVisibility();
    }

    /**
     * Oculta los marcadores de los profesores en tiempo de ejecución según el nivel de zoom
     */
    private void setMarkersVisibility() {
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                for (Marker marker : markers) {
                    marker.setVisible(cameraPosition.zoom > Constants.ZOOM_MIN_PROFESORES);
                }
            }
        });
    }

    /**
     * Muestra los marcadores si el nivel de zoom actual lo permite
     */
    private void showMarkersIfZoom() {
        if (map.getCameraPosition().zoom < Constants.ZOOM_MIN_PROFESORES) {
            for (Marker marker : markers) {
                marker.setVisible(false);
            }
        }
    }
}
