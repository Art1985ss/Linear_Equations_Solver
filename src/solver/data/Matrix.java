package solver.data;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Matrix {
    private final List<Row> rows = new ArrayList<>();

    public void addRow(Row row) {
        rows.add(row);
    }

    public Row getRow(int rowNum) {
        return rows.get(rowNum);
    }

    public ComplexNumber getByPoint(Point p) {
        return rows.get(p.x).getByColumn(p.y);
    }

    public int rowCount() {
        return rows.size();
    }

    public int columnCount() {
        return rows.get(0).size();
    }

    public void swapRows(int row1, int row2) {
        Collections.swap(rows, row1, row2);
    }

    public void swapColumns(int col1, int col2) {
        rows.forEach(row -> row.swap(col1, col2));
    }

    public boolean isValid() {
        return rows.stream().allMatch(Row::isValid);
    }

    public boolean isInfiniteSolutions() {
        List<Row> tempRows = new ArrayList<>(rows);
        tempRows = tempRows.stream().filter(Row::notZeroRow).collect(Collectors.toList());
        System.out.println(tempRows);
        return rows.get(0).size() - 1 != tempRows.size();
    }


    @Override
    public String toString() {
        return rows.stream().map(Row::getResult).map(String::valueOf).collect(Collectors.joining(", "));
    }

    public String textToFile() {
        return rows.stream()
                .filter(Row::notZeroRow)
                .map(Row::getResult)
                .map(String::valueOf)
                .collect(Collectors.joining("\n"));
    }
}
