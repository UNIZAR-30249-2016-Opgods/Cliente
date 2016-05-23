package opgods.opcampus.teachers;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 22/05/2016.
 */
public class JsonParserTeacher {
    public StringBuilder getResponse(InputStream inputStream) {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Consigue el código de error de un JSON
     *
     * @param json a parsear
     * @return código de error
     */
    private int getErrorFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            return jsonObject.getInt(Constants.ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Constants.UNKOWN_ERROR;
    }

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
