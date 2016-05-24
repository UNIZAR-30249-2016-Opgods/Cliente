package opgods.opcampus;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

import opgods.opcampus.maps.TileProviderFactory;
import opgods.opcampus.parking.GetSlotsAdapter;
import opgods.opcampus.parking.SlotsMarkerManager;
import opgods.opcampus.teachers.GetTeachersAdapter;
import opgods.opcampus.teachers.TeacherInfoWindow;
import opgods.opcampus.teachers.TeacherMarkerManager;
import opgods.opcampus.teachers.TeacherSearcher;
import opgods.opcampus.util.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private GoogleMap mMap;
    private MapView mapView;
    private MenuItem searchItem;
    private SectionManager sectionManager;

    private FloatingActionMenu faMenu;
    private FloatingActionButton fabPlanta_0;
    private FloatingActionButton fabPlanta_1;
    private FloatingActionButton fabPlanta_2;
    private FloatingActionButton fabPlanta_3;
    private FloatingActionButton fabPlanta_4;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapView = (MapView) findViewById(R.id.mapView);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                sectionManager = new SectionManager(MainActivity.this, mMap);
                sectionManager.initView();
            }
        });

        findViewsById();
        setButtonActions();

        faMenu.setClosedOnTouchOutside(true);
        faMenu.hideMenu(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.drawer_layout, 0);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void findViewsById() {
        faMenu = (FloatingActionMenu) findViewById(R.id.fa_menu);
        fabPlanta_0 = (FloatingActionButton) findViewById(R.id.planta_0);
        fabPlanta_1 = (FloatingActionButton) findViewById(R.id.planta_1);
        fabPlanta_2 = (FloatingActionButton) findViewById(R.id.planta_2);
        fabPlanta_3 = (FloatingActionButton) findViewById(R.id.planta_3);
        fabPlanta_4 = (FloatingActionButton) findViewById(R.id.planta_4);
    }

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

    private void setFloor(String title, String planta, String profesores) {
        setTitle(title);
        mMap.clear();
        TileProvider tileProvider = TileProviderFactory.getTileProvider(planta);
        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
        faMenu.close(true);
        new GetTeachersAdapter(TeacherMarkerManager.getInstance(MainActivity.this, mMap)).execute(profesores);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Buscar profesores...");
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        TeacherSearcher searcher = new TeacherSearcher(MainActivity.this, searchView, TeacherMarkerManager.getInstance(MainActivity.this, mMap));
        searchView.setOnQueryTextListener(searcher);
        searchView.setOnSuggestionListener(searcher);
        searchItem = menu.findItem(R.id.action_search);
        searchItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_parking) {
            searchItem.setVisible(false);
            mMap.clear();
            setTitle(R.string.parking);
            int zoomLevel = 16;
            faMenu.hideMenu(false);

            LatLng adaByron = new LatLng(41.683662, -0.887611);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(adaByron, zoomLevel));
            TileProvider tileProvider = TileProviderFactory.getTileProvider(Constants.PLAZAS);
            mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
            new GetSlotsAdapter(SlotsMarkerManager.getInstance(mMap, MainActivity.this)).execute();
        } else if (id == R.id.nav_teacher) {
            searchItem.setVisible(true);
            mMap.clear();
            setTitle("Planta 0");
            int zoomLevel = 19;
            faMenu.showMenu(false);

            LatLng adaByron = new LatLng(41.683982, -0.888867);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(adaByron, zoomLevel));
            TileProvider tileProvider = TileProviderFactory.getTileProvider(Constants.PLANTA_0);
            mMap.addTileOverlay(new TileOverlayOptions().tileProvider(tileProvider));
            mMap.setInfoWindowAdapter(new TeacherInfoWindow(getApplicationContext()));
            new GetTeachersAdapter(TeacherMarkerManager.getInstance(MainActivity.this, mMap)).execute(Constants.PROFESORES_P0);
        } else if (id == R.id.nav_cafe) {
            searchItem.setVisible(false);
            mMap.clear();
            setTitle(R.string.cafe);
            int zoomLevel = 20;
            faMenu.hideMenu(false);

            LatLng adaByron = new LatLng(41.683646, -0.888620);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(adaByron, zoomLevel));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://opgods.opcampus/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://opgods.opcampus/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}