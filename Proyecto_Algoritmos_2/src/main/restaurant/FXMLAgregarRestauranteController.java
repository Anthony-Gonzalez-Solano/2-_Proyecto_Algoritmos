/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.restaurant;

import domain.Restaurant;
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
import main.Comidas.FXMLAgregarComidaController;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarRestauranteController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField textFieldNombre;
    private TextField textFieldLocation;
    @FXML
    private ComboBox<Place> comboLugares;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT(); // crear txt

        try {
            for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {

                comboLugares.getItems().add((Place) util.Utility.getmGraphPlace().getVertexByIndex(i).data);
            } //recorremos la lista de restaurantes para agregarlas al comboBox
        } catch (ListException ex) {
            Logger.getLogger(FXMLAgregarComidaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        try {
            if (textFieldNombre.getText().isEmpty() || comboLugares.getSelectionModel().isEmpty()) { // si algun campo de texto esta vacio, mandara una alerta

                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No debe dejar campos vacios");
                a.showAndWait();
                textFieldNombre.setText("");
                comboLugares.getSelectionModel().clearSelection();

            } else if (util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()) {
                Restaurant r = new Restaurant(this.textFieldNombre.getText(), comboLugares.getSelectionModel().getSelectedItem().getName());
                util.Utility.getlGraphRestaurants_Supermarkets().addVertex(r);
                txt.writeFile("restaurantes.txt", r.secondToString());// escribimos en los txt
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Restaurante agregado correctamente");
                a.showAndWait();
                textFieldLocation.setText("");
                textFieldNombre.setText("");
            } else {
                Restaurant r = new Restaurant(this.textFieldNombre.getText(), this.textFieldLocation.getText());
                if (!util.Utility.getlGraphRestaurants_Supermarkets().containsVertex(r)) {
                    util.Utility.getlGraphRestaurants_Supermarkets().addVertex(r);
                    for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {

                            if (!(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.equals(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data))) {
                                util.Utility.getlGraphRestaurants_Supermarkets().addEdge(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data);
                                util.Utility.getlGraphRestaurants_Supermarkets().addWeight(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data, util.Utility.random());
                            }
                        }
                    }

                }
                txt.writeFile("restaurantes.txt", r.secondToString());// escribimos en los txt
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Restaurante agregado correctamente");
                a.showAndWait();
               comboLugares.getSelectionModel().clearSelection();
                textFieldNombre.setText("");

            }

        } catch (GraphException | ListException ex) {
            Logger.getLogger(FXMLAgregarRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void comboLugares(ActionEvent event) {
         if (comboLugares.getSelectionModel().getSelectedIndex() != -1) {
         
         }
    }

}
