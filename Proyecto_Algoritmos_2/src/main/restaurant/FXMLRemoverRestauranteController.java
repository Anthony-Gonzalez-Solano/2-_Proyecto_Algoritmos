/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.restaurant;

import domain.Restaurant;
import domain.graph.GraphException;
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
 * @author Dell 7470
 */
public class FXMLRemoverRestauranteController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private ComboBox<Restaurant> comboRestaurantes;
    @FXML
    private Button btnRemover;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();

        try {

            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {

                comboRestaurantes.getItems().add((Restaurant)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
            } //recorremos la lista de carreras para agregarlas al comboBox, para poder suprimirlas

        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

   

    @FXML
    private void btnRemover(ActionEvent event) {
        boolean exist=false;
                if (comboRestaurantes.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Debe seleccionar un restaurante para poder removerlo");
            a.showAndWait();
        } else {
            Restaurant r = new Restaurant(comboRestaurantes.getSelectionModel().getSelectedItem().getName(), comboRestaurantes.getSelectionModel().getSelectedItem().getLocation());
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("¿Esta seguro que quiere remover la carrera?");
            ButtonType yes = new ButtonType("Sí");
            ButtonType no = new ButtonType("No");
            a.getButtonTypes().clear();
            a.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> option = a.showAndWait();
            if (option.get() == yes) {
                try {
                    util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(comboRestaurantes.getSelectionModel().getSelectedItem().getName());
                    txt.removeElement("restaurantes.txt", comboRestaurantes.getSelectionModel().getSelectedItem().secondToString());
                    
                    int x = comboRestaurantes.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                    comboRestaurantes.getItems().remove(x); // se remueve
                    comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                    
                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                    a2.setHeaderText(" El restaurante"  +comboRestaurantes.getSelectionModel().getSelectedItem().getName()+ "   ha sido eliminado correctamente");
                    a2.showAndWait();
                } catch (GraphException | ListException ex) {
                    Logger.getLogger(FXMLRemoverRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
 @FXML
    private void comboRestaurantes(ActionEvent event) throws ListException {
        
    }
}
