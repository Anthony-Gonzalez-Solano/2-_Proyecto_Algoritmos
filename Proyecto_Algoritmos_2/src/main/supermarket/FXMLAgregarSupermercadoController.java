/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.supermarket;

import domain.Restaurant;
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
import util.FileTXT;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLAgregarSupermercadoController implements Initializable {
    private util.FileTXT txt;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField txtFieldName;
    @FXML
    private ComboBox<String> cBoxPlace;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //            for (int i = 0; i < util.Utility.getmGraphPlace().size(); i++) {
//                this.cBoxPlace.getItems().add((Place)util.Utility.getmGraphPlace().getVertexByIndex(i).data);
//            }
        System.out.println(util.Utility.getmGraphPlace().toString());
    txt = new FileTXT(); // crear txt
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
    private void btnAdd(ActionEvent event) throws GraphException, ListException {
        Supermarket t;
        t=new Supermarket(txtFieldName.getText(),cBoxPlace.getValue());
        if (txtFieldName.getText().isEmpty() || cBoxPlace.getValue().equals("")) {//validaciones de campos vacios
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No debe dejar campos vacios");
            a.showAndWait();
        }else{
            if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()&&util.Utility.getlGraphRestaurants_Supermarkets().containsVertex(t)){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Supermercado ya existe");
            a.showAndWait();
            }else{
            if(util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
                
                util.Utility.getlGraphRestaurants_Supermarkets().addVertex(t);
                txt.writeFile("Restaurant_Supermarket.txt", t.toString());
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Supermercado ingresado correctamente");
                a.showAndWait();
                txtFieldName.setText("");
                cBoxPlace.getSelectionModel().clearSelection();
            }else{
                util.Utility.getlGraphRestaurants_Supermarkets().addVertex(t);
                txt.writeFile("Restaurant_Supermarket.txt", t.toString());
                Supermarket sT=null;
                
                 for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {
                        if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data.getClass() == Supermarket.class){
                            sT=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data;
                                    if(!(sT.getName().equals(txtFieldName.getText()))&&!(sT.getLocation().equals(cBoxPlace.getValue())))
                                        util.Utility.getlGraphRestaurants_Supermarkets().addEdge(t, sT);
                            }   
                        }
                       
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    for (int j = 0; j < util.Utility.getlGraphRestaurants_Supermarkets().size(); j++) {
                        if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data.getClass() == Supermarket.class){
                            sT=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data;
                            if(util.Utility.getlGraphRestaurants_Supermarkets().containsEdge(t,sT )){
                            int i2 = util.Utility.getmGraphPlace().getIndexOfVertex(sT.getLocation());
                                if ((int) util.Utility.getmGraphPlace().getAdjacencyMatrix()[i][i2] != 0) {
                                    if(sT.getLocation().equals(t.getLocation())){
                                       util.Utility.getlGraphRestaurants_Supermarkets().addWeight(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data, sT.getLocation() + "km"); 
                                    }else{
                                        int dist=(int)util.Utility.getmGraphPlace().getAdjacencyMatrix()[i][i2];
                                        util.Utility.getlGraphRestaurants_Supermarkets().addWeight(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data, util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(j).data, dist + "km"); 
                                    }
                                        
                                }
                            }   
                        }
                    }   
                }
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Supermercado ingresado correctamente");
                a.showAndWait();
                txtFieldName.setText("");
                cBoxPlace.getSelectionModel().clearSelection();
                }
            }
        }
    }
    
}
    

