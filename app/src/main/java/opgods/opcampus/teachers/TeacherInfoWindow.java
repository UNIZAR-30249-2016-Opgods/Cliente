package opgods.opcampus.teachers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import opgods.opcampus.R;

/**
 * Ventana personalizada de información de un profesor
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
        TextView tvSnippet = ((TextView) myContentsView.findViewById(R.id.snippet));
        String[] data = formatData(marker.getTitle());
        tvTitle.setText(data[0]);
        tvSnippet.setText(data[1]);

        TextView tvTitle2 = ((TextView) myContentsView.findViewById(R.id.title2));
        TextView tvSnippet2 = ((TextView) myContentsView.findViewById(R.id.snippet2));
        if (marker.getSnippet() != null) {
            data = formatData(marker.getSnippet());
            tvTitle2.setText(data[0]);
            tvSnippet2.setText(data[1]);
        } else {
            tvTitle2.setText("");
            tvSnippet2.setText("");
        }

        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    /**
     * Formatea la información del profesor
     *
     * @param data con la información
     * @return array de Strings con nombre e información
     */
    private String[] formatData(String data) {
        data = data.replace("; ", "\n").replace(":", ": ");
        return data.split("--");
    }
}
