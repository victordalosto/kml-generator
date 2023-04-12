package dalosto.dnit.kmlgenerator.domain;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KMLData {

    private String name;
    private List<Coordinates> coordinates;

}
