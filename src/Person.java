import java.time.LocalDateTime;

public class Person {
    public Person(String name, java.time.LocalDateTime birthday, String passportID, Location location){
        this.name = name;
        this.birthday = birthday;
        this.passportID = passportID;
        this.location = location;
    }

    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Длина строки должна быть не меньше 7, Поле может быть null
    private Location location; //Поле может быть null

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}