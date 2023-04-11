package dalosto.dnit.kmlgenerator.domain;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KMLModel {

    private String fileName;
    private List<Coordinates> coordenadas;

}
