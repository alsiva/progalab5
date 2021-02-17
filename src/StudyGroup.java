import java.util.Date;

public class StudyGroup implements Comparable<StudyGroup> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer studentsCount; //Значение поля должно быть больше 0, Поле не может быть null
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    public void setCoordinates(float x, int y) {
        this.coordinates = new Coordinates(x, y);
    }

    public void setCreationDate(java.util.Date date) {
        this.creationDate = date;
    }

    public void setStudentsCount(int count) {
        this.studentsCount = count;
    }

    public Integer getStudentsCount() { return this.studentsCount; }

    public void setFormOfEducation(FormOfEducation foe) {
        this.formOfEducation = foe;
    }

    public void setSemesterEnum(Semester sem) {
        this.semesterEnum = sem;
    }

    public void setGroupAdmin(String name, java.time.LocalDateTime birthday, String passportID, Location location) {
        this.groupAdmin = new Person(name, birthday, passportID, location);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public Person getGroupAdmin() {
        return this.groupAdmin;
    }

    public int compareTo(StudyGroup other) {
        return studentsCount.compareTo(other.studentsCount);
    }
}




