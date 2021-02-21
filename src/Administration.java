import java.time.Instant;
import java.util.Set;

public class Administration {
    private final Set<StudyGroup> groups;
    private final Instant creationDate = Instant.now();

    public Set<StudyGroup> getGroups() {
        return groups;
    }

    public Administration(Set<StudyGroup> groups) {
        this.groups = groups;
    }

    public void add(StudyGroup studyGroup) {
        groups.add(studyGroup);
    }

    public void info() {
        System.out.println("Collection type: " + groups.getClass().toString());
        System.out.println("Collection creation time: " + creationDate.toString());
        System.out.println("Elements in collection: " + groups.size());
    }

    public void show() {
        for (StudyGroup studyGroup : groups) {
            System.out.println(studyGroup.toString());
        }
    }

    public void updateId(StudyGroup other) {
        boolean wasRemoved = groups.removeIf(studyGroup -> studyGroup.getId().equals(other.getId()));

        if (wasRemoved) {
            groups.add(other);
        }
    }

    public void  removeById(Long id) {
        groups.removeIf(studyGroup -> studyGroup.getId().equals(id));
    }

    public void clear() {
        groups.clear();
    }

    public void addIfMin(StudyGroup other) {
        StudyGroup min = null;
        for (StudyGroup studyGroup: groups) {
            if (min == null || studyGroup.getStudentsCount() < min.getStudentsCount()) {
                min = studyGroup;
            }
        }

        if (min == null || other.getStudentsCount() < min.getStudentsCount()) {
            groups.add(other);
        }
    }

    public void removeLower(StudyGroup other) {
        groups.removeIf(studyGroup -> studyGroup.getStudentsCount() < other.getStudentsCount());
    }

    public void removeAllByStudentsCount(long count) {
        groups.removeIf(studyGroup -> studyGroup.getStudentsCount() == count);
    }

    public Integer countByGroupAdmin(Person groupAdmin) {
        int count = 0;
        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getGroupAdmin().equals(groupAdmin)) {
                count++;
            }
        }
        return count;
    }

    public void filterLessThanSemesterEnum(Semester semester) {
        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getSemesterEnum().ordinal() < semester.ordinal()) {
                System.out.println(studyGroup);
            }
        }
    }

    public boolean hasElementWithId(Long id) {
        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
