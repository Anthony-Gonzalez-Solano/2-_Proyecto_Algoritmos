/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.supermarket;

import domain.Supermarket;
import domain.graph.GraphException;
import domain.graph.Place;
import domain.list.ListException;
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

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLModificarSuperMercadoController implements Initializable {

    @FXML
    private ComboBox<Supermarket> cBoxSuperMarkets;
    @FXML
    private TextField txtFieldName;
    @FXML
    private Button btnModify;
    @FXML
    private ComboBox<Place> cBoxPlace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                this.cBoxSuperMarkets.getItems().add((Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
            }
            for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {
                this.cBoxPlace.getItems().add((Place)util.Utility.getmGraphPlace().getVertexByIndex(i).data);
        } 
    }   catch (ListException ex) {    
            Logger.getLogger(FXMLModificarSuperMercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void btnModify(ActionEvent event) throws GraphException, ListException {
        if (txtFieldName.getText().isEmpty()) {//validaciones de campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        }
        util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(this.cBoxSuperMarkets.getValue());
        util.Utility.getlGraphRestaurants_Supermarkets().addVertex(new Supermarket(txtFieldName.getText(), cBoxPlace.getValue().getName()));
    }
    
}
