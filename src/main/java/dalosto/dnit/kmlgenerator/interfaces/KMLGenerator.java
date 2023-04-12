package dalosto.dnit.kmlgenerator.interfaces;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;


/**
 * Class that generates KMLs from files having coordinates in the following format:
 *  -3.123456;40.123456 
 *  -3.1234-4;40,1234e-4
 * All methods will output the Path where the kml file was generated */
public interface KMLGenerator {


    /** Create KML from a specific file */
    Path createFromFile(File file) throws FileNotFoundException;

    
    /** Create KML from a List of files */
    List<Path> createFromListOfFiles(List<File> listOfFiles) throws FileNotFoundException;


    /** Create KML from a directory containing files */
    List<Path> createFromDirectory(Path dir) throws FileNotFoundException;

    
    /** Changes the Directory that holds the files generated 
      * The default is the output directory       */
    void setSaveDirectory(Path root);


}
