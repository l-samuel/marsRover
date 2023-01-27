package domain;

import domain.basistypes.NotNull;

import java.util.List;

public record Mars(int width, int height, List<Coordinate> obstacles) {

    public Mars(int width, int height, List<Coordinate> obstacles) {
        this.width = NotNull.notNull(width, ()-> new IllegalArgumentException("width can't be null"));
        this.height = NotNull.notNull(height, ()-> new IllegalArgumentException("height can't be null"));
        this.obstacles = NotNull.notNull(obstacles, ()-> new IllegalArgumentException("obstacles can't be null"));
        if( width < 0 || height < 0) {
            throw new IllegalArgumentException("width or height can't negative");
        }
    }
}
