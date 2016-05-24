package opgods.opcampus.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * JSON parser
 * @param <T>
 */
public abstract class JsonParser<T> {
    /**
     * Consigue las respuesta de un inputStream
     *
     * @param inputStream de la respuesta
     * @return respuesta
     */
    public String getResponse(InputStream inputStream) {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder response = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Consigue el código de error de un JSON
     *
     * @param json a parsear
     * @return código de error
     */
    public int getErrorFromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            return jsonObject.getInt(Constants.ERROR);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return Constants.UNKOWN_ERROR;
    }

    /**
     * Método para conseguir la información de un JSON
     *
     * @param json con la información
     * @return lista con los objetos de la respuesta
     */
    public abstract List<T> getDataFromJson(String json);
}
