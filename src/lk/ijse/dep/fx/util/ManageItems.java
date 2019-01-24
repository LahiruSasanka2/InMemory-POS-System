package lk.ijse.dep.fx.util;

import java.util.ArrayList;

public class ManageItems {

    public static ArrayList<Item> itemDatabase =  new ArrayList<>();

    public static boolean isFieldsEmpty(String code,String desc , String qyt , String price){
        if(code.trim().isEmpty()||desc.trim().isEmpty()||qyt.trim().isEmpty()||price.trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    public static boolean addItem(String code, String description , String unitPrice, String quantity){
        if(isUserValide(code)){
            Item newItem = new Item(code,description,unitPrice,quantity);
            ManageItems.itemDatabase.add(newItem);
            return true;
        }else {
            return false;
        }
    }



    private static boolean isUserValide(String code){
        for (Item item:ManageItems.itemDatabase) {
            if(item.getCode().equals(code)){
                return false;
            }
        }
        return true;
    }


}
