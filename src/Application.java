import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

public class Application {
    private static final BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
    private static final Queue<String> lastCommands = new LinkedList<>();

    public static void main(String[] args) throws IOException, ParseException {
        Set<StudyGroup> set;
        try {
            set = ReadCSV.readCSV("source.csv");
        } catch (CsvValidationException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return;
        }

        CommandManager commandManager = new CommandManager(set);

        String command;
        do {
            System.out.println("Введите комманду");
            command = stdinReader.readLine();

            if (command.equals("help")) {
                commandManager.help();
            } else if (command.equals("info")) {
                commandManager.info();
            } else if (command.equals("show")) {
                commandManager.show();
            } else if (command.startsWith("add")) {
                commandManager.add(readStudyGroupFromStdin());
            } else if (command.startsWith("update id")) {
                commandManager.updateId(readStudyGroupFromStdin());
            } else if (command.startsWith("remove_by_id")) {
                String idAsStr = command.substring("remove_by_id".length()).trim();
                long id;
                try {
                    id = Long.parseLong(idAsStr);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for remove_by_id: " + idAsStr + " is not a long");
                    continue;
                }
                commandManager.removeById(id);
            } else if (command.equals("clear")) {
                commandManager.clear();
            } else if (command.equals("save")) {
                commandManager.save();
            } else if (command.startsWith("execute_script")) {
                String fileName = command.substring("execute_script".length()).trim();
                System.out.println("todo: execute script wit file name " + fileName);
            } else if (command.startsWith("add_if_min ")) {
                commandManager.addIfMin(readStudyGroupFromStdin());
            } else if (command.startsWith("remove_lower")) {
                commandManager.removeLower(readStudyGroupFromStdin());
            } else if (command.equals("history")) {
                for (String lastCommand : lastCommands) {
                    System.out.println(lastCommand);
                }
            } else if (command.startsWith("remove_all_by_students_count ")) {
                String countAsStr = command.substring("remove_all_by_students_count ".length()).trim();
                long count;
                try {
                    count = Long.parseLong(countAsStr);
                    commandManager.removeAllByStudentsCount(count);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for remove_all_by_students_count: " + countAsStr + " is not long");
                }
            }

            lastCommands.add(command);
            if (lastCommands.size() > 10) {
                lastCommands.remove();
            }
        } while (!command.equals("exit"));

    }

    private static final DateTimeFormatter adminBirthdayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Random rng = new Random();
    private static StudyGroup readStudyGroupFromStdin() throws IOException{
        Long id = rng.nextLong();
        String name = readField("name", field -> !field.isEmpty());
        float x = readField("coordinate y", Float::parseFloat, field -> field <= 870);
        int y = readField("coordinate x", Integer::parseInt, field -> field > -318);
        Date creationDate = new Date(); // creation date is now
        int studentsCount = readField("students count", Integer::parseInt, field -> field > 0);
        FormOfEducation formOfEducation = readField("education", FormOfEducation::valueOf, Objects::nonNull);
        Semester semester = readField("semester", Semester::valueOf, Objects::nonNull);
        String adminName = readField("adminName", field -> !field.isEmpty());
        LocalDateTime adminBirthday = readField(
                "admin birthday",
                str -> LocalDateTime.parse(str, adminBirthdayFormatter),
                Objects::nonNull
        );

        String passportId = readField("passportID", field -> field.length() > 7);
        int locationX = readField("location x", Integer::parseInt, field -> true);// уточнить
        int locationY = readField("location y", Integer::parseInt, Objects::nonNull);//уточнить
        String locationName = readField("locationName", field -> !field.isEmpty());

        return new StudyGroup(
                id,
                name,
                new Coordinates(x,y),
                creationDate,
                studentsCount,
                formOfEducation,
                semester,
                new Person(adminName, adminBirthday, passportId, new Location(locationX, locationY, locationName))
        );
    }

    private static <T> T readField(String fieldName, Parser<T> parser, Predicate<T> isValid) throws IOException {
        T field;

        do {
            System.out.println("Please enter " + fieldName + ":");
            String fieldAsStr = stdinReader.readLine().trim();
            field = parser.parse(fieldAsStr);
        } while (!isValid.test(field));

        return field;
    }

    private static String readField(String fieldName, Predicate<String> isValid) throws IOException {
        return readField(fieldName, str -> str, isValid);
    }

}