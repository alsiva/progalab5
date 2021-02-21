import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

public class Application {
    public static void main(String[] args) throws IOException {
        // todo: read from args

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a file name: ");
        System.out.flush();
        String filename = scanner.nextLine();

        Set<StudyGroup> set;
        try {
            set = FileStorage.readCSV(filename);
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found. Error: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Error while reading from file: " + e.getMessage());
            return;
        } catch (CsvValidationException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return;
        }

        Administration administration = new Administration(set);

        BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
        new CommandReader(administration, stdinReader).readCommands();
    }
}