/**
 * class that contains x,y coordinates
 */
public class Coordinates {
    public Coordinates(float x, int y) {
        this.x = x;
        this.y = y;
    }

    private float x; //Значение поля должно быть больше -318
    private int y; //Максимальное значение поля: 870

    /**
     * @return x coordinate
     */
    public float getX() { return x; }

    /**
     * @return y coordinate
     */
    public int getY() { return y;}

}