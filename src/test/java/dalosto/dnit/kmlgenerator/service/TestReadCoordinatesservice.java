package dalosto.dnit.kmlgenerator.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dalosto.dnit.kmlgenerator.domain.Coordinates;


@SpringBootTest
public class TestReadCoordinatesService {

   @Autowired
   ReadCoordinatesservice readLinesImp;


   @Test
   void testGeneratingKMLFrom_rightformatfile() throws NumberFormatException, FileNotFoundException {
      File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\rightformatfile.csv");
      List<Coordinates> coordinates = readLinesImp.getFromFile(file);
      assertEquals(5, coordinates.size());
   }


   @Test
   void testGeneratingKMLFrom_rightformatfile2() throws NumberFormatException, FileNotFoundException {
      File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\rightformatfile2.csv");
      List<Coordinates> coordinates = readLinesImp.getFromFile(file);
      assertEquals(5, coordinates.size());
   }


   @Test
   void testGeneratingKMLFrom_rightformatfile3() throws NumberFormatException, FileNotFoundException {
      File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\rightformatfile3.csv");
      List<Coordinates> coordinates = readLinesImp.getFromFile(file);
      assertEquals(5, coordinates.size());
   }


   @Test
   void shouldGetFileNotFoundExceptionFromAInvalidFile() {
      File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\nonexistent.csv");
      assertThrows(FileNotFoundException.class, () -> readLinesImp.getFromFile(file));
   }


   @Test
   void shouldGetExceptionFromAEmptyFile() {
      File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\emptyfile.csv");
      assertThrows(NumberFormatException.class, () -> readLinesImp.getFromFile(file));
   }


   @Test
   void shouldThrowsErrorCreatingUsingInvalidFormatFile() {
      File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\wrongformat.csv");
      assertThrows(NumberFormatException.class, () -> readLinesImp.getFromFile(file));
   }

}
