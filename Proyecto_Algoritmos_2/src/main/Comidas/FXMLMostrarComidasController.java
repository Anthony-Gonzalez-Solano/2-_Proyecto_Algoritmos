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
import javafx.scene.control.Alert;
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
    private TableView<List<String>> tableViewComidas;
    @FXML
    private TableColumn<List<String>, String> columnaNombre;
    @FXML
    private TableColumn<List<String>, String> columnaPrecio;
    @FXML
    private TableColumn<List<String>, String> columnaRestaurante;

    /**
     * Initializes the controller class. NO LISTA TODAVIA
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        columnaNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
        columnaPrecio.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
        columnaRestaurante.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(2)));
//
//        this.tableViewComidas.getColumns().add(columnaNombre);
//        this.tableViewComidas.getColumns().add(columnaPrecio);
//        this.tableViewComidas.getColumns().add(columnaRestaurante);

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
          Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay comidas agregados. Por favor ingrese una comida primero");
            a.showAndWait();
        }
        return preOrder(util.Utility.getTreeFood().getRoot());
    }

    public boolean isEmpty() {
        return util.Utility.getTreeFood().getRoot() == null;
    }

    private String preOrder(BTreeNode node) throws ListException {
        String result = "";
        list = new ArrayList();
        if (node != null) {
            Food f = (Food) node.data;
            list.add(f.getName());                                         
            list.add(f.getPrice() + "");
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                if (util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data.getClass() == Restaurant.class) {
                    Restaurant r = (Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data;
                    if (r.getId() == f.getRestaurantID()) {
                        list.add(r.getName());
                    }
                }
            }
            tableViewComidas.getItems().add(list);//se llena la talba
            preOrder(node.left);
            preOrder(node.right);
        }
        return result;
    }
}
