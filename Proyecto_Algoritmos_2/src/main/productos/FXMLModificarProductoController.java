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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        txt = new FileTXT(); // crear txt
        txtFieldPrice.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtFieldPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        try {
            
               preOrder();
            
        } catch (TreeException ex) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay productos para modificar");
            a.showAndWait();
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
                Product p = new Product(txtFieldName.getText(), Double.valueOf(txtFieldPrice.getText()), c.getSupermarketID(),c.getID());
                if (util.Utility.getTreeProducts().contains(c) == true) {
                    util.Utility.getTreeProducts().remove(c);
                    util.Utility.getTreeProducts().add(p);
                    txt.modifyFile("productos.txt", c.toString(), p.toString());// se modifica el archivo
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
        return findProduct(util.Utility.getTreeProducts().getRoot(),Name);
    }
    
    private Product findProduct(BTreeNode node, String Name) {
        
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
