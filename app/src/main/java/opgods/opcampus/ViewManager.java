package opgods.opcampus;

import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import opgods.opcampus.cafeteria.CafeMarkerManager;
import opgods.opcampus.maps.TileProviderFactory;
import opgods.opcampus.parking.SlotInfoWindow;
import opgods.opcampus.parking.SlotsMarkerManager;
import opgods.opcampus.teachers.TeacherInfoWindow;
import opgods.opcampus.teachers.TeacherMarkerManager;
import opgods.opcampus.util.Constants;
import opgods.opcampus.util.GetAdapter;


/**
 * Clase encargada de mostrar la vista de la aplicación según las acciones del usuario
 */
public class ViewManager {
    private MainActivity mainActivity;
    private GoogleMap map;

    private FloatingActionMenu faMenu;
    private FloatingActionButton fabPlanta_0;
    private FloatingActionButton fabPlanta_1;
    private FloatingActionButton fabPlanta_2;
    private FloatingActionButton fabPlanta_3;
    private FloatingActionButton fabPlanta_4;


    public ViewManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.map = mainActivity.getMap();
        findViewsById();
        setButtonActions();
    }


    /**
     * Busca todos los elementos de la vista por su ID
     */
    private void findViewsById() {
        faMenu = (FloatingActionMenu) mainActivity.findViewById(R.id.fa_menu);
        fabPlanta_0 = (FloatingActionButton) mainActivity.findViewById(R.id.planta_0);
        fabPlanta_1 = (FloatingActionButton) mainActivity.findViewById(R.id.planta_1);
        fabPlanta_2 = (FloatingActionButton) mainActivity.findViewById(R.id.planta_2);
        fabPlanta_3 = (FloatingActionButton) mainActivity.findViewById(R.id.planta_3);
        fabPlanta_4 = (FloatingActionButton) mainActivity.findViewById(R.id.planta_4);
    }


    /**
     * Inicializa la vista
     */
    public void initView() {
        faMenu.setClosedOnTouchOutside(true);
        faMenu.hideMenu(false);
        map.getUiSettings().setMapToolbarEnabled(false);
        parkingView();
    }


    /**
     * Muestra la configuración de la sección de parking
     */
    public void parkingView() {
        new GetAdapter(new SlotsMarkerManager(mainActivity)).execute(Constants.PARKING);
        map.setInfoWindowAdapter(new SlotInfoWindow(mainActivity.getApplicationContext()));
        changeView(false, mainActivity.getString(R.string.parking), 16, new LatLng(41.683662, -0.887611), Constants.PLAZAS, Constants.DEFAULT_STYLE);
    }


    /**
     * Muestra la configuración de la sección de profesores
     *
     */
    public void teacherView() {
        new GetAdapter(TeacherMarkerManager.getInstance(mainActivity)).execute(Constants.PROFESORES_P0);
        map.setInfoWindowAdapter(new TeacherInfoWindow(mainActivity.getApplicationContext()));
        changeView(true, "Planta 0", 19, new LatLng(41.683982, -0.888867), Constants.PLANTA_0, Constants.DEFAULT_STYLE);
        faMenu.showMenu(false);
    }


    /**
     * Muestra la configuración de la sección de cafetería
     */
    public void cafeView() {
        new GetAdapter(new CafeMarkerManager(mainActivity)).execute(Constants.CAFETERIA);
        changeView(false, mainActivity.getString(R.string.cafe), 20, new LatLng(41.683646, -0.888620), Constants.PLANTA_0, Constants.CAFE_STYLE);
    }


    /**
     * Cambia los elementos de la vista
     *
     * @param searchVisibility ocultar/mostrar searchItem
     * @param title título del activity
     * @param zoom nivel de zoom del mapa
     * @param place lugar a mostrar en el mapa
     * @param layer capa a pedir al Geoserver
     */
    private void changeView(boolean searchVisibility, String title, int zoom,
                            LatLng place, String layer, String style) {
        mainActivity.getSearchItem().setVisible(searchVisibility);
        map.clear();
        mainActivity.setTitle(title);
        faMenu.hideMenu(false);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoom));
        TileProvider tileProvider = TileProviderFactory.getTileProvider(layer, style);
        map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
    }


    /**
     * Establece las acciones de los botones de plantas
     */
    private void setButtonActions() {
        fabPlanta_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFloor("Planta 0", Constants.PLANTA_0, Constants.PROFESORES_P0);
            }
        });
        fabPlanta_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFloor("Planta 1", Constants.PLANTA_1, Constants.PROFESORES_P1);
            }
        });
        fabPlanta_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFloor("Planta 2", Constants.PLANTA_2, Constants.PROFESORES_P2);
            }
        });
        fabPlanta_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFloor("Planta 3", Constants.PLANTA_3, Constants.PROFESORES_P3);
            }
        });
        fabPlanta_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFloor("Planta 4", Constants.PLANTA_4, Constants.PROFESORES_P4);
            }
        });
    }


    /**
     * Muestra una determinada planta y carga los profesores que hay en ella
     *
     * @param title título de la actividad
     * @param planta planta a mostrar
     * @param profesores profesores a mostrar
     */
    public void setFloor(String title, String planta, String profesores) {
        mainActivity.setTitle(title);
        map.clear();
        TileProvider tileProvider = TileProviderFactory.getTileProvider(planta, Constants.DEFAULT_STYLE);
        map.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
        faMenu.close(true);
        new GetAdapter(TeacherMarkerManager.getInstance(mainActivity)).execute(profesores);
    }
}