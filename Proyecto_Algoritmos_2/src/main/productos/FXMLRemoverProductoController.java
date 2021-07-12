/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.productos;

import domain.Product;
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
public class FXMLRemoverProductoController implements Initializable {
    private util.FileTXT txt;
    private Alert a;
    @FXML
    private ComboBox<String> cBoxProducts;
    @FXML
    private Button btnRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txt = new FileTXT(); // crear txt
        a = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        try {
            preOrder();//metodo que recorre el arbol de productos y los va agregando al combobox
        } catch (TreeException ex) {
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay productos para eliminar");
            a.showAndWait();
        }
    }    

    @FXML
    private void btnRemove(ActionEvent event) throws TreeException {
    if (cBoxProducts.getSelectionModel().isEmpty()) {//se asegura que se haya seleccionado un objeto
            a.setAlertType(Alert.AlertType.ERROR);
            a.setHeaderText("Necesita escoger un producto");
            a.showAndWait();
        } else {
        Product p=(Product)findProduct(cBoxProducts.getValue());
        if (util.Utility.getTreeProducts().contains(p) == true) {

                    a.setAlertType(Alert.AlertType.INFORMATION);
                    a.setHeaderText("¿Esta seguro que quiere remover esta comida: " + cBoxProducts.getValue() + " ?");
                    ButtonType yes = new ButtonType("Si");
                    ButtonType no = new ButtonType("No");
                    a.getButtonTypes().clear();
                    a.getButtonTypes().addAll(yes, no);

                    Optional<ButtonType> option = a.showAndWait();
                    if (option.get() == yes) {//pregunta si queire remover el producto
                        txt.removeElement("productos.txt", p);
                        a.setAlertType(Alert.AlertType.CONFIRMATION);
                        a.setHeaderText(" El producto " + cBoxProducts.getValue() + "   ha sido eliminada correctamente");
                        a.showAndWait();
                        util.Utility.getTreeProducts().remove(p);//remueve el producto de la lsita
                        int x = cBoxProducts.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                        cBoxProducts.getItems().remove(x); // se remueve
                        cBoxProducts.getSelectionModel().clearSelection();//limpiamos el comboBox
                    }
            }
        }
    }
    public String preOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return preOrder(util.Utility.getTreeProducts().getRoot());
    }
    
    public boolean isEmpty() {
        return util.Utility.getTreeProducts().getRoot() == null;
    }
    
    private String preOrder(BTreeNode node) {
        Product p = null;
        String result = "";
        if (node != null) {
            p=(Product) node.data;
            cBoxProducts.getItems().add(p.getName());
            preOrder(node.left);
            preOrder(node.right);
        }
        return result;
    }
    public Product findProduct(String Name) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return findProduct(util.Utility.getTreeProducts().getRoot(),Name);
    }
    
    private Product findProduct(BTreeNode node, String Name) {  //metodo recursivo que busca y devuelve un objeto tipo producto
        if(node==null)
            return null;
        else{
            Product p = (Product)node.data;
            if(p.getName().equals(Name)){
                return p; //ya lo encontro
            }else
                if(util.Utility.lessT(Name, p.getName()))
                    return findProduct(node.left, Name);
                else
                    return findProduct(node.right, Name);
        }
    }
}
