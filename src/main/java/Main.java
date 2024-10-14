import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce la ruta del archivo CSV (ej. data/estudiantes.csv): ");
        String rutaCSV = scanner.nextLine();

        System.out.print("Introduce la ruta del archivo XML a generar (ej. data/estudiantes.xml): ");
        String rutaXML = scanner.nextLine();

        ConversorCSVaXML conversor = new ConversorCSVaXML(rutaCSV, rutaXML);
        conversor.convertir();

        scanner.close();
    }
}