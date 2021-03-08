import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.*;

/**
 * Main application class
 * @author Alex Ivanov
 */
public class Application {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Please specify input file name as program argument");
            return;
        }

        String filename = args[0];

        Set<StudyGroup> set;
        try {
            set = FileStorage.readCSV(filename);
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found (" + e.getMessage() + ")");
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