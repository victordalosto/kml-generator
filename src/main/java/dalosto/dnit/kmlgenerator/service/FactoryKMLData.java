package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.Coordinates;
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.interfaces.ReadLines;


@Component
public class FactoryKMLData {

    @Autowired
    ReadLines readLinesAdapter;


    public KMLData createFromFile(File file) throws FileNotFoundException {
        KMLData kmlData = new KMLData();
        kmlData.setName(file.getName());
        kmlData.setCoordinates(getCoordinatesFromFile(file));
        return kmlData;
    }


    private List<Coordinates> getCoordinatesFromFile(File file) throws FileNotFoundException {
        List<Coordinates> coordinates = new ArrayList<>();
        List<String> linesFromFile = readLinesAdapter.readLinesFromFile(file);
        for (String line : linesFromFile) {
            Coordinates coordenada = new Coordinates(line.split(";")[0], line.split(";")[1]);
            coordinates.add(coordenada);
        }
        return coordinates;
    }


}