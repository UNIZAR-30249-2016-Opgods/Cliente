package opgods.opcampus.teachers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import opgods.opcampus.R;

/**
 * Created by URZU on 22/05/2016.
 */
public class TeacherInfoWindow implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;

    public TeacherInfoWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myContentsView = inflater.inflate(R.layout.teacher_info, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView tvTitle = ((TextView) myContentsView.findViewById(R.id.title));
        tvTitle.setText(marker.getTitle());
        TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.snippet));
        tvSnippet.setText(formatInfo(marker.getSnippet()));

        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

    private String formatInfo(String info) {
        return info.replace(";", "\n").replace(" ", "").replace(":", ": ");
    }
}
