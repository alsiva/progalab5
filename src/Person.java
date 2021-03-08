import java.time.LocalDate;

/**
 * class that defines person
 */
public class Person {
    public Person(String name, LocalDate birthday, String passportID, Location location){
        this.adminName = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.location = location;
    }

    private final String adminName; //Поле не может быть null, Строка не может быть пустой
    private final LocalDate birthday; //Поле не может быть null
    private final String passportID; //Длина строки должна быть не меньше 7, Поле может быть null
    private final Location location; //Поле может быть null

    /**
     * @return person name
     */
    public String getName() {
        return adminName;
    }

    /**
     * @return person birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @return person passport id
     */
    public String getPassportID() {
        return passportID;
    }

    /**
     * @return person location
     */
    public Location getLocation() {
        return location;
    }
}