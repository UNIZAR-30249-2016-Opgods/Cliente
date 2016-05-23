package opgods.opcampus.teachers;

import android.content.Context;
import android.database.MatrixCursor;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import java.util.List;

import opgods.opcampus.MainActivity;
import opgods.opcampus.R;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class TeacherSearcher implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {
    private Context context;
    private SearchView searchView;
    private TeacherMarkerManager manager;

    public TeacherSearcher(MainActivity activity, SearchView searchView, TeacherMarkerManager teacherMarkerManager) {
        this.context = activity.getApplicationContext();
        this.searchView = searchView;
        this.manager = teacherMarkerManager;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Text searched", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String name) {
        new GetTeachersAdapter(this).execute(Constants.BUSCADOR_PROFESORES + name);
        return false;
    }

    public void setTeachers(List<Teacher> teachers) {
        if (teachers != null) {
            // Cursor
            String[] columns = new String[] { "_id", "text" };
            Object[] temp = new Object[] { 0, "default" };

            MatrixCursor cursor = new MatrixCursor(columns);

            for (int i = 0; i < teachers.size(); i++) {
                temp[0] = i;
                temp[1] = teachers.get(i);
                cursor.addRow(temp);
            }
            TeacherCursorAdapter adapter = new TeacherCursorAdapter(context, cursor, teachers);
            // autocompleta con una letra
            AutoCompleteTextView searchAutoCompleteTextView = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);
            searchAutoCompleteTextView.setThreshold(1);
            searchView.setSuggestionsAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        TeacherCursorAdapter adapter = (TeacherCursorAdapter) searchView.getSuggestionsAdapter();
        manager.loadMarker(adapter.getTeacher(position));
        adapter.changeCursor(null);
        return false;
    }
}
