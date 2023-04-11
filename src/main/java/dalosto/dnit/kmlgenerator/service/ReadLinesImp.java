package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import dalosto.dnit.kmlgenerator.interfaces.ReadLines;


/** Adapter that reads the coordinates from a file. */
@Component
public class ReadLinesImp implements ReadLines {


    public List<String> readLinesFromFile(File file) throws FileNotFoundException {
        List<String> listSnvs = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                listSnvs.add(scanner.nextLine());
            }
        }
        return listSnvs;
    }

}
