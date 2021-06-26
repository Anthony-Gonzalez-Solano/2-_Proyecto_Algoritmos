/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Comidas;

import domain.Food;
import domain.Restaurant;
import domain.list.ListException;
import domain.tree.TreeException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarComidaController implements Initializable {
 private static int autoID;
 
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private Button btnAgregar;
    @FXML
    private ComboBox<Restaurant> comboRestaurantes;
    private util.FileTXT txt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();

        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                
                comboRestaurantes.getItems().add((Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
            } //recorremos la lista de restaurantes para agregarlas al comboBox
        } catch (ListException ex) {
            Logger.getLogger(FXMLAgregarComidaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
         if (comboRestaurantes.getSelectionModel().isEmpty()|| textFieldNombre.getText().isEmpty()|| textFieldPrecio.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No debe dejar campos vacios, verifique los campos de texto.\n Y que haya elegido un restaurante donde agregar su comida");
            a.showAndWait();
         } else if (util.Utility.getTreeFood().isEmpty()) {
                Food f = new Food(this.textFieldNombre.getText(),Integer.parseInt(textFieldPrecio.getText()),autoID);
                util.Utility.getTreeFood().add(f);
                txt.writeFile("comidas.txt", f.secondToString());// escribimos en los txt
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Comida agregada correctamente");
                a.showAndWait();
                textFieldNombre.setText("");
                textFieldPrecio.setText("");
                //JJ
    }
    }

    @FXML
    private void comboRestaurantes(ActionEvent event) {
        if (comboRestaurantes.getSelectionModel().getSelectedIndex() != -1) {
         
         }
    }

}
