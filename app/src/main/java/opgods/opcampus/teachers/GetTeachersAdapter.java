package opgods.opcampus.teachers;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import opgods.opcampus.MainActivity;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 22/05/2016.
 */
public class GetTeachersAdapter extends AsyncTask<Void, Void, List<Teacher>> {
    private MainActivity activity;
    private String floor;

    public GetTeachersAdapter(MainActivity activity, String floor) {
        this.activity = activity;
        this.floor = floor;
    }

    @Override
    protected List<Teacher> doInBackground(Void... params) {
        List<Teacher> teachers = null;
        try {
            URL url = new URL(Constants.SERVER + floor);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                JsonParserTeacher parser = new JsonParserTeacher();
                StringBuilder response = parser.getResponse(httpUrlConnection.getInputStream());
                teachers = parser.getDataFromJson(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teachers;
    }


    @Override
    protected void onPostExecute(List<Teacher> result) {
        super.onPostExecute(result);
        activity.setTeachers(result);
    }
}
