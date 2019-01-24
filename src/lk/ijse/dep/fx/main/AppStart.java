package lk.ijse.dep.fx.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lk.ijse.dep.fx.util.ManageUser;
import lk.ijse.dep.fx.util.User;

import java.io.IOException;

public class AppStart extends Application {

    static {
        ManageUser.userDatabase.add(new User("System","system","system"));
        ManageUser.userDatabase.add(new User("Admin","admin","admin"));
        ManageUser.userDatabase.add(new User("User","user","user"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/dep/fx/views/LoginPage.fxml"));
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setTitle("LogIn Page");
        primaryStage.getIcons().add(new Image("/lk/ijse/dep/fx/img/POS LOGO.png"));
    }
}
