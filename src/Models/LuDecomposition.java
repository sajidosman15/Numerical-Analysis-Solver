package Models;

import Controller.Calculation;
import java.text.DecimalFormat;

public class LuDecomposition {

    private double matrix[][];
    private double Umatrix[][];
    private double Lmatrix[][];
    private double ZVector[];
    private double d[];
    private double result[];
    private int size;
    private static final DecimalFormat df = new DecimalFormat("0.000");

    public LuDecomposition(double matrix[][], double ZVector[], int size) {
        this.matrix = matrix;
        this.ZVector = ZVector;
        this.size = size;
        this.Umatrix = new double[size][size];
        this.Lmatrix = new double[size][size];
        this.d = new double[size];
        this.result = new double[size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, this.Umatrix[i], 0, size);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.Lmatrix[i][j] = 0;
            }
        }
        for (int i = 0; i < size; i++) {
            this.Lmatrix[i][i] = 1;
            this.d[i] = 0;
            this.result[i] = 1;
        }

    }

    public void findUandLMatrix() {
        System.out.println("Step : 1\nFind the [U] Matrix\n");
        for (int col = 0; col < size - 1; col++) {
            for (int row = col + 1; row < size; row++) {
                double val = Umatrix[row][col] / Umatrix[col][col];
                val = Double.parseDouble(df.format(val));
                System.out.println(Umatrix[row][col] + " / " + Umatrix[col][col] + " = " + val);
                System.out.println("Row" + (row + 1) + " - Row" + (col + 1) + "*(" + val + ") =>");
                Lmatrix[row][col] = val;
                for (int i = col; i < size; i++) {
                    Umatrix[row][i] = Umatrix[row][i] - (Umatrix[col][i] * val);
                    Umatrix[row][i] = Double.parseDouble(df.format(Umatrix[row][i]));
                    if (Umatrix[row][i] > -0.01 && Umatrix[row][i] < 0.01) {
                        Umatrix[row][i] = 0;
                    }
                }

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        System.out.print(Umatrix[i][j] + "\t");
                    }
                    System.out.println("");
                }
                System.out.println("");
            }
        }
    }

    public void findValueOfD() {

        for (int i = 0; i < size; i++) {
            String text = "";
            String displaytextnumber = "";
            String displaytext = "";
            for (int j = 0; j < size; j++) {
                text = text + "(" + Double.parseDouble(df.format(Lmatrix[i][j] * d[j])) + ")";
                if (Lmatrix[i][j] * d[j] != 0) {
                    displaytext = displaytext + "(" + Lmatrix[i][j] + " * d" + (j + 1) + ") - ";
                    displaytextnumber = displaytextnumber + "(" + Lmatrix[i][j] + " * " + d[j] + ") - ";
                }

                if (j != size - 1) {
                    text = text + "+";
                }
            }

            if (!displaytext.isEmpty()) {
                displaytext = displaytext.substring(0, displaytext.length() - 2);
                displaytextnumber = displaytextnumber.substring(0, displaytextnumber.length() - 2);
            }

            Calculation cal = new Calculation(text);
            double value = Double.parseDouble(cal.getAnswer());

            if (value == 0) {
                d[i] = ZVector[i];
                System.out.println("d" + (i + 1) + " = " + d[i] + "\n");
            } else {
                d[i] = ZVector[i] - value;
                System.out.println("d" + (i + 1) + " = " + ZVector[i] + " - " + displaytext);
                System.out.println("d" + (i + 1) + " = " + ZVector[i] + " - " + displaytextnumber);
                System.out.println("d" + (i + 1) + " = " + ZVector[i] + " - " + value);
                System.out.println("d" + (i + 1) + " = " + df.format(d[i]) + "\n");
            }

            d[i] = Double.parseDouble(df.format(d[i]));
        }
    }

    public void findResult() {

        for (int i = size - 1; i >= 0; i--) {
            String text = "";
            String displaytextnumber = "";
            String displaytext = "";
            for (int j = 0; j < size; j++) {

                if (j != i) {
                    text = text + "(" + Double.parseDouble(df.format(Umatrix[i][j] * result[j])) + ")+";
                    if (Umatrix[i][j] * result[j] != 0) {
                        displaytext = displaytext + "(" + Umatrix[i][j] + " * X" + (j + 1) + ") - ";
                        displaytextnumber = displaytextnumber + "(" + Umatrix[i][j] + " * " + result[j] + ") - ";
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
            value = d[i] - left;
            result[i] = value / Umatrix[i][i];
            result[i] = Double.parseDouble(df.format(result[i]));
            value=Double.parseDouble(df.format(value));

            if (left == 0) {
                System.out.println(Umatrix[i][i] + "X" + (i + 1) + " = " + d[i]);
                System.out.println("X" + (i + 1) + " = " + value + " / " + Umatrix[i][i]);
                System.out.println("X" + (i + 1) + " = " + result[i] + "\n");
            } else {
                System.out.println(Umatrix[i][i] + "X" + (i + 1) + " = " + d[i]+" - "+displaytext);
                System.out.println(Umatrix[i][i] + "X" + (i + 1) + " = " + d[i]+" - "+displaytextnumber);
                System.out.println(Umatrix[i][i] + "X" + (i + 1) + " = " + d[i] + " - " + left);
                System.out.println("X" + (i + 1) + " = " + value + " / " + Umatrix[i][i]);
                System.out.println("X" + (i + 1) + " = " + result[i] + "\n");
            }

        }
    }

    public void runLuDecomposition() {
        findUandLMatrix();

        System.out.println("The Final [U] Matrix is :");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(Umatrix[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");

        System.out.println("Step : 2\nThe [L] Matrix is :");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(Lmatrix[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");

        System.out.println("Step : 3");
        System.out.println("We Know [L]*[D]=[B]");
        int middle = (int) Math.ceil((float) size / 2);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(Lmatrix[i][j] + "\t");
            }
            if (i == middle - 1) {
                System.out.print("*");
            }
            System.out.print("\td" + (i + 1) + "\t");
            if (i == middle - 1) {
                System.out.print("=");
            }
            System.out.println("\t" + ZVector[i]);
        }
        System.out.println("");

        for (int i = 0; i < size; i++) {
            String text = "";
            for (int j = 0; j < size; j++) {
                if (Lmatrix[i][j] != 0) {
                    if (Lmatrix[i][j] == 1) {
                        text = text + "d" + (j + 1) + " + ";
                    } else {
                        text = text + Lmatrix[i][j] + "d" + (j + 1) + " + ";
                    }
                }
            }
            text = text.substring(0, text.length() - 2);
            text = text + " = " + ZVector[i];
            System.out.println(text);
        }
        System.out.println("");

        findValueOfD();

        System.out.println("From The Equations we find");
        for (int i = 0; i < size; i++) {
            System.out.println("d" + (i + 1) + " = " + d[i]);
        }

        System.out.println("\nStep : 4");
        System.out.println("Again, We Know [U]*[X]=[D]");
        middle = (int) Math.ceil((float) size / 2);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(Umatrix[i][j] + "\t");
            }
            if (i == middle - 1) {
                System.out.print("*");
            }
            System.out.print("\tX" + (i + 1) + "\t");
            if (i == middle - 1) {
                System.out.print("=");
            }
            System.out.println("\t" + d[i]);
        }
        System.out.println("");

        for (int i = 0; i < size; i++) {
            String text = "";
            for (int j = 0; j < size; j++) {
                if (Umatrix[i][j] != 0) {
                    if (Umatrix[i][j] == 1) {
                        text = text + "X" + (j + 1) + " + ";
                    } else {
                        text = text + Umatrix[i][j] + "X" + (j + 1) + " + ";
                    }
                }
            }
            text = text.substring(0, text.length() - 2);
            text = text + " = " + d[i];
            System.out.println(text);
        }
        System.out.println("");

        findResult();
        
        System.out.println("\nThe Result is");
        for (int i = 0; i < size; i++) {
            System.out.println("X" + (i + 1) + " = " + result[i]);
        }
    }
}
