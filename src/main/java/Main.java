
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author diegu
 */
public class Main {

    public static void crearCSV(HashMap mapa, String nombre) {
        FileWriter out = null;
        try {
            out = new FileWriter(nombre + "_histograma.csv");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try ( CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("Palabras:", "Resultado: "))) {
            mapa.forEach((palabra, palabras) -> {
                try {
                    printer.printRecord(palabra, palabras);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        // Leer archivo y mostrar el archivo.
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Qué nombre tiene el archivo?");
        String nombre = sc.nextLine();

        //Creo el archivo si no existe
        File f = new File(nombre);
        String archivo = "";

        try ( BufferedReader br = new BufferedReader(new FileReader(f))) {
            String s;
            while ((s = br.readLine()) != null) {
                archivo += s;
                System.out.println(s + "\n");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        String replace = archivo.replaceAll("[^a-zA-Z0-9á-úÁ-ÚñÑ ]", "");
        String[] palabras = replace.split(" ");
        HashMap<String, Integer> mapa = new HashMap<>();

        for (String palabra : palabras) {
            if (palabra.length() > 2) {
                mapa.merge(palabra, 1, Integer::sum);
            }
        }

        for (HashMap.Entry<String, Integer> entry : mapa.entrySet()) {
            System.out.printf("Palabra '%s' con frecuencia %d\n", entry.getKey(), entry.getValue());
        }
        crearCSV(mapa, nombre);
    }

}
