package Models;

import Controller.Calculation;
import java.text.DecimalFormat;

public class GaussianElimination {

    private double matrix[][];
    private double ZVector[];
    private double result[];
    private int size;
    private static final DecimalFormat df = new DecimalFormat("0.000");

    public GaussianElimination(double matrix[][], double ZVector[], int size) {
        this.matrix = matrix;
        this.ZVector = ZVector;
        this.size = size;
        this.result = new double[size];

        for (int i = 0; i < size; i++) {
            this.result[i] = 1;
        }

    }

    public void findEquations() {

        for (int col = 0; col < size - 1; col++) {
            System.out.println("Step : " + (col + 1));
            for (int row = col + 1; row < size; row++) {

                double firstValue = matrix[col][col];
                double secondValue = matrix[row][col];

                System.out.println("R" + (row + 1) + " => " + firstValue + "R" + (row + 1) + " - " + secondValue + "R" + (col + 1) + "\n");
                int i;
                for (i = col; i < size; i++) {
                    System.out.print("R" + (row + 1) + "(" + (i + 1) + ")" + " => " + firstValue + " * " + matrix[row][i] + " - " + secondValue + " * " + matrix[col][i]);
                    matrix[row][i] = matrix[row][i] * firstValue - matrix[col][i] * secondValue;
                    matrix[row][i] = Double.parseDouble(df.format(matrix[row][i]));

                    if (matrix[row][i] > -0.01 && matrix[row][i] < 0.01) {
                        matrix[row][i] = 0;
                    }
                    System.out.println(" = " + matrix[row][i]);
                }
                System.out.print("R" + (row + 1) + "(" + (i + 1) + ")" + " => " + firstValue + " * " + ZVector[row] + " - " + secondValue + " * " + ZVector[col]);
                ZVector[row] = ZVector[row] * firstValue - ZVector[col] * secondValue;
                ZVector[row] = Double.parseDouble(df.format(ZVector[row]));
                System.out.println(" = " + ZVector[row]);
                System.out.println("");

                printMatrix();
            }
        }
    }

    public void findResult() {

        for (int i = size - 1; i >= 0; i--) {
            String text = "";
            String displaytextnumber = "";
            String displaytext = "";
            for (int j = 0; j < size; j++) {

                if (j != i) {
                    text = text + "(" + Double.parseDouble(df.format(matrix[i][j] * result[j])) + ")+";
                    if (matrix[i][j] * result[j] != 0) {
                        displaytext = displaytext + "(" + matrix[i][j] + " * X" + (j + 1) + ") - ";
                        displaytextnumber = displaytextnumber + "(" + matrix[i][j] + " * " + result[j] + ") - ";
                    }
                }

            }

            //Removing the last + sign
            text = text.substring(0, text.length() - 1);
            if (!displaytext.isEmpty()) {
                displaytext = displaytext.substring(0, displaytext.length() - 2);
                displaytextnumber = displaytextnumber.substring(0, displaytextnumber.length() - 2);
            }

            Calculation cal = new Calculation(text);
            double left = Double.parseDouble(cal.getAnswer());
            double value = 0;
            value = ZVector[i] - left;
            result[i] = value / matrix[i][i];
            result[i] = Double.parseDouble(df.format(result[i]));
            value = Double.parseDouble(df.format(value));

            if (left == 0) {
                System.out.println(matrix[i][i] + "X" + (i + 1) + " = " + ZVector[i]);
                System.out.println("X" + (i + 1) + " = " + value + " / " + matrix[i][i]);
                System.out.println("X" + (i + 1) + " = " + result[i] + "\n");
            } else {
                System.out.println(matrix[i][i] + "X" + (i + 1) + " = " + ZVector[i] + " - " + displaytext);
                System.out.println(matrix[i][i] + "X" + (i + 1) + " = " + ZVector[i] + " - " + displaytextnumber);
                System.out.println(matrix[i][i] + "X" + (i + 1) + " = " + ZVector[i] + " - " + left);
                System.out.println("X" + (i + 1) + " = " + value + " / " + matrix[i][i]);
                System.out.println("X" + (i + 1) + " = " + result[i] + "\n");
            }

        }
    }

    private void printMatrix() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.print(":\t" + ZVector[i]);
            System.out.println("");
        }
        System.out.println("");
    }

    private void printEquations() {
        System.out.println("From the matrix, We find the equations are:\n");
        for (int i = 0; i < size; i++) {
            String text = "";
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] != 0) {
                    if (matrix[i][j] == 1) {
                        text = text + "X" + (j + 1) + " + ";
                    } else {
                        text = text + matrix[i][j] + "X" + (j + 1) + " + ";
                    }
                }
            }
            text = text.substring(0, text.length() - 2);
            text = text + " = " + ZVector[i];
            System.out.println(text);
        }
        System.out.println("");
    }

    public void runGaussianElimination() {

        System.out.println("Given That,\nThe Matrix is:\n");
        printMatrix();

        findEquations();
        printEquations();

        System.out.println("From the Equations, We get:\n");
        findResult();

        System.out.println("\nThe Result is");
        for (int i = 0; i < size; i++) {
            System.out.println("X" + (i + 1) + " = " + result[i]);
        }
    }
}
