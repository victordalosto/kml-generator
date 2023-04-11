package dalosto.dnit.kmlgenerator.interfaces;
import java.io.File;
import java.io.FileNotFoundException;
import dalosto.dnit.kmlgenerator.domain.KMLModel;


/** Create a KMLModel that is used to handles KML data for Exportation */
public interface FactoryKMLModel {


    /** Create a KMLModel from a File containing coordinates
     *  Files must be presented in the following format:
     *  -3.123456;40.123456
     *  -3.1234-4;40.1234e-4                                 */
    KMLModel createFromFile(File path) throws FileNotFoundException;


}
