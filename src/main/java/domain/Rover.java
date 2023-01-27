package domain;

import domain.basistypes.NotNull;

import java.util.List;

public record Rover(Coordinate coordinate, Direction direction, Mars mars) {

    public Rover(Coordinate coordinate, Direction direction, Mars mars) {
        this.coordinate = NotNull.notNull(coordinate, ()-> new IllegalArgumentException("coordinate can't be null"));
        this.direction = NotNull.notNull(direction,()-> new IllegalArgumentException("direction can't be null"));
        this.mars =  NotNull.notNull(mars, ()-> new IllegalArgumentException("mars can't be null"));
    }

    public Rover move(final List<Command> commands) {
        Rover rover = this;
        for (Command command : commands) {
            Direction oldDirection = rover.direction();
            Coordinate oldCoordinate = rover.coordinate();
            rover = moveRover(command, oldCoordinate, oldDirection);
        }
        return rover;
    }

    private Rover moveRover(final Command command, final Coordinate coordinate, final Direction direction) {
        Coordinate nextCoordinate = coordinate;
        Direction nextDirection = direction;
        switch (command) {
            case F -> nextCoordinate = moveForward(coordinate, direction);
            case B -> nextCoordinate = moveBackward(coordinate, direction);
            case L -> nextDirection = direction.leftRotation();
            case R -> nextDirection = direction.rightRotation();
        }
        return new Rover(nextCoordinate, nextDirection, mars);
    }

    private Coordinate moveForward(final Coordinate coordinate, final Direction direction) {
        int yPosition = coordinate.y();
        int xPosition = coordinate.x();
        switch (direction) {
            case NORTH -> yPosition = (yPosition + 1) % mars.height();
            case SOUTH -> yPosition = yPosition > 0 ? (yPosition - 1) : mars.height() - 1;
            case WEST -> xPosition = xPosition > 0 ? (xPosition - 1) : mars.width() - 1;
            case EAST -> xPosition = (xPosition + 1) % mars.width();
        }
        return checkObstacle(coordinate, new Coordinate(xPosition, yPosition));
    }

    private Coordinate moveBackward(final Coordinate coordinate, final Direction direction) {
        int yPosition = coordinate.y();
        int xPosition = coordinate.x();
        switch (direction) {
            case NORTH -> yPosition = yPosition > 0 ? (yPosition - 1) : mars.height() - 1;
            case SOUTH -> yPosition = (yPosition + 1) % mars.height();
            case WEST -> xPosition =  (xPosition + 1) % mars.width();
            case EAST -> xPosition = xPosition > 0 ? (xPosition - 1) : mars.width() - 1;
        }
        return checkObstacle(coordinate, new Coordinate(xPosition, yPosition));
    }

    private Coordinate checkObstacle(Coordinate actualCoordinate, Coordinate newCoordinate) throws ObstacleException {
        List<Coordinate> obstacles = mars().obstacles();
        boolean hasObstacle = obstacles.contains(newCoordinate);
        if(hasObstacle){
            throw new ObstacleException("obstacle, last position " + actualCoordinate.x() + "," + actualCoordinate.y());
        }
        return newCoordinate;
    }

}
