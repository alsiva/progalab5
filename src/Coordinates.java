public class Coordinates {
    public Coordinates(float x, int y) {
        if (x <= -318) {
            throw new IllegalArgumentException(); // todo: message
        }
        this.x = x;

        if (y > 870) {
            throw new IllegalArgumentException(); //todo: message
        }
        this.y = y;
    }

    private float x; //Значение поля должно быть больше -318
    private int y; //Максимальное значение поля: 870
}