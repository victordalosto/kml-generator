package dalosto.dnit.kmlgenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.interfaces.KMLGenerator;
import jakarta.annotation.PostConstruct;


@Component
public class App {

    @Autowired
    KMLGenerator kmlGenerator;


    @PostConstruct
    void run() throws FileNotFoundException {
        List<File> files = List.of(Paths.get("test.csv").toFile());
        kmlGenerator.createFromListOfFiles(files);
    }

}
