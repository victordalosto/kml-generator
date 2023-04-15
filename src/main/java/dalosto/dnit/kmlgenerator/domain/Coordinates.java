package dalosto.dnit.kmlgenerator.domain;
import lombok.Getter;


@Getter
public class Coordinates {

    private double y;
    private double x; // In the Google Earth, x is the second parameter

    
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
        int minusIndex = str.lastIndexOf("-");
        if (minusIndex > 0) {
            return str.substring(0, minusIndex) + "E-" + str.substring(minusIndex + 1, str.length());
        } 

        int maxIndex = str.lastIndexOf("+");
        if (maxIndex > 0) {
            return str.substring(0, maxIndex) + "E+" + str.substring(maxIndex + 1, str.length());
        } 
        
        return str;
    }

}