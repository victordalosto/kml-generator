package dalosto.dnit.kmlgenerator.domain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestCoordinates {

    @Test
    void shouldGenerateValidCoordinatesWhenUsingComma() {
        Coordinates coordinates = new Coordinates("1.2345", "-6.789");
        assertEquals(1.2345, coordinates.getY());
        assertEquals(-6.789, coordinates.getX());
    }


    @Test
    void shouldGenerateValidCoordinatesWhenUsingDot() {
        Coordinates coordinates = new Coordinates("1,2345", "-6,789");
        assertEquals(1.2345, coordinates.getY());
        assertEquals(-6.789, coordinates.getX());
    }


    @Test
    void shouldGenerateValidCoordinatesEvenWhenStringHaveNoise() {
        Coordinates coordinates = new Coordinates("1,2345asd", "-g6.789ccz");
        assertEquals(1.2345, coordinates.getY());
        assertEquals(-6.789, coordinates.getX());
    }


    @Test
    void shouldGenerateValidCoordinatesWhenUsingCientificNumbers() {
        Coordinates coordinates = new Coordinates("1.2345-5", "-6.789-5");
        assertEquals(1.2345E-5, coordinates.getY());
        assertEquals(-6.789E-5, coordinates.getX());
    }


    @Test
    void shouldGenerateValidCoordinatesWhenUsingCientificNumbersAndPositiveExpoent() {
        Coordinates coordinates = new Coordinates("1.2345+5", "-6.789+5");
        assertEquals(1.2345E+5, coordinates.getY());
        assertEquals(-6.789E+5, coordinates.getX());
    }


    @Test
    void shouldGenerateValidCoordinatesWhenUsingCientificNumbersWithExpoentAsE() {
        Coordinates coordinates = new Coordinates("1.2345e-5", "-6.789-E5");
        assertEquals(1.2345E-5, coordinates.getY());
        assertEquals(-6.789E-5, coordinates.getX());
    }


    @Test
    void NumberFormatExceptionShouldBeThrownWhenCoordinateIsBlank() {
        assertThrows(NumberFormatException.class, () -> new Coordinates("", "12345"));
    }


    @Test
    void NumberFormatExceptionShouldBeThrownWhenCoordinateIsAText() {
        assertThrows(NumberFormatException.class, () -> new Coordinates("asda", "12345"));
    }


    @Test
    void NumberFormatExceptionShouldBeThrownWhenInputCoordinateIsNull() {
        assertThrows(NumberFormatException.class, () -> new Coordinates(null, "12345"));
    }

}
