package opgods.opcampus.teachers;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

import opgods.opcampus.util.AsyncTaskCompleteListener;

/**
 * Created by URZU on 22/05/2016.
 */
public class GetTeachersAdapter extends AsyncTask<String, Void, String> {
    private AsyncTaskCompleteListener<String> listener;

    public GetTeachersAdapter(AsyncTaskCompleteListener<String> listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = "";
        try {
            URL url = new URL(params[0]);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(10000);
            httpUrlConnection.setConnectTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setDoInput(true);

            int status = httpUrlConnection.getResponseCode();

            if (status == HttpURLConnection.HTTP_OK) {
                JsonParserTeacher parser = new JsonParserTeacher();
                response = parser.getResponse(httpUrlConnection.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        listener.onTaskComplete(result);
    }
}
