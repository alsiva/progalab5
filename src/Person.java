public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Длина строки должна быть не меньше 7, Поле может быть null
    private Location location; //Поле может быть null
}