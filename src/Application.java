import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class Application {
    public static void main(String[] args) throws IOException {
        Set<StudyGroup> set = new LinkedHashSet<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        String command;
        do {
            System.out.println("Введите комманду");
            command = reader.readLine();

            if (command.equals("help")) {
                throw new NotImplementedException();
            } else if (command.equals("info")) {
                throw new NotImplementedException();
            } else if (command.equals("show")) {
                throw new NotImplementedException();
            } else if (command.startsWith("add")) {
                throw new NotImplementedException();
            } else if (command.startsWith("update id")) {
                String idAsStr = command.substring("update id".length()).trim();
                long id;
                try {
                    id = Long.parseLong(idAsStr);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for update id: " + idAsStr + " is not a long");
                    continue;
                }

                System.out.println("todo: update id " + id);
            } else if (command.startsWith("remove_by_id")) {
                String idAsStr = command.substring("remove_by_id".length()).trim();
                long id;
                try {
                    id = Long.parseLong(idAsStr);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for remove_by_id: " + idAsStr + " is not a long");
                    continue;
                }
                System.out.println("todo: remove_by_id " + id);
            } else if (command.equals("clear")) {
                throw new NotImplementedException();
            } else if (command.equals("save")) {
                throw new NotImplementedException();
            } else if (command.startsWith("execute_script")) {
                String fileName = command.substring("execute_script".length()).trim();
                System.out.println("todo: execute script wit file name " + fileName);
            } else if (command.startsWith("add_if_min ")) {
                throw new NotImplementedException();
            } else if (command.startsWith("remove_lower")) {
                throw new NotImplementedException();
            } else if (command.equals("history")) {
                throw new NotImplementedException();
            } else if (command.startsWith("remove_all_by_students_count ")) {
                String countAsStr = command.substring("remove_all_by_students_count ".length()).trim();
                long count;
                try {
                    count = Long.parseLong(countAsStr);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for remove_all_by_students_count: " + countAsStr + " is not long");
                }
            }
        } while (!command.equals("exit"));

    }
}
