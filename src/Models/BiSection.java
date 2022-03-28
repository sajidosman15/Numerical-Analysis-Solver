
package Models;

import Controller.Calculation;

public class BiSection extends Method{
    private String xl;
    private String xu;
    private String xr;
    private String xrold="-99999";

    public BiSection(String function, String xl, String xu) {
        super(function);
        this.xl=xl;
        this.xu=xu;
    }
    
    public String getFofXLandFofXU(){
        return getTwoFunctionMulti(xl,xu);
    }
    
    public String getXR(){
        String text="("+negativeCheck(xl)+"+"+negativeCheck(xu)+")"+"/2";
        System.out.println("=> "+text);
        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();
        this.xr=decimalFormat(value);
        return decimalFormat(value);
    }
    
    public String getFofXLandFofXR(){
        String result= getTwoFunctionMulti(xl,xr);
        return result;
    }
    
    public String getError(){
        
        if(xrold.equals("-99999")){
            xrold=xr;
            return "-";
        }
        else{
            System.out.println("   (Xr old - Xr new)");
            System.out.println("=>|-----------------| ×100");
            System.out.println("        Xr new");
            String text="(("+negativeCheck(xrold)+"-"+negativeCheck(xr)+")/"+negativeCheck(xr)+")×100";
//            System.out.println(text);
            System.out.println("\n     ("+xrold+" - "+xr+")");
            System.out.println("=>|-----------------| ×100");
            System.out.println("        "+xr);
            Calculation cal = new Calculation(text);
            String value = decimalFormat(cal.getAnswer());
            xrold=xr;
            if(value.charAt(0)=='-'){
                value = value.substring( 1, value.length());
            }
            value+="%";
            return value;
        }   
        
    }
    
    public void runBisection(int iterationNumber){
        Table[] table=new Table[iterationNumber];
        String greenBold = "\033[4;31m";
        String blue="\033[4;34m";
        String reset = "\033[0m";
        int j=0;
        for (int i = 1; i < iterationNumber+1; i++) {
            j++;
            table[i-1]=new Table();
            table[i-1].iter=i;
            table[i-1].xl=xl;
            table[i-1].xu=xu;
            System.out.println(greenBold + "Iteration : "+i + reset);
            System.out.println(blue + "\nStep 1:" + reset);
            System.out.println("f(xl) * f(xu) =");
            String mul=getFofXLandFofXU();
            table[i-1].fofxl=getFofFalse(negativeCheck(xl));
            table[i-1].fofxu=getFofFalse(negativeCheck(xu));
            System.out.println("=> "+mul);
            if(Double.parseDouble(mul)>0){
                System.out.println("f(xl) * f(xu) < 0");
                break;
            }
            
            System.out.println(blue + "\nStep 2:" + reset);
            System.out.println("xr = (xl + xu) / 2");
            table[i-1].xr=getXR();
            System.out.println("=> "+table[i-1].xr);
            
            System.out.println("\nError:");
            table[i-1].error=getError();
            System.out.println("=> "+table[i-1].error);
            
            if(table[i-1].error.equalsIgnoreCase("0%")){
                break;
            }
            
            
            System.out.println(blue + "\nStep 3:" + reset);
            System.out.println("f(xl) * f(xr) =");
            String result=getFofXLandFofXR();
            table[i-1].fofxr=getFofFalse(negativeCheck(xr));
            System.out.println("=> "+result);
            double res=Double.parseDouble(result);
            if(res<0){
                this.xu=this.xr;
                System.out.println("\nXu = Xr = "+xr);
            }
            else if(res>0){
                this.xl=this.xr;
                System.out.println("\nXl = Xr = "+xr);
            }
            else{
                break;
            }
            System.out.println("");
        }
        System.out.println("\n");
        System.out.println(greenBold + "****Table****" + reset);
        System.out.println("Iter\tXl\t\tXu\t\tXr\t\tError\t\tf(xl)\t\tf(xu)\t\tf(xr)");
        for (int i = 0; i < j; i++) {
            table[i].printRow();
        }
        System.out.println("");
    }
}
