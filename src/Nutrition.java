import java.awt.*;

public class Nutrition extends Actor {
    public Nutrition(int id, Point coords, Nest nest) {
        super(id, coords, nest);
    }

    @Override
    protected char getSymbol() {
        return 'N';
    }
}
