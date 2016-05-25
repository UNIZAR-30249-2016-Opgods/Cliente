package opgods.opcampus.teachers;

import android.support.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import opgods.opcampus.MainActivity;
import opgods.opcampus.R;
import opgods.opcampus.ViewManager;
import opgods.opcampus.util.AsyncTaskCompleteListener;
import opgods.opcampus.util.Constants;

/**
 * Clase encargada de mostrar los marcadores de los profesores
 *
 * Singleton
 */
public class TeacherMarkerManager implements AsyncTaskCompleteListener<String> {
    private static TeacherMarkerManager instance = null;
    private MainActivity activity;
    private GoogleMap map;
    private Map<String, Marker> markers;
    private Teacher teacherSearch;

    public static TeacherMarkerManager getInstance(MainActivity activity) {
        if (instance == null) {
            instance = new TeacherMarkerManager(activity, activity.getMap());
        }

        return instance;
    }

    private TeacherMarkerManager(MainActivity activity, GoogleMap map) {
        this.activity = activity;
        this.map = map;
        this.markers = new HashMap<>();
        this.map.setInfoWindowAdapter(new TeacherInfoWindow(activity.getApplicationContext()));
    }


    /**
     * Muestra los profesores en el mapa
     *
     * @param teachers profesores a mostrar
     */
    private void loadMarkers(List<Teacher> teachers) {
        markers.clear();
        for (Teacher teacher : teachers) {
            if (markers.containsKey(teacher.getDespacho())) {
                Marker marker = markers.get(teacher.getDespacho());
                if ((marker.getTitle().contains("Disponible: sí") && !teacher.estaDisponible())
                        || (marker.getTitle().contains("Disponible: no") && teacher.estaDisponible())) {
                    marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_whoknows));
                }
                String texto = teacher.getNombre() + "--" + teacher.getInfo() + "\nDisponible: ";
                texto = getDisponible(teacher, texto);
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
                texto = getDisponible(teacher, texto);
                markers.put(teacher.getDespacho(), map.addMarker(new MarkerOptions()
                        .position(teacher.getLocalizacion())
                        .title(texto)
                        .icon(icon)));
            }
        }

        showMarkersIfZoom();
        setMarkersVisibility();
    }

    @NonNull
    private String getDisponible(Teacher teacher, String texto) {
        if (teacher.estaDisponible()) {
            texto += "sí";
        } else {
            texto += "no";
        }
        return texto;
    }


    /**
     * Muestra un profesor en el mapa y mueve la camara a su posición
     *
     * @param teacher
     */
    public void loadMarker(Teacher teacher) {
        teacherSearch = teacher;
        final Marker marker = markers.get(teacher.getDespacho());
        if (marker == null) {
            ViewManager manager = activity.getViewManager();
            manager.setFloor("Planta " + teacher.getPlanta(), Constants.PLANTA + teacher.getPlanta(), Constants.PROFESORES + teacher.getPlanta());
        } else {
            CameraPosition to =  new CameraPosition.Builder().target(marker.getPosition()).zoom(20f).bearing(0).tilt(0).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(to), new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {
                    marker.setVisible(true);
                    marker.showInfoWindow();
                    teacherSearch = null;
                }

                @Override
                public void onCancel() {}
            });
        }
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

    @Override
    public void onTaskComplete(String result) {
        JsonParserTeacher parser = new JsonParserTeacher();
        List<Teacher> teachers = parser.getDataFromJson(result);
        loadMarkers(teachers);
        if (teacherSearch != null) {
            loadMarker(teacherSearch);
        }
    }
}