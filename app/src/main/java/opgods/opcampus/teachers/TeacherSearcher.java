package opgods.opcampus.teachers;

import android.support.v7.widget.SearchView;
import android.util.Log;

/**
 * Created by URZU on 21/05/2016.
 */
public class TeacherSearcher implements SearchView.OnQueryTextListener {
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Text searched", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("Text changed", newText);
        return false;
    }
}
