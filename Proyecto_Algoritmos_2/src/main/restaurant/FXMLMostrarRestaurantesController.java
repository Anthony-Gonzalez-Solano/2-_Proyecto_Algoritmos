/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.restaurant;

import domain.Restaurant;
import domain.list.ListException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Dell 7470
 */
public class FXMLMostrarRestaurantesController implements Initializable {

    @FXML
    private TableView<Restaurant> tableRestaurantes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                if (this.tableRestaurantes.getColumns().isEmpty()) {
            TableColumn<Restaurant, String> column1 = new TableColumn<>("Nombre"); // nombre de las columnas
            column1.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Restaurant, String> column2 = new TableColumn<>("Localizacion");
            column2.setCellValueFactory(new PropertyValueFactory<>("location"));
             TableColumn<Restaurant, String> column3 = new TableColumn<>("ID");
            column3.setCellValueFactory(new PropertyValueFactory<>("id"));
            this.tableRestaurantes.getColumns().add(column1);//agregar columnas
            this.tableRestaurantes.getColumns().add(column2);
            this.tableRestaurantes.getColumns().add(column3);

        }

        try {
            while (!this.tableRestaurantes.getItems().isEmpty()) {
                this.tableRestaurantes.getItems().remove(0);
            }
            for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                this.tableRestaurantes.getItems().add((Restaurant) util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data);//recorremos la lista de restaurantes y casteamos para agregar los datos de la lista a la tabla 

            }
        } catch (NullPointerException eda) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error inesperado");
            a.showAndWait();
        } catch (ListException ex) {

            Logger.getLogger(FXMLMostrarRestaurantesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
