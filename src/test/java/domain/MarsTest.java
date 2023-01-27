package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class MarsTest {

    @Test
    void should_not_accept_height_negative_value() {
        Assertions.assertThatThrownBy(()-> new Mars(1, -1, new ArrayList<>())).hasMessage("width or height can't negative");
    }

    @Test
    void should_not_accept_width_negative_value() {
        Assertions.assertThatThrownBy(()-> new Mars(-1, 1, new ArrayList<>())).hasMessage("width or height can't negative");
    }
}