package opgods.opcampus.teachers;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import opgods.opcampus.R;

/**
 * Created by URZU on 23/05/2016.
 */
public class TeacherCursorAdapter extends CursorAdapter {
    private List<Teacher> teachers;
    private TextView text;

    public TeacherCursorAdapter(Context context, Cursor cursor, List<Teacher> teachers) {
        super(context, cursor, false);
        this.teachers = teachers;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.teacher_item, parent, false);
        text = (TextView) view.findViewById(R.id.item);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(teachers.get(cursor.getPosition()).getNombre());
    }

    public Teacher getTeacher(int position) {
        return teachers.get(position);
    }
}