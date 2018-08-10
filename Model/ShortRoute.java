package Model;

import javafx.scene.shape.Path;

public class ShortRoute {
    public Path shortPach;
    private int distance ;

    public ShortRoute(Path shortPach, int distance) {
        this.shortPach = shortPach;
        this.distance = distance;
    }

    public Path getShortPach() {
        return shortPach;
    }

    public int getDistance() {
        return distance;
    }
}
