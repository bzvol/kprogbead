public class NestConfiguration {
    private int width = 30;
    private int height = 30;
    private int antCount = 10;
    private int nutritionCount = 10;
    private int pheromoneCount = 10;
    private int spawnRate = 200;

    public int getWidth() {
        return width;
    }

    public NestConfiguration setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public NestConfiguration setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getAntCount() {
        return antCount;
    }

    public NestConfiguration setAntCount(int antCount) {
        this.antCount = antCount;
        return this;
    }

    public int getNutritionCount() {
        return nutritionCount;
    }

    public NestConfiguration setNutritionCount(int nutritionCount) {
        this.nutritionCount = nutritionCount;
        return this;
    }

    public int getPheromoneCount() {
        return pheromoneCount;
    }

    public NestConfiguration setPheromoneCount(int pheromoneCount) {
        this.pheromoneCount = pheromoneCount;
        return this;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public NestConfiguration setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
        return this;
    }
}
