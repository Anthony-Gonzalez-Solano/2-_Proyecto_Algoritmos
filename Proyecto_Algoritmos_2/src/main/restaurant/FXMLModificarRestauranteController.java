/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.restaurant;

import domain.Restaurant;
import domain.list.ListException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLModificarRestauranteController implements Initializable {

    private FileTXT txt;
    @FXML
    private ComboBox<Restaurant> comboRestaurantes;
    @FXML
    private Button btnModificar;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private Label txtNombre;
    @FXML
    private TextField textFieldLocation;
    private Alert a5;
    @FXML
    private ComboBox<String> cBoxPlace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            textFieldNombre.setVisible(true);
            a5 = new Alert(Alert.AlertType.ERROR);
            a5.setContentText(" ");
            DialogPane dp = a5.getDialogPane();
            dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dp.getStyleClass().add("myDialog");
            cBoxPlace.getItems().add("Cachi");//se agregan los lugares al combobox
            cBoxPlace.getItems().add("Caballo Blanco");
            cBoxPlace.getItems().add("Cartago");
            cBoxPlace.getItems().add("Cervantes");
            cBoxPlace.getItems().add("Orosi");
            cBoxPlace.getItems().add("Paraiso");
            cBoxPlace.getItems().add("Santa Rosa");
            cBoxPlace.getItems().add("Tierra Blanca");
            cBoxPlace.getItems().add("Turrialba");
            cBoxPlace.getItems().add("Ujarras");
            txt = new FileTXT();
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Restaurant.class) {
                    comboRestaurantes.getItems().add((Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
                }
            }
        } catch (ListException ex) {
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("No hay restaurantes agregados");
            a5.setContentText(" ");
            a5.showAndWait();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldNombre.getText().isEmpty() || comboRestaurantes.getSelectionModel().isEmpty()||cBoxPlace.getSelectionModel().isEmpty()) { //validamos campos vacios
            a5.setAlertType(Alert.AlertType.INFORMATION);
            a5.setHeaderText("Debe ingresar un restaurante para poder modificarlo");
            a5.showAndWait();
            a5.setAlertType(Alert.AlertType.ERROR);
        } else {
            Restaurant r = new Restaurant(textFieldNombre.getText(), cBoxPlace.getValue());
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Restaurant) {
                        Restaurant r2 = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                        if (r2.getClass() == Restaurant.class) {
                            if (r2.equals(comboRestaurantes.getSelectionModel().getSelectedItem())) {
                                util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data = r;
                            }
                        }
                    }
                }
                txt.modifyFile("Restaurant_Supermarket.txt", comboRestaurantes.getSelectionModel().getSelectedItem().secondToString(), r.secondToString());// se modifica el archivo
                int x = comboRestaurantes.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                comboRestaurantes.getItems().remove(x); // se remueve
                comboRestaurantes.getItems().add(x, r);// se agregan de nuevo al comBox el restaurante modificado
                comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                cBoxPlace.getSelectionModel().clearSelection();
                textFieldNombre.setText("");

                a5.setAlertType(Alert.AlertType.CONFIRMATION);
                a5.setHeaderText("El  restaurante ha sido mofificado correctamente");
                a5.setContentText(" ");
                a5.showAndWait();
                

            } catch (ListException e) {
                a5.setAlertType(Alert.AlertType.ERROR);
                a5.setHeaderText("No hay restaurantes agregados");
                a5.setContentText(" ");
                a5.showAndWait();
            }
        }
    }

    @FXML
    private void comboRestaurantes(ActionEvent event) {
        if (comboRestaurantes.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios
            cBoxPlace.getSelectionModel().select(comboRestaurantes.getSelectionModel().getSelectedItem().getLocation());
            textFieldNombre.setText(comboRestaurantes.getSelectionModel().getSelectedItem() + "");// agregamos al textField el restaurante seleccionado

        }
    }

    @FXML
    private void cBoxPlace(ActionEvent event) {
        if (cBoxPlace.getSelectionModel().getSelectedIndex() != -1) {

        }
    }
}
