package Controller;

import Models.BiSection;
import Models.FalsePosition;
import Models.NewtonRaphson;
import Models.Secant;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        /*
            Supported Functions
            1.log => log(30)
            2.e^2 => e(2)
            3.sin => sin(30)
            4.cos => cos(30)
            5.tan => tan(30)
            6.cot => cot(30)
            7.root => √25
            8.power => 2^2
            9.ln => ln(30)
        
         */
//        String function="-0.6x^2+2.4x+5.5";
//        String function="-26+85x-91x^2+44x^3-8x^4+x^5";
//        String function="4x^3-6x^2+7x-2.3";
//        String function="-13-20x+19x^2-3x^3";
//        String function="x^3-x-1";
//        String function="x^2-3";
//        String function="3x-cos(x)-1";
//        String function="5x^2-14x-3";
        /*
            Bi section and False Position Input
         */
        String function = "28x^5+3x^3-56x^2+20x-320";
        String xl = "1";
        String xu = "2";
        int iterationNumber = 5;

        /*
            Newton Raphson Input
         */
        String functionX = "7x^3-8x+4";
        String functionPrimeX = "21x^2-8";
        String initialRoot = "-1";
        int initialIteration = 0;
        int numberOfIteration = 10;
        
        /*
            Secant Method Input
         */
        String SecantfunctionX = "x^3-0.165x^2+3.993×10^-4";
        String XiMinus1 = "0.02";
        String Xi = "0.05";

        Scanner in = new Scanner(System.in);
        System.out.println("1. Bi Section.");
        System.out.println("2. False Position.");
        System.out.println("3. Newton Raphson.");
        System.out.println("4. Secant Method.");
        System.out.print("Choose Method: ");
        int method = in.nextInt();

        switch (method) {
            case 1:
                BiSection obj = new BiSection(function, xl, xu);
                obj.runBisection(iterationNumber);
                break;
            case 2:
                FalsePosition obj1 = new FalsePosition(function, xl, xu);
                obj1.runBisection(iterationNumber);
                break;
            case 3:
                NewtonRaphson obj3 = new NewtonRaphson(functionX, functionPrimeX, initialIteration, initialRoot);
                obj3.runNewton(numberOfIteration);
                break;
            case 4:
                Secant obj4 = new Secant(SecantfunctionX, XiMinus1, Xi);
                obj4.runSecant(5);
                break;
            default:
                break;
        }

//        Calculation cal=new Calculation("(-21.125)×25");
//        System.out.println(cal.getAnswer());
    }
}