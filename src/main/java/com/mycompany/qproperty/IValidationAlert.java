/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.qproperty;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 *
 * @author Anmol Saru Magar
 * Date :29/3/2024
 * File Name: IValidationAlert.java
 * Purpose :
 * Implements showValidationAlert method that displays alert window when true
 *
 * ******************************************************
 */
public interface IValidationAlert {
    @FXML
    default void showValidationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }
}