package dalosto.dnit.kmlgenerator.domain;
import lombok.Getter;


@Getter
public class Coordinates {

    private double y;
    private double x; // In the Google Earth import, x is the second parameter


    public Coordinates(Double y, Double x) {
        this.y = y;
        this.x = x;
    }


    public Coordinates(String first, String second) {
        this.y = getDoubleFromString(first);
        this.x = getDoubleFromString(second);
    }


    private Double getDoubleFromString(String str) {
        str = clearContent(str);
        str = fixCientificFormat(str);
        return Double.parseDouble(str);
    }


    private String clearContent(String str) {
        if (str == null)
            throw new NumberFormatException("The coordinates is null.");
        return str.replaceAll("[\\sa-zA-Z]+", "").replaceAll("[,]+", ".");
    }


    // Accounts the fact that some number are in Cientific format and are not directly converted by java
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