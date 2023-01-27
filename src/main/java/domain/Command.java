package domain;

public enum Command {
    F("forward"), B("backward"), L("left"), R("right");

    private final String command;

    Command(String command) {
        this.command = command;
    }
}
