import java.awt.*;

public class Wall extends Actor {
    public Wall(int id, Point coords, Nest nest) {
        super(id, coords, nest);
    }

    @Override
    protected char getSymbol() {
        return '#';
    }
}
