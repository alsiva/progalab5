import java.util.Date;

public class StudyGroup implements Comparable<StudyGroup> {
    private final Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final Coordinates coordinates; //Поле не может быть null
    private final java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private final Integer studentsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private final FormOfEducation formOfEducation; //Поле не может быть null
    private final Semester semesterEnum; //Поле не может быть null
    private final Person groupAdmin; //Поле может быть null

    public StudyGroup(
            Long id,
            String name,
            Coordinates coordinates,
            Date creationDate,
            Integer studentsCount,
            FormOfEducation formOfEducation,
            Semester semesterEnum,
            Person groupAdmin
    ) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    @Override
    public String toString() {
        return "StudyGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", studentsCount=" + studentsCount +
                ", formOfEducation=" + formOfEducation +
                ", semesterEnum=" + semesterEnum +
                ", groupAdmin=" + groupAdmin +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public String getName() { return this.name; }

    public int getStudentsCount() { return this.studentsCount; }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return this.groupAdmin;
    }

    public int compareTo(StudyGroup other) {
        return studentsCount.compareTo(other.studentsCount);
    }
}




