package dalosto.dnit.kmlgenerator.domain;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KMLData {

    private String name;
    private List<Coordinates> coordinates;


    public void setName(String name) {
        int dotIndex = name.lastIndexOf('.');
        this.name = dotIndex == -1 ? name : name.substring(0, dotIndex);
    }

}
