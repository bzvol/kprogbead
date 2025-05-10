import java.awt.*;

public class Ant extends Actor {
    private static final int WAIT_TIME = 200;

    private int nutritionCount = 50;

    public Ant(int id, Point coords, Nest nest) {
        super(id, coords, nest);
    }

    @Override
    protected char getSymbol() {
        return 'A';
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (nutritionCount <= 0) {
                die();
            }

            try {
                //noinspection BusyWait
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                System.out.println("Ant " + this.getActorId() + " interrupted during wait");
            }
        }
    }
}
