package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.Coordinates;
import dalosto.dnit.kmlgenerator.domain.KMLModel;
import dalosto.dnit.kmlgenerator.interfaces.FactoryKMLModel;
import dalosto.dnit.kmlgenerator.interfaces.ReadLines;


@Component
public class FactoryKMLModelImp implements FactoryKMLModel {

    @Autowired
    ReadLines readLinesAdapter;


    public KMLModel createFromFile(File file) throws FileNotFoundException {
        KMLModel kmlModel = new KMLModel();
        kmlModel.setFileName(file.getName());
        kmlModel.setCoordenadas(getCoordenadasFromFile(file));
        return kmlModel;
    }


    private List<Coordinates> getCoordenadasFromFile(File file) throws FileNotFoundException {
        List<Coordinates> coordinates = new ArrayList<>();
        List<String> linesFromFile = readLinesAdapter.readLinesFromFile(file);
        for (String line : linesFromFile) {
            Coordinates coordenada = new Coordinates(line.split(";")[0], line.split(";")[1]);
            coordinates.add(coordenada);
        }
        return coordinates;
    }


    // /** Limits the the number of coordinates for the KML as maxCoordinateSize */
    // private List<Coordenadas> filterCoordinates(List<Coordenadas> listTemporary) {
    //     if (listTemporary.size() == 0) {
    //         System.out.println("Couldn't generate coordinates from input file");
    //     }
    //     List<Coordenadas> list = new ArrayList<>();
    //     int jump = Math.max(1, listTemporary.size() / Coordenadas.maxCoordinateSize);
    //     for (int i = 0; i < listTemporary.size() - 1; i += jump) {
    //         list.add(listTemporary.get(i));
    //     }
    //     list.add(listTemporary.get(listTemporary.size() - 1));
    //     return list;
    // }

}