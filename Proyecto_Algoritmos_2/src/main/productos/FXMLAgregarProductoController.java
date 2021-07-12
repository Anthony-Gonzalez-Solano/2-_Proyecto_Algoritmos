/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.productos;

import domain.Product;
import domain.Supermarket;
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
import javafx.scene.control.TextField;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLAgregarProductoController implements Initializable {

    private util.FileTXT txt;
    @FXML
    private ComboBox<String> cBoxSuper;
    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldPrice;
    @FXML
    private Button btnAdd;

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
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                if (a instanceof Supermarket) {
                    this.cBoxSuper.getItems().add(((Supermarket) a).getName());
                }
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLAgregarProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAdd(ActionEvent event) throws ListException, TreeException {
        Product sT = null;
        Supermarket c = null;
        boolean found = false;
        for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
            Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
            if (a instanceof Supermarket) {
                c = (Supermarket) a;
                if (c.getName().equals(cBoxSuper.getValue())) {
                    sT = new Product(txtFieldName.getText(), Double.valueOf(this.txtFieldPrice.getText()), c.getId());
                    if (!util.Utility.getTreeProducts().isEmpty() && (util.Utility.getTreeProducts().contains(sT))) {
                        found = true;
                    }
                }
            }
        }
        if (txtFieldName.getText().isEmpty() || cBoxSuper.getValue().equals("") || this.txtFieldPrice.getText().isEmpty()) {//validaciones de campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        }
        if (found == true) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Producto ingresado ya existe");
            a.showAndWait();
        } else {
            Supermarket b = null;
            if (util.Utility.getTreeProducts().isEmpty()) {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (a instanceof Supermarket) {
                        b = (Supermarket) a;
                        if (b.getName().equals(cBoxSuper.getValue())) {
                            Product p = new Product(txtFieldName.getText(), Double.valueOf(this.txtFieldPrice.getText()), b.getId());
                            util.Utility.getTreeProducts().add(p);
                            txtFieldName.setText("");
                            txtFieldPrice.setText("");
                            cBoxSuper.getSelectionModel().clearSelection();
                            txt.writeFile("productos.txt", p.toString());
                            Alert u = new Alert(Alert.AlertType.INFORMATION);
                            u.setHeaderText("Producto ingresado existosamente");
                            u.showAndWait();
                        }
                    }
                }
            } else {
                Product sT2 = null;
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (a instanceof Supermarket) {
                        b = (Supermarket) a;
                        if (b.getName().equals(cBoxSuper.getValue())) {
                            sT2 = new Product(txtFieldName.getText(), Double.valueOf(this.txtFieldPrice.getText()), b.getId());

                            if (found == false) {
                                util.Utility.getTreeProducts().add(sT2);
                                txtFieldName.setText("");
                                txtFieldPrice.setText("");
                                cBoxSuper.getSelectionModel().clearSelection();
                                txt.writeFile("productos.txt", sT2.toString());
                                Alert u = new Alert(Alert.AlertType.INFORMATION);
                                u.setHeaderText("Producto ingresado existosamente");
                                u.showAndWait();
                            }
                        }
                    }
                }
            }
        }
    }

}
