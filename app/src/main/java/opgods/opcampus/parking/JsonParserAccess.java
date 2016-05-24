package opgods.opcampus.parking;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.JsonParser;

/**
 * Parser de los puntos de acceso del parking
 */
public class JsonParserAccess extends JsonParser {
    /**
     * Obtiene los puntos de acceso
     *
     * @param json con la información
     * @return lista con los puntos de acceso
     */
    public List<LatLng> getDataFromJson(String json) {
        List<LatLng> access = new ArrayList<>();
        try {
            JSONObject o = new JSONObject(json);
            JSONArray jsonArray = o.getJSONArray("datos");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonOb = new JSONObject(jsonArray.get(i).toString());
                double latitud = jsonOb.getDouble("latitud");
                double longitud = jsonOb.getDouble("longitud");
                access.add(new LatLng(latitud, longitud));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return access;
    }
}
