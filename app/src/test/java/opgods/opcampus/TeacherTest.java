package opgods.opcampus;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import opgods.opcampus.teachers.Teacher;

import static junit.framework.Assert.assertEquals;

/**
 * Created by URZU on 24/05/2016.
 */
public class TeacherTest {
    private static final int id = 6;
    private static final String nombre = "foo";
    private static final boolean disponible = true;
    private static final String info = "bar";
    private static final LatLng localizacion = new LatLng(0.3, 7.0);
    private static final String despacho = "foobar";
    private static final int planta = 2;

    @Test
    public void newTeacher() {
        Teacher teacher = new Teacher(id, info, localizacion, nombre, despacho, disponible, planta);
        assertEquals(teacher.getId(), id);
        assertEquals(teacher.getNombre(), nombre);
        assertEquals(teacher.estaDisponible(), disponible);
        assertEquals(teacher.getInfo(), info);
        assertEquals(teacher.getLocalizacion(), localizacion);
        assertEquals(teacher.getDespacho(), despacho);
        assertEquals(teacher.getPlanta(), planta);

    }
}