package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    public Button exitBtn;

    @FXML
    public Button signBtn;

    @FXML
    public TextField user_IN;
    
    @FXML
    public PasswordField pass_IN;

    @FXML
    public String userName;

    @FXML
    public String passWord;

    //Retrieves inputted user name
    @FXML
    public String get_User(){
        userName = exitBtn.getText();
        return userName;
    }

    //Retrieves inputted password
    @FXML
    public String get_Pass(){
        passWord = signBtn.getText();
        return passWord;
    }

    public boolean authenticated(String attemptedUser, String attemptedPass) {
    	return false;
    }

    // Allows the window to be exited
    @FXML
    public void exit_Button(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void signIn_button(ActionEvent event) throws IOException {
    	String username = get_User();
    	String password = get_Pass();
    	if(authenticated(username,password)) {
    		Parent DbsScreen = FXMLLoader.load(getClass().getResource("DBScreen.fxml"));
            Scene DbsScene = new Scene(DbsScreen);
            Stage dbsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dbsStage.setScene(DbsScene);
            dbsStage.show();
    	}
    	else {
    		Stage stage = (Stage) signBtn.getScene().getWindow();
            stage.close();
    	}
    }

}

