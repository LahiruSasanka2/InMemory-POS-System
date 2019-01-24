package lk.ijse.dep.fx.util;

import java.util.ArrayList;

public class ManageCustomers {

    public static ArrayList<Customer> customerDatabase = new ArrayList<>();

    public static boolean isFieldEmpty(String id,String name,String address){
        if(id.trim().isEmpty() || name.trim().isEmpty() || address.trim().isEmpty()){
            return true;
        }
        return false;
    }
    public static boolean isCustomerValid(String customerID){
        for (Customer customer: customerDatabase) {
            if(customer.getId().equals(customerID)){
                return false;
            }
        }
        return true;
    }
    public static boolean addCustomer(String id, String name, String address){
        if(isCustomerValid(id)){
            Customer newCustomer = new Customer(id, name, address);
            customerDatabase.add(newCustomer);
            return true;
        }else{
            return false;
        }
    }

}
