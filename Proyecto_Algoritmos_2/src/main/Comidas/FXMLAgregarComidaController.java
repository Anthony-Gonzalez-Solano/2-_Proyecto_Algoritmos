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
import javafx.beans.value.ObservableValue;
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
    private Alert a5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();
        a5 = new Alert(Alert.AlertType.ERROR);
        a5.setContentText(" ");
        DialogPane dp = a5.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                if (a.getClass() == Restaurant.class) {
                    comboRestaurantes.getItems().add((Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);
                }
            } //recorremos la lista de restaurantes para agregarlas al comboBox
        } catch (ListException ex) {
            a5.setAlertType(Alert.AlertType.INFORMATION);
            a5.setHeaderText("No hay restaurantes agregados. Por favor ingrese un restaurante primero");
            a5.setContentText(" ");
            a5.showAndWait();

        }
        textFieldPrecio.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {//metodo que solamente deja introducir numeros al textfield
                textFieldPrecio.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void btnAgregar(ActionEvent event) {
        boolean found = false;
        Restaurant r = null;

        try {

            if (comboRestaurantes.getSelectionModel().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldPrecio.getText().isEmpty()) {
                a5.setAlertType(Alert.AlertType.INFORMATION);
                a5.setHeaderText("No debe dejar campos vacios, verifique los campos de texto.\n Y que haya elegido un restaurante donde agregar su comida");
                a5.setContentText(" ");
                a5.showAndWait(); // validacion campos vacios

            } else if (util.Utility.getTreeFood().isEmpty()) { // si no hay comidas se agrega
                Restaurant r2 = null;

                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (a.getClass() == Restaurant.class) { //validamos que sea un objeto Restaurante
                        r2 = (Restaurant) a;

                        if (r2.getName().equals(comboRestaurantes.getSelectionModel().getSelectedItem().getName())) {// si son iguales con respecto al nombre
                            Food f = new Food(textFieldNombre.getText(), Double.valueOf(textFieldPrecio.getText()), r2.getId());
                            util.Utility.getTreeFood().add(f);//agregamos al arbol
                            txt.writeFile("comidas.txt", f.secondToString());// escribimos en los txt
                            comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                            a5.setAlertType(Alert.AlertType.CONFIRMATION);
                            a5.setHeaderText(" La comida" + textFieldNombre.getText() + " fue agregada correctamente");
                            a5.setContentText(" ");
                            a5.showAndWait();
                            textFieldNombre.setText("");
                            textFieldPrecio.setText("");
                        }
                    }
                }

            } else {

                Food f3 = null;
                Restaurant r3 = null;
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (a.getClass() == Restaurant.class) {//verificamos que sea objeto Restaurante
                        r3 = (Restaurant) a;
                        if (r3.getName().equals(comboRestaurantes.getSelectionModel().getSelectedItem().getName())) {//verificamos que sean iguales
                            f3 = new Food(textFieldNombre.getText(), Double.valueOf(this.textFieldPrecio.getText()), r3.getId());

                        }
                        if ((util.Utility.getTreeFood().contains(f3))) {//si ya existe lo pone true
                            found = true;
                        }
                    }
                }
                if (found == false) {// si no existe lo agrega
                    txt.writeFile("comidas.txt", f3.secondToString());// escribimos en los txt
                    util.Utility.getTreeFood().add(f3);
                    comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                    a5.setAlertType(Alert.AlertType.CONFIRMATION);
                    a5.setHeaderText(" La comida " + textFieldNombre.getText() + " fue agregada correctamente");
                    a5.setContentText(" ");

                    a5.showAndWait();

                    textFieldNombre.setText("");
                    textFieldPrecio.setText("");
                } else {

                    a5.setAlertType(Alert.AlertType.INFORMATION);
                    a5.setHeaderText("La comida " + textFieldNombre.getText() + " ya esta en este restaurante");
                    a5.showAndWait();
                    

                }
            }

        } catch (ListException e) {
            a5.setAlertType(Alert.AlertType.INFORMATION);
            a5.setHeaderText("No hay restaurantes agregados. Por favor ingrese un restaurante primero");
            a5.setContentText(" ");
            a5.showAndWait();
        } catch (TreeException ex) {
            Logger.getLogger(FXMLAgregarComidaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void comboRestaurantes(ActionEvent event) {// no se produzca errores
        if (comboRestaurantes.getSelectionModel().getSelectedIndex() != -1) {

        }
    }

}
