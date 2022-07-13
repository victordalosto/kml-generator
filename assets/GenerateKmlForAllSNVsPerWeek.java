package assets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateKmlForAllSNVsPerWeek {

    public static String header = "";
    public static String line = "";
    public static String body = "";
    public static String footing = "</Folder></Document></kml>";
    public static List<String> semana = new ArrayList<>(29);
    public static Map<String, String> trechos = new HashMap<>();


    // public static void main(String[] args) throws FileNotFoundException {
    //     executeRoutine();
    // }


    private synchronized static void executeRoutine() {
        try {
            getHeader();
            getSNVs();

            String allKMLPath = "assets\\SNV_202204A";
            try (BufferedReader reader = new BufferedReader(new FileReader(allKMLPath, StandardCharsets.UTF_8))) {
                StringBuffer fullText = new StringBuffer();
                while ((line = reader.readLine()) != null)
                    fullText.append(line);
                line = fullText.toString();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            line = line.replaceAll("í", "i").replaceAll("é", "e").replaceAll("ê", "e").replaceAll("ô", "o");

            createMarker();
            saveKML(body, "testaKML");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    private synchronized  static void getHeader() {
        try (BufferedReader reader = new BufferedReader(new FileReader("assets\\Header", StandardCharsets.UTF_8))) {
            StringBuffer fullText = new StringBuffer();
            while ((header = reader.readLine()) != null)
                fullText.append(header);
            header = fullText.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    
    private synchronized  static void getSNVs() throws FileNotFoundException {
        String caminhoTrechos = "assets\\trechos.csv";
        try (Scanner scanner = new Scanner(new File(caminhoTrechos))) {
            while(scanner.hasNext()) {
                String [] snv = scanner.nextLine().split(";");
                trechos.put(snv[0], snv[1]);
            }
        }
    }


    private synchronized  static void createMarker() {
        for (int i=1; i<=28; i++) {
            semana.add("<Folder><name>Semana: " + i + "</name><open>1</open>");
        }

        trechos.forEach((SNV, mes_data) -> {
            matchTXT(SNV, mes_data);
        });
    }


    private synchronized  static void matchTXT(String SNV, String mes_data) {
        Pattern pattern = Pattern.compile("<Placemark>[\\s]+<name>"+SNV+"(.*?)</Placemark>");
        Matcher matcher = pattern.matcher(line);
        String [] data = mes_data.split("_");
        if (matcher.find()) {
            String snvBody = matcher.group(0);
            snvBody = snvBody.replaceAll("CHANGESEMANA", data[0]);
            snvBody = snvBody.replaceAll("CHANGEDATA", data[1]);
            snvBody = snvBody.replaceAll("<styleUrl>#style0</styleUrl>", "<styleUrl>#style"+data[0]+"</styleUrl>");
            semana.set(Integer.parseInt(data[0])-1, semana.get(Integer.parseInt(data[0])-1) + snvBody);
        } else {
            System.out.println("Não encontrado: " + SNV);
        }
    }





    public synchronized  static void saveKML(String body, String path) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(path + ".kml")) {
            semana.forEach(s -> {
                header += s + "</Folder>";
            });
            out.println(header + footing);
        }
    }
    
}
