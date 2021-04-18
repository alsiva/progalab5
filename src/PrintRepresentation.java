import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class PrintRepresentation {

    private final SimpleDateFormat CREATION_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final DateTimeFormatter BIRTHDAY_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    String toPrint(StudyGroup studyGroup) {
        return "{" +
            "id=" + studyGroup.getId() +
            ", name='" + studyGroup.getName() + '\'' +
            ", coordinates=" + toPrint(studyGroup.getCoordinates()) +
            ", creationDate=" + toPrint(studyGroup.getCreationDate()) +
            ", studentsCount=" + studyGroup.getStudentsCount() +
            ", formOfEducation=" + studyGroup.getFormOfEducation() +
            ", semesterEnum=" + studyGroup.getSemesterEnum() +
            ", groupAdmin=" + toPrint(studyGroup.getGroupAdmin()) +
        '}';
    }

    String toPrint(Coordinates coordinates) {
        return "(x=" + coordinates.getX() + ", y=" + coordinates.getY() + ")";
    }

    String toPrint(Date date) {
        return CREATION_DATE_FORMAT.format(date);
    }

    String toPrint(Person person) {
        return "{" +
            "name=" + person.getName() +
            ", birthday=" + person.getBirthday().format(BIRTHDAY_DATE_FORMAT) +
            ", passportId=" + person.getPassportID() +
            ", location=" + toPrint(person.getLocation()) +
        "}";
    }

    String toPrint(Location location) {
        return "{" +
            "x=" + location.getX() +
            ", y=" + location.getY() +
            ", name=" + location.getLocationName() +
        "}";
    }


}
