package opgods.opcampus.teachers;

import android.app.Activity;
import android.content.Context;
import android.database.MatrixCursor;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.AutoCompleteTextView;

import java.util.List;

import opgods.opcampus.R;
import opgods.opcampus.util.AsyncTaskCompleteListener;
import opgods.opcampus.util.Constants;
import opgods.opcampus.util.GetAdapter;

/**
 * Clase encargada de la función de autocomletado de profesores
 */
public class TeacherSearcher implements SearchView.OnQueryTextListener, SearchView.OnSuggestionListener, AsyncTaskCompleteListener<String> {
    private Context context;
    private SearchView searchView;
    private TeacherMarkerManager manager;

    public TeacherSearcher(Activity activity, SearchView searchView, TeacherMarkerManager teacherMarkerManager) {
        this.context = activity.getApplicationContext();
        this.searchView = searchView;
        this.manager = teacherMarkerManager;
        // autocompleta con una letra
        AutoCompleteTextView searchAutoCompleteTextView = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);
        searchAutoCompleteTextView.setThreshold(1);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d("Text searched", query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String name) {
        if (name.length() == 0) {
            searchView.setSuggestionsAdapter(null);
        } else {
            // realiza un petición cada vez que el usuario introduce una nueva letra
            new GetAdapter(this).execute(Constants.BUSCADOR_PROFESORES + name);
        }
        return false;
    }


    /**
     * Pobla la barra de autocompletado
     *
     * @param teachers profesores a mostrar
     */
    private void setTeachers(List<Teacher> teachers) {
        if (teachers != null) {
            // Cursor
            String[] columns = new String[] { "_id", "text" };
            Object[] temp = new Object[] { 0, "default" };

            MatrixCursor cursor = new MatrixCursor(columns);

            for (int i = 0; i < teachers.size() && i < 7; i++) {
                temp[0] = i;
                temp[1] = teachers.get(i);
                cursor.addRow(temp);
            }
            TeacherCursorAdapter adapter = new TeacherCursorAdapter(context, cursor, teachers);
            adapter.changeCursor(cursor);
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
        return false;
    }

    @Override
    public void onTaskComplete(String result) {
        JsonParserTeacher parser = new JsonParserTeacher();
        List<Teacher> teachers = parser.getDataFromJson(result);
        setTeachers(teachers);
    }
}
