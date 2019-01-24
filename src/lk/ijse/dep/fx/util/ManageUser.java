package lk.ijse.dep.fx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;

public class ManageUser {

    public static ArrayList<User> userDatabase = new ArrayList<>();
    public static User loginUser;

    public static boolean validateUser(String userName , String password){
        for (User user : userDatabase) {
            if(user.getUserName().equals(userName)){
                if(user.getPassword().equals(password)){
                    ManageUser.loginUser =user;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean createNewUser(String userType, String userName, String password){

        for(User user : userDatabase){                      // check same user Availability
            if(user.getUserName().equals(userName)){
                new Alert(Alert.AlertType.INFORMATION,"User Name already exist in the system \n Try Different User Name", ButtonType.OK).showAndWait();
                return false;
            }
        }
        if(userType.equals("Admin")){                       // Maximum Number of Admins is 2
            int numberOfAdmins = 0 ;
            for(User user : userDatabase){
                if(user.getUserType().equals("Admin")){
                    numberOfAdmins++;
                }
            }
            if(numberOfAdmins>=2){
                new Alert(Alert.AlertType.INFORMATION,"Maximum Number of admin is two. Can not add more Admin users.", ButtonType.OK).showAndWait();
                return false;
            }
        }

        userDatabase.add(new User(userType, userName,password));        // Adding new customer
        new Alert(Alert.AlertType.INFORMATION,"User Successfully Added to the system", ButtonType.OK).showAndWait();
        return true;
    }

}
