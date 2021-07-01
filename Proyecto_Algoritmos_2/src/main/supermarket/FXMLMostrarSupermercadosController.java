/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.supermarket;

import domain.Supermarket;
import domain.list.ListException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Adrian Ureña Moraga <Agitor Lucens V>
 */
public class FXMLMostrarSupermercadosController implements Initializable {
    private TableColumn <List<String>,String>column1;
    private TableColumn <List<String>,String>column2;
    private TableColumn <List<String>,String>column3;
    @FXML
    private TableView<List<String>> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         if(this.tableView.getColumns().isEmpty()){
            column1=new TableColumn<>("Nombre");
            column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
            column2=new TableColumn<>("Lugar");
            column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
            column3=new TableColumn<>("ID");
            column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            
            this.tableView.getColumns().addAll(column1,column2,column3);//agregar columnas

    }
        try {
            Supermarket s=null;
            for (int i = 1; i <= util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                List list = new ArrayList(); 
                s=(Supermarket) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                list.add(s.getName());
                list.add(s.getLocation());
                list.add(s.getId());
                tableView.getItems().add(list);
            }   } catch (ListException ex) {
            Logger.getLogger(FXMLMostrarSupermercadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
