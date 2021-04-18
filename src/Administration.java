import java.time.Instant;
import java.util.Set;

/**
 * Group administration; responsible for all operations with study groups
 */
public class Administration {
    private final Set<StudyGroup> groups;
    private final Instant creationDate = Instant.now();
    private final PrintRepresentation printRepresentation = new PrintRepresentation();

    public Set<StudyGroup> getGroups() {
        return groups;
    }

    public Administration(Set<StudyGroup> groups) {
        this.groups = groups;
    }

    public void add(StudyGroup studyGroup) {
        groups.add(studyGroup);
    }

    /**
     * returns information about collection
     */
    public void info() {
        System.out.println("Collection type: " + groups.getClass().toString());
        System.out.println("Collection creation time: " + printRepresentation.toPrint(creationDate));
        System.out.println("Elements in collection: " + groups.size());
    }

    /**
     * shows all study groups in collection
     */
    public void show() {
        for (StudyGroup studyGroup : groups) {
            System.out.println(printRepresentation.toPrint(studyGroup));
        }
    }

    /**
     * replaces old study group with other if they have the same id
     * @param other study group
     */
    public void updateId(StudyGroup other) {
        boolean wasRemoved = groups.removeIf(studyGroup -> studyGroup.getId().equals(other.getId()));

        if (wasRemoved) {
            groups.add(other);
        }
    }

    /**
     * removes study group in collection by it's id
     * @param id
     */
    public void removeById(Long id) {
        groups.removeIf(studyGroup -> studyGroup.getId().equals(id));
    }

    /**
     * clears collection from all study groups
     */
    public void clear() {
        groups.clear();
    }

    /**
     * adds other study group if it has lower students count than old one
     * @param other study group
     */
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

    /**
     * removes all study groups that have lower students count than old one
     * @param other study group
     */
    public void removeLower(StudyGroup other) {
        groups.removeIf(studyGroup -> studyGroup.getStudentsCount() < other.getStudentsCount());
    }

    /**
     * removes study group that student count is equal to count in params
     * @param count
     */
    public void removeAllByStudentsCount(long count) {
        groups.removeIf(studyGroup -> studyGroup.getStudentsCount() == count);
    }

    /**
     * returns the amount of groups that have group admin from params
     * @param groupAdmin
     * @return count
     */
    public Integer countByGroupAdmin(Person groupAdmin) {
        int count = 0;
        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getGroupAdmin().equals(groupAdmin)) {
                count++;
            }
        }
        return count;
    }

    /**
     * prints groups that have lower semester than in params
     * @param semester
     */
    public void filterLessThanSemesterEnum(Semester semester) {
        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getSemesterEnum().ordinal() < semester.ordinal()) {
                System.out.println(printRepresentation.toPrint(studyGroup));
            }
        }
    }

    /**
     * returnes true if collection has study group with id from params
     * @param id
     * @return true or false
     */
    public boolean hasElementWithId(Long id) {
        for (StudyGroup studyGroup: groups) {
            if (studyGroup.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
