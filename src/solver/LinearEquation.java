package solver;

import solver.command.Controller;
import solver.command.SwapColumnsCommand;
import solver.command.SwapRowsCommand;
import solver.data.ComplexNumber;
import solver.data.Matrix;
import solver.data.Row;

import java.awt.*;

public class LinearEquation {
    private final Matrix matrix;
    private final StringBuilder sb = new StringBuilder("Start solving the equation.\n");
    private final Controller controller = new Controller();
    private boolean noSolution = false;
    private String text;

    public LinearEquation(Matrix matrix) {
        this.matrix = matrix;
    }

    public void execute() {
        leftReduction();
        if (notSolvable()) {
            return;
        }
        rightReduction();
        //controller.undo();
    }

    private boolean notSolvable() {
        if (!matrix.isValid()) {
            text = "No solutions";
            noSolution = true;
            return true;
        }
        if (matrix.isInfiniteSolutions()) {
            text = "Infinitely many solutions";
            noSolution = true;
            return true;
        }
        return false;
    }

    private void leftReduction() {
        for (int rowNum = 0; rowNum < matrix.rowCount() && rowNum < matrix.columnCount() - 1; rowNum++) {
            if (matrix.getByPoint(new Point(rowNum, rowNum)).equals(ComplexNumber.ZERO)) {
                noSolution = !getNotZero(rowNum, rowNum);
                if (noSolution) {
                    return;
                }
            }
            Row row = matrix.getRow(rowNum);
            ComplexNumber entry = row.getByColumn(rowNum);
            Row temp = row;
            if (!entry.equals(new ComplexNumber("1"))) {
                temp = row.divideBy(entry);
                sb.append("R").append(rowNum + 1).append(" / ").append(entry)
                        .append(" -> R").append(rowNum + 1).append("\n");
            }
            row.update(temp);
            setBottomZero(row, rowNum, rowNum);
        }
    }

    private boolean getNotZero(int r, int c) {
        for (int i = r + 1; i < matrix.rowCount(); i++) {
            ComplexNumber value = matrix.getByPoint(new Point(i, c));
            if (!value.equals(ComplexNumber.ZERO)) {
                swapRows(r, i);
                return true;
            }
        }

        for (int i = c + 1; i < matrix.columnCount() - 1; i++) {
            ComplexNumber value = matrix.getByPoint(new Point(r, c));
            if (!value.equals(ComplexNumber.ZERO)) {
                swapColumns(c, i);
                return true;
            }
        }

        for (int row = r + 1; row < matrix.rowCount(); row++) {
            for (int col = c + 1; col < matrix.columnCount() - 1; col++) {
                ComplexNumber value = matrix.getByPoint(new Point(row, col));
                if (!value.equals(ComplexNumber.ZERO)) {
                    swapRows(r, row);
                    swapColumns(c, col);
                    return true;
                }
            }
        }
        return false;
    }

    private void swapRows(int r1, int r2) {
        controller.setCommand(new SwapRowsCommand(r1, r2, matrix));
        controller.execute();
        sb.append("R").append(r1 + 1).append(" <-> R").append(r2 + 1).append("\n");
    }

    private void swapColumns(int c1, int c2) {
        controller.setCommand(new SwapColumnsCommand(c1, c2, matrix));
        controller.execute();
        sb.append("C").append(c1 + 1).append(" <-> C").append(c2 + 1).append("\n");
    }

    private void setBottomZero(Row row, int rowNum, int col) {
        for (int i = rowNum + 1; i < matrix.rowCount(); i++) {
            nullify(row, rowNum, col, i);
        }
    }

    private void rightReduction() {
        for (int rowNum = matrix.rowCount() - 1; rowNum > 0; rowNum--) {
            Row row = matrix.getRow(rowNum);
            int entryColumn = row.getEntryColumn();
            setTopZero(row, rowNum, entryColumn);
        }
    }

    private void setTopZero(Row row, int rowNum, int entryColumn) {
        for (int i = rowNum - 1; i >= 0; i--) {
            nullify(row, rowNum, entryColumn, i);
        }
    }

    private void nullify(Row row, int rowNum, int entryColumn, int i) {
        Row r = matrix.getRow(i);
        ComplexNumber entry = r.getByColumn(entryColumn);
        if (!entry.equals(ComplexNumber.ZERO)) {
            ComplexNumber multiplier = entry.multiply(ComplexNumber.ONE.multiply(new ComplexNumber(-1, 0)));
            Row temp = row.multiplyBy(multiplier);
            r.addRows(temp);
            sb.append(multiplier).append(" * R").append(rowNum + 1).append(" + ")
                    .append(r.getId()).append(" -> R").append(i + 1).append("\n");
        }
    }

    @Override
    public String toString() {
        String text = noSolution ? this.text : "(" + matrix.toString() + ")";
        return sb.append("The solution is: ").append(text).append("\n").toString();
    }

    public String textToFile() {
        return noSolution ? this.text : matrix.textToFile();
    }
}
