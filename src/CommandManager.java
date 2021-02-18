import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Set;

public class CommandManager {
    private final Set<StudyGroup> set;
    private final Instant creationDate = Instant.now();

    public CommandManager(Set<StudyGroup> set) {
        this.set = set;
    }

    public void add(StudyGroup studyGroup) {
        set.add(studyGroup);
    }

    public void info() {
        System.out.println("Collection type: " + set.getClass().toString());
        System.out.println("Collection creation time: " + creationDate.toString());
        System.out.println("Elements in collection: " + set.size());
    }

    public void show() {
        for (StudyGroup studyGroup : set) {
            System.out.println(studyGroup.toString());
        }
    }

    public void help() {
        System.out.println(HELP_CONTENTS);
    }

    public void updateId(StudyGroup other) {
        for (StudyGroup studyGroup: set) {
            if (studyGroup.getId().equals(other.getId())) {
                studyGroup = other;
            }
        }
    }

    public void  removeById(Long id) {
        set.removeIf(studyGroup -> studyGroup.getId().equals(id));
    }

    public void clear() {
        set.clear();
    }

    public void save() {
        try {
            CSVWriter writer = new CSVWriter(new PrintWriter("students.csv"));

            for (StudyGroup studyGroup: set) {
                String[] line = new String[15];

                line[0] = studyGroup.getId().toString();
                line[1] = studyGroup.getName();

                Coordinates coordinates = studyGroup.getCoordinates();
                float x = coordinates.getX();
                int y = coordinates.getY();
                line[2] = Float.toString(x);
                line[3] = Integer.toString(y);

                java.util.Date creationDate = studyGroup.getCreationDate();
                String creationDateAsStr = creationDate.toString();
                line[4] = creationDateAsStr;

                Integer studentsCount = studyGroup.getStudentsCount();
                line[5] = studentsCount.toString();

                FormOfEducation formOfEducation = studyGroup.getFormOfEducation();
                line[6] = formOfEducation.toString();

                Semester semester = studyGroup.getSemesterEnum();
                line[7] = semester.toString();

                Person admin = studyGroup.getGroupAdmin();
                String adminName = admin.getName();
                line[8] = adminName;

                java.time.LocalDateTime birthday = admin.getBirthday();
                line[9] = birthday.toString();

                String passportID = admin.getPassportID();
                line[10] = passportID;

                Location location = admin.getLocation();
                line[11] = location.toString();

                Integer xL = location.getX();
                line[12] = xL.toString();

                Integer yL = location.getY();
                line[13] = yL.toString();

                String locName = location.getLocationName();
                line[14] = locName;

                writer.writeNext(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Unable to save file");
        }

    }

    public void executeScript() {
        //TODO executeScript решил отложить
    }

    public void addIfMin(StudyGroup other) {
        StudyGroup min = null;
        for (StudyGroup studyGroup: set) {
            if (min == null || min.getStudentsCount() < studyGroup.getStudentsCount()) {
                min = studyGroup;
            }
        }
        if (min.getStudentsCount() > other.getStudentsCount()) {
            set.add(other);
        }
    }

    public void removeLower(StudyGroup other) {
        for (StudyGroup studyGroup: set) {
            if (other.getStudentsCount() > studyGroup.getStudentsCount()) {
                set.remove(studyGroup);
            }
        }
    }

    public void removeAllByStudentsCount(Long count) {
        set.removeIf(studyGroup -> studyGroup.getStudentsCount().equals(count));
    }

    public Integer countByGroupAdmin(Person groupAdmin) {
        int count = 0;
        for (StudyGroup studyGroup: set) {
            if (studyGroup.getGroupAdmin().equals(groupAdmin)) {
                count += 1;
            }
        }
        return count;
    }

    public void filterLessThanSemesterEnum(Semester semester) {
        for (StudyGroup studyGroup: set) {
            if (studyGroup.getSemesterEnum().ordinal() < semester.ordinal()) {
                System.out.println(studyGroup);
            }
        }
    }

    private static String HELP_CONTENTS = "" +
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
