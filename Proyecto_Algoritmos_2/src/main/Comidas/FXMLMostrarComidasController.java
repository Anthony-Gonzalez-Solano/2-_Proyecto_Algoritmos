/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Comidas;

import domain.Food;
import domain.Restaurant;
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
import main.productos.FXMLMostrarProductosController;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLMostrarComidasController implements Initializable {

    private List<String> list;
    @FXML
    private TableView<List<String>> tableComidas;
    @FXML
    private TableColumn<List<String>, String> colum0;
    @FXML
    private TableColumn<List<String>, String> colum1;
    @FXML
    private TableColumn<List<String>, String> colum2;

    /**
     * Initializes the controller class. NO LISTA TODAVIA
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colum0.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));

        colum1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));

        colum2.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        this.tableComidas.getColumns().addAll(colum0, colum1, colum2);
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
            Food f = (Food) node.data;
            list.add(f.getName());
            list.add(f.getPrice());
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Restaurant.class) {
                    Restaurant r = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (r.getId() == f.getRestaurantID()) {
                        list.add(r.getName());
                    }
                }
            }

            tableComidas.getItems().add(list);//se llena la talba
            preOrder(node.left);
            preOrder(node.right);
        }
        return result;
    }
}
