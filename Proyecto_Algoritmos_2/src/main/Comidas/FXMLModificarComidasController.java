/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Comidas;

import domain.Food;
import domain.tree.BTreeNode;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLModificarComidasController implements Initializable {

    private Alert a5;
    private util.FileTXT txt;
    @FXML
    private ComboBox<Food> comboComidas;
    @FXML
    private Button btnModificar;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldPrecio;

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
            preOrder();
        } catch (TreeException ex) {
            Logger.getLogger(FXMLModificarComidasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void comboComidas(ActionEvent event) {
        if (comboComidas.getSelectionModel().getSelectedIndex() != -1) {
            textFieldNombre.setText(comboComidas.getSelectionModel().getSelectedItem().getName());
            textFieldPrecio.setText(comboComidas.getSelectionModel().getSelectedItem().getPrice() + "");
        }
    }

    @FXML
    private void btnModificar(ActionEvent event) {
        if (textFieldNombre.getText().isEmpty() || textFieldPrecio.getText().isEmpty()) { //validamos campos vacios
            a5.setAlertType(Alert.AlertType.INFORMATION);
            a5.setHeaderText("No debe dejar campos vacios. Ingrese un nombre y precio para poder modificarlo");
            a5.setContentText(" ");
            a5.showAndWait();
        } else {

            try {
                Food f = new Food(textFieldNombre.getText(), Double.valueOf(textFieldPrecio.getText()), comboComidas.getSelectionModel().getSelectedItem().getRestaurantID());

                if (util.Utility.getTreeFood().contains(comboComidas.getSelectionModel().getSelectedItem()) == true) {
                    util.Utility.getTreeFood().remove(comboComidas.getSelectionModel().getSelectedItem());
                    util.Utility.getTreeFood().add(f);
                    txt.modifyFile("comidas.txt", comboComidas.getSelectionModel().getSelectedItem().secondToString(), f.secondToString());// se modifica el archivo
                    int x = comboComidas.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                    comboComidas.getItems().remove(x); // se remueve
                    comboComidas.getItems().add(x, f);// se agregan de nuevo al comBox la comida modificado
                    comboComidas.getSelectionModel().clearSelection();//limpiamos el comboBox
                    textFieldNombre.setText("");
                    textFieldPrecio.setText("");

                    a5.setAlertType(Alert.AlertType.CONFIRMATION);
                    a5.setHeaderText("La comida ha sido modificada correctamente");
                    a5.setContentText(" ");
                    a5.showAndWait();

                }
            } catch (TreeException ex) {
                Logger.getLogger(FXMLModificarComidasController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public String preOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return preOrder(util.Utility.getTreeFood().getRoot());
    }

    public boolean isEmpty() {
        return util.Utility.getTreeFood().getRoot() == null;
    }

    private String preOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            comboComidas.getItems().add((Food) node.data);
            preOrder(node.left);
            preOrder(node.right);

        }
        return result;
    }
}
