package opgods.opcampus.teachers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opgods.opcampus.R;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class TeacherMarkerManager {
    private GoogleMap map;
    private Map<String, Marker> markers;

    public TeacherMarkerManager(GoogleMap map) {
        this.map = map;
        this.markers = new HashMap<>();
    }

    public void loadMarkers(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            if (markers.containsKey(teacher.getDespacho())) {
                Marker marker = markers.get(teacher.getDespacho());
                if ((marker.getTitle().contains("Disponible: sí") && !teacher.estaDisponible())
                        || (marker.getTitle().contains("Disponible: no") && teacher.estaDisponible())) {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_whoknows));
                }
                String texto = teacher.getNombre() + "--" + teacher.getInfo() + "\nDisponible: ";
                if (teacher.estaDisponible()) {
                    texto += "sí";
                } else {
                    texto += "no";
                }
                marker.setSnippet(texto);
                markers.put(teacher.getDespacho(), marker);
            } else {
                BitmapDescriptor icon;
                if (teacher.estaDisponible()) {
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_able);
                } else {
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_disable);
                }
                String texto = teacher.getNombre() + "--" + teacher.getInfo() + "\nDisponible: ";
                if (teacher.estaDisponible()) {
                    texto += "sí";
                } else {
                    texto += "no";
                }
                markers.put(teacher.getDespacho(), map.addMarker(new MarkerOptions()
                        .position(teacher.getLocalizacion())
                        .title(texto)
                        .icon(icon)));
            }
        }

        showMarkersIfZoom();
        setMarkersVisibility();
    }

    /**
     * Muestra los marcadores si el nivel de zoom actual lo permite
     */
    private void showMarkersIfZoom() {
        if (map.getCameraPosition().zoom < Constants.ZOOM_MIN_PROFESORES) {
            for (Map.Entry<String, Marker> entry : markers.entrySet()) {
                entry.getValue().setVisible(false);
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
                for (Map.Entry<String, Marker> entry : markers.entrySet()) {
                    entry.getValue().setVisible(cameraPosition.zoom > Constants.ZOOM_MIN_PROFESORES);
                }
            }
        });
    }
}
