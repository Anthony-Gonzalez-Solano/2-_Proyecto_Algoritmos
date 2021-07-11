/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Comidas;

import domain.Food;
import domain.Restaurant;
import domain.list.ListException;
import domain.tree.TreeException;
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
public class FXMLAgregarComidaController implements Initializable {

    private static int autoID;

    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private Button btnAgregar;
    @FXML
    private ComboBox<Restaurant> comboRestaurantes;
    private util.FileTXT txt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();

        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                if (a.getClass() == Restaurant.class) {
                    comboRestaurantes.getItems().add((Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
                }
            } //recorremos la lista de restaurantes para agregarlas al comboBox
        } catch (ListException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay restaurantes agregados. Por favor ingrese un restaurante primero");
            a.showAndWait();
        }
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        boolean found = false;
        Restaurant r = new Restaurant("", "");

        try {

            if (comboRestaurantes.getSelectionModel().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldPrecio.getText().isEmpty()) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("No debe dejar campos vacios, verifique los campos de texto.\n Y que haya elegido un restaurante donde agregar su comida");
                a.showAndWait();

            } else if (util.Utility.getTreeFood().isEmpty()) {
                Restaurant r2 = new Restaurant("", "");

                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (a.getClass() == Restaurant.class) {
                        r2 = (Restaurant) a;
                    }
                    if (r2.getName().equals(comboRestaurantes.getSelectionModel().getSelectedItem().getName())) {
                        Food f = new Food(textFieldNombre.getText(), Double.valueOf(textFieldPrecio.getText()), r2.getId());
                        util.Utility.getTreeFood().add(f);
                        txt.writeFile("comidas.txt", f.secondToString());// escribimos en los txt
                    }
                }
                comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText(" La comida" + textFieldNombre.getText() + " fue agregada correctamente");
                a.showAndWait();
                textFieldNombre.setText("");
                textFieldPrecio.setText("");
            } else {
                try {
                    for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                        if (a.getClass() == Restaurant.class) {
                            r = (Restaurant) a;
                            if (r.getName().equals(comboRestaurantes.getSelectionModel().getSelectedItem().getName())) {
                                Food f = new Food(textFieldNombre.getText(), Double.valueOf(this.textFieldPrecio.getText()), r.getId());
                                if ((util.Utility.getTreeFood().contains(f))) {
                                    found = true;
                                }
                            }
                        }
                    }

                } catch (ListException | TreeException ex) {
                    Logger.getLogger(FXMLAgregarComidaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Food f = new Food("", 0, 0);
                Restaurant r3 = new Restaurant("", "");
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (a.getClass() == Restaurant.class) {
                        r3 = (Restaurant) a;
                        if (r3.getName().equals(comboRestaurantes.getSelectionModel().getSelectedItem().getName())) {
                            f = new Food(textFieldNombre.getText(), Double.valueOf(this.textFieldPrecio.getText()), r3.getId());

                        }
                    }
                }
                if (found == false) {
                    txt.writeFile("comidas.txt", f.secondToString());// escribimos en los txt
                    util.Utility.getTreeFood().add(f);
                    comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText(" La comida" + textFieldNombre.getText() + " fue agregada correctamente");
                    a.showAndWait();
                    textFieldNombre.setText("");
                    textFieldPrecio.setText("");
                } else {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("La comida " + textFieldNombre.getText() + " ya esta en este restaurante");
                    a.showAndWait();

                }

            }
        } catch (ListException e) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay restaurantes agregados. Por favor ingrese un restaurante primero");
            a.showAndWait();
        }
    }

    @FXML
    private void comboRestaurantes(ActionEvent event
    ) {
        if (comboRestaurantes.getSelectionModel().getSelectedIndex() != -1) {

        }
    }

}
