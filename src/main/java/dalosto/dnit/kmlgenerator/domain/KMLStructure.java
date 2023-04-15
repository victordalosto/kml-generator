package dalosto.dnit.kmlgenerator.domain;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
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
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class KMLStructure {

    private String id = "redLine";
    private Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    private Element root = document.createElement("kml");
    private String name;


    public KMLStructure(KMLData kmlData) throws ParserConfigurationException {
        this.appendKMLHeader();
        this.appendName(kmlData.getName());
        this.appendStyle();
        this.appendCoordinates(kmlData.getCoordinates());
    }



    private Element appendKMLHeader() {
        Attr xmlnsAttribute = this.document.createAttribute("xmlns");
        xmlnsAttribute.setValue("http://earth.google.com/kml/2.2");
        this.root.setAttributeNode(xmlnsAttribute);
        this.document.appendChild(root);
        return root;
    }


    private void appendName(String name) {
        this.name = name;
        Element nameElement = document.createElement("name");
        nameElement.appendChild(document.createTextNode(name));
        root.appendChild(nameElement);
    }


    private void appendStyle() {
        Attr idAttribute = document.createAttribute("id");
        idAttribute.setValue(id);
        Element styleElement = document.createElement("Style");
        Element lineStyle = document.createElement("LineStyle");
        Element color = document.createElement("color");
        Element width = document.createElement("width");
        Element colorMode = document.createElement("colorMode");
        color.appendChild(document.createTextNode("ffb87e00"));
        width.appendChild(document.createTextNode("7"));
        colorMode.appendChild(document.createTextNode("normal"));
        lineStyle.appendChild(color);
        lineStyle.appendChild(width);
        lineStyle.appendChild(colorMode);
        styleElement.setAttributeNode(idAttribute);
        styleElement.appendChild(lineStyle);
        root.appendChild(styleElement);
    }


    private void appendCoordinates(List<Coordinates> coordenadas) {
        Element node = document.createElement("Placemark");
        Element eName = document.createElement("name");
        eName.appendChild(document.createTextNode(name));
        node.appendChild(eName);
        Element styleURL = document.createElement("styleUrl");
        styleURL.appendChild(document.createTextNode("#" + id));
        node.appendChild(styleURL);
        Element lineString = document.createElement("LineString");
        node.appendChild(lineString);
        Element extrude = document.createElement("extrude");
        extrude.appendChild(document.createTextNode("1"));
        lineString.appendChild(extrude);
        Element tessellate = document.createElement("tessellate");
        tessellate.appendChild(document.createTextNode("1"));
        lineString.appendChild(tessellate);
        Element coordinates = document.createElement("coordinates");
        StringBuilder stringCoordinates = new StringBuilder();
        for (int i = 0; i < coordenadas.size(); i++) {
            stringCoordinates.append(coordenadas.get(i).getX() + "," + coordenadas.get(i).getY() + " ");
        }
        coordinates.appendChild(document.createTextNode(stringCoordinates.toString()));
        lineString.appendChild(coordinates);
        root.appendChild(node);
    }


    public Path saveFileIn(File outPut) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(outPut);
        transformer.transform(domSource, streamResult);
        return outPut.toPath();
    }

}