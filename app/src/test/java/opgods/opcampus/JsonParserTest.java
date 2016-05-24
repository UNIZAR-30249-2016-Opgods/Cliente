package opgods.opcampus;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import opgods.opcampus.util.JsonParser;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 24/05/2016.
 */
public class JsonParserTest {
    private static final String jsonError = "{\"error\": 1,\"message\": \"foo\"}";
    private static final String jsonUnknownError = "{\"bar\": 1,\"message\": \"foo\"}";
    private static final String response = "{\"error\": 0,\"message\": \"foo\"}";
    private JsonParser jsonParser;

    @Before
    public void setUp() {
        jsonParser = new JsonParser() {
            @Override
            public List<?> getDataFromJson(String json) {
                return null;
            }
        };
    }

    @Test
    public void getErrorFromJson() {
        int error = jsonParser.getErrorFromJson(jsonError);
        assertEquals(error, 1);
    }

    @Test
    public void getUnknownErrorFromJson() {
        int error = jsonParser.getErrorFromJson(jsonUnknownError);
        assertEquals(error, -1);
    }

    @Test
    public void getResponse() {
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        assertEquals(response, jsonParser.getResponse(inputStream));
    }
}
