package dalosto.dnit.kmlgenerator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import dalosto.dnit.kmlgenerator.domain.Coordinates;


@SpringBootTest
public class TestCoordinates {

    @Test
    void testValidCoordinatesUsingComma() {
        Coordinates coordinates = new Coordinates("1.2345", "-6.789");
        assertEquals(1.2345, coordinates.getY());
        assertEquals(-6.789, coordinates.getX());
    }


    @Test
    void testValidCoordinatesUsingDot() {
        Coordinates coordinates = new Coordinates("1,2345", "-6,789");
        assertEquals(1.2345, coordinates.getY());
        assertEquals(-6.789, coordinates.getX());
    }


    @Test
    void testValidCoordinatesRemovingNoise() {
        Coordinates coordinates = new Coordinates("1,2345asd", "-g6.789ccz");
        assertEquals(1.2345, coordinates.getY());
        assertEquals(-6.789, coordinates.getX());
    }


    @Test
    void testValidCoordinatesUsingCientificFormat() {
        Coordinates coordinates = new Coordinates("1.2345-5", "-6.789-5");
        assertEquals(1.2345E-5, coordinates.getY());
        assertEquals(-6.789E-5, coordinates.getX());
    }


    @Test
    void testValidCoordinatesUsingCientificFormatWithExponential() {
        Coordinates coordinates = new Coordinates("1.2345e-5", "-6.789-E5");
        assertEquals(1.2345E-5, coordinates.getY());
        assertEquals(-6.789E-5, coordinates.getX());
    }


    @Test
    void testBlankAsInvalidCoordinates() {
        assertThrows(NumberFormatException.class, () -> new Coordinates("", "12345"));
    }


    @Test
    void testTextAsInvalidCoordinates() {
        assertThrows(NumberFormatException.class, () -> new Coordinates("asda", "12345"));
    }


    @Test
    void testNullAsInvalidCoordinates() {
        assertThrows(NumberFormatException.class, () -> new Coordinates(null, "12345"));
    }

}
