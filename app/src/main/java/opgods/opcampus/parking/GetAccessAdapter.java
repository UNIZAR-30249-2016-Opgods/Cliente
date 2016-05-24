package opgods.opcampus.parking;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import opgods.opcampus.MainActivity;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 22/05/2016.
 */
public class GetAccessAdapter extends AsyncTask<String, Void, List<LatLng>> {
    private MainActivity activity;

    public GetAccessAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<LatLng> doInBackground(String... params) {
        List<LatLng> access = null;
        try {
            URL url = new URL(Constants.SERVER + Constants.ACCESOS);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                JsonParserAccess parser = new JsonParserAccess();
                StringBuilder response = parser.getResponse(httpUrlConnection.getInputStream());
                access = parser.getDataFromJson(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return access;
    }

    @Override
    protected void onPostExecute(List<LatLng> result) {
        super.onPostExecute(result);
        activity.setAccess(result);
    }
}