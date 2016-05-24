package opgods.opcampus;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import opgods.opcampus.maps.TileProviderFactory;
import opgods.opcampus.parking.GetSlotsAdapter;
import opgods.opcampus.parking.SlotsMarkerManager;
import opgods.opcampus.util.Constants;

/**
 * Created by URZU on 24/05/2016.
 */
public class SectionManager {
    private MainActivity mainActivity;
    private GoogleMap map;

    public SectionManager(MainActivity mainActivity, GoogleMap map) {
        this.mainActivity = mainActivity;
        this.map = map;
    }

    public void initView() {
        mainActivity.setTitle(R.string.parking);
        int zoomLevel = 16;

        LatLng adaByron = new LatLng(41.683662, -0.887611);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(adaByron, zoomLevel));
        map.getUiSettings().setMapToolbarEnabled(false);
        TileProvider tileProvider = TileProviderFactory.getTileProvider(Constants.PLAZAS);
        map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
        new GetSlotsAdapter(SlotsMarkerManager.getInstance(map, mainActivity)).execute();
    }
}
