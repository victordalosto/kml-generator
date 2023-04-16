package dalosto.dnit.kmlgenerator.domain;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class TestKMLStructure {

    private KMLData mocKmlData = new KMLData();


    @BeforeEach
    void mocKmlData() {
        mocKmlData.setName("mock");
        mocKmlData.setCoordinates(List.of(new Coordinates("1.2345", "-6.789")));
    }


    @Test
    void shouldConvertKMLDataIntoAValidKMLStructure() {
        try {
            KMLStructure kmlStructure = new KMLStructure(mocKmlData);
            assertEquals("mock", kmlStructure.getName());
        } catch (ParserConfigurationException e) {
            fail();
        }
    }

}
