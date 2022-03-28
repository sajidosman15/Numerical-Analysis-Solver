
package Models;
import Controller.Calculation;
import java.text.DecimalFormat;

public class Method {
    protected String function;
    
    public Method(String function){
        this.function=function;
    }
    
    public String negativeCheck(String val){
        if (val.charAt(0)=='-'){
            val="("+val+")";
        }
        return val;
    }
    
    public String decimalFormat(String val){
        double num=Double.parseDouble(val);
        val=new DecimalFormat("##.###").format(num);
        return val;
    }
    
    public String getFof(String x){
        String text="";      
        for (int i = 0; i < function.length(); i++) {
            if(function.charAt(i)=='x'){
                if(i!=0){
                    if(function.charAt(i-1)!='(' && function.charAt(i-1)!='+' && function.charAt(i-1)!='-' && function.charAt(i-1)!='/' && function.charAt(i-1)!='×'){
                        text=text+"×"+x;
                    }else{
                        text+=x;
                    }
                }else{
                    text+=x;
                }
                
            }else{
                text+=function.charAt(i);
            }
            
        }
        
        System.out.print(text+"  ");
        
        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();
        
        return decimalFormat(value);
    }
    
    public String getFofFalse(String x){
        String text="";      
        for (int i = 0; i < function.length(); i++) {
            if(function.charAt(i)=='x'){
                if(i!=0){
                    if(function.charAt(i-1)!='(' && function.charAt(i-1)!='+' && function.charAt(i-1)!='-' && function.charAt(i-1)!='/' && function.charAt(i-1)!='×'){
                        text=text+"×"+x;
                    }else{
                        text+=x;
                    }
                }else{
                    text+=x;
                }
                
            }else{
                text+=function.charAt(i);
            }
            
        }
        
//        System.out.print(text+"  ");
        
        Calculation cal = new Calculation(text);
        String value = cal.getAnswer();
        
        return decimalFormat(value);
    }
    
    public String getTwoFunctionMulti(String num1,String num2){
        
        System.out.print("=> ");
        String result1=getFof(negativeCheck(num1));
        System.out.print("*  ");
        String result2=getFof((negativeCheck(num2)));
        System.out.print("\n=> ");
        String text=negativeCheck(result1)+"×"+negativeCheck(result2);
        System.out.println(text);
        Calculation cal = new Calculation(text);
        String value = decimalFormat(cal.getAnswer());
        return value;

    }
}
