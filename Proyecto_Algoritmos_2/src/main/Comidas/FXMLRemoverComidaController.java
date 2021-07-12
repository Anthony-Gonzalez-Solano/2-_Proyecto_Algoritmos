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
import javafx.scene.control.DialogPane;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLRemoverComidaController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private ComboBox<Food> comboComidas;
    @FXML
    private Button btnRemover;
    private Alert a5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();
        a5 = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a5.getDialogPane();// estilos a las alertas
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        try {

            preOrder();

        } catch (TreeException ex) {
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("No hay comidas");
            a5.setContentText(" ");
            a5.showAndWait();
        }
    }

    @FXML
    private void comboComidas(ActionEvent event) {
    }

    @FXML
    private void btnRemover(ActionEvent event) {
        boolean exist = false;
        if (comboComidas.getSelectionModel().isEmpty()) {//validacion campos vacios
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("Debe seleccionar una comida para poder removerlo");
            a5.showAndWait();
        } else {
            try {
                Food f = new Food(comboComidas.getSelectionModel().getSelectedItem().getName(), comboComidas.getSelectionModel().getSelectedItem().getPrice(), comboComidas.getSelectionModel().getSelectedItem().getRestaurantID());
                if (util.Utility.getTreeFood().contains(f) == true) {// si existe el elemento se remueve

                    a5.setAlertType(Alert.AlertType.ERROR);
                    a5.setHeaderText("¿Esta seguro que quiere remover esta comida: " + comboComidas.getSelectionModel().getSelectedItem().getName() + " ?");
                    ButtonType yes = new ButtonType("Si");
                    ButtonType no = new ButtonType("No");
                    a5.getButtonTypes().clear();
                    a5.getButtonTypes().addAll(yes, no);

                    Optional<ButtonType> option = a5.showAndWait();
                    if (option.get() == yes) {
                        util.Utility.getTreeFood();

                        txt.removeElement("comidas.txt", comboComidas.getSelectionModel().getSelectedItem().secondToString());// escribimos en el txt
                        int x = comboComidas.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                        comboComidas.getItems().remove(x); // se remueve
                        comboComidas.getSelectionModel().clearSelection();//limpiamos el comboBox
                        a5.setAlertType(Alert.AlertType.ERROR);
                        a5.setHeaderText(" La comida ha sido eliminada correctamente");
                        a5.setContentText(" ");
                        a5.showAndWait();
                        util.Utility.getTreeFood().remove(f);// se remueve

                    }
                } else {
                    a5.setAlertType(Alert.AlertType.ERROR);
                    a5.setHeaderText(" La comida " + comboComidas.getSelectionModel().getSelectedItem().getName() + "   no existe");
                    a5.setContentText(" ");
                    a5.showAndWait();
                }

            } catch (TreeException ex) {
                Logger.getLogger(FXMLRemoverComidaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String preOrder() throws TreeException {
        if (isEmpty()) {
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("El arbol esta vacio ");
            a5.setContentText(" ");
            a5.showAndWait();
        }
        return preOrder(util.Utility.getTreeFood().getRoot());
    }

    public boolean isEmpty() {
        return util.Utility.getTreeFood().getRoot() == null;
    }

    private String preOrder(BTreeNode node) {
        String result = "";// agregamos los elementos del arbol al ComboBox
        if (node != null) {
            comboComidas.getItems().add((Food) node.data);
            // result = node.data + " ";
            preOrder(node.left);
            preOrder(node.right);

            //    result += preOrder(node.right);
        }
        return result;
    }
}
