package dalosto.dnit.kmlgenerator.domain;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KMLData {

    private String name;
    private List<Coordinates> coordinates;


    public void setName(String name) {
        int dotIndex = name.lastIndexOf('.');
        String nameWithoutFileFormat = dotIndex == -1 ? name : name.substring(0, dotIndex);
        this.name = nameWithoutFileFormat;
    }

}
