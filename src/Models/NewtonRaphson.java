package Models;

import Controller.Calculation;
import java.text.DecimalFormat;

public class NewtonRaphson extends Method {

    private final String functionPrimeX;
    private int initialIteration;
    private String oldRoot;
    private String newRoot;

    public String ProcessedFunction;
    public String ProcessedPrimeFunction;
    public String fofXi;
    public String fPrimeofXi;

    public NewtonRaphson(String function, String functionPrimeX, int initialIteration, String oldRoot) {
        super(function);
        this.functionPrimeX = functionPrimeX;
        this.initialIteration = initialIteration;
        this.oldRoot = oldRoot;
    }

    public String calculationFormat(String val) {
        double num = Double.parseDouble(val);
        val = new DecimalFormat("##.###").format(num);
        return val;
    }
    
    public String rootFormat(String val) {
        double num = Double.parseDouble(val);
        val = new DecimalFormat("##.###").format(num);
        return val;
    }
    
    public String errorFormat(String val) {
        double num = Double.parseDouble(val);
        val = new DecimalFormat("##.###").format(num);
        return val;
    }

    public String getFofFunction(String x) {
        String text = "";
        for (int i = 0; i < function.length(); i++) {
            if (function.charAt(i) == 'x') {
                if (i != 0) {
                    if (function.charAt(i-1)!='(' && function.charAt(i - 1) != '+' && function.charAt(i - 1) != '-' && function.charAt(i - 1) != '/' && function.charAt(i - 1) != '×') {
                        text = text + "×" + x;
                    } else {
                        text += x;
                    }
                } else {
                    text += x;
                }

            } else {
                text += function.charAt(i);
            }

        }

        ProcessedFunction = text;

        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();

        return calculationFormat(value);
    }

    public String getFofPrimeFunction(String x) {
        String text = "";
        for (int i = 0; i < functionPrimeX.length(); i++) {
            if (functionPrimeX.charAt(i) == 'x') {
                if (i != 0) {
                    if (function.charAt(i-1)!='(' && functionPrimeX.charAt(i - 1) != '+' && functionPrimeX.charAt(i - 1) != '-' && functionPrimeX.charAt(i - 1) != '/' && functionPrimeX.charAt(i - 1) != '×') {
                        text = text + "×" + x;
                    } else {
                        text += x;
                    }
                } else {
                    text += x;
                }

            } else {
                text += functionPrimeX.charAt(i);
            }

        }
        ProcessedPrimeFunction = text;

        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();

        return calculationFormat(value);
    }

    public String getError() {
        System.out.println("\n      ( X" + (initialIteration + 1) + " - X" + (initialIteration) + " )");
        System.out.println("=>|-----------------| ×100");
        System.out.println("\t  X" + (initialIteration + 1));
        String text = "((" + negativeCheck(oldRoot) + "-" + negativeCheck(newRoot) + ")/" + negativeCheck(newRoot) + ")×100";
        System.out.println("\n     (" + newRoot + " - " + oldRoot + ")");
        System.out.println("=>|-----------------| ×100");
        System.out.println("         " + newRoot);
        Calculation cal = new Calculation(text);
        String value = errorFormat(cal.getAnswer());
        oldRoot = newRoot;
        if (value.charAt(0) == '-') {
            value = value.substring(1, value.length());
        }
        value += "%";
        return value;

    }

    public void findRoot() {
        fofXi = getFofFunction(negativeCheck(oldRoot));
        fPrimeofXi = getFofPrimeFunction(negativeCheck(oldRoot));
        String text = negativeCheck(oldRoot) + "-(" + negativeCheck(fofXi) + "/" + negativeCheck(fPrimeofXi) + ")";
        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();

        this.newRoot = rootFormat(value);

        System.out.println("\t     f(X" + initialIteration + ")");
        System.out.println("X" + (initialIteration + 1) + " = X" + initialIteration + " - ------------");
        System.out.println("\t     f'(X" + initialIteration + ")\n");

        System.out.println("\t       " + ProcessedFunction);
        System.out.println("X" + (initialIteration + 1) + " = " + oldRoot + " - -------------------------------------");
        System.out.println("\t\t" + ProcessedPrimeFunction);
        
        System.out.println("\n\t\t" + fofXi);
        System.out.println("X" + (initialIteration + 1) + " = " + oldRoot + " - --------------");
        System.out.println("\t\t" + fPrimeofXi);

        System.out.println("\nX" + (initialIteration + 1) + " = " + newRoot);
    }

    public void runNewton(int iterationNumber) {
        
        Table[] table=new Table[iterationNumber];
        String greenBold = "\033[4;31m";
        String blue="\033[4;34m";
        String reset = "\033[0m";
        int j=0;
        
        for (int i = 1; i < iterationNumber+1; i++) {
            j++;
            
            table[i-1]=new Table();
            table[i-1].iter=i;
            table[i-1].i=initialIteration;
            table[i-1].xi=oldRoot;
            
            System.out.println(greenBold + "\nIteration : "+i + reset);
            System.out.println(blue + "\nStep 1:" + reset);
            findRoot();
            table[i-1].fofxi=this.fofXi;
            table[i-1].fprimeofxi=this.fPrimeofXi;
            table[i-1].xiplus1=newRoot;

            System.out.println(blue + "\nStep 2:" + reset);
            System.out.println("Error:");
            table[i-1].error=getError();
            System.out.println("\n=> " +table[i-1].error );

            if (table[i-1].error.equalsIgnoreCase("0%")) {
                break;
            }
            initialIteration++;
        }
        
        System.out.println("\n");
        System.out.println(greenBold + "****Table****" + reset);
        System.out.println("Iter\ti\t Xi\t\t   f(Xi)\t\t    f'(Xi)\t\t  Xi+1\t\tError");
        for (int i = 0; i < j; i++) {
            table[i].printRaphsonRow();
        }
        System.out.println("");

    }

}
