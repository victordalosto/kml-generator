package dalosto.dnit.kmlgenerator.interfaces;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import dalosto.dnit.kmlgenerator.domain.Coordinates;


/** Adapter that reads a file line by line */
public interface ReadCoordinates {


    /** Read lines from a file and store each line as a Coordinate in a List */
    List<Coordinates> getFromFile(File file) throws FileNotFoundException, NumberFormatException;

}
