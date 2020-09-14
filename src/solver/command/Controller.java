package solver.command;

import java.util.ArrayDeque;
import java.util.Deque;

public class Controller implements Command {
    private final Deque<Command> undoList = new ArrayDeque<>();
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        command.execute();
        undoList.addLast(command);
    }

    @Override
    public void undo() {
        while (undoList.size() > 0) {
            Command command = undoList.pollLast();
            command.undo();
        }
    }
}
