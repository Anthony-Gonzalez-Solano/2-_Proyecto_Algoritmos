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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FXMLModificarSuperMercadoController implements Initializable {
    private Alert a;
    private util.FileTXT txt;
    @FXML
    private ComboBox<String> cBoxSuperMarkets;
    @FXML
    private TextField txtFieldName;
    @FXML
    private Button btnModify;
    @FXML
    private ComboBox<String> cBoxPlace;

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
            fillcBoxSuper();//metodo que recorre el grafo de supermercados y restaurante y agrega los supermercados
        } catch (ListException ex) {
            Logger.getLogger(FXMLModificarSuperMercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cBoxPlace.getItems().add("Cachí");//se agregan los lugares al combobox
        cBoxPlace.getItems().add("Caballo Blanco");
        cBoxPlace.getItems().add("Cartago");
        cBoxPlace.getItems().add("Cervantes");
        cBoxPlace.getItems().add("Orosi");
        cBoxPlace.getItems().add("Paraíso");
        cBoxPlace.getItems().add("Santa Rosa");
        cBoxPlace.getItems().add("Tierra Blanca");
        cBoxPlace.getItems().add("Turrialba");
        cBoxPlace.getItems().add("Ujarrás");
    }

    @FXML
    private void btnModify(ActionEvent event) throws GraphException, ListException {
        if (txtFieldName.getText().isEmpty() || cBoxPlace.getSelectionModel().getSelectedIndex()==-1 || cBoxSuperMarkets.getSelectionModel().getSelectedIndex()==-1) {//validaciones de campos vacios
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        } else {
            Supermarket sT = null;
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {//recorre el grafo y cada objeto sacado se asegura que sea de tipo supermercado
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket) {
                    Supermarket t = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (t.getName().equals(cBoxSuperMarkets.getValue())) {//si el nombre corresponde entonces lo encontro
                        sT = t;
                    }
                }
            }
            Supermarket sT2 = new Supermarket(txtFieldName.getText(), cBoxPlace.getValue());
            int aux = sT.getId();//guarda el id del objeto a modificar para que no se cambie
            util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(sT);//remueve el objeto
            sT2.setId(aux);//cambia su id para que correpsonda con el eliminado
            util.Utility.getlGraphRestaurants_Supermarkets().addVertex(sT2);//agrega el objeto
            txt.modifyFile("Restaurant_Supermarket.txt", sT.toString(), sT2.toString());//modifica el elemento en el txt
            int x = cBoxSuperMarkets.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
            cBoxSuperMarkets.getItems().remove(x); // se remueve
            cBoxSuperMarkets.getSelectionModel().clearSelection();//limpiamos el comboBox
            this.txtFieldName.setText("");//limpia texfield
            cBoxPlace.getSelectionModel().clearSelection();
            fillcBoxSuper();//actualiza el combobox
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText(sT.getName() + " modificado exitosamente!");
            a.showAndWait();
        }
    }

    public void fillcBoxSuper() throws ListException {//llena el combobox con los supermercados ingresados
        for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
            if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket) {
                Supermarket t = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                this.cBoxSuperMarkets.getItems().add(t.getName());
            }
        }
    }

    @FXML
    private void cBoxSuperMarkets(ActionEvent event) throws ListException {//metodo para que a la hora de seleccionar el supermercado automaticamente se seleccione el lugar que tenia previamente
        for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
            if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket) {
                Supermarket t = (Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                if(t.getName().equals(cBoxSuperMarkets.getValue()))
                    cBoxPlace.getSelectionModel().select(t.getLocation());//escoge en el combobox el lugar al que pertenece este supermercado
            }
        }
    }
}
