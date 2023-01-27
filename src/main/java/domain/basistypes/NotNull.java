package domain.basistypes;

import java.util.function.Supplier;

import static java.util.Objects.isNull;

public final class NotNull {

    private NotNull(){

    }

    public static <T> T notNull(T value, Supplier<RuntimeException> exceptionSupplier){
        if(isNull(value)) {
            throw exceptionSupplier.get();
        }
        return value;
    }
}
