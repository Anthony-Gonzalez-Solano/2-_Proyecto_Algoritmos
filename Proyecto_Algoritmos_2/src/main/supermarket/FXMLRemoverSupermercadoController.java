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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLRemoverSupermercadoController implements Initializable {
    private util.FileTXT txt;
    @FXML
    private ComboBox<String> cBoxSupermarkets;
    @FXML
    private Button btnRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         txt = new FileTXT();
        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Supermarket.class){
                    Supermarket t=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                this.cBoxSupermarkets.getItems().add(t.getName());
                }
            }
    }   catch (ListException ex) {    
            Logger.getLogger(FXMLModificarSuperMercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnRemove(ActionEvent event) throws GraphException, ListException {
        if (cBoxSupermarkets.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Debe seleccionar un restaurante para poder removerlo");
            a.showAndWait();
        } else {
            
        Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("¿Esta seguro que quiere remover el Supermercado: " + cBoxSupermarkets.getSelectionModel().getSelectedItem() + "?");
                ButtonType yes = new ButtonType("Si");
                ButtonType no = new ButtonType("No");
                a.getButtonTypes().clear();
                a.getButtonTypes().addAll(yes, no);

                Optional<ButtonType> option = a.showAndWait();
                if (option.get() == yes) {
                    
        for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Supermarket.class){
                    Supermarket t=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if(t.getName().equals(this.cBoxSupermarkets.getValue())){
                         int x = cBoxSupermarkets.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                         cBoxSupermarkets.getItems().remove(x); // se remueve
                        cBoxSupermarkets.getSelectionModel().clearSelection();//limpiamos el comboBox
                        util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(t);
                        txt.removeElement("Restaurant_Supermarket.txt", t.toString());
                    }
                }
            }   
            }
        }
    }
}
