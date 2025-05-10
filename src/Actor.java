import java.awt.*;

public abstract class Actor extends Thread {
    protected final int id;

    protected Nest nest;
    protected Point coords;

    protected Actor(int id, Point coords, Nest nest) {
        this.id = id;
        this.coords = coords;
        this.nest = nest;
    }

    public int getActorId() {
        return id;
    }

    public Point getCoords() {
        return coords;
    }

    protected void die() {
        this.nest.removeActor(this);
        this.interrupt();
    }

    protected abstract char getSymbol();

    @Override
    public final String toString() {
        return String.valueOf(this.getSymbol());
    }

    @Override
    public void run() {

    }
}
