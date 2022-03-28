
package Models;

public class Table {
    public int iter;
    public String xl;
    public String xu;
    public String xr;
    public String error;
    public String fofxl;
    public String fofxu;
    public String fofxr;
    
    public int i;
    public String xi;
    public String xiMinus1;
    public String fofxi;
    public String fprimeofxi;
    public String xiplus1;
    
    public void printRow(){
        System.out.println(iter+"\t"+xl+"\t\t"+xu+"\t\t"+xr+"\t\t"+error+"\t\t"+fofxl+"\t\t"+fofxu+"\t\t"+fofxr);
    }
    
    public void printRaphsonRow(){
        System.out.println(iter+"\t"+i+"      X"+i+" = "+xi+"     \tf(X"+i+") = "+fofxi+"\t\tf'(X"+i+") = "+fprimeofxi+"\t\tX"+(i+1)+" = "+xiplus1+"    \t"+error);
    }
    
    public void printSecantRow(){
        System.out.println("    "+iter+"\t\t "+xiMinus1+"   \t  "+xi+"  \t    "+xiplus1+"\t\t"+error);
    }
}
