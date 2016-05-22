package opgods.opcampus.parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import opgods.opcampus.R;

/**
 * Created by URZU on 22/05/2016.
 */
public class SlotInfoWindow implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;

    public SlotInfoWindow(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myContentsView = inflater.inflate(R.layout.slot_info, null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return myContentsView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }
}
