package dalosto.dnit.kmlgenerator;
import java.io.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.exception.InvalidKMLException;
import dalosto.dnit.kmlgenerator.interfaces.KMLGenerator;
import jakarta.annotation.PostConstruct;


@Component
public class GenerateKMLApplication {

    @Autowired
    KMLGenerator kmlGenerator;


    @PostConstruct
    void run() throws FileNotFoundException, InvalidKMLException {
        // List<File> files = List.of(Paths.get("test.csv").toFile());
        // kmlGenerator.createFromListOfFiles(files);
    }

}