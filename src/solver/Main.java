package solver;

import solver.data.ComplexNumber;
import solver.data.Matrix;
import solver.data.Row;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static String inFileName = "in.txt";
    private static String outFileName = "out.txt";

    public static void main(String[] args) {
        processArgs(args);
        Matrix matrix = createMatrix();
        LinearEquation linearEquation = new LinearEquation(matrix);
        linearEquation.execute();
        System.out.println(linearEquation);
        FileService.setText(outFileName, linearEquation.textToFile());
        System.out.println("Saved to file " + outFileName);

    }

    private static void processArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-in".equals(args[i])) {
                inFileName = args[i + 1];
                continue;
            }
            if ("-out".equals(args[i])) {
                outFileName = args[i + 1];
            }
        }
    }

    private static Matrix createMatrix() {
        String text = FileService.getText(inFileName).trim();
        Scanner scanner = new Scanner(text);
        Matrix matrix = new Matrix();
        int count = 1;
        int rows = Integer.parseInt(scanner.nextLine().split("\\s+")[1]);
        while (count <= rows) {

            String t = scanner.nextLine();
            String name = "R" + count;
            List<ComplexNumber> list =
                    Arrays.stream(t.split("\\s+"))
                    .map(ComplexNumber::new).collect(Collectors.toList());
            matrix.addRow(new Row(list, name));
            count++;
        }
        scanner.close();
        return matrix;
    }

}
