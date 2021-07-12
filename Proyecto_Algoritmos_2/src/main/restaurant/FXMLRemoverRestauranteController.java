/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.restaurant;

import domain.Food;
import domain.Restaurant;
import domain.graph.GraphException;
import domain.list.ListException;
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
public class FXMLRemoverRestauranteController implements Initializable {

    private Alert a5;
    private util.FileTXT txt;
    @FXML
    private ComboBox<String> comboRestaurantes;
    @FXML
    private Button btnRemover;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();
        a5 = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a5.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        try {

            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Restaurant.class) {
                    Restaurant t = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    this.comboRestaurantes.getItems().add(t.getName());
                }
            }
        } catch (ListException ex) {
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("Lista vacia");
            a5.setContentText("");
            a5.showAndWait();
        }

    }

    @FXML
    private void btnRemover(ActionEvent event) {

        if (comboRestaurantes.getSelectionModel().isEmpty()) {
            a5.setAlertType(Alert.AlertType.CONFIRMATION);
            a5.setHeaderText("Debe seleccionar un restaurante para poder removerlo");
            a5.setContentText("");
            a5.showAndWait();
        } else {
            a5.setAlertType(Alert.AlertType.INFORMATION);
            a5.setHeaderText("¿Esta seguro que quiere remover el restaurante: " + comboRestaurantes.getSelectionModel().getSelectedItem() + "?");
            ButtonType yes = new ButtonType("Si");
            ButtonType no = new ButtonType("No");
            a5.getButtonTypes().clear();
            a5.getButtonTypes().addAll(yes, no);
            Optional<ButtonType> option = a5.showAndWait();
            if (option.get() == yes) {
                try {
                    for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                        if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Restaurant.class) {
                            Restaurant t = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                            if (t.getName().equals(this.comboRestaurantes.getValue())) {
                                if (!(util.Utility.getTreeFood().isEmpty()) && findProduct(t.getId()) == true) {
                                    a5.setAlertType(Alert.AlertType.ERROR);
                                    a5.setHeaderText("No ha podido ser removido porque existen comidas ligadas al restaurante");
                                    a5.setContentText("");
                                    a5.showAndWait();
                                } else {
                                    int x = comboRestaurantes.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                                    comboRestaurantes.getItems().remove(x); // se remueve
                                    comboRestaurantes.getSelectionModel().clearSelection();//limpiamos el comboBox
                                    txt.removeElement("Restaurant_Supermarket.txt", t.secondToString());
                                    util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(t);

                                    a5.setAlertType(Alert.AlertType.CONFIRMATION);
                                    a5.setHeaderText("El restaurante ha sido eliminado correctamente");
                                    a5.setContentText("");
                                    a5.showAndWait();
                                }
                            }
                        }
                    }
                } catch (ListException es) {
                    a5.setAlertType(Alert.AlertType.ERROR);
                    a5.setHeaderText("La lista de restaurantes esta vacia");
                    a5.setContentText("");
                    a5.showAndWait();
                } catch (GraphException ex) {
                    Logger.getLogger(FXMLRemoverRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TreeException ex) {
                    Logger.getLogger(FXMLRemoverRestauranteController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    @FXML
    private void comboRestaurantes(ActionEvent event
    ) {
        if (comboRestaurantes.getSelectionModel().getSelectedIndex() != -1) {

        }

    }

    public boolean findProduct(int superID) throws TreeException {
        if (isEmpty()) {
            a5.setAlertType(Alert.AlertType.ERROR);
            a5.setHeaderText("El arbol esta vacio");
            a5.setContentText("");
            a5.showAndWait();
        }
        return findProduct(util.Utility.getTreeFood().getRoot(), superID);
    }

    private boolean findProduct(BTreeNode node, int restaurantID) {

        if (node == null) {
            return false;
        } else {
            Food p = (Food) node.data;
            if (p.getRestaurantID() == restaurantID) {
                return true; //ya lo encontro
            } else if (util.Utility.lessT(restaurantID, p.getRestaurantID())) {
                return findProduct(node.left, restaurantID);
            } else {
                return findProduct(node.right, restaurantID);
            }
        }
    }

    public boolean isEmpty() {
        return util.Utility.getTreeFood().getRoot() == null;
    }
}
