package dalosto.dnit.kmlgenerator.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.exception.InvalidKMLException;


@SpringBootTest
public class TestFactoryKMLData {

    @Autowired
    FactoryKMLData factoryKMLData;


    @Test
    void shouldCreateAValidKMLDataFromAFile() {
        try {
            File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\rightformatfile.csv");
            KMLData kmlData = factoryKMLData.createFromFile(file);
            assertEquals(5, kmlData.getCoordinates().size());
            assertEquals("rightformatfile", kmlData.getName());
        } catch (InvalidKMLException e) {
            fail();
        }
    }
    
    
    @Test
    void shouldThrowsErrorWhenCreatingFromBlankFile() {
        File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\emptyfile.csv");
        assertThrows(InvalidKMLException.class, () -> factoryKMLData.createFromFile(file));
    }
    

    @Test
    void shouldThrowsErrorWhenCreatingFromInexistentFile() {
        File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\inexistent.csv");
        assertThrows(InvalidKMLException.class, () -> factoryKMLData.createFromFile(file));
    }
    
    
    
    @Test
    void shouldThrowsErrorWhenCreatingUsingInvalidFormatFile() {
        File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\wrongformat.csv");
        assertThrows(InvalidKMLException.class, () -> factoryKMLData.createFromFile(file));
    }


}
