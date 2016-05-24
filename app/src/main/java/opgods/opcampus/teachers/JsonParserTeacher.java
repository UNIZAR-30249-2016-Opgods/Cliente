package opgods.opcampus.teachers;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.JsonParser;

/**
 * Parser de profesores
 */
public class JsonParserTeacher extends JsonParser {
    /**
     * Obtiene los profesores
     *
     * @param json con la información
     * @return lista con los profesores
     */
    public List<Teacher> getDataFromJson(String json) {
        List<Teacher> teachers = new ArrayList<>();
        try {
            JSONObject o = new JSONObject(json);
            JSONArray jsonArray = o.getJSONArray("datos");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonOb = new JSONObject(jsonArray.get(i).toString());
                int id = jsonOb.getInt("id");
                String info = jsonOb.getString("info");
                double latitud = jsonOb.getJSONObject("despacho").getJSONObject("localizacion").getJSONObject("punto").getDouble("latitud");
                double longitud = jsonOb.getJSONObject("despacho").getJSONObject("localizacion").getJSONObject("punto").getDouble("longitud");
                int planta = jsonOb.getJSONObject("despacho").getJSONObject("localizacion").getInt("utcPlanta");
                LatLng localizacion = new LatLng(latitud, longitud);
                String nombre = jsonOb.getString("nombre");
                String despacho = jsonOb.getJSONObject("despacho").getString("codigo");
                boolean disponibilidad = jsonOb.getBoolean("disponibilidad");
                teachers.add(new Teacher(id, info, localizacion, nombre, despacho, disponibilidad, planta));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teachers;
    }
}
