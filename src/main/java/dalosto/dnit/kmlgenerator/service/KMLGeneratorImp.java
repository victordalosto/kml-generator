package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.domain.KMLStructure;
import dalosto.dnit.kmlgenerator.exception.InvalidKMLException;
import dalosto.dnit.kmlgenerator.interfaces.KMLGenerator;
import lombok.extern.slf4j.Slf4j;


/**
 * This implementation of KMLGenerator uses the Commander and Facade design patterns
 */
@Slf4j
@Component
public class KMLGeneratorImp implements KMLGenerator {
    
    @Autowired
    private FactoryKMLData factoryKMLData;
    
    @Autowired
    private ExportKML exportKML;


    @Override
    public Path createFromFile(File file) {
        try {
            KMLData kmlData = factoryKMLData.createFromFile(file);
            KMLStructure kmlStructure = new KMLStructure(kmlData);
            Path savedLocation = exportKML.generateKML(kmlStructure);
            log.warn("SUCESSO: " + file.getName() + " salvo em: " + savedLocation);
            return savedLocation;
        } catch (InvalidKMLException | ParserConfigurationException | TransformerException e) {
            log.error("ERROR: " + e.getMessage());
            return null;
        } 
    }


    @Override
    public List<Path> createFromListOfFiles(List<File> files) {
        List<Path> outputs = new ArrayList<>();
        for (File file : files) {
            Path output = createFromFile(file);
            if (output != null) {
                outputs.add(output);
            }
        }
        return outputs;
    }


    @Override
    public List<Path> createFromDirectory(Path dir) {
        List<File> listOfFiles = Stream.of(dir.toFile().listFiles())
                                       .filter(file -> !file.isDirectory())
                                       .collect(Collectors.toList());
        return createFromListOfFiles(listOfFiles);
    }


    @Override
    public void setSavingDirectory(Path dir) throws IOException {
        this.exportKML.setSavingDirectory(dir);
    }

}
