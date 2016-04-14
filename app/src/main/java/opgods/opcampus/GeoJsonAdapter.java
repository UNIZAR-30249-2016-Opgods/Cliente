package opgods.opcampus;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by URZU on 14/04/2016.
 */
public class GeoJsonAdapter extends AsyncTask<String, String, String> {
    private String urlAda = "http://geoserver-naxsel.rhcloud.com/tiger/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=tiger:planta_calle_ada_test&maxFeatures=50&outputFormat=application%2Fjson";
    private MainActivity mainActivity;
    private StringBuilder response = new StringBuilder();
    private JSONObject jsonObject;

    public GeoJsonAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL(urlAda);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                getResponse(httpUrlConnection.getInputStream());
                jsonObject = new JSONObject(response.toString());
            } else {
                getResponse(httpUrlConnection.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private void getResponse(InputStream inputStream) {
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mainActivity.setLayer(jsonObject);
    }
}
