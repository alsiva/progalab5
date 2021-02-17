import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Application {
    private static final BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));
    private static final Queue<String> lastCommands = new LinkedList<>();

    public static void main(String[] args) throws IOException, ParseException {
        Set<StudyGroup> set = new LinkedHashSet<>();

        // read original data from source file

        set.stream().filter(studyGroup -> studyGroup.getStudentsCount() > 8).collect(Collectors.toList())

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

    private static boolean isNameValid(String name) {
        return !name.isEmpty();
    }

    private static StudyGroup readStudyGroupFromStdin() throws IOException, ParseException {
        StudyGroup studyGroup = new StudyGroup();

        String id = UUID.randomUUID().toString();

        String name = readField("name", field -> !field.isEmpty());

        int x = readField("coordinate x", Integer::parseInt, field -> field > -318);
        float y = readField("coordinate y", Float::parseFloat, field -> field <= 870);
        Date creationDate = new Date(); // creation date is now
        int studentsCount = readField("students count", Integer::parseInt, field -> field > 0);

        FormOfEducation formOfEducation = readField("education", FormOfEducation::valueOf, Objects::nonNull);
        System.out.println("Please enter semester:");
        String semesterEnum = stdinReader.readLine();

        String adminName = readField("adminName", field -> !field.isEmpty());

        System.out.println("Please enter admin birthday:");
        String adminBirthday = stdinReader.readLine();

        String passportId = readField("passportID", field -> field.length() > 7);
        System.out.println("Please enter admin coordinate x");
        int locationX = readField("location x", Integer::parseInt, field -> true);// уточнить
        System.out.println("Please enter admin coordinate y");
        int locationY = readField("location y", Integer::parseInt, Objects::nonNull);//уточнить

        String locationName = readField("locationName", field -> !field.isEmpty());

//        return new StudyGroup(
//                id,
//                name,
//        )
        studyGroup.setId(Long.parseLong(id));
        studyGroup.setName(name);
        studyGroup.setCoordinates(Float.parseFloat(x), Integer.parseInt(y));
        studyGroup.setCreationDate(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(creationDate));
        studyGroup.setStudentsCount(Integer.parseInt(studentsCount));
        studyGroup.setFormOfEducation(FormOfEducation.valueOf(formOfEducation));
        studyGroup.setSemesterEnum(Semester.valueOf(semesterEnum));
        studyGroup.setGroupAdmin(adminName, LocalDateTime.parse(adminBirthday,DateTimeFormatter.ISO_DATE_TIME), passportId, new Location(Integer.parseInt(locationX), Integer.parseInt(locationY), locationName));

        return studyGroup;
    }

    private static String readName() throws IOException {
        String name;



        do {
            System.out.println("Please enter name:");
            name = stdinReader.readLine().trim();
        } while (name.isEmpty());


        return name;
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
