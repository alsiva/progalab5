public class Location {

    public Location(int x, Integer y, String locationName) {
        this.x=x;
        this.y=y;
        this.locationName = locationName;
    }

    private int x;
    private Integer y; //Поле не может быть null
    private String locationName; //Поле не может быть null

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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}