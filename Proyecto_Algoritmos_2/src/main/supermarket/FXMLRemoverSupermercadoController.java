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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLRemoverSupermercadoController implements Initializable {

    @FXML
    private ComboBox<Supermarket> cBoxSupermarkets;
    @FXML
    private Button btnRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                this.cBoxSupermarkets.getItems().add((Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
            }
    }   catch (ListException ex) {    
            Logger.getLogger(FXMLModificarSuperMercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnRemove(ActionEvent event) throws GraphException, ListException {
        util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(this.cBoxSupermarkets.getValue());
    }
    
}
