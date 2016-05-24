package opgods.opcampus;

import org.junit.Test;

import java.util.List;

import opgods.opcampus.parking.JsonParserSlots;
import opgods.opcampus.parking.Slot;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 24/05/2016.
 */
public class JsonParserSlotsTest {
    private static final String json = "{\"error\":false,\"info\":\"foo\",\"datos\":[{\"id\":\"500\",\"nombre\":\"foo\",\"punto\":{\"latitud\":0.0,\"longitud\":0.0},\"ocupacion\":{\"nPlazas\":50,\"ocupadas\":2,\"libres\":48}}]}";
    private static final String jsonError = "{\"error\":false,\"info\":\"yap\",\"datos\":\"foo\"}";
    private JsonParserSlots parser = new JsonParserSlots();

    @Test
    public void getDataFromJson() {
        List<Slot> teachers = parser.getDataFromJson(json);
        assertEquals(1, teachers.size());
    }

    @Test
    public void getDataFromJsonError() {
        List<Slot> teachers = parser.getDataFromJson(jsonError);
        assertEquals(0, teachers.size());
    }
}
