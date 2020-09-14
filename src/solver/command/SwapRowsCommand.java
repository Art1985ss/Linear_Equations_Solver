package solver.command;

import solver.data.Matrix;

public class SwapRowsCommand implements Command {
    private final int row1;
    private final int row2;
    private final Matrix matrix;

    public SwapRowsCommand(int row1, int row2, Matrix matrix) {
        this.row1 = row1;
        this.row2 = row2;
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.swapRows(row1, row2);
    }

    @Override
    public void undo() {
        execute();
    }
}
