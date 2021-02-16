public class Location {

    public Location(int x, Integer y, String name) {
        this.x=x;
        this.y=y;
        this.name=name;
    }

    private int x;
    private Integer y; //Поле не может быть null
    private String name; //Поле не может быть null

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}