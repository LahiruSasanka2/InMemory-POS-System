package lk.ijse.dep.fx.util;

public class Validation {
    public static boolean isInteger(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
