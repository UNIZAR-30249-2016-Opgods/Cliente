package opgods.opcampus;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import opgods.opcampus.parking.RoutesCalculator;

import static junit.framework.Assert.assertNotSame;

/**
 * Created by URZU on 25/05/2016.
 */
public class RoutesCalculatorTest {
    @Test
    public void testCalculeRoute() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        String key = "AIzaSyA3BtOe4pnbV2ECRufc6eJcElX3XjVFGos";
        RoutesCalculator routesCalculator = new RoutesCalculator(key);
        DirectionCallback callback = new DirectionCallback() {
            @Override
            public void onDirectionSuccess(Direction direction, String rawBody) {
                if (direction.isOK()) {
                    Route route = direction.getRouteList().get(0);
                    Leg leg = route.getLegList().get(0);
                    assertNotSame(0, leg.getDistance().getText().length());
                    signal.countDown();
                }
            }

            @Override
            public void onDirectionFailure(Throwable t) {

            }
        };

        routesCalculator.paintRoute(new LatLng(41.405368, 2.151772), new LatLng(41.403968, 2.150324), callback);
        signal.await(5, TimeUnit.SECONDS);
    }
}
