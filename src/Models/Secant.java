package Models;

import Controller.Calculation;
import java.text.DecimalFormat;

public class Secant extends Method {

    private String XiMinus1;
    private String Xi;
    private String XiPlus1;
    private int initialIteration=0;

    public Secant(String function, String XiMinus1, String Xi) {
        super(function);
        this.XiMinus1 = XiMinus1;
        this.Xi = Xi;
    }

    public String calculationFormat(String val) {
        double num = Double.parseDouble(val);
        val = new DecimalFormat("##.#######").format(num);
        return val;
    }
    
    public String rootFormat(String val) {
        double num = Double.parseDouble(val);
        val = new DecimalFormat("##.#####").format(num);
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
                    if (function.charAt(i - 1) != '(' && function.charAt(i - 1) != '+' && function.charAt(i - 1) != '-' && function.charAt(i - 1) != '/' && function.charAt(i - 1) != '×') {
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

//        ProcessedFunction = text;
        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();

        return calculationFormat(value);
    }

    public String getError() {
        System.out.println("\n      ( X" + (initialIteration + 1) + " - X" + (initialIteration) + " )");
        System.out.println("=>|-----------------| ×100");
        System.out.println("\t  X" + (initialIteration + 1));
        String text = "((" + negativeCheck(Xi) + "-" + negativeCheck(XiPlus1) + ")/" + negativeCheck(XiPlus1) + ")×100";
        System.out.println("\n     (" + XiPlus1 + " - " + Xi + ")");
        System.out.println("=>|-----------------| ×100");
        System.out.println("         " + XiPlus1);
        Calculation cal = new Calculation(text);
        String value = errorFormat(cal.getAnswer());
        XiMinus1=Xi;
        Xi = XiPlus1;
        if (value.charAt(0) == '-') {
            value = value.substring(1, value.length());
        }
        value += "%";
        return value;

    }
    
    public void findRoot(){
        System.out.println("\n             f(X"+initialIteration+") * (X"+initialIteration+" - X"+(initialIteration-1)+")");
        System.out.println("X"+(initialIteration + 1)+" = "+"X"+initialIteration+" - -------------------------");
        System.out.println("               f(X"+initialIteration+") - f(X"+(initialIteration-1)+")");
        
        
        System.out.println("\n\t       "+negativeCheck(getFofFunction(negativeCheck(Xi)))+" * ("+negativeCheck(Xi)+" - "+negativeCheck(XiMinus1)+")");
        System.out.println("X"+(initialIteration + 1)+" = "+Xi+" - -----------------------------");
        System.out.println("                 "+negativeCheck(getFofFunction(negativeCheck(Xi)))+" - "+negativeCheck(getFofFunction(negativeCheck(XiMinus1))));
        
        
        String text=negativeCheck(Xi)+"-(("+negativeCheck(getFofFunction(negativeCheck(Xi)))+"×("+negativeCheck(Xi)+"-"+negativeCheck(XiMinus1)+"))/("+negativeCheck(getFofFunction(negativeCheck(Xi)))+"-"+negativeCheck(getFofFunction(negativeCheck(XiMinus1)))+"))";
                
                
//        System.out.println("=> "+text);
        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();
        this.XiPlus1=rootFormat(value);
        System.out.println("\nX" + (initialIteration + 1) + " = " + XiPlus1);
    }
    
    public void runSecant(int iterationNumber){
        
        System.out.println("\n\nGiven That");
        System.out.println("f(x) = "+function);
        System.out.println("Xi-1 = "+XiMinus1);
        System.out.println("Xi = "+Xi+"\n");
        
        Table[] table=new Table[iterationNumber];
        String greenBold = "\033[4;31m";
        String blue="\033[4;34m";
        String reset = "\033[0m";
        int j=0;
        
        for (int i = 1; i < 5+1; i++) {
            j++;
            
            table[i-1]=new Table();
            table[i-1].iter=i;
            table[i-1].xi=Xi;
            table[i-1].xiMinus1=XiMinus1;
            
            System.out.println(greenBold + "\nIteration : "+i + reset);
            System.out.println(blue + "\nStep 1:" + reset);
            findRoot();
            table[i-1].xiplus1=XiPlus1;

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
        System.out.println("Iteration\t Xi-1\t\t   Xi\t\t    Xi+1\t\tError");
        for (int i = 0; i < j; i++) {
            table[i].printSecantRow();
        }
        System.out.println("");
    }

}
