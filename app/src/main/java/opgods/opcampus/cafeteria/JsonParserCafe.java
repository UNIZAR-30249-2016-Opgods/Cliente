package opgods.opcampus.cafeteria;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.JsonParser;

/**
 * Parser de las cafeterías
 */
public class JsonParserCafe extends JsonParser {
    /**
     * Obtiene las cafeterías
     *
     * @param json con las cafeterías
     * @return lista con las cafeterías
     */
    public List<Cafe> getDataFromJson(String json) {
        List<Cafe> cafes = new ArrayList<>();
        try {
            JSONObject o = new JSONObject(json);
            JSONArray jsonArray = o.getJSONArray("datos");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonOb = new JSONObject(jsonArray.get(i).toString());
                int id = jsonOb.getInt("id");
                double latitud = jsonOb.getJSONObject("punto").getDouble("latitud");
                double longitud = jsonOb.getJSONObject("punto").getDouble("longitud");
                LatLng localizacion = new LatLng(latitud, longitud);
                int nPlazas = jsonOb.getJSONObject("ocupacion").getInt("nPlazas");
                int libres = jsonOb.getJSONObject("ocupacion").getInt("libres");
                cafes.add(new Cafe(id, localizacion, nPlazas, libres));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cafes;
    }
}
