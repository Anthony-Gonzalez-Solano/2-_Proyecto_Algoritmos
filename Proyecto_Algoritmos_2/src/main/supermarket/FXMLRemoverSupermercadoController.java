/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.supermarket;

import domain.Product;
import domain.Supermarket;
import domain.graph.GraphException;
import domain.graph.Place;
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
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLRemoverSupermercadoController implements Initializable {
    private Alert a;
    private util.FileTXT txt;
    @FXML
    private ComboBox<String> cBoxSupermarkets;
    @FXML
    private Button btnRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT();
        a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(" ");
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {//se llena el combobox de supermercados asegurandose que al recorrer el grafo el objeto sea tipo Supermarket
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Supermarket.class) {
                    Supermarket t = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    this.cBoxSupermarkets.getItems().add(t.getName());
                }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLModificarSuperMercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnRemove(ActionEvent event) throws GraphException, ListException, TreeException {
        if (cBoxSupermarkets.getSelectionModel().isEmpty()) {//se asegura que el haya seleccionado un supermercado a eliminar
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText("Debe seleccionar un restaurante para poder removerlo");
            a.showAndWait();
        } else {

            Alert a2= new Alert(Alert.AlertType.CONFIRMATION);//se pregunta si se quiere remover el supermercado
            a2.setHeaderText("¿Esta seguro que quiere remover el Supermercado: " + cBoxSupermarkets.getSelectionModel().getSelectedItem() + "?");
            a2.setContentText(" ");
            DialogPane dp = a2.getDialogPane();
            dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            dp.getStyleClass().add("myDialog");
            ButtonType yes = new ButtonType("Si");
            ButtonType no = new ButtonType("No");
            a2.getButtonTypes().clear();
            a2.getButtonTypes().addAll(yes, no);

            Optional<ButtonType> option = a2.showAndWait();
            if (option.get() == yes) {//si dice "Si" se remueve

                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {//se recorre el grafo y se asegura que el objeto sacado sea tipo Supermarket
                    if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Supermarket.class) {
                        Supermarket t = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                        if (t.getName().equals(this.cBoxSupermarkets.getValue())) {
                            if (!(util.Utility.getTreeProducts().isEmpty()) && findProduct(t.getId()) == true) {//si la lista no esta vacia y encontro un producto ligado al supermercado niega la peticion
                                a.setAlertType(Alert.AlertType.ERROR);
                                a.setHeaderText("No ha podido ser removido porque existen productos ligados al supermercado");
                                a.showAndWait();
                            } else {
                                int x = cBoxSupermarkets.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                                cBoxSupermarkets.getItems().remove(x); // se remueve
                                cBoxSupermarkets.getSelectionModel().clearSelection();//limpiamos el comboBox
                                util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(t);//se remueve el supermercado
                                txt.removeElement("Restaurant_Supermarket.txt", t.toString());
                                a.setAlertType(Alert.AlertType.INFORMATION);
                                a.setHeaderText("Removido exitosamente");
                                a.showAndWait();
                            }
                        }
                    }
                }
            }
        }
    }
    public boolean isEmpty() {
        return util.Utility.getTreeProducts().getRoot() == null;
    }

    public boolean findProduct(int superID) throws TreeException {//metodo recursivo para ver si existe el producto
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return findProduct(util.Utility.getTreeProducts().getRoot(), superID);
    }

    private boolean findProduct(BTreeNode node, int superID) {

        if (node == null) {
            return false;
        } else {
            Product p = (Product) node.data;
            if (p.getSupermarketID() == superID) {
                return true; //ya lo encontro
            } else if (util.Utility.lessT(superID, p.getSupermarketID())) {
                return findProduct(node.left, superID);
            } else {
                return findProduct(node.right, superID);
            }
        }
    }
}
