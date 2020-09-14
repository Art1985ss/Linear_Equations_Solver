package solver.data;

import static java.lang.Double.parseDouble;
import java.util.Objects;
import java.util.regex.Pattern;

public class ComplexNumber {
    public static final ComplexNumber ZERO = new ComplexNumber(0, 0);
    public static final ComplexNumber ONE = new ComplexNumber(1, 0);
    private static final Pattern ONLY_REAL = Pattern.compile("[-+]?\\d+(\\.\\d*)?");
    private static final Pattern ONLY_IMAGINARY = Pattern.compile("[-+]?\\d*(\\.\\d*)?i");
    private static final Pattern ONLY_I = Pattern.compile("[-+]?i");
    private static final Pattern RE_IM = Pattern.compile("(?<=\\d)(?=[-+])");
    private final double x;
    private final double y;

    public ComplexNumber(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public ComplexNumber(String s) {
        if (ONLY_IMAGINARY.matcher(s).matches()) {
            x = 0;
            y = parseDouble(s.replace('i', ONLY_I.matcher(s).matches() ? '1' : ' '));
        } else if (ONLY_REAL.matcher(s).matches()) {
            x = parseDouble(s);
            y = 0;
        } else {
            x = parseDouble(RE_IM.split(s)[0]);
            final String secondPart = RE_IM.split(s)[1];
            y = parseDouble(secondPart.replace('i', ONLY_I.matcher(secondPart).matches() ? '1' : ' '));
        }
    }

    public ComplexNumber add(ComplexNumber comp) {
        return new ComplexNumber(x + comp.x, y + comp.y);
    }

    public ComplexNumber multiply(ComplexNumber comp) {
        return new ComplexNumber(x * comp.x - y * comp.y, y * comp.x + comp.y * x);
    }

    public ComplexNumber divide(ComplexNumber comp) {
        double divisor = comp.x * comp.x + comp.y * comp.y;
        double xTemp = (x * comp.x + y * comp.y) / divisor;
        double yTemp = (y * comp.x - x * comp.y) / divisor;
        return new ComplexNumber(xTemp, yTemp);
    }

    @Override
    public String toString() {
        double tempX = Math.round(x * 10000.0) / 10000.0;
        double tempY = Math.round(y * 10000.0) / 10000.0;
        if (y == 0) {
            return String.valueOf(tempX);
        }
        if (x == 0) {
            return tempY + "i";
        }
        String text = String.format("%s%s%si", tempX, y > 0 ? "+" : "-", Math.abs(tempY));
        text = text.replace("1.000i", "i");
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
