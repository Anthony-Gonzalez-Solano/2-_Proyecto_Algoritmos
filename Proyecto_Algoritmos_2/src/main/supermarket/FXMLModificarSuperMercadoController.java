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
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLModificarSuperMercadoController implements Initializable {
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
        try {
            fillcBoxSuper();
        } catch (ListException ex) {
            Logger.getLogger(FXMLModificarSuperMercadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //            for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {
//                this.cBoxPlace.getItems().add((Place)util.Utility.getmGraphPlace().getVertexByIndex(i).data);
//        }
cBoxPlace.getItems().add("Cachi");
cBoxPlace.getItems().add("Caballo Blanco");
cBoxPlace.getItems().add("Cartago");
cBoxPlace.getItems().add("Cervantes");
cBoxPlace.getItems().add("Orosi");
cBoxPlace.getItems().add("Paraiso");
cBoxPlace.getItems().add("Santa Rosa");
cBoxPlace.getItems().add("Tierra Blanca");
cBoxPlace.getItems().add("Turrialba");
cBoxPlace.getItems().add("Ujarras");
    }
    @FXML
    private void btnModify(ActionEvent event) throws GraphException, ListException {
        if (txtFieldName.getText().isEmpty()) {//validaciones de campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        }else{
            Supermarket sT=null;
        for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket){
                Supermarket t=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if(t.getName().equals(cBoxSuperMarkets.getValue()))
                        sT=t;
                }
            }
        Supermarket sT2=new Supermarket(txtFieldName.getText(), cBoxPlace.getValue());
        util.Utility.getlGraphRestaurants_Supermarkets().removeVertex(sT);
        util.Utility.getlGraphRestaurants_Supermarkets().addVertex(sT2);
        txt.modifyFile("Restaurant_Supermarket.txt", sT.toString(), sT2.toString());
        int x = cBoxSuperMarkets.getSelectionModel().getSelectedIndex(); // tomamos el valor del indice
                         cBoxSuperMarkets.getItems().remove(x); // se remueve
                        cBoxSuperMarkets.getSelectionModel().clearSelection();//limpiamos el comboBox
                        this.txtFieldName.setText("");
                        cBoxPlace.getSelectionModel().clearSelection();
                        fillcBoxSuper();
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setHeaderText(sT.getName()+" modificado exitosamente!");
                        a.showAndWait();
        }
    }
    public void fillcBoxSuper() throws ListException{
        for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket){
                Supermarket t=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                this.cBoxSuperMarkets.getItems().add(t.getName());
                }
            }
    }
}
