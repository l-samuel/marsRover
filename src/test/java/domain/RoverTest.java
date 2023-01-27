package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static domain.Command.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class RoverTest {

    private final Mars mars = new Mars(4, 4, new ArrayList<>());

    public static Stream<Arguments> forwardTestParameters() {
        return Stream.of(
                Arguments.of("NORTH", new Coordinate(0,0), new Coordinate(0,1)),
                Arguments.of("NORTH", new Coordinate(0,4), new Coordinate(0,1)),
                Arguments.of("SOUTH", new Coordinate(0,0), new Coordinate(0,3)),
                Arguments.of("SOUTH", new Coordinate(0,1), new Coordinate(0,0)),
                Arguments.of("WEST", new Coordinate(0,0), new Coordinate(3,0)),
                Arguments.of("WEST", new Coordinate(3,0), new Coordinate(2,0)),
                Arguments.of("EAST", new Coordinate(0,0), new Coordinate(1,0)),
                Arguments.of("EAST", new Coordinate(4,0), new Coordinate(1,0))
                );
    }

    public static Stream<Arguments> backwardTestParameters() {
        return Stream.of(
                Arguments.of("NORTH", new Coordinate(0,0), new Coordinate(0,3)),
                Arguments.of("NORTH", new Coordinate(0,1), new Coordinate(0,0)),
                Arguments.of("SOUTH", new Coordinate(0,0), new Coordinate(0,1)),
                Arguments.of("SOUTH", new Coordinate(0,4), new Coordinate(0,1)),
                Arguments.of("WEST", new Coordinate(0,0), new Coordinate(1,0)),
                Arguments.of("WEST", new Coordinate(4,0), new Coordinate(1,0)),
                Arguments.of("EAST", new Coordinate(0,0), new Coordinate(3,0)),
                Arguments.of("EAST", new Coordinate(4,0), new Coordinate(3,0))
                );
    }

    public static Stream<Arguments> leftTestParameters() {
        return Stream.of(
                Arguments.of("NORTH","WEST"),
                Arguments.of("WEST","SOUTH"),
                Arguments.of("SOUTH","EAST"),
                Arguments.of("EAST","NORTH")
                );
    }

    public static Stream<Arguments> rightTestParameters() {
        return Stream.of(
                Arguments.of("NORTH","EAST"),
                Arguments.of("WEST","NORTH"),
                Arguments.of("SOUTH","WEST"),
                Arguments.of("EAST","SOUTH")
        );
    }

    @Test
    void rover_should_have_initial_coordinate_and_direction() {
        Coordinate coordinate = new Coordinate(1, 1);
        Rover rover = new Rover(coordinate, Direction.NORTH, mars);
        assertThat(rover.coordinate()).isEqualTo(coordinate);
        assertThat(rover.direction()).isEqualTo(Direction.NORTH);
    }

    @ParameterizedTest
    @MethodSource("forwardTestParameters")
    void should_move_forward_when_command_is_F(String initialDirection, Coordinate initialCoordinate, Coordinate expectedCoordinate){
        Direction direction = Direction.valueOf(initialDirection);
        Rover rover = new Rover(initialCoordinate, direction, mars);
        Rover result = rover.move(List.of(F));
        assertThat(result).isEqualTo(new Rover(expectedCoordinate, direction, mars));
    }

    @ParameterizedTest
    @MethodSource("backwardTestParameters")
    void should_move_backward_when_command_is_B(String initialDirection, Coordinate initialCoordinate, Coordinate expectedCoordinate){
        Direction direction = Direction.valueOf(initialDirection);
        Rover rover = new Rover(initialCoordinate, direction, mars);
        Rover result = rover.move(List.of(B));
        assertThat(result).isEqualTo(new Rover(expectedCoordinate, direction, mars));
    }

    @ParameterizedTest
    @MethodSource("leftTestParameters")
    void should_turn_left_when_command_is_L(String initialDirection, String expectedDirection){
        Direction direction = Direction.valueOf(initialDirection);
        Rover rover = new Rover(new Coordinate(0,0), direction, mars);
        Rover result = rover.move(List.of(L));
        assertThat(result).isEqualTo(new Rover(new Coordinate(0,0), Direction.valueOf(expectedDirection), mars));
    }

    @ParameterizedTest
    @MethodSource("rightTestParameters")
    void should_turn_right_when_command_is_R(String initialDirection, String expectedDirection){
        Direction direction = Direction.valueOf(initialDirection);
        Rover rover = new Rover(new Coordinate(0,0), direction, mars);
        Rover result = rover.move(List.of(R));
        assertThat(result).isEqualTo(new Rover(new Coordinate(0,0), Direction.valueOf(expectedDirection), mars));
    }

    @Test
    void should_accept_a_list_of_command() {
        Rover rover = new Rover(new Coordinate(0,0), Direction.NORTH, mars);
        List<Command> commands = List.of(F,B,R,F,F,R,F,F,L, F, F, F);
        Rover result = rover.move(commands);
        assertThat(result).isEqualTo(new Rover(new Coordinate(1,2), Direction.EAST,mars));
    }

    @Test
    void should_detect_obstacle_when_forward_and_stop_to_the_last_possible_point() {
        Mars mars = new Mars(4, 4, List.of(new Coordinate(1,0)));
        Rover rover = new Rover(new Coordinate(0,0), Direction.EAST, mars);
        List<Command> commands = List.of(F);
        assertThatThrownBy(()-> rover.move(commands)).hasMessage("obstacle, last position 0,0");
    }

    @Test
    void should_detect_obstacle_when_backward_and_stop_to_the_last_possible_point() {
        Mars mars = new Mars(4, 4, List.of(new Coordinate(0,0)));
        Rover rover = new Rover(new Coordinate(0,1), Direction.NORTH, mars);
        List<Command> commands = List.of(B);
        assertThatThrownBy(()-> rover.move(commands)).hasMessage("obstacle, last position 0,1");
    }
}
