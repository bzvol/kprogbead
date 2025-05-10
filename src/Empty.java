import java.awt.*;

public class Empty extends Actor {
    public Empty(int id, Point coords, Nest nest) {
        super(id, coords, nest);
    }

    @Override
    protected char getSymbol() {
        return ' ';
    }
}
