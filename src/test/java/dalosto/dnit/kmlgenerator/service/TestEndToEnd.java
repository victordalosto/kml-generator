package dalosto.dnit.kmlgenerator.service;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dalosto.dnit.kmlgenerator.interfaces.KMLGenerator;


@SpringBootTest
public class TestEndToEnd {

    @Autowired
    KMLGenerator kmlGenerator;

    @BeforeEach
    @AfterEach
    void setupTemporatyTestDirectory() throws IOException {
        Path tempExportDir = Paths.get("src", "test", "java", "dalosto", "dnit", "kmlgenerator", "files", "export");
        kmlGenerator.setSavingDirectory(tempExportDir);
        if (tempExportDir.toFile().exists()) {
            tempExportDir.toFile().delete();
        }
        tempExportDir.toFile().mkdir();
    }


    @Test
    void shouldCreateAValidKMLDataFromAFile() {
        try {
            Path createFromFile = kmlGenerator.createFromFile(
                                        Paths.get("src", "test", "java", "dalosto", 
                                        "dnit", "kmlgenerator", "files", "rightformatfile.csv")
                                        .toFile());
            if (!createFromFile.toFile().exists()) {
                fail();
            }
            String linesFromKML;
            
                linesFromKML = String.join("", Files.readAllLines(createFromFile));
            
            assertTrue(linesFromKML.contains("<name>rightformatfile</name>"));
            assertTrue(linesFromKML.contains("<coordinates>-40.2037020052551,-3.455323187765579 -40.2039058523626,-3.455794406996205 -40.20469978622161,-3.4567689552126875 -40.20553663543146,-3.4576792466157915 -40.2062876539484,-3.4588572694841084 </coordinates>"));
            assertTrue(linesFromKML.contains("<kml xmlns=\"http://earth.google.com/kml/"));
            assertTrue(linesFromKML.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));
            assertTrue(linesFromKML.endsWith("</kml>"));
            assertTrue(createFromFile.toFile().getName().contains(".kml"));
        } catch (Exception e) {
            fail();
        }
        
    }

}
