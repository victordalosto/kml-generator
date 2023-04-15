package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.domain.Coordinates;
import dalosto.dnit.kmlgenerator.interfaces.ReadCoordinates;


/** Adapter that reads the coordinates from a file. */
@Component
public class ReadLinesImp implements ReadCoordinates {

    public List<Coordinates> readCoordinatesFromFile(File file) throws FileNotFoundException, NumberFormatException {
        List<Coordinates> coordinates = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                Coordinates coordinate = convertStringToCoordinates(scanner.nextLine());
                coordinates.add(coordinate);
            }
        }
        if (coordinates.size() == 0) {
            throw new NumberFormatException("The file is empty.");
        }
        return coordinates;
    }


    public Coordinates convertStringToCoordinates(String text) throws NumberFormatException {
        if (!text.contains(";")) {
            throw new NumberFormatException("The coordinates are not in the correct format.");
        }
        String args[] = text.split(";");
        Coordinates coordinates = new Coordinates(args[0], args[1]);
        return coordinates;
    }

}
