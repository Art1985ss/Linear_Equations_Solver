package solver.data;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Row {
    private final String id;
    private List<ComplexNumber> list;

    public Row(List<ComplexNumber> list, String id) {
        this.list = list;
        this.id = id;
    }

    public boolean isValid() {
        int count = 0;
        for (int i = 0; i < list.size() - 1; i++) {
            if (!list.get(i).equals(ComplexNumber.ZERO)) {
                count++;
            }
        }
        if (count == 0) {
            return list.get(list.size() - 1).equals(ComplexNumber.ZERO);
        }
        return true;
    }

    public boolean notZeroRow() {
        return !list.stream().allMatch(d -> d.equals(ComplexNumber.ZERO));
    }

    public void addRows(Row row) {
        for (int i = 0; i < this.list.size(); i++) {
            ComplexNumber a = this.list.get(i);
            ComplexNumber b = row.list.get(i);
            ComplexNumber result = a.add(b);
            this.list.set(i, result);
        }
    }

    public Row multiplyBy(ComplexNumber multiplier) {
        List<ComplexNumber> resultList = list.stream()
                .map(d -> d.multiply(multiplier))
                .collect(Collectors.toList());
        return new Row(resultList, id);
    }

    public Row divideBy(ComplexNumber divisor) {
        List<ComplexNumber> resultList = list.stream()
                .map(d -> d.divide(divisor))
                .collect(Collectors.toList());
        return new Row(resultList, id);
    }

    public ComplexNumber getByColumn(int col) {
        return list.get(col);
    }

    public ComplexNumber getResult() {
        return list.get(list.size() - 1);
    }

    public int getEntryColumn() {
        for (int i = 0; i < list.size(); i++) {
            ComplexNumber a = list.get(i).multiply(new ComplexNumber(10_000, 10_000));
            if (!ComplexNumber.ZERO.equals(a)) return i;
        }
        return 0;
    }

    public void update(Row row) {
        this.list = row.list;
    }

    public String getId() {
        return id;
    }

    public int size() {
        return list.size();
    }

    public void swap(int i1, int i2) {
        Collections.swap(list, i1, i2);
    }

    @Override
    public String toString() {
        return this.id + " : \t" +
                list.stream()
                        .map(s -> String.format("%30.27s", s))
                        .collect(Collectors.joining("\t")) + "\n";
    }
}
