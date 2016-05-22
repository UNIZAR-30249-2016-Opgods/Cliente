package opgods.opcampus.teachers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.R;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class TeacherMarkerManager {
    private GoogleMap map;
    private List<Marker> markers;

    public TeacherMarkerManager(GoogleMap map) {
        this.map = map;
        this.markers = new ArrayList<>();
    }

    public void loadMarkers(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            markers.add(map.addMarker(new MarkerOptions()
                    .position(teacher.getLocalización())
                    .title(teacher.getNombre())
                    .snippet(teacher.getInfo())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_able))));
        }

        showMarkersIfZoom();
        setMarkersVisibility();
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
}
