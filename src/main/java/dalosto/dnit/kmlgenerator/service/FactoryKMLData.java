package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.Coordinates;
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.exception.InvalidKMLException;
import dalosto.dnit.kmlgenerator.interfaces.ReadCoordinates;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class FactoryKMLData {

    @Autowired
    ReadCoordinates readCoordinatesAdapter;


    public KMLData createFromFile(File file) throws InvalidKMLException {
        try {
            KMLData kmlData = createKMLData(file);
            return kmlData;
        } catch (FileNotFoundException e) {
            log.error(" Não foi possível encontrar o arquivo: " + file.getName());
            throw new InvalidKMLException(" Não foi possível encontrar o arquivo: " + file.getName());
        } catch (NumberFormatException e) {
            log.error(" O arquivo " + file.getName() + " não está no formato correto.");
            throw new InvalidKMLException(" O arquivo " + file.getName() + " não está no formato correto.");
        }
    }


    private KMLData createKMLData(File file) throws FileNotFoundException, NumberFormatException {
        KMLData kmlData = new KMLData();
        kmlData.setName(file.getName());
        kmlData.setCoordinates(getCoordinatesFromFile(file));
        return kmlData;
    }


    private List<Coordinates> getCoordinatesFromFile(File file) throws FileNotFoundException, NumberFormatException {
        return readCoordinatesAdapter.getFromFile(file);
    }

}