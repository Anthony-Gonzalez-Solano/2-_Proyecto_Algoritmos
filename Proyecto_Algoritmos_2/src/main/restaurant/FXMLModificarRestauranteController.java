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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            btnModificar.setVisible(false);
            txt = new FileTXT();
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                comboRestaurantes.getItems().add((Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
            }
            // TODO
        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("No hay restaurantes agregados");
            a.showAndWait();
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldNombre.getText().isEmpty()) { //validamos campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Debe ingresar un restaurante para poder modificarlo");
            a.showAndWait();
        } else {
            Restaurant r = new Restaurant(textFieldNombre.getText(), comboRestaurantes.getSelectionModel().getSelectedItem().getLocation());
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {

                    Restaurant r2 = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (r2.equals(comboRestaurantes.getSelectionModel().getSelectedItem())) {
                        r = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    }
                }

                txt.modifyFile("restaurantes.txt", comboRestaurantes.getSelectionModel().getSelectedItem().secondToString(), r.secondToString());// se modifica el archivo
                int x = comboRestaurantes.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                comboRestaurantes.getItems().remove(x); // se remueve
                comboRestaurantes.getItems().add(x, r);// se agregan de nuevo al comBox el restaurante modificado
                comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                textFieldNombre.setText("");

                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("El  restaurante ha sido mofificado correctamente");
                a.showAndWait();
                textFieldNombre.setVisible(false);
                btnModificar.setVisible(false);

            } catch (ListException e) {

            }
        }
    }

    @FXML
    private void comboRestaurantes(ActionEvent event) {
        if (comboRestaurantes.getSelectionModel().getSelectedIndex() != -1) { // evitar errores cuando se activa el evento del comboBox, porque tomaba datos vacios
            textFieldNombre.setVisible(true);
            btnModificar.setVisible(true);
            textFieldNombre.setText(comboRestaurantes.getSelectionModel().getSelectedItem() + "");// agregamos al textField el restaurante seleccionado

        }
    }
}
