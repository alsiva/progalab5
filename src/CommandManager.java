import com.opencsv.CSVWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Set;

public class CommandManager {
    Set<StudyGroup> set = new LinkedHashSet<>();//это надо сделать нормально потом поправлю

    public void add(StudyGroup studyGroup) {
        set.add(studyGroup);
    }

    public void show() {
        for (StudyGroup studyGroup : set) {
            System.out.println(studyGroup);
        }
    }

    public void updateId(Long id) {
        for (StudyGroup studyGroup: set) {
            if (studyGroup.getId().equals(id)) {
                studyGroup.setId(id);
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
            CSVWriter writer = new CSVWriter(new PrintWriter("NotExistingFile"));
            String line = "";
            for (StudyGroup studyGroup: set) {
                line += studyGroup.getId().toString() + ",";
                line += studyGroup.getName() + ",";

                Coordinates coordinates = studyGroup.getCoordinates();
                Float x = coordinates.getX();
                Integer y = coordinates.getY();
                line += x.toString() + ",";
                line += y.toString() + ",";

                java.util.Date creationDate = studyGroup.getCreationDate();
                String creationDateAsStr = creationDate.toString();
                line += creationDateAsStr + ",";

                Integer studentsCount = studyGroup.getStudentsCount();
                line += studentsCount.toString() + ",";

                FormOfEducation formOfEducation = studyGroup.getFormOfEducation();
                line += formOfEducation.toString() + ",";

                Semester semester = studyGroup.getSemesterEnum();
                line += semester.toString() + ",";

                Person admin = studyGroup.getGroupAdmin();
                String adminName = admin.getName();
                line += adminName + ",";

                java.time.LocalDateTime birthday = admin.getBirthday();
                line += birthday.toString();

                String passportID = admin.getPassportID();
                line += passportID + ",";

                Location location = admin.getLocation();
                line += location.toString();

                Integer xL = location.getX();
                line += xL.toString() + ",";

                Integer yL = location.getY();
                line += yL.toString();

                String locName = location.getName();
                line += locName + ",";

                //TODO доделать save
            }
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }

    }

    public void executeScript() {
        //TODO executeScript решил отложить
    }

    public void exit() {
        System.exit(0);
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

    public void history() {
        //TODO history решил отложить
    }

    public void removeAllByStudentsCount(Integer count) {
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
}
