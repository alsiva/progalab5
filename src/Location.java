/**
 * class that defines location
 */
public class Location {
    public Location(int x, int y, String locationName) {
        this.x = x;
        this.y = y;
        this.locationName = locationName;
    }

    private final int x;
    private final int y; // Поле не может быть null
    private final String locationName; // Поле не может быть null

    /**
     * returns coordinate x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * returns location y
     * @return y
     */
    public Integer getY() {
        return y;
    }

    /**
     * returns location name
     * @return location name
     */
    public String getLocationName() {
        return locationName;
    }
}