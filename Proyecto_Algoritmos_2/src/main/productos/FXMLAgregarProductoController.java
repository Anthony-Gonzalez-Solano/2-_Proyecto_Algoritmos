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
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLAgregarProductoController implements Initializable {
    private Alert a;
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
        a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(" ");
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
        txtFieldPrice.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {//metodo que solamente deja introducir numeros al textfield
                txtFieldPrice.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        try {
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                Object a = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                if (a instanceof Supermarket) {//se recorre el grafo de supermercados y restaurantes y se asegura que el objeto sacado sea Supermarket y lo introduce en el combobox
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
                        found = true;//se busca el objeto que se quiere agregar para ver si ya existe
                    }
                }
            }
        }
        if (txtFieldName.getText().isEmpty() || cBoxSuper.getValue().equals("") || this.txtFieldPrice.getText().isEmpty()) {//validaciones de campos vacios
            a.setAlertType(Alert.AlertType.INFORMATION);//se valida que los campos no esten vacios
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        }
        if (found == true) {
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("Producto ingresado ya existe");//se valida que no exista
            a.showAndWait();
        } else {
            Supermarket b = null;
            if (util.Utility.getTreeProducts().isEmpty()) {//si la lista esta vacia simpelmente se agrega
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object y = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (y instanceof Supermarket) {
                        b = (Supermarket) y;
                        if (b.getName().equals(cBoxSuper.getValue())) {
                            Product p = new Product(txtFieldName.getText(), Double.valueOf(this.txtFieldPrice.getText()), b.getId());
                            util.Utility.getTreeProducts().add(p);
                            txtFieldName.setText("");
                            txtFieldPrice.setText("");
                            cBoxSuper.getSelectionModel().clearSelection();
                            txt.writeFile("productos.txt", p.secondToSting());
                            a.setAlertType(Alert.AlertType.INFORMATION);
                            a.setHeaderText("Producto ingresado existosamente");
                            a.showAndWait();
                        }
                    }
                }
            } else {
                Product sT2 = null;
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    Object y = util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (y instanceof Supermarket) {
                        b = (Supermarket) y;
                        if (b.getName().equals(cBoxSuper.getValue())) {//se busca el id del supermercado
                            sT2 = new Product(txtFieldName.getText(), Double.valueOf(this.txtFieldPrice.getText()), b.getId());

                            if (found == false) {
                                util.Utility.getTreeProducts().add(sT2);//se agrega el producto tanto al a la lista como al txt
                                txtFieldName.setText("");//se limpia textfields
                                txtFieldPrice.setText("");
                                cBoxSuper.getSelectionModel().clearSelection();
                                txt.writeFile("productos.txt", sT2.secondToSting());
                                a.setAlertType(Alert.AlertType.INFORMATION);
                                a.setHeaderText("Producto ingresado existosamente");
                                a.showAndWait();
                                a.setAlertType(Alert.AlertType.ERROR);
                            }
                        }
                    }
                }
            }
        }
    }

}
