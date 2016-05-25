package opgods.opcampus.cafeteria;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.R;
import opgods.opcampus.util.AsyncTaskCompleteListener;
import opgods.opcampus.util.Constants;

/**
 * Clase encargada de mostrar los marcadores del parking
 *
 * Singleton
 */
public class CafeMarkerManager implements AsyncTaskCompleteListener<String> {
    private static CafeMarkerManager instance = null;
    private GoogleMap map;
    private List<Marker> cafes;

    public static CafeMarkerManager getInstance(GoogleMap map) {
        if (instance == null) {
            instance = new CafeMarkerManager(map);
        }

        return instance;
    }

    private CafeMarkerManager(GoogleMap map) {
        this.map = map;
        this.cafes = new ArrayList<>();
    }

    private void setCafes(List<Cafe> cafes) {
        this.map.setInfoWindowAdapter(null);
        this.cafes.clear();
        for (Cafe cafe : cafes) {
            this.cafes.add(map.addMarker(new MarkerOptions()
                    .position(cafe.getLocalizacion())
                    .title("Cafetería")
                    .snippet(cafe.getLibres() + "/" + cafe.getnPlazas())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cafe))));
        }
        showMarkersIfZoom();
        setMarkersVisibility();
    }

    /**
     * Muestra los marcadores si el nivel de zoom actual lo permite
     */
    private void showMarkersIfZoom() {
        if (map.getCameraPosition().zoom < Constants.ZOOM_MIN_CAFE) {
            for (Marker marker : cafes) {
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
                for (Marker marker : cafes) {
                    marker.setVisible(cameraPosition.zoom > Constants.ZOOM_MIN_CAFE);
                }
            }
        });
    }

    @Override
    public void onTaskComplete(String result) {
        JsonParserCafe parser = new JsonParserCafe();
        List<Cafe> cafes = parser.getDataFromJson(result);
        setCafes(cafes);
    }
}
