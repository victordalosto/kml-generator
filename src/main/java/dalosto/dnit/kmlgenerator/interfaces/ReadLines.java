package dalosto.dnit.kmlgenerator.interfaces;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;


/** Adapter for a Scanner that reads a file line by line */
public interface ReadLines {


    /** Read lines from a file and store each line in a row of a List<String>*/
    List<String> readLinesFromFile(File file) throws FileNotFoundException;

}
