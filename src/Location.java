public class Location {
    public Location(int x, int y, String locationName) {
        this.x = x;
        this.y = y;
        this.locationName = locationName;
    }

    private final int x;
    private final int y; // Поле не может быть null
    private final String locationName; // Поле не может быть null

    public int getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getLocationName() {
        return locationName;
    }
}