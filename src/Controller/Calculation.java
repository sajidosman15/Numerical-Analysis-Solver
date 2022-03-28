package Controller;

import java.text.DecimalFormat;

public class Calculation {

    private String text;
    private boolean error = false;

    public Calculation(String text) {
        this.text = text;
    }

    public String getAnswer() {
        double ans = 0;
        if(text.contains("^-")){
            text=text.replace("^-", "^~");
        }
        /*Change the minus sign
         */
        if (text.contains("-")) {
            text = text.replace("-", "−");
        }
        /*If text contain brackets then remove the brackets
         */
        if (text.contains("(") || text.contains(")")) {
            text = firstBracketCheck(text);//reducing first bracket
            if (text.contains(".")) {
                text = text.replace(".", "·");
            }

            ans = calculate(text);//calculating final ans without the bracket
        } else {
            ans = calculate(text);
        }
        if (error == true) {
            return "Error";
        }
        return ans + "";

    }

    private String firstBracketCheck(String text) {
        try {
            int firstindex = -1, lastindex = -1;
            String texts1, texts2, texts3;
            /*Identify the indexes of brackets and
            separate them in different module
             */
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '(') {
                    firstindex = i;
                }
                if (text.charAt(i) == ')') {
                    lastindex = i;
                    break;
                }
            }

            texts2 = text.substring(firstindex + 1, lastindex);//texts inside the bracket is storing here

            /*if bracket is not the first character
            then store those first characters
             */
            if (firstindex != 0) {
                texts1 = text.substring(0, firstindex);
            } else {
                texts1 = "";
            }

            /*if brackets is not the last character then
            store those last characters
             */
            if (lastindex != text.length()) {
                texts3 = text.substring(lastindex + 1, text.length());
            } else {
                texts3 = "";
            }

            /*if the last character of the first part of string does not
            contain a symbol then add multiplication there
             */
            int txt1lngt = texts1.length();
            if (txt1lngt != 0) {
                char lastchar = texts1.charAt(txt1lngt - 1);
                if (lastchar >= '0' && lastchar <= '9') {
                    texts1 += "×";
                }
            }

            double ans = calculate(texts2);//calculate the values of string that was inside the bracket
            text = texts1 + ans + texts3;//marge part1,value of bracket and part3

            /*if the text contain more brackets then again pass it 
            to the function untill all brackets are reduced
             */
            if (text.contains("(") || text.contains(")")) {
                if (text.contains(".")) {
                    text = text.replace(".", "·");
                }
                text = firstBracketCheck(text);
            }
            return text;
        } catch (Exception ex) {
            error = true;
            return 0 + "";
        }
    }

    private double calculate(String text) {
        try {
            if (text.charAt(0) == '+') {//if the first character is plus then remove it
                text = text.substring(1, text.length());
            } else if (text.charAt(0) == '−') {//if the first character is minus then remove it
                text = "0" + text;
            }

            /*separate the parts of before sign and after sign
            then calculate them.
             */
            if (text.contains("+")) {
                String texts1 = text.substring(0, text.lastIndexOf("+"));
                String texts2 = text.substring(text.lastIndexOf("+") + 1, text.length());
                double number1 = calculate(texts1);
                double number2 = calculate(texts2);
                return number1 + number2;
            } else if (text.contains("−")) {
                String texts1 = text.substring(0, text.lastIndexOf("−"));
                String texts2 = text.substring(text.lastIndexOf("−") + 1, text.length());
                double number1 = calculate(texts1);
                double number2 = calculate(texts2);
                return number1 - number2;
            } else if (text.contains("×")) {
                String texts1 = text.substring(0, text.lastIndexOf("×"));
                String texts2 = text.substring(text.lastIndexOf("×") + 1, text.length());
                double number1 = calculate(texts1);
                double number2 = calculate(texts2);
                return number1 * number2;
            } else if (text.contains("/")) {
                String texts1 = text.substring(0, text.lastIndexOf("/"));
                String texts2 = text.substring(text.lastIndexOf("/") + 1, text.length());
                double number1 = calculate(texts1);
                double number2 = calculate(texts2);
                return number1 / number2;
            } else if (text.contains("%")) {
                String texts1 = text.substring(0, text.lastIndexOf("%"));
                String texts2 = text.substring(text.lastIndexOf("%") + 1, text.length());
                double val = calculate(texts1);
                val = val / 100;
                if (!texts2.isEmpty()) {
                    switch (texts2.charAt(0)) {
                        case '√':
                            texts2 = val + "×" + texts2;//if character after % is a root
                            break;
                        case '^':
                            texts2 = val + texts2;//if character after % is a square
                            break;
                        default:
                            error = true;
                            return 0;
                    }
                    val = calculate(texts2);
                }
                return val;
            } else if (text.contains("ln")) {
                String texts1 = text.substring(text.lastIndexOf("n") + 1, text.length());
                double val = calculate(texts1);
                val=Math.log(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            }else if (text.contains("log")) {
                String texts1 = text.substring(text.lastIndexOf("g") + 1, text.length());
                double val = calculate(texts1);
                val=Math.log10(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            }else if (text.contains("e")) {
                String texts1 = text.substring(text.lastIndexOf("e") + 1, text.length());
                double val = calculate(texts1);
                val=Math.exp(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            }else if (text.contains("sin")) {
                String texts1 = text.substring(text.lastIndexOf("n") + 1, text.length());
                double val = calculate(texts1);
                val = Math.toRadians(val);
                val=Math.sin(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            } 
            else if (text.contains("cos")) {
                String texts1 = text.substring(text.lastIndexOf("s") + 1, text.length());
                double val = calculate(texts1);
                val = Math.toRadians(val);
                val=Math.cos(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            }else if (text.contains("tan")) {
                String texts1 = text.substring(text.lastIndexOf("n") + 1, text.length());
                double val = calculate(texts1);
                val = Math.toRadians(val);
                val=Math.tan(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            }else if (text.contains("cot")) {
                String texts1 = text.substring(text.lastIndexOf("t") + 1, text.length());
                double val = calculate(texts1);
                val = Math.toRadians(val);
                val=Math.tan(val);
                String format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                val=1.0/val;
                format=new DecimalFormat("##.###").format(val);
                val=Double.parseDouble(format);
                return val;  
            }else if (text.contains("√")) {
                String texts1 = text.substring(text.lastIndexOf("√") + 1, text.length());
                String texts2 = text.substring(0, text.lastIndexOf("√"));
                if (texts1.charAt(0) == '^') {//if character after root is a square
                    error = true;
                    return 0;
                }
                double val = calculate(texts1);
                val = Math.sqrt(val);
                if (!texts2.isEmpty()) {
                    if (texts2.charAt(texts2.length() - 1) == '√') {//if character before root is a root
                        texts2 += val;
                    } else {//if character before root is a number or square
                        texts2 += "×" + val;
                    }
                    val = calculate(texts2);
                }
                return val;
            } else if (text.contains("^")) {
                if(text.contains("^~")){
                    text=text.replace("^~", "^-");
                }
                String texts1 = text.substring(0, text.lastIndexOf("^"));
                String texts2 = text.substring(text.lastIndexOf("^") + 1, text.length());
                double val = calculate(texts1);
                double power = calculate(texts2);
                val = Math.pow(val, power);
                return val;
            } else {
                /*base case. replace the point and convert string to double
                 */
                if (text.contains("·")) {
                    text = text.replace("·", ".");
                }
                return Double.parseDouble(text);
            }

        } catch (Exception ex) {
            error = true;
            return 0;
        }
    }
}
