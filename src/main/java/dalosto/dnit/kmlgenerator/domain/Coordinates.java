package dalosto.dnit.kmlgenerator.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class Coordinates {

    private double x;
    private double y;


    public Coordinates(String first, String second) {
        this.y = getDoubleFromString(first);
        this.x = getDoubleFromString(second); // Coordinates are inverted in the Google Earth import, so x is the second
    }


    private Double getDoubleFromString(String str) {
        str = clearContent(str);
        str = fixCientificFormat(str);
        return Double.parseDouble(str);
    }


    private String clearContent(String str) {
        return str.replaceAll("[\\sa-zA-Z]+", "").replaceAll("[,]+", ".");
    }


    // Accounts the fact that some number are in Cientific format as in (-1.234-5)
    private String fixCientificFormat(String str) {
        String newStr;
        int lastHyphenIndex = str.lastIndexOf("-");
        if (!str.contains("E") && lastHyphenIndex > 0) {
            newStr = str.substring(0, lastHyphenIndex) + "E-" + str.substring(lastHyphenIndex + 1, str.length());
        } else {
            newStr = str;
        }
        return newStr;
    }

}