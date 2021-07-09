/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.Comidas;

import domain.Food;
import domain.tree.BTreeNode;
import domain.tree.TreeException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
        colum0.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
        colum1.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
        colum2.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));

        list = new ArrayList<>();
        Food f = new Food("", 0, 0);

        list.add(f.getName() + "");//agregamos en el primer indice el ID
        list.add(f.getPrice() + "");//agregamos en el segundo indice el student ID
        list.add(f.getRestaurantID() + "");//agregamos en el tercer indice el lastName

        tableComidas.getItems().add(list);// se agregan los datos a la tabla

    }

    public String preOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
        }
        return preOrder(util.Utility.getTreeFood().getRoot());
    }

    public boolean isEmpty() {
        return util.Utility.getTreeFood().getRoot() == null;
    }

    private String preOrder(BTreeNode node) {
        String result = "";
        if (node != null) {
            // comboComidas.getItems().add((Food) node.data);
            // result = node.data + " ";
            preOrder(node.left);
            preOrder(node.right);

            //    result += preOrder(node.right);
        }
        return result;
    }
}
