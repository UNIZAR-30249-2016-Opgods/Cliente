package opgods.opcampus;

import org.junit.Test;

import java.util.List;

import opgods.opcampus.cafeteria.Cafe;
import opgods.opcampus.cafeteria.JsonParserCafe;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 25/05/2016.
 */
public class JsonParserCafeTest {
    private static final String json = "{\"error\":false,\"info\":\"foo\",\"datos\":[{\"id\":\"500\",\"nombre\":\"foo\",\"punto\":{\"latitud\":0.0,\"longitud\":0.0},\"ocupacion\":{\"nPlazas\":50,\"ocupadas\":2,\"libres\":48}}]}";
    private static final String jsonError = "{\"error\":false,\"info\":\"yap\",\"datos\":\"foo\"}";
    private JsonParserCafe parser = new JsonParserCafe();

    @Test
    public void getDataFromJson() {
        List<Cafe> cafes = parser.getDataFromJson(json);
        assertEquals(1, cafes.size());
    }

    @Test
    public void getDataFromJsonError() {
        List<Cafe> cafes = parser.getDataFromJson(jsonError);
        assertEquals(0, cafes.size());
    }
}
