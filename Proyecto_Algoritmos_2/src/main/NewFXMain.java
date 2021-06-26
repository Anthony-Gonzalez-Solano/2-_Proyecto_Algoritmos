/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Anthony G.S
 */
public class NewFXMain extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            primaryStage = new Stage();
            //Parent root = FXMLLoader.load(getClass().getResource("FXMLSecurity.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("FXMLVentanaPrincipal.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Proyecto , Algoritmos y estructura de datos - 2021 , Ciclo I");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
           
            //util.Utility.fillList();
        } catch (IOException ex) {
            Logger.getLogger(NewFXMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
