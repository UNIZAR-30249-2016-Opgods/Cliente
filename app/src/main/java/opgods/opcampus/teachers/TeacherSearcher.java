package opgods.opcampus.teachers;

import android.content.Context;
import android.database.MatrixCursor;
import android.support.v7.widget.SearchView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 21/05/2016.
 */
public class TeacherSearcher implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener {
    private Context context;
    private SearchView searchView;
    private TeacherCursorAdapter adapter;

    public TeacherSearcher(Context context, SearchView searchView) {
        this.context = context;
        this.searchView = searchView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Text searched", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String name) {
        Log.d("Text changed", name);
        new GetTeachersAdapter(this).execute(Constants.BUSCADOR_PROFESORES + name);
        return false;
    }

    public void setTeachers(List<Teacher> teachers) {
        List<String> teachersName = new ArrayList<>();
        if (teachers != null) {
            for (Teacher teacher : teachers) {
                Log.d("Teacher", teacher.getNombre());
                teachersName.add(teacher.getNombre());
            }
            populateAdapter(teachersName);
        } else {
            populateAdapter(teachersName);
        }
    }

    private void populateAdapter(List<String> teachers) {
        // Cursor
        String[] columns = new String[] { "_id", "text" };
        Object[] temp = new Object[] { 0, "default" };

        MatrixCursor cursor = new MatrixCursor(columns);

        for (int i = 0; i < teachers.size(); i++) {
            temp[0] = i;
            temp[1] = teachers.get(i);
            cursor.addRow(temp);
        }
        adapter = new TeacherCursorAdapter(context, cursor, teachers);
        searchView.setSuggestionsAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSuggestionSelect(int position) {
        return false;
    }

    @Override
    public boolean onSuggestionClick(int position) {
        return false;
    }
}
