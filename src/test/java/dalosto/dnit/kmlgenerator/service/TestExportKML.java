package dalosto.dnit.kmlgenerator.service;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dalosto.dnit.kmlgenerator.domain.Coordinates;
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.domain.KMLStructure;


@SpringBootTest
public class TestExportKML {

    @Autowired
    private ExportKML exportKML;

    private static KMLStructure mocKmlStructure;
    private Path tempExportDir = Paths.get("src", "test", "java", "dalosto", "dnit", "kmlgenerator", "files", "export");


    @BeforeAll
    public static void mocKmlData() throws ParserConfigurationException {
        KMLData kmlData = new KMLData("mock", List.of(new Coordinates("1.2345", "-6.789")));
        mocKmlStructure = new KMLStructure(kmlData);
    }


    @BeforeEach
    @AfterEach
    public void setupTemporatyTestDirectory() throws IOException {
        exportKML.setSavingDirectory(tempExportDir);
        if (tempExportDir.toFile().exists()) {
            tempExportDir.toFile().delete();
        }
        tempExportDir.toFile().mkdir();
    }


    @Test
    public void shouldCreateAValidKMLFromAValidKMLStructure() throws IOException, TransformerException {
        Path generateKML = exportKML.generateKML(mocKmlStructure);
        String linesFromKML = String.join("", Files.readAllLines(generateKML));
        assertTrue(linesFromKML.contains("<name>mock</name>"));
        assertTrue(linesFromKML.contains("<coordinates>-6.789,1.2345 </coordinates>"));
        assertTrue(linesFromKML.contains("<kml xmlns=\"http://earth.google.com/kml/"));
        assertTrue(linesFromKML.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
        assertTrue(linesFromKML.endsWith("</kml>"));
        assertTrue(generateKML.toFile().getName().contains(".kml"));
    }

    
}
