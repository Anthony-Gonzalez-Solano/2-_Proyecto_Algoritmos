/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.restaurant;

import domain.Restaurant;
import domain.graph.EdgeWeight;
import domain.graph.GraphException;
import domain.list.ListException;
import java.io.File;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLAgregarRestauranteController implements Initializable {

    private Alert a5;
    private EdgeWeight ew2;
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
        a5 = new Alert(Alert.AlertType.ERROR);
        a5.setContentText(" ");
        DialogPane dp = a5.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        comboLugares.getItems().add("Cachí");
        comboLugares.getItems().add("Caballo Blanco");//agregamos los lugares
        comboLugares.getItems().add("Cartago");
        comboLugares.getItems().add("Cervantes");
        comboLugares.getItems().add("Orosi");
        comboLugares.getItems().add("Paraíso");
        comboLugares.getItems().add("Santa Rosa");
        comboLugares.getItems().add("Tierra Blanca");
        comboLugares.getItems().add("Turrialba");
        comboLugares.getItems().add("Ujarrás");

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

            } else if (util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()) {// comprobacion para que no se caiga cuando no hay elementos
                Restaurant r = new Restaurant(this.textFieldNombre.getText(), comboLugares.getSelectionModel().getSelectedItem());
                util.Utility.getlGraphRestaurants_Supermarkets().addVertex(r);
                txt.writeFile("Restaurant_Supermarket.txt", r.secondToString());// escribimos en los txt
                a5.setAlertType(Alert.AlertType.CONFIRMATION);
                a5.setHeaderText(" El restaurante " + textFieldNombre.getText() + " fue agregado correctamente!!");
                a5.setContentText(" ");
                a5.showAndWait();
                textFieldNombre.setText("");

            } else {

                Restaurant r = new Restaurant(this.textFieldNombre.getText(), comboLugares.getSelectionModel().getSelectedItem());
                if (!util.Utility.getlGraphRestaurants_Supermarkets().containsVertex(r)) { // si no existe el vertice en el grafo se agrega
                    util.Utility.getlGraphRestaurants_Supermarkets().addVertex(r);
                    for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {
                            if (!util.Utility.getlGraphRestaurants_Supermarkets().containsEdge(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data)) {// agregamos las aristas y pesos a los vertices
                                util.Utility.getlGraphRestaurants_Supermarkets().addEdge(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data);
                                util.Utility.getlGraphRestaurants_Supermarkets().addWeight(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data, util.Utility.random(50) + "km");
                            }
                        }
                    }
                }
//       
                if (new File("aristas.txt").exists()) {// borramos el txt para que no se sobrecargue y que no aparescan las anteriores aristas
                    new File("aristas.txt").delete();
                }
                txt.writeFile("aristas.txt", util.Utility.getlGraphRestaurants_Supermarkets().toString());
                txt.writeFile("Restaurant_Supermarket.txt", r.secondToString());// escribimos en los txt

                a5.setAlertType(Alert.AlertType.CONFIRMATION);
                a5.setHeaderText(" El restaurante " + textFieldNombre.getText() + " fue agregado correctamente");//mensaje
                a5.setContentText(" ");
                a5.showAndWait();
                comboLugares.getSelectionModel().clearSelection();
                textFieldNombre.setText("");
                autoID++;
            }
        } catch (GraphException ex) {
            Logger.getLogger(FXMLAgregarRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("No hay restaurantes agregados");
            a5.setContentText(" ");
            a5.showAndWait();
        }
    }

    @FXML
    private void comboLugares(ActionEvent event) {
        if (comboLugares.getSelectionModel().getSelectedIndex() != -1) {

        }
    }

}
