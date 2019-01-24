package lk.ijse.dep.fx.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lk.ijse.dep.fx.controllers.SystemSettingPageController;

import java.util.ArrayList;

public class User {

    // UserType 1 : SystemUser  2 : Admin   3:User

    private String userType;        // "System" "Admin" "User"
    private String userName;
    private String password;
    private Button button = new Button("Delete");

    public User(String userType, String userName, String password) {
        this.userType = userType;
        this.userName = userName;
        this.password = password;
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                for(User user:ManageUser.userDatabase){
                    if(user.getUserName().equals(getUserName())){
                        int x = ManageUser.userDatabase.indexOf(user);
                        ManageUser.userDatabase.remove(x);
                        break;
                    }
                }
                if(ManageUser.loginUser.getUserType().equals("System")){
                    ArrayList<User> copyOfUserDataBase = new ArrayList<>();

                    for (User user1 : ManageUser.userDatabase) {
                        if(!user1.getUserType().equals("System")){
                            copyOfUserDataBase.add(user1);
                        }
                    }

                    ObservableList<User> usersTable = FXCollections.observableArrayList(copyOfUserDataBase);
                    SystemSettingPageController.copyOftblUser.setItems(usersTable);
                    SystemSettingPageController.copyOftblUser.refresh();
                }


                if(ManageUser.loginUser.getUserType().equals("Admin")){
                    ArrayList<User> copyOfUserDataBase = new ArrayList<>();

                    for (User user1 : ManageUser.userDatabase) {
                        if(!user1.getUserType().equals("System")&&!user1.getUserType().equals("Admin")){
                            copyOfUserDataBase.add(user1);
                        }
                    }

                    ObservableList<User> usersTable = FXCollections.observableArrayList(copyOfUserDataBase);
                    SystemSettingPageController.copyOftblUser.setItems(usersTable);
                    SystemSettingPageController.copyOftblUser.refresh();
                }
            }
        });
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
