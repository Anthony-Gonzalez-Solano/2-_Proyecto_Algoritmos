/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.supermarket;

import domain.Supermarket;
import domain.graph.GraphException;
import domain.graph.Place;
import domain.list.ListException;
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
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLAgregarSupermercadoController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtFieldName;
    @FXML
    private ComboBox<Place> cBoxPlace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {
                this.cBoxPlace.getItems().add((Place)util.Utility.getmGraphPlace().getVertexByIndex(i).data);
            }
        } catch (ListException ex) {
            Logger.getLogger(FXMLAgregarSupermercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void btnAdd(ActionEvent event) throws GraphException, ListException {
        if (txtFieldName.getText().isEmpty() || cBoxPlace.getValue().equals("")) {//validaciones de campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        }else{
            if(util.Utility.getlGraphRestaurants_Supermarkets().isEmpty())
        util.Utility.getlGraphRestaurants_Supermarkets().addVertex(new Supermarket(txtFieldName.getText(),cBoxPlace.getValue().getName()));
            else{
                util.Utility.getlGraphRestaurants_Supermarkets().addVertex(new Supermarket(txtFieldName.getText(),cBoxPlace.getValue().getName()));
                Supermarket sT2=new Supermarket(txtFieldName.getText(),cBoxPlace.getValue().getName());
                Supermarket sT=null;
                for (int i = 1; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    sT=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if(!(sT.getName().equals(txtFieldName.getText()))&&!(sT.getLocation().equals(cBoxPlace.getValue().getName())))
                        util.Utility.getlGraphRestaurants_Supermarkets().addEdge(sT2, sT);
                }
            }
    }
    }
    
}
