package domain;

public enum Direction {
    NORTH, SOUTH,EAST,WEST;

    public Direction leftRotation() {
        switch (this) {
            case NORTH -> {
                return WEST;
            }
            case WEST -> {
                return SOUTH;
            }
            case EAST -> {
                return NORTH;
            }
            case SOUTH -> {
                return EAST;
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Direction rightRotation() {
        switch (this) {
            case NORTH -> {
                return EAST;
            }
            case WEST -> {
                return NORTH;
            }
            case EAST -> {
                return SOUTH;
            }
            case SOUTH -> {
                return WEST;
            }
            default -> throw new IllegalStateException("Unexpected value: " + this);
        }
    }

}

