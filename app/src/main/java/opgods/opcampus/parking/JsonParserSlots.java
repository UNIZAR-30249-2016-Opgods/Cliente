package opgods.opcampus.parking;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.JsonParser;

/**
 * Created by URZU on 22/05/2016.
 */
public class JsonParserSlots extends JsonParser {
    public List<Slot> getDataFromJson(String json) {
        List<Slot> slots = new ArrayList<>();
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
                slots.add(new Slot(id, localizacion, nPlazas, libres));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return slots;
    }
}
