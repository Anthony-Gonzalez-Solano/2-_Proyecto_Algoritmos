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

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLRemoverProductoController implements Initializable {
    private util.FileTXT txt;
    @FXML
    private ComboBox<String> cBoxProducts;
    @FXML
    private Button btnRemove;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            preOrder();
        } catch (TreeException ex) {
            Logger.getLogger(FXMLRemoverProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnRemove(ActionEvent event) throws TreeException {
    if (cBoxProducts.getSelectionModel().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Necesita escoger un producto");
            a.showAndWait();
        } else {
        Product p=(Product)findProduct(cBoxProducts.getValue());
        if (util.Utility.getTreeProducts().contains(p) == true) {

                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setHeaderText("¿Esta seguro que quiere remover esta comida: " + cBoxProducts.getValue() + " ?");
                    ButtonType yes = new ButtonType("Si");
                    ButtonType no = new ButtonType("No");
                    a.getButtonTypes().clear();
                    a.getButtonTypes().addAll(yes, no);

                    Optional<ButtonType> option = a.showAndWait();
                    if (option.get() == yes) {
                        txt.removeElement("productos.txt", p);
                        int x = cBoxProducts.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                        cBoxProducts.getItems().remove(x); // se remueve
                        cBoxProducts.getSelectionModel().clearSelection();//limpiamos el comboBox
                        Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                        a2.setHeaderText(" El producto " + cBoxProducts.getValue() + "   ha sido eliminada correctamente");
                        a2.showAndWait();
                        util.Utility.getTreeProducts().remove(p);

                    }
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
        Product p = null;
        String result = "";
        if (node != null) {
            p=(Product) node.data;
            cBoxProducts.getItems().add(p.getName());
            // result = node.data + " ";
            preOrder(node.left);
            preOrder(node.right);

            //    result += preOrder(node.right);
        }
        return result;
    }
    public Product findProduct(String Name) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return findProduct(util.Utility.getTreeFood().getRoot(),Name);
    }
    
    private Product findProduct(BTreeNode node, String Name) {
        Product p = (Product)node.data;
        if(node==null)
            return null;
        else
            if(p.getName().equals(Name)){
                return p; //ya lo encontro
            }else
                if(util.Utility.lessT(Name, p.getName()))
                    return findProduct(node.left, Name);
                else
                    return findProduct(node.right, Name);
    }
}
