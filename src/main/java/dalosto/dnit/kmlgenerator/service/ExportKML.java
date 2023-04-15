package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.transform.TransformerException;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.KMLStructure;


@Component
public class ExportKML {

    private Path savingDirectory = Paths.get("output");


    public void setSavingDirectory(Path saveDirectory) throws IOException {
        if (!saveDirectory.toFile().isDirectory()) {
            Files.createDirectory(saveDirectory);
        }
        this.savingDirectory = saveDirectory;
    }


    public Path generateKML(KMLStructure kmlStructure) throws TransformerException {
        File file = fileLocation(savingDirectory, kmlStructure.getName());
        return kmlStructure.saveFileIn(file);
    }


    private File fileLocation(Path saveDirectory, String name) {
        String fileNameWithoutExtension = name.replaceAll("\\.[a-zA-Z0-9]+$", "") + ".kml";
        return Paths.get(saveDirectory.toString(), fileNameWithoutExtension).toFile();
    }

}