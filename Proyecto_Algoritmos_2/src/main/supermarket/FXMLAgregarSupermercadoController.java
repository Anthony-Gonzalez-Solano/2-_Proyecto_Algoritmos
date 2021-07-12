/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.supermarket;

import domain.Restaurant;
import domain.Supermarket;
import domain.graph.GraphException;
import domain.graph.Place;
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
import javafx.scene.layout.AnchorPane;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLAgregarSupermercadoController implements Initializable {

    private Alert a;
    private util.FileTXT txt;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtFieldName;
    @FXML
    private ComboBox<String> cBoxPlace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT(); // crear txt
        cBoxPlace.getItems().add("Cachi");//se agregan los objetos al combobox
        cBoxPlace.getItems().add("Caballo Blanco");
        cBoxPlace.getItems().add("Cartago");
        cBoxPlace.getItems().add("Cervantes");
        cBoxPlace.getItems().add("Orosi");
        cBoxPlace.getItems().add("Paraiso");
        cBoxPlace.getItems().add("Santa Rosa");
        cBoxPlace.getItems().add("Tierra Blanca");
        cBoxPlace.getItems().add("Turrialba");
        cBoxPlace.getItems().add("Ujarras");
        a = new Alert(Alert.AlertType.ERROR);//se declara alerta con estilo personalizado
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
    }

    @FXML
    private void btnAdd(ActionEvent event) throws GraphException, ListException {
        Supermarket t;
        t = new Supermarket(txtFieldName.getText(), cBoxPlace.getValue());
        if (txtFieldName.getText().isEmpty() || cBoxPlace.getValue().equals("")) {//validaciones de campos vacios
            a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {//se asegura que la lista no este vacia y que no contenga el objeto
            if (!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty() && util.Utility.getlGraphRestaurants_Supermarkets().containsVertex(t)) {
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Supermercado ya existe");
                a.showAndWait();
            } else {
                if (util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()) {//si no tiene nada se agrega directamente

                    util.Utility.getlGraphRestaurants_Supermarkets().addVertex(t);
                    txt.writeFile("Restaurant_Supermarket.txt", t.toString());
                    a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Supermercado ingresado correctamente");
                    a.showAndWait();
                    txtFieldName.setText("");
                    cBoxPlace.getSelectionModel().clearSelection();
                } else {
                    util.Utility.getlGraphRestaurants_Supermarkets().addVertex(t);//se agrega a la lista
                    for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {//se unen todas los vertices, se agega un peso
                            if (!util.Utility.getlGraphRestaurants_Supermarkets().containsEdge(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data)) {
                                util.Utility.getlGraphRestaurants_Supermarkets().addEdge(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data);
                                util.Utility.getlGraphRestaurants_Supermarkets().addWeight(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data, util.Utility.random(50) + "km");
                            }
                        }
                    }
                    if (new File("aristas.txt").exists()) {
                        new File("aristas.txt").delete();
                    }
                    txt.writeFile("aristas.txt", util.Utility.getlGraphRestaurants_Supermarkets().toString());//se escribe en el txt
                    txt.writeFile("Restaurant_Supermarket.txt", t.toString());// escribimos en los txt
                    a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("Supermercado ingresado correctamente");
                    a.showAndWait();
                    txtFieldName.setText("");
                    cBoxPlace.getSelectionModel().clearSelection();
                }
            }
        }
    }

}
