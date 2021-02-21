public class Coordinates {
    public Coordinates(float x, int y) {
        this.x = x;
        this.y = y;
    }

    private float x; //Значение поля должно быть больше -318
    private int y; //Максимальное значение поля: 870

    public float getX() { return x; }
    public int getY() { return y;}

}