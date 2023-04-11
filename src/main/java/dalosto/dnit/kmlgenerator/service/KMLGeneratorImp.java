package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.KMLModel;
import dalosto.dnit.kmlgenerator.interfaces.ExportKML;
import dalosto.dnit.kmlgenerator.interfaces.FactoryKMLModel;
import dalosto.dnit.kmlgenerator.interfaces.KMLGenerator;

/**
 * Class that implements the KMLGenerator interface
 * This implementation uses the Commander and Facade design patterns */
@Component
public class KMLGeneratorImp implements KMLGenerator {

    @Autowired
    private FactoryKMLModel factoryKML;

    @Autowired
    private ExportKML exportKML;


    @Override
    public Path createFromFile(File file) throws FileNotFoundException {
        KMLModel model = factoryKML.createFromFile(file);
        Path output = exportKML.generate(model);
        return output;
    }


    @Override
    public List<Path> createFromListOfFiles(List<File> files) throws FileNotFoundException {
        List<Path> outputs = new ArrayList<>();
        for (File file : files) {
            Path output = createFromFile(file);
            outputs.add(output);
        }
        return outputs;
    }


    @Override
    public List<Path> createFromDirectory(Path dir) throws FileNotFoundException {
        List<File> listOfFiles = Stream.of(dir.toFile().listFiles())
                                       .filter(file -> !file.isDirectory())
                                       .collect(Collectors.toList());
        return createFromListOfFiles(listOfFiles);
    }


    @Override
    public void setSaveDirectory(Path dir) {
        this.exportKML.setSavingDirectory(dir);
    }

}
