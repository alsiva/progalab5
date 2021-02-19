import java.time.Instant;
import java.util.Set;

public class CollectionManager {
    private final Set<StudyGroup> set;
    private final Instant creationDate = Instant.now();

    public CollectionManager(Set<StudyGroup> set) {
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
        boolean wasRemoved = set.removeIf(studyGroup -> studyGroup.getId().equals(other.getId()));

        if (wasRemoved) {
            set.add(other);
        }
    }

    public void  removeById(Long id) {
        set.removeIf(studyGroup -> studyGroup.getId().equals(id));
    }

    public void clear() {
        set.clear();
    }



    public void executeScript() {
        //TODO executeScript решил отложить
    }

    public void addIfMin(StudyGroup other) {
        StudyGroup min = null;
        for (StudyGroup studyGroup: set) {
            if (min == null || studyGroup.getStudentsCount() < min.getStudentsCount()) {
                min = studyGroup;
            }
        }

        if (min == null || other.getStudentsCount() < min.getStudentsCount()) {
            set.add(other);
        }
    }

    public void removeLower(StudyGroup other) {
        set.removeIf(studyGroup -> studyGroup.getStudentsCount() < other.getStudentsCount());
    }

    public void removeAllByStudentsCount(long count) {
        set.removeIf(studyGroup -> studyGroup.getStudentsCount() == count);
    }

    public Integer countByGroupAdmin(Person groupAdmin) {
        int count = 0;
        for (StudyGroup studyGroup: set) {
            if (studyGroup.getGroupAdmin().equals(groupAdmin)) {
                count++;
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

    public boolean hasElementWithId(Long id) {
        for (StudyGroup studyGroup: set) {
            if (studyGroup.getId().equals(id)) {
                return true;
            }
        }
        return false;
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
