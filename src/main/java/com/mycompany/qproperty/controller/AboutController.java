/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar
 * File Name:AboutController.java
 * Date :6/7/2024
 * Purpose :
 * Describes the menu button on about view which sends user to main window

 * ******************************************************
 */
public class AboutController implements Initializable{
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
  
    @FXML
    private void menu() throws IOException {
        App.setRoot("Main");
    }

}
