/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.qproperty;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
/**
 *
 * @author Anmol Saru Magar
 * File Name:IExitable.java
 * Date :31/3/2024
 * Purpose :
 * IExitable implements exitClick method that allows user if they want to exit the application or not
 *
 * ******************************************************
 */
    //exitable interface that prompts new window to exit
    public interface IExitable {
    @FXML
    default void exitClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Close?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
              Platform.exit();
            }
        });
    }
   
    }

