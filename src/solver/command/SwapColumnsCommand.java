package solver.command;

import solver.data.Matrix;

public class SwapColumnsCommand implements Command {
    private final int col1;
    private final int col2;
    private final Matrix matrix;

    public SwapColumnsCommand(int col1, int col2, Matrix matrix) {
        this.col1 = col1;
        this.col2 = col2;
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.swapColumns(col1, col2);
    }

    @Override
    public void undo() {
        execute();
    }
}
