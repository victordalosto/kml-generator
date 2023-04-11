package dalosto.dnit.kmlgenerator.service;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.springframework.stereotype.Component;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import dalosto.dnit.kmlgenerator.domain.Coordinates;
import dalosto.dnit.kmlgenerator.domain.KMLModel;
import dalosto.dnit.kmlgenerator.interfaces.ExportKML;


@Component
public class ExportKMLImp implements ExportKML {

    private String id = "redLine";
    private Path saveDirectory = Paths.get("output");


    public void setSavingDirectory(Path saveDirectory) {
        this.saveDirectory = saveDirectory;
    }


    public Path generate(KMLModel kmlModel) {
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = document.createElement("kml");
            appendKMLHeader(root, document);
            appendName(kmlModel.getFileName(), root, document);
            appendStyle(root, document);
            appendCoordinates(kmlModel.getFileName(), kmlModel.getCoordenadas(), root, document);
            return saveFile(kmlModel.getFileName(), document);
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("\nError while creating kml: " + kmlModel.getFileName());
            e.printStackTrace();
            return null;
        }
    }


    private Element appendKMLHeader(Element root, Document document) {
        Attr xmlnsAttribute = document.createAttribute("xmlns");
        xmlnsAttribute.setValue("http://earth.google.com/kml/2.2");
        root.setAttributeNode(xmlnsAttribute);
        document.appendChild(root);
        return root;
    }


    private void appendName(String name, Element root, Document document) {
        Element nameElement = document.createElement("name");
        nameElement.appendChild(document.createTextNode(name));
        root.appendChild(nameElement);
    }


    private void appendStyle(Element root, Document document) {
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


    private void appendCoordinates(String name, List<Coordinates> coordenadas, Element root, Document document) {
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


    private Path saveFile(String name, Document document) throws TransformerException {
        name = name.replaceAll("\\.[a-zA-Z0-9]+$", "") + ".kml";
        File outPut = Paths.get(saveDirectory.toString(), name).toFile();
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