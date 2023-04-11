package dalosto.dnit.kmlgenerator.interfaces;
import java.nio.file.Path;
import dalosto.dnit.kmlgenerator.domain.KMLModel;


/** Class that Export a KMLModel into a real KML in the root Directory */
public interface ExportKML {
    

    /** Set the saving directory for the kml generated 
     *  Default is the output directory */
    void setSavingDirectory(Path root);


    /** Generate a KML file using the previously DNIT pattern  */
    Path generate(KMLModel kmlModel);
    
}
