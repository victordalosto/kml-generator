package dalosto.dnit.kmlgenerator.interfaces;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


/**
 * Class that generates KMLs from files having coordinates in the format: 
 * -3.123456;40.123456 
 * -3.1234-4;40,1234e-4 
 * All methods will output the Path where the kml file was generated   */
public interface KMLGenerator {


    /** Create KML from a specific file */
    Path createFromFile(File file);


    /** Create KML from a List of files */
    List<Path> createFromListOfFiles(List<File> listOfFiles);


    /** Create KML from a directory containing files */
    List<Path> createFromDirectory(Path dir);


    /** Changes the root Directory that stores the saved files.
      * default is "./output/"                              */
    void setSavingDirectory(Path root) throws IOException;


}
