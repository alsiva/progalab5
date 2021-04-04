import java.util.HashMap;
import java.util.Map;

public enum CSVField {
    ID("id"),
    NAME("name"),
    COORDINATE_X("coordinate.x"),
    COORDINATE_Y("coordinate.y"),
    CREATION_DATE("creationDate"),
    STUDENTS_COUNT("studentsCount"),
    FORM_OF_EDUCATION("formOfEducation"),
    SEMESTER("semesterEnum"),
    GROUP_ADMIN_NAME("groupAdmin.name"),
    GROUP_ADMIN_BIRTHDAY("groupAdmin.birthday"),
    GROUP_ADMIN_PASSPORT_ID("groupAdmin.passportId"),
    GROUP_ADMIN_LOCATION_X("groupAdmin.location.x"),
    GROUP_ADMIN_LOCATION_Y("groupAdmin.location.y"),
    GROUP_ADMIN_LOCATION_NAME("groupAdmin.location.name");

    public final String name;

    CSVField(String name) {
        this.name = name;
    }

    private static final Map<String, CSVField> nameToField = new HashMap<>();
    static {
        for (CSVField value : CSVField.values()) {
            nameToField.put(value.name, value);
        }
    }

    static CSVField getFieldByName(String fieldName) {
        return nameToField.get(fieldName);
    }
}
