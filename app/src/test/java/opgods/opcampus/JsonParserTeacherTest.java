package opgods.opcampus;

import org.junit.Test;

import java.util.List;

import opgods.opcampus.teachers.JsonParserTeacher;
import opgods.opcampus.teachers.Teacher;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 24/05/2016.
 */
public class JsonParserTeacherTest {
    private static final String json = "{\"error\":false,\"info\":\"yap\",\"datos\":[{\"id\":\"24\",\"nombre\":\"foo\",\"disponibilidad\":true,\"info\":\"bar\",\"despacho\":{\"localizacion\":{\"punto\":{\"latitud\":0.0,\"longitud\":0.0},\"utcPlanta\":0,\"utcEdificio\":0},\"codigo\":\"foobar\"}}]}";
    private static final String jsonError = "{\"error\":false,\"info\":\"yap\",\"datos\":\"foo\"}";
    private JsonParserTeacher parser = new JsonParserTeacher();

    @Test
    public void getDataFromJson() {
        List<Teacher> teachers = parser.getDataFromJson(json);
        assertEquals(1, teachers.size());
    }

    @Test
    public void getDataFromJsonError() {
        List<Teacher> teachers = parser.getDataFromJson(jsonError);
        assertEquals(0, teachers.size());
    }
}
