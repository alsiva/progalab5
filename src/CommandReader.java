import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * reads commands from user or script
 */
public class CommandReader {
    private final Administration administration;
    private final BufferedReader in;
    private final Queue<String> lastCommands = new LinkedList<>();

    public CommandReader(Administration administration, BufferedReader in) {
        this.administration = administration;
        this.in = in;
    }

    /**
     * @throws IOException
     * method that reads string commands from user or script
     */
    public void readCommands() throws IOException {
        while(true) {
            System.out.println("Please enter command:");
            String command = in.readLine();
            if (command == null) {
                break;
            }

            if (command.equals("help")) {
                System.out.println(HELP_CONTENTS);

            } else if (command.equals("info")) {
                administration.info();

            } else if (command.equals("show")) {
                administration.show();

            } else if (command.equals("add")) {
                administration.add(readStudyGroup());

            } else if (command.startsWith("update ")) {
                String idAsStr = command.substring("update ".length()).trim();
                long id;
                try {
                    id = Long.parseLong(idAsStr);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for update: " + idAsStr + " is not a long");
                    continue;
                }

                // is there an element in set with given id?
                boolean hasElement = administration.hasElementWithId(id);
                if (!hasElement) {
                    System.err.println("No element with id: " + id);
                    continue;
                }

                administration.updateId(readStudyGroup(id));

            } else if (command.startsWith("remove_by_id")) {
                String idAsStr = command.substring("remove_by_id".length()).trim();
                long id;
                try {
                    id = Long.parseLong(idAsStr);
                } catch (NumberFormatException e) {
                    System.err.println("Illegal argument for remove_by_id: " + idAsStr + " is not a long");
                    continue;
                }
                administration.removeById(id);

            } else if (command.equals("clear")) {
                administration.clear();

            } else if (command.startsWith("save ")) {
                String fileName = command.substring("save ".length()).trim();
                FileStorage.writeCsv(administration.getGroups(), fileName);

            } else if (command.startsWith("execute_script ")) {
                String fileName = command.substring("execute_script ".length()).trim();

                BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                new CommandReader(administration, fileReader).readCommands();

            } else if (command.equals("add_if_min")) {
                administration.addIfMin(readStudyGroup());

            } else if (command.equals("remove_lower")) {
                administration.removeLower(readStudyGroup());

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
                    continue;
                }
                administration.removeAllByStudentsCount(count);

            } else if (command.equals("count_by_group_admin")) {
                Person groupAdmin = readGroupAdmin();
                if (groupAdmin == null) {
                    System.err.println("Illegal argument for count_by_group_admin: admin should not be null");
                    continue;
                }
                int count = administration.countByGroupAdmin(groupAdmin);
                System.out.println("total elements with given group admin: " + count);

            } else if (command.startsWith("filter_less_than_semester_enum ")) {
                String semesterAsString = command.substring("filter_less_than_semester_enum ".length()).trim();

                Semester semester;
                try {
                    semester = Semester.valueOf(semesterAsString);
                } catch (IllegalArgumentException e) {
                    System.err.println("Illegal argument for filter_less_than_semester_enum: failed to parse semester");
                    continue;
                }
                administration.filterLessThanSemesterEnum(semester);

            } else if (command.equals("exit")) {
                break;

            } else {
                System.err.println("command " + command + " not recognized");
                continue;
            }

            lastCommands.add(command);
            if (lastCommands.size() > 10) {
                lastCommands.remove();
            }
        }
    }
    private static final Random rng = new Random();

    /**
     * returns study group with random id
     * @return study group with random id
     * @throws IOException
     */
    private StudyGroup readStudyGroup() throws IOException {
        long id = rng.nextLong(); // generate random id
        return readStudyGroup(id);
    }

    /**
     * returns study group with defined id
     * @param id
     * @return study group with defined id
     * @throws IOException
     */
    private StudyGroup readStudyGroup(long id) throws IOException {
        System.out.println("Please enter name");
        String name = readUntilSuccess(field -> {
            if (field.isEmpty()) {
                throw new FailedToParseException("name could not be empty");
            }
            return field;
        });

        Coordinates coordinates = readCoordinates();

        Date creationDate = new Date(); // creation date is now

        System.out.println("Please enter students count");
        int studentsCount = readUntilSuccess(fieldAsString -> {
            int value;
            try {
                value = Integer.parseInt(fieldAsString);
            } catch (NumberFormatException e) {
                throw new FailedToParseException("Failed to read student count: " + e.getMessage());
            }

            if (value <= 0) {
                throw new FailedToParseException("Students count should be greater than 0");
            }

            return value;
        });

        List<String> forms = new ArrayList<>();
        for (FormOfEducation value : FormOfEducation.values()) {
            forms.add(value.toString());
        }

        System.out.println("Please enter form of education (" + String.join(", ", forms) + "), leave empty to skip");
        FormOfEducation formOfEducation = readUntilSuccess(fieldAsString -> {
            FormOfEducation value;
            try {
                value = FormOfEducation.valueOf(fieldAsString);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read form of education: " + e.getMessage());
            }
            return value;
        });

        List<String> semesters = new ArrayList<>();
        for (Semester semester : Semester.values()) {
            semesters.add(semester.toString());
        }
        System.out.println("Please enter semester (" + String.join(", ", semesters) + "), leave empty to skip");
        Semester semester = readUntilSuccess(fieldAsString -> {
            Semester value;
            try {
                value = Semester.valueOf(fieldAsString);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read semester: " + e.getMessage());
            }
            return value;
        });

        Person groupAdmin = readGroupAdmin();

        return new StudyGroup(
                id,
                name,
                coordinates,
                creationDate,
                studentsCount,
                formOfEducation,
                semester,
                groupAdmin
        );
    }

    /**
     * reads coordinates from user
     * @return coordinates
     * @throws IOException
     */
    private Coordinates readCoordinates() throws IOException {
        System.out.println("Please enter coordinate x");
        float x = readUntilSuccess(fieldAsString -> {
            float value;
            try {
                value = Float.parseFloat(fieldAsString);
            } catch (NumberFormatException e) {
                throw new FailedToParseException("Failed to read x: " + e.getMessage());
            }

            if (value <= -318) {
                throw new FailedToParseException("x should be greater than 318");
            }

            return value;
        });

        System.out.println("Please enter coordinate y");
        int y = readUntilSuccess(fieldAsString -> {
            int value;
            try {
                value = Integer.parseInt(fieldAsString);
            } catch (NumberFormatException e) {
                throw new FailedToParseException("Failed to read y: " + e.getMessage());
            }

            if (value > 870) {
                throw new FailedToParseException("y should be less than 870");
            }

            return value;
        });

        return new Coordinates(x, y);
    }

    public static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * reads group admin from user
     * @return group admin
     * @throws IOException
     */
    private Person readGroupAdmin() throws IOException {
        System.out.println("Please enter admin name, leave empty to skip");
        String adminName = readUntilSuccess();
        if (adminName.isEmpty()) {
            return null;
        }

        System.out.println("Please enter admin birthday");
        LocalDate adminBirthday = readUntilSuccess(fieldAsString -> {
            LocalDate value;
            try {
                value = LocalDate.parse(fieldAsString, BIRTHDAY_FORMATTER);
            } catch (DateTimeParseException e) {
                throw new FailedToParseException("Failed to read admin birthday: " + e.getMessage());
            }
            return value;
        });

        System.out.println("Please enter passport id");
        String passportId = readUntilSuccess(fieldAsString -> {
            String value;
            try {
                value = fieldAsString;
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read passport id: " + e.getMessage());
            }

            if (value.length() < 7) {
                throw new FailedToParseException("Passport id length should be greater than 7");
            }

            return value;
        });

        Location location = readLocation();

        return new Person(adminName, adminBirthday, passportId, location);
    }

    /**
     * reads location from user
     * @return location
     * @throws IOException
     */
    private Location readLocation() throws IOException {
        System.out.println("Please enter location x, leave empty to skip");
        Integer locationX = readUntilSuccess(fieldAsString -> {
            if (fieldAsString.isEmpty()) {
                return null;
            }

            int value;
            try {
                value = Integer.parseInt(fieldAsString);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read location x: " + e.getMessage());
            }
            return value;
        });

        if (locationX == null) {
            return null;
        }

        System.out.println("Please enter location y");
        int locationY = readUntilSuccess(fieldAsString -> {
            int value;
            try {
                value = Integer.parseInt(fieldAsString);
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read location y: " + e.getMessage());
            }
            return value;
        });

        System.out.println("Please enter location name");
        String locationName = readUntilSuccess(fieldAsString -> {
            String value;
            try {
                value = fieldAsString;
            } catch (IllegalArgumentException e) {
                throw new FailedToParseException("Failed to read location name: " + e.getMessage());
            }
            return value;
        });

        return new Location(locationX, locationY, locationName);
    }

    private <T> T readUntilSuccess(Parser<T> parser) throws IOException {
        while (true) {
            try {
                String field = in.readLine().trim();
                return parser.parse(field);
            } catch (FailedToParseException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private String readUntilSuccess() throws IOException {
        return readUntilSuccess(str -> str);
    }

    private static final String HELP_CONTENTS = "" +
            "help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add {element} : добавить новый элемент в коллекцию\n" +
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_by_id id : удалить элемент из коллекции по его id\n" +
            "clear : очистить коллекцию\n" +
            "save : сохранить коллекцию в файл\n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
            "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный\n" +
            "history : вывести последние 10 команд (без их аргументов)\n" +
            "remove_all_by_students_count studentsCount : удалить из коллекции все элементы, значение поля studentsCount которого эквивалентно заданному\n" +
            "count_by_group_admin groupAdmin : вывести количество элементов, значение поля groupAdmin которых равно заданному\n" +
            "filter_less_than_semester_enum semesterEnum : вывести элементы, значение поля semesterEnum которых меньше заданного";
}
