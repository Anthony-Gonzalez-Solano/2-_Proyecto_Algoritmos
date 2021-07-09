/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Security;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anthony G.S
 */
public class FXMLVentanaPrincipalController implements Initializable {
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private MenuItem Mi_Options_close;
    @FXML
    private MenuItem Mi_Options_finish;
    @FXML
    private MenuItem MI_GLugares_Agregar;
    @FXML
    private MenuItem MI_GLugares_Eliminar;
    @FXML
    private MenuItem MI_GLugares_Mostrar;
    @FXML
    private MenuItem MI_Restaurantes_Agregar;
    @FXML
    private MenuItem MI_Restaurantes_Modificar;
    @FXML
    private MenuItem MI_Restaurantes_Eliminar;
    @FXML
    private MenuItem MI_Restaurantes_Mostrar;
    @FXML
    private MenuItem MI_Supermercado_Agregar;
    @FXML
    private MenuItem MI_Supermercado_Modificar;
    @FXML
    private MenuItem MI_Supermercado_Eliminar;
    @FXML
    private MenuItem MI_Supermercado_Mostrar;
    @FXML
    private MenuItem MI_Comida_Agregar;
    @FXML
    private MenuItem MI_Comida_Modificar;
    @FXML
    private MenuItem MI_Comida_Eliminar;
    @FXML
    private MenuItem MI_Comida_Mostrar;
    @FXML
    private MenuItem MI_Producto_Agregar;
    @FXML
    private MenuItem MI_Producto_Modificar;
    @FXML
    private MenuItem MI_Producto_Eliminar;
    @FXML
    private MenuItem MI_Producto_Mostrar;
    @FXML
    private Menu MI_Busqueda;
    @FXML
    private MenuItem MI_Rep_Restaurantes_Supermercados;
    @FXML
    private MenuItem MI_Rep_Comidas_Productos;
    @FXML
    private MenuItem MI_Rep_Busquedas;
    @FXML
    private MenuItem MI_BusquedaR;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    @FXML
    private void Mi_Options_close(ActionEvent event) {
         Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("¿Esta seguro que quiere Cerrar la seción?");
        ButtonType yes = new ButtonType("Sí");
        ButtonType no = new ButtonType("No");
        a.getButtonTypes().clear();
        a.getButtonTypes().addAll(yes,no);

        Optional<ButtonType> option = a.showAndWait(); 
        if (option.get() == yes) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("FXMLSecurity.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FXMLSecurityController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) bp.getScene().getWindow();
            stage.setScene(scene);
        }
    }

    @FXML
    private void Mi_Options_finish(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("¿Esta seguro que quiere Finalizar el programa?");
        ButtonType yes = new ButtonType("Sí");
        ButtonType no = new ButtonType("No");
        a.getButtonTypes().clear();
        a.getButtonTypes().addAll(yes,no);

        Optional<ButtonType> option = a.showAndWait(); 
        if (option.get() == yes) {
            System.exit(0);
        }
    }

    @FXML
    private void MI_GLugares_Agregar(ActionEvent event) {
        loadPage("places/FXMLGrafoLugares");
    }

    @FXML
    private void MI_GLugares_Eliminar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_GLugares_Mostrar(ActionEvent event) {
        //loadPage(page);
        //loadPage("FXMLGrafoLugares");//carga el ejercicio 1
    }

    @FXML
    private void MI_Restaurantes_Agregar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Restaurantes_Modificar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Restaurantes_Eliminar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Restaurantes_Mostrar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Supermercado_Agregar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Supermercado_Modificar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Supermercado_Eliminar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Supermercado_Mostrar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Comida_Agregar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Comida_Modificar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Comida_Eliminar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Comida_Mostrar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Producto_Agregar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Producto_Modificar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Producto_Eliminar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Producto_Mostrar(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Busqueda(ActionEvent event) {
        loadPage("Busqueda/FXMLBusqueda");
    }

    @FXML
    private void MI_Rep_Restaurantes_Supermercados(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Rep_Comidas_Productos(ActionEvent event) {
        //loadPage(page);
    }

    @FXML
    private void MI_Rep_Busquedas(ActionEvent event) {
        //loadPage(page);
    }
    
    
    
    private void loadPage(String page){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
         this.bp.setCenter(root);   
    }

    @FXML
    private void MI_BusquedaR(ActionEvent event) {
        loadPage("Busqueda/FXMLBusqueda");
    }
}
