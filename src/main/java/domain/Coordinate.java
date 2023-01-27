package domain;

import domain.basistypes.NotNull;

public record Coordinate(int x, int y) {

    public Coordinate(int x, int y) {
        this.x = NotNull.notNull(x, ()-> new IllegalArgumentException("x can't not be null"));
        this.y = NotNull.notNull(y, ()-> new IllegalArgumentException("y can't not be null"));
    }
}
