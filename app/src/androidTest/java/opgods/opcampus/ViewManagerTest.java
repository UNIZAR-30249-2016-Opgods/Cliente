package opgods.opcampus;

import android.test.ActivityInstrumentationTestCase2;


/**
 * Created by URZU on 25/05/2016.
 */
public class ViewManagerTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private ViewManager manager;

    public ViewManagerTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        MainActivity mainActivity = getActivity();
        manager = new ViewManager(mainActivity);
    }

    public void testInit() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                manager.initView();
            }
        });
    }

    public void testParking() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                manager.initView();
                manager.parkingView();
            }
        });
    }

    public void testTeacher() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                manager.initView();
                manager.teacherView();
            }
        });
    }

    public void testCafe() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                manager.initView();
                manager.cafeView();
            }
        });
    }
}
