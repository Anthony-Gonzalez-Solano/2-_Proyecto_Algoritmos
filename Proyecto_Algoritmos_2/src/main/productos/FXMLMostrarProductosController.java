/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.productos;

import domain.Product;
import domain.Supermarket;
import domain.list.ListException;
import domain.tree.BTreeNode;
import domain.tree.TreeException;
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
public class FXMLMostrarProductosController implements Initializable {
    private List<String> list;
    private TableColumn<List<String>, String> column1;
    private TableColumn<List<String>, String> column2;
    private TableColumn<List<String>, String> column3;
    private TableColumn<List<String>, String> column4;
    @FXML
    private TableView<List<String>> tableView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            column1=new TableColumn<>("Nombre");
            column1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
            column2=new TableColumn<>("Precio");
            column2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
            column3=new TableColumn<>("Lugar");
            column3.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            column4=new TableColumn<>("ID");
            column4.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
            this.tableView.getColumns().addAll(column1,column2,column3,column4);
        try {
            preOrder();
        } catch (TreeException ex) {
            Logger.getLogger(FXMLMostrarProductosController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ListException ex) {
            Logger.getLogger(FXMLMostrarProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public String preOrder() throws TreeException, ListException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return preOrder(util.Utility.getTreeProducts().getRoot());
    }

    public boolean isEmpty() {
        return util.Utility.getTreeProducts().getRoot() == null;
    }

    private String preOrder(BTreeNode node) throws ListException {
        String result = "";
        List list = new ArrayList();
        if (node != null) {
            Product p= (Product)node.data;
            list.add(p.getName());
            list.add(String.valueOf(p.getPrice()));
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket){
                Supermarket s=(Supermarket)util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                if(s.getId()==p.getSupermarketID())
                    list.add(s.getName());
                }
            }
            list.add(String.valueOf(p.getID()));
            tableView.getItems().add(list);//se llena la talba
            preOrder(node.left);
            preOrder(node.right);
        }
        return result;
    }
}
