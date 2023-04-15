package dalosto.dnit.kmlgenerator;
import java.io.File;
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
        File file = new File("src\\test\\java\\dalosto\\dnit\\kmlgenerator\\files\\rightformatfile.csv");
        kmlGenerator.createFromFile(file);
    }

}