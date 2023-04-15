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
import dalosto.dnit.kmlgenerator.domain.KMLData;
import dalosto.dnit.kmlgenerator.interfaces.ExportKML;
import lombok.Getter;
import lombok.Setter;


@Component
public class ExportKMLImp implements ExportKML {

    private Path savingDirectory = Paths.get("output");


    public void setSavingDirectory(Path saveDirectory) {
        this.savingDirectory = saveDirectory;
    }


    public Path generateKML(KMLData kmlData) {
        try {
            KML kml = new KML();
            kml.appendKMLHeader();
            kml.appendName(kmlData.getName());
            kml.appendStyle();
            kml.appendCoordinates(kmlData.getCoordinates());
            File futureFileLocation = setFutureFileLocation(savingDirectory, kmlData.getName());
            kml.saveFileIn(futureFileLocation);
            return futureFileLocation.toPath();
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("\nError while creating kml: " + kmlData.getName());
            e.printStackTrace();
            return null;
        }
    }


    private File setFutureFileLocation(Path saveDirectory, String name) {
        if (!saveDirectory.toFile().isDirectory()) {
            saveDirectory.toFile().mkdirs();
        }
        String fileName = name.replaceAll("\\.[a-zA-Z0-9]+$", "") + ".kml";
        return Paths.get(saveDirectory.toString(), fileName).toFile();
    }



    @Getter
    @Setter
    private class KML {

        private String id;
        private Document document;
        private Element root;
        private String name;


        public KML() throws ParserConfigurationException {
            this.id = "redLine";
            this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            this.root = document.createElement("kml");
        }


        public Element appendKMLHeader() {
            Attr xmlnsAttribute = this.document.createAttribute("xmlns");
            xmlnsAttribute.setValue("http://earth.google.com/kml/2.2");
            this.root.setAttributeNode(xmlnsAttribute);
            this.document.appendChild(root);
            return root;
        }


        public void appendName(String name) {
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

}