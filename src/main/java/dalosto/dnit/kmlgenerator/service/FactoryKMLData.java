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


@Component
public class FactoryKMLData {

    @Autowired
    ReadCoordinates readCoordinatesAdapter;


    public KMLData createFromFile(File file) throws InvalidKMLException {
        try {
            KMLData kmlData = createKMLData(file);
            return kmlData;
        } catch (FileNotFoundException e) {
            throw new InvalidKMLException(" Nao foi possivel encontrar o arquivo: " + file.getName());
        } catch (NumberFormatException e) {
            throw new InvalidKMLException(" O arquivo " + file.getName() + " nao esta no formato correto.");
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