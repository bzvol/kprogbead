import java.awt.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Obviously started writing at 10pm on deadline day...
// Obviously couldn't finish it...
// Obviously dumb me
// My only excuse is that we are releasing v1 of our project in my work,
// and I needed to do overtime.

public class Nest implements AutoCloseable {
    public static void main(String[] args) {
        try (var nest = new Nest()) {
            nest.start();
        }
    }

    private final Random random = new Random();

    private final NestConfiguration config;
    private final Map<Point, Actor> nest;
    private final int maxNestSize;

    private boolean started = false;
    private final ExecutorService executor;
    private final int spawnRate;
    private final Timer spawner = new Timer();

    public Nest() {
        this(new NestConfiguration());
    }

    public Nest(NestConfiguration config) {
        this.config = config;
        this.maxNestSize = config.getWidth() * config.getHeight();
        this.nest = new HashMap<>(maxNestSize);
        this.spawnRate = config.getSpawnRate();

        this.executor = Executors.newFixedThreadPool(
                config.getAntCount() + config.getNutritionCount() + config.getPheromoneCount());

        initNest();
    }

    public void start() {
        if (started) {
            throw new IllegalStateException("Simulation has already started");
        }

        for (var actor : nest.values()) {
            executor.execute(actor);
        }

        spawner.scheduleAtFixedRate(
                new SpawnerTask(this),
                0, config.getSpawnRate());
    }

    private void initNest() {
        // Max objects = total area - number of walls
        var maxActorCount = this.maxNestSize - (this.config.getWidth() + this.config.getHeight() * 2);
        if (this.config.getAntCount()
                + this.config.getNutritionCount()
                + this.config.getPheromoneCount()
                > maxActorCount) {
            throw new IllegalArgumentException("Too many objects to fit in the nest");
        }

        int i = 0;
        Point coords;

        // Create walls
        for (int j = 0; j < this.config.getWidth(); j++) {
            coords = new Point(j, 0);
            nest.put(coords, new Wall(i++, coords, this));
            coords = new Point(j, this.config.getHeight() - 1);
            nest.put(coords, new Wall(i++, coords, this));
        }
        for (int j = 1; j < this.config.getHeight() - 1; j++) {
            coords = new Point(0, j);
            nest.put(coords, new Wall(i++, coords, this));
            coords = new Point(this.config.getWidth() - 1, j);
            nest.put(coords, new Wall(i++, coords, this));
        }

        // Spawn live actors
        for (int j = 0; j < this.config.getAntCount(); j++) {
            coords = genNewCoord(this.config.getWidth(), this.config.getHeight(), false);
            nest.put(coords, new Ant(i++, coords, this));
        }
        for (int j = 0; j < this.config.getNutritionCount(); j++) {
            coords = genNewCoord(this.config.getWidth(), this.config.getHeight(), false);
            nest.put(coords, new Nutrition(i++, coords, this));
        }
        for (int j = 0; j < this.config.getPheromoneCount(); j++) {
            coords = genNewCoord(this.config.getWidth(), this.config.getHeight(), false);
            nest.put(coords, new Pheromone(i++, coords, this));
        }

        // Fill empty spaces
        do {
            coords = genNewCoord(this.config.getWidth(), this.config.getHeight(), false);
            nest.put(coords, new Empty(i++, coords, this));
        } while (coords != null);
    }

    private Point genNewCoord(int width, int height, boolean includeEmpty) {
        int i = 0;
        do {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Point coords = new Point(x, y);

            var existing = nest.get(coords);
            if (existing == null || (includeEmpty && existing instanceof Empty)) {
                return coords;
            }
        } while (i++ < this.maxNestSize);

        return null;
    }

    public void removeActor(Actor actor) {
        nest.put(actor.getCoords(), new Empty(actor.getActorId(), actor.getCoords(), this));
    }

    private void spawn() {
        var coords = genNewCoord(this.config.getWidth(), this.config.getHeight(), true);
        if (coords != null) {
            var finalCoords = coords;
            nest.computeIfPresent(coords, (k, emptyActor) ->
                    new Pheromone(emptyActor.getActorId(), finalCoords, this));
        }

        coords = genNewCoord(this.config.getWidth(), this.config.getHeight(), true);
        if (coords != null) {
            var finalCoords = coords;
            nest.computeIfPresent(coords, (k, emptyActor) ->
                    new Nutrition(emptyActor.getActorId(), finalCoords, this));
        }
    }

    private static class SpawnerTask extends TimerTask {
        private final Nest nest;

        public SpawnerTask(Nest nest) {
            this.nest = nest;
        }

        @Override
        public void run() {
            nest.spawn();
        }
    }

    @Override
    public void close() {
        this.spawner.cancel();
    }
}
