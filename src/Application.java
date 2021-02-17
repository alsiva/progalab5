import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

public class Application {
    private static final BufferedReader stdinReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        Set<StudyGroup> set = new LinkedHashSet<>();

        // read original data from source file


        CommandManager commandManager = new CommandManager(set);

        String command;
        do {
            System.out.println("Введите комманду");
            command = stdinReader.readLine();

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
                StudyGroup newGroup = readStudyGroupFromStdin();
                commandManager.addIfMin(newGroup);

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

    private static StudyGroup readStudyGroupFromStdin() throws IOException, ParseException {
        StudyGroup studyGroup = new StudyGroup();

        System.out.println("Please enter id:");
        String id = stdinReader.readLine();
        System.out.println("Please enter name:");
        String name = stdinReader.readLine();
        System.out.println("Please enter coordinate x:");
        String x = stdinReader.readLine();
        System.out.println("Please enter coordinate y:");
        String y = stdinReader.readLine();
        System.out.println("Please enter creation date:");
        String creationDate = stdinReader.readLine();
        System.out.println("Please enter students amount:");
        String studentsCount = stdinReader.readLine();
        System.out.println("Please enter form of education:");
        String formOfEducation = stdinReader.readLine();
        System.out.println("Please enter semester:");
        String semesterEnum = stdinReader.readLine();
        System.out.println("Please enter admin name:");
        String adminName = stdinReader.readLine();
        System.out.println("Please enter admin birthday:");
        String adminBirthday = stdinReader.readLine();
        System.out.println("Please enter admin passport ID");
        String passportId = stdinReader.readLine();
        System.out.println("Please enter admin coordinate x");
        String locationX = stdinReader.readLine();
        System.out.println("Please enter admin coordinate y");
        String locationY = stdinReader.readLine();
        System.out.println("Please enter admin location name");
        String locationName = stdinReader.readLine();

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
}
