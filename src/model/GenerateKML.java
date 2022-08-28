package src.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerateKML {

    private static final int maxCoordinateSize = 2500;



    /** Generate a KML given the nameKML and the .CSV path with coordinates */
    public static void create(String nameKML, String path) {
        List<Coordinates> listCoord = getCoordinatesFromCSV(nameKML, path);
        initialize(nameKML, listCoord, path);
    }




    /** Generates a List of coordinates from a CSV file */
    private static List<Coordinates> getCoordinatesFromCSV(String nameKML, String path) {
        List<Coordinates> listTemporary = new ArrayList<>(maxCoordinateSize + 1);
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                line = line.replaceAll("[\\sa-zA-Z]+", "").replaceAll("[,]+", ".");
                String[] array = line.split(";");
                if (array.length != 2)
                    throw new RuntimeException("Wrong format for the input file");
                try {

                    Coordinates coordenada = new Coordinates(Double.parseDouble(array[0]), Double.parseDouble(array[1]));
                    listTemporary.add(coordenada);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Couldn't convert to number the values in input file");
            }}
            return filterCoordinates(listTemporary);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Couldn't find input file");
        }
    }




    /** Limits the the number of coordinates for the KML as maxCoordinateSize  */
    private static List<Coordinates> filterCoordinates(List<Coordinates> listTemporary) {
        if (listTemporary.size() == 0)
            throw new RuntimeException("Couldn't generate coordinates from input file");
        List<Coordinates> list = new ArrayList<>();
        int jump = Math.max(1, listTemporary.size() / maxCoordinateSize);
        for (int i = 0; i < listTemporary.size() - 1; i += jump)
            list.add(listTemporary.get(i));
        list.add(listTemporary.get(listTemporary.size() - 1));
        return list;
    }




    /** Generate a KML file using the previously DNIT pattern */
    private static void initialize(String nameKML, List<Coordinates> listCoord, String inputPath) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // KML root element
            Element root = document.createElement("kml");
            Attr attr = document.createAttribute("xmlns");
            attr.setValue("http://earth.google.com/kml/2.2");
            root.setAttributeNode(attr);
            document.appendChild(root);

            // Document element
            Element elementDocument = document.createElement("Document");
            root.appendChild(elementDocument);

            // Name element
            Element nameE = document.createElement("name");
            nameE.appendChild(document.createTextNode(nameKML));
            elementDocument.appendChild(nameE);

            // Style element
            Element styleElement = document.createElement("Style");
            Attr attr2 = document.createAttribute("id");
            attr2.setValue("redLine");
            styleElement.setAttributeNode(attr2);
            Element lineStyle = document.createElement("LineStyle");
            Element color = document.createElement("color");
            color.appendChild(document.createTextNode("ffb87e00"));
            Element width = document.createElement("width");
            width.appendChild(document.createTextNode("7"));
            Element colorMode = document.createElement("colorMode");
            colorMode.appendChild(document.createTextNode("normal"));
            lineStyle.appendChild(color);
            lineStyle.appendChild(width);
            lineStyle.appendChild(colorMode);
            styleElement.appendChild(lineStyle);
            elementDocument.appendChild(styleElement);

            // PlaceMark line
            Element linePlaceMark = document.createElement("Placemark");
            Element nameE2 = document.createElement("name");
            nameE2.appendChild(document.createTextNode(nameKML));
            Element styleURL = document.createElement("styleUrl");
            styleURL.appendChild(document.createTextNode("#redLine"));
            Element lineString = document.createElement("LineString");
            Element extrude = document.createElement("extrude");
            extrude.appendChild(document.createTextNode("1"));
            Element tessellate = document.createElement("tessellate");
            tessellate.appendChild(document.createTextNode("1"));
            Element coordinatesTag = document.createElement("coordinates");
            String stringCoordinates = "";
            for (int i = 0; i < listCoord.size(); i++)
                stringCoordinates += listCoord.get(i).getX() + "," + listCoord.get(i).getY() + " ";
            coordinatesTag.appendChild(document.createTextNode(stringCoordinates));
            lineString.appendChild(extrude);
            lineString.appendChild(tessellate);
            lineString.appendChild(coordinatesTag);
            linePlaceMark.appendChild(nameE2);
            linePlaceMark.appendChild(styleURL);
            linePlaceMark.appendChild(lineString);
            elementDocument.appendChild(linePlaceMark);

            // PlaceMark Inicio
            Element inicioPlaceMark = document.createElement("Placemark");
            Element nameE3 = document.createElement("name");
            nameE3.appendChild(document.createTextNode("Inicio"));
            Element descriptionInicio = document.createElement("description");
            descriptionInicio.appendChild(document.createTextNode("Inicio"));
            Element pointInicio = document.createElement("Point");
            Element coordinatesInicio = document.createElement("coordinates");
            String stringInicio = listCoord.get(0).getX() + ", " + listCoord.get(0).getY() + ", 100.";
            coordinatesInicio.appendChild(document.createTextNode(stringInicio));
            Element altitudeModeInicio = document.createElement("altitudeMode");
            altitudeModeInicio.appendChild(document.createTextNode("relativeToGround"));
            Element extrudeInicio = document.createElement("extrude");
            extrudeInicio.appendChild(document.createTextNode("1"));
            pointInicio.appendChild(coordinatesInicio);
            pointInicio.appendChild(altitudeModeInicio);
            pointInicio.appendChild(extrudeInicio);
            inicioPlaceMark.appendChild(nameE3);
            inicioPlaceMark.appendChild(descriptionInicio);
            inicioPlaceMark.appendChild(pointInicio);
            elementDocument.appendChild(inicioPlaceMark);

            // PlaceMark Fim
            Element fimPlaceMark = document.createElement("Placemark");
            Element nameE4 = document.createElement("name");
            nameE4.appendChild(document.createTextNode("Fim"));
            Element descriptionFim = document.createElement("description");
            descriptionFim.appendChild(document.createTextNode("Fim"));
            Element pointFim = document.createElement("Point");
            Element coordinatesFim = document.createElement("coordinates");
            int lastValue = listCoord.size() - 1;
            String stringFim = listCoord.get(lastValue).getX() + ", " + listCoord.get(lastValue).getY() + ", 100.";
            coordinatesFim.appendChild(document.createTextNode(stringFim));
            Element altitudeModeFim = document.createElement("altitudeMode");
            altitudeModeFim.appendChild(document.createTextNode("relativeToGround"));
            Element extrudeFim = document.createElement("extrude");
            extrudeFim.appendChild(document.createTextNode("1"));
            pointFim.appendChild(coordinatesFim);
            pointFim.appendChild(altitudeModeFim);
            pointFim.appendChild(extrudeFim);
            fimPlaceMark.appendChild(nameE4);
            fimPlaceMark.appendChild(descriptionFim);
            fimPlaceMark.appendChild(pointFim);
            elementDocument.appendChild(fimPlaceMark);

            // transform the DOM Object to an XML File
            File file = new File(inputPath);
            if (nameKML == null || nameKML.isEmpty())
                nameKML = file.getName();
            nameKML = nameKML.substring(0, nameKML.lastIndexOf('.')) + ".kml";
            String kmlPath = Paths.get(file.getParent(), nameKML).toString();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(kmlPath));
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
            throw new RuntimeException("Error while creating kml");
        }
    }

}