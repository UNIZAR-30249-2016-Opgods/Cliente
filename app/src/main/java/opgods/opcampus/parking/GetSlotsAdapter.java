package opgods.opcampus.parking;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import opgods.opcampus.MainActivity;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 22/05/2016.
 */
public class GetSlotsAdapter extends AsyncTask<String, Void, List<Slot>> {
    private MainActivity activity;

    public GetSlotsAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected List<Slot> doInBackground(String... params) {
        List<Slot> slots = null;
        try {
            URL url = new URL(Constants.PARKING);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                JsonParserSlots parser = new JsonParserSlots();
                StringBuilder response = parser.getResponse(httpUrlConnection.getInputStream());
                slots = parser.getDataFromJson(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return slots;
    }


    @Override
    protected void onPostExecute(List<Slot> result) {
        super.onPostExecute(result);
        activity.setSlots(result);
    }
}
