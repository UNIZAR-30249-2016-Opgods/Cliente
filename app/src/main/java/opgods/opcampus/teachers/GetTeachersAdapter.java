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
public class GetTeachersAdapter extends AsyncTask<String, Void, List<Teacher>> {
    private TeacherSearcher teacherSearcher;
    private MainActivity activity;

    public GetTeachersAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public GetTeachersAdapter(TeacherSearcher teacherSearcher) {
        this.teacherSearcher = teacherSearcher;
    }

    @Override
    protected List<Teacher> doInBackground(String... params) {
        List<Teacher> teachers = null;
        try {
            URL url = new URL(Constants.SERVER + params[0]);

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
        if (activity != null) {
            activity.setTeachers(result);
        } else if (teacherSearcher != null) {
            teacherSearcher.setTeachers(result);
        }
    }
}
