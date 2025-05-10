import java.awt.*;

public class Pheromone extends Actor {
    public Pheromone(int id, Point coords, Nest nest) {
        super(id, coords, nest);
    }

    @Override
    protected char getSymbol() {
        return 'P';
    }
}
