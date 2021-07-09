/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.productos;

import domain.Food;
import domain.Product;
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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLModificarProductoController implements Initializable {
    private util.FileTXT txt;
    @FXML
    private ComboBox<String> cBoxProduct;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldPrice;
    @FXML
    private Button btnModify;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
               preOrder();
            
        } catch (TreeException ex) {
            Logger.getLogger(FXMLModificarProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnModify(ActionEvent event) throws TreeException {
        if (txtFieldName.getText().isEmpty() || txtFieldPrice.getText().isEmpty()) { //validamos campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios. Ingrese un nombre y precio para poder modificarlo");
            a.showAndWait();
        } else {
                Product c = findProduct(cBoxProduct.getValue());
                Product p = new Product(txtFieldName.getText(), Double.valueOf(txtFieldPrice.getText()), c.getSupermarketID());
                txt.modifyFile("productos.txt", c.toString(), p.toString());// se modifica el archivo
                if (util.Utility.getTreeFood().contains(cBoxProduct.getSelectionModel().getSelectedItem()) == true) {
                    util.Utility.getTreeFood().remove(cBoxProduct.getSelectionModel().getSelectedItem());
                    util.Utility.getTreeProducts().add(p);
                    int x = cBoxProduct.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                    cBoxProduct.getItems().remove(cBoxProduct.getValue()); // se remueve
                    cBoxProduct.getItems().add(x, p.getName());// se agregan de nuevo al comBox la comida modificado
                    cBoxProduct.getSelectionModel().clearSelection();//limpiamos el comboBox

                    
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setHeaderText("La comida ha sido mofificada correctamente");
                    a.showAndWait();
                    txtFieldName.setText("");
                    txtFieldPrice.setText("");
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
            cBoxProduct.getItems().add(p.getName());
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
