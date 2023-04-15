package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.exception.InvalidKMLException;
import dalosto.dnit.kmlgenerator.interfaces.KMLGenerator;


/**
 * This implementation of KMLGenerator uses the Commander and Facade design patterns
 */
@Component
public class KMLGeneratorImp implements KMLGenerator {

    @Autowired
    private FactoryKMLData factoryKMLData;
    
    @Autowired
    private ExportKML exportKML;


    @Override
    public Path createFromFile(File file) throws InvalidKMLException {
        KMLData kmlData = factoryKMLData.createFromFile(file);
        Path output = exportKML.generateKML(kmlData);
        return output;
    }


    @Override
    public List<Path> createFromListOfFiles(List<File> files) throws InvalidKMLException {
        List<Path> outputs = new ArrayList<>();
        for (File file : files) {
            Path output = createFromFile(file);
            outputs.add(output);
        }
        return outputs;
    }


    @Override
    public List<Path> createFromDirectory(Path dir) throws InvalidKMLException {
        List<File> listOfFiles = Stream.of(dir.toFile().listFiles()).filter(file -> !file.isDirectory())
                .collect(Collectors.toList());
        return createFromListOfFiles(listOfFiles);
    }


    @Override
    public void setSaveDirectory(Path dir) {
        this.exportKML.setSavingDirectory(dir);
    }

}
