package opgods.opcampus;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.List;

import opgods.opcampus.parking.JsonParserAccess;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 24/05/2016.
 */
public class JsonParserAccessTest {
    private static final String json = "{\"error\":false,\"info\":\"foo\",\"datos\":[{\"latitud\":0.0,\"longitud\":0.0}]}";
    private static final String jsonError = "{\"error\":false,\"info\":\"yap\",\"datos\":\"foo\"}";
    private JsonParserAccess parser = new JsonParserAccess();

    @Test
    public void getDataFromJson() {
        List<LatLng> teachers = parser.getDataFromJson(json);
        assertEquals(1, teachers.size());
    }
    @Test
    public void getDataFromJsonError() {
        List<LatLng> teachers = parser.getDataFromJson(jsonError);
        assertEquals(0, teachers.size());
    }
}
