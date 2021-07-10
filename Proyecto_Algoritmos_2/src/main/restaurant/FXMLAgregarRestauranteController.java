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
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarRestauranteController implements Initializable {

    private static int autoID;
    private util.FileTXT txt;
    @FXML
    private Button btnAgregar;
    @FXML
    private TextField textFieldNombre;

    @FXML
    private ComboBox<String> comboLugares;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT(); // crear txt

        comboLugares.getItems().add("Cachi");
        comboLugares.getItems().add("Caballo Blanco");
        comboLugares.getItems().add("Cartago");
        comboLugares.getItems().add("Cervantes");
        comboLugares.getItems().add("Orosi");
        comboLugares.getItems().add("Paraiso");
        comboLugares.getItems().add("Santa Rosa");
        comboLugares.getItems().add("Tierra Blanca");
        comboLugares.getItems().add("Turrialba");
        comboLugares.getItems().add("Ujarras");

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
                Restaurant r = new Restaurant(this.textFieldNombre.getText(), comboLugares.getSelectionModel().getSelectedItem());
                util.Utility.getlGraphRestaurants_Supermarkets().addVertex(r);
                txt.writeFile("restaurantes.txt", r.secondToString());// escribimos en los txt
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText(" El restaurante " + textFieldNombre.getText() + " fue agregado correctamente!!");
                a.showAndWait();
                textFieldNombre.setText("");
            } else {
                Restaurant r = new Restaurant(this.textFieldNombre.getText(), comboLugares.getSelectionModel().getSelectedItem());
                if (!util.Utility.getlGraphRestaurants_Supermarkets().containsVertex(r)) {
                    util.Utility.getlGraphRestaurants_Supermarkets().addVertex(r);
                    for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {
                            //  Restaurant r2=(Restaurant)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                            //if(!(r2.getName().equals(textFieldNombre.getText()))&&!(r2.getLocation().equals(comboLugares.getSelectionModel().getSelectedItem().getName())))
                            //util.Utility.getlGraphRestaurants_Supermarkets().addEdge(r, r2);

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
                autoID++;
            }
        } catch (GraphException ex) {
            Logger.getLogger(FXMLAgregarRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLAgregarRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void comboLugares(ActionEvent event) {
        if (comboLugares.getSelectionModel().getSelectedIndex() != -1) {

        }
    }

}
