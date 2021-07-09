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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();

        try {

            preOrder();

        } catch (TreeException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Lista vacia");
            a.showAndWait();
        }
    }

    @FXML
    private void comboComidas(ActionEvent event) {
    }

    @FXML
    private void btnRemover(ActionEvent event) {
        boolean exist = false;
        if (comboComidas.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Debe seleccionar una comida para poder removerlo");
            a.showAndWait();
        } else {
            try {
                Food f = new Food(comboComidas.getSelectionModel().getSelectedItem().getName(), comboComidas.getSelectionModel().getSelectedItem().getPrice(), comboComidas.getSelectionModel().getSelectedItem().getRestaurantID());
                if (util.Utility.getTreeFood().contains(f) == true) {

                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("¿Esta seguro que quiere remover esta comida: " + comboComidas.getSelectionModel().getSelectedItem().getName() + " ?");
                    ButtonType yes = new ButtonType("Si");
                    ButtonType no = new ButtonType("No");
                    a.getButtonTypes().clear();
                    a.getButtonTypes().addAll(yes, no);

                    Optional<ButtonType> option = a.showAndWait();
                    if (option.get() == yes) {
                        util.Utility.getTreeFood();

                        txt.removeElement("comidas.txt", comboComidas.getSelectionModel().getSelectedItem().secondToString());
                        int x = comboComidas.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                        comboComidas.getItems().remove(x); // se remueve
                        comboComidas.getSelectionModel().clearSelection();//limpiamos el comboBox
                        Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                        a2.setHeaderText(" La comida " + comboComidas.getSelectionModel().getSelectedItem().getName() + "   ha sido eliminada correctamente");
                        a2.showAndWait();
                        util.Utility.getTreeFood().remove(f);

                    }
                }

            } catch (TreeException ex) {
                Logger.getLogger(FXMLRemoverComidaController.class.getName()).log(Level.SEVERE, null, ex);
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
            // result = node.data + " ";
            preOrder(node.left);
            preOrder(node.right);

            //    result += preOrder(node.right);
        }
        return result;
    }
}
