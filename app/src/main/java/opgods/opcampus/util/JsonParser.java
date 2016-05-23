package opgods.opcampus.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by URZU on 23/05/2016.
 */
public abstract class JsonParser {
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

    public abstract List<?> getDataFromJson(String json);
}
