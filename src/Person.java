import java.time.LocalDate;

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

    public String getName() {
        return adminName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public Location getLocation() {
        return location;
    }
}