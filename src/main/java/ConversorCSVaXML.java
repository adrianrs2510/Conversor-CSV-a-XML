import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConversorCSVaXML {
    private static final Logger logger = LoggerFactory.getLogger(ConversorCSVaXML.class);

    private final String rutaCSV;
    private final String rutaXML;

    public ConversorCSVaXML(String rutaCSV, String rutaXML) {
        this.rutaCSV = rutaCSV;
        this.rutaXML = rutaXML;
    }

    public void convertir() {
        try (CSVReader reader = new CSVReader(new FileReader(rutaCSV))) {
            String[] encabezados = reader.readNext(); // Leer encabezados

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("Estudiantes");
            doc.appendChild(raiz);

            String[] linea;
            while ((linea = reader.readNext()) != null) {
                Element estudiante = doc.createElement("Estudiante");

                for (int i = 0; i < encabezados.length; i++) {
                    Element elemento = doc.createElement(encabezados[i]);
                    elemento.appendChild(doc.createTextNode(linea[i]));
                    estudiante.appendChild(elemento);
                }

                raiz.appendChild(estudiante);
            }

            // Escribir documento XML
            try (FileWriter writer = new FileWriter(rutaXML)) {
                javax.xml.transform.TransformerFactory.newInstance().newTransformer()
                        .transform(new javax.xml.transform.dom.DOMSource(doc),
                                new javax.xml.transform.stream.StreamResult(writer));
                logger.info("Archivo XML generado correctamente en: {}", rutaXML);
            }
        } catch (Exception e) {
            logger.error("Error durante la conversión: {}", e.getMessage());
        }
    }
}