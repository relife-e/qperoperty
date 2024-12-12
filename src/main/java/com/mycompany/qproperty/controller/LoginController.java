
package com.mycompany.qproperty.controller;



import com.mycompany.qproperty.App;
import com.mycompany.qproperty.IExitable;
import com.mycompany.qproperty.IValidationAlert;
import com.mycompany.qproperty.model.User;
import com.mycompany.qproperty.model.UserModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;


/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: LoginController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function of Login window. Describes the data flow of between user and model which
 * allows user to login
 *
 * *
 */
public class LoginController implements Initializable, IExitable, IValidationAlert {


    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;
    /**
     * Initializes the controller class.
     */
    
    UserModel model = new UserModel();
    ArrayList <User> users;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    @FXML
    private void loginBtn() throws IOException {
    String firstEntry = usernameField.getText();
    String secondEntry = passwordField.getText();
    boolean login = model.authenticateUser(firstEntry, secondEntry);
    if(login)
    {   
        
        App.setRoot("Main");
        
    }
    else {
        this.validate();
    }
    }
    @FXML
    private void registerBtn() throws IOException {
   
        App.setRoot("Register");
    }
   
    private void validate() {
        this.showValidationAlert("Invalid Credntials");
    }
}
