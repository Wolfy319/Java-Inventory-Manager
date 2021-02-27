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
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import CS3250.UserAuthenticator;
import CS3250.UserData;

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
    private String userName;

    @FXML
    private String passWord;

    //Retrieves inputted user name
    @FXML
    public String get_User(){
        userName = user_IN.getText();
        return userName;
    }

    //Retrieves inputted password
    @FXML
    public String get_Pass(){
        passWord = pass_IN.getText();
        return passWord;
    }

    public boolean authenticated(String attemptedUser, String attemptedPass) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	UserData data = new UserData();
    	try{
    		data.initializeDatabase("jdbc:mysql://216.137.177.30:3306/testDB?allowPublicKeyRetrieval=true&useSSL=false team3 UpdateTrello!1");
    	} catch(Exception e) {
    		System.out.print("Unable to connect to database");  	
    	}
    	
    	return UserAuthenticator.authenticate(attemptedUser, attemptedPass, data);
    }

    // Allows the window to be exited
    @FXML
    public void exit_Button(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void signIn_button(ActionEvent event) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    	String username = get_User();
    	String password = get_Pass();
    	if(authenticated(username,password)) {
    		// TODO - FXMLLoader can't find DBScreen for some reason
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

