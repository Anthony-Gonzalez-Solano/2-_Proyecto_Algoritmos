/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domain.Restaurant;
import domain.Supermarket;
import domain.list.ListException;
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
import javafx.scene.control.DialogPane;
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
    @FXML
    private Menu M_grafoLugares;
    @FXML
    private Menu M_Establecimientos;
    @FXML
    private Menu M_mercancias;
    @FXML
    private Menu M_reportes;
    private Alert a;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (util.Utility.getIntro() != null) {
            M_Establecimientos.setVisible(false);
            M_mercancias.setVisible(false);
            M_reportes.setVisible(false);
        }
        a = new Alert(Alert.AlertType.ERROR);
        DialogPane dp = a.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dp.getStyleClass().add("myDialog");
    }

    @FXML
    private void Mi_Options_close(ActionEvent event) {
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("¿Esta seguro que quiere Cerrar la seción?");
        ButtonType yes = new ButtonType("Sí");
        ButtonType no = new ButtonType("No");
        a.getButtonTypes().clear();
        a.getButtonTypes().addAll(yes, no);
        a.setAlertType(Alert.AlertType.ERROR);

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
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("¿Esta seguro que quiere Finalizar el programa?");
        ButtonType yes = new ButtonType("Sí");
        ButtonType no = new ButtonType("No");
        a.getButtonTypes().clear();
        a.getButtonTypes().addAll(yes, no);
        a.setAlertType(Alert.AlertType.ERROR);
        
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
    private void MI_Restaurantes_Agregar(ActionEvent event) {
        loadPage("restaurant/FXMLAgregarRestaurante");
    }

    @FXML
    private void MI_Restaurantes_Modificar(ActionEvent event) {
        int res = 0;
        if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Restaurant)
                        res++;
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(res==0){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Restaurantes para modificar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("restaurant/FXMLModificarRestaurante");
    }

    @FXML
    private void MI_Restaurantes_Eliminar(ActionEvent event) {
        int res = 0;
        if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Restaurant)
                        res++;
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(res==0){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Restaurantes para eliminar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("restaurant/FXMLRemoverRestaurante");
    }

    @FXML
    private void MI_Restaurantes_Mostrar(ActionEvent event) {
        int res = 0;
        if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Restaurant)
                        res++;
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(res==0){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Restaurantes para mostrar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("restaurant/FXMLMostrarRestaurantes");
    }

    @FXML
    private void MI_Supermercado_Agregar(ActionEvent event) {
        loadPage("supermarket/FXMLAgregarSupermercado");
    }

    @FXML
    private void MI_Supermercado_Modificar(ActionEvent event) {
        int sup = 0;
        if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket)
                        sup++;
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(sup==0){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Supermercados para modificar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("supermarket/FXMLModificarSuperMercado");
    }

    @FXML
    private void MI_Supermercado_Eliminar(ActionEvent event) {
        int sup = 0;
        if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket)
                        sup++;
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(sup==0){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Supermercados para eliminar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
       loadPage("supermarket/FXMLRemoverSupermercado");
    }

    @FXML
    private void MI_Supermercado_Mostrar(ActionEvent event) {
        int sup = 0;
        if(!util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            try {
                for (int i = 0; i < util.Utility.getlGraphRestaurants_Supermarkets().size(); i++) {
                    if(util.Utility.getlGraphRestaurants_Supermarkets().getVertexByIndex(i).data instanceof Supermarket)
                        sup++;
                }
            } catch (ListException ex) {
                Logger.getLogger(FXMLVentanaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(sup==0){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Supermercados para mostrar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("supermarket/FXMLMostrarSupermercados");
    }

    @FXML
    private void MI_Comida_Agregar(ActionEvent event) {
        loadPage("Comidas/FXMLAgregarComida");
    }

    @FXML
    private void MI_Comida_Modificar(ActionEvent event) {
        if(util.Utility.getTreeFood().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Comidas para modificar   :c");
            a.setContentText("Agrega algunas!! :D ");
            a.showAndWait();
        }else
        loadPage("Comidas/FXMLModificarComidas");
    }

    @FXML
    private void MI_Comida_Eliminar(ActionEvent event) {
        if(util.Utility.getTreeFood().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Comidas para eliminar   :c");
            a.setContentText("Agrega algunas!! :D ");
            a.showAndWait();
        }else
        loadPage("Comidas/FXMLRemoverComida");
    }

    @FXML
    private void MI_Comida_Mostrar(ActionEvent event) {
        if(util.Utility.getTreeFood().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Comidas para mostrar   :c");
            a.setContentText("Agrega algunas!! :D ");
            a.showAndWait();
        }else
      loadPage("Comidas/FXMLMostrarComidas");
    }

    @FXML
    private void MI_Producto_Agregar(ActionEvent event) {
        loadPage("productos/FXMLAgregarProducto");
    }

    @FXML
    private void MI_Producto_Modificar(ActionEvent event) {
        if(util.Utility.getTreeProducts().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Productos para modificar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("productos/FXMLModificarProducto");
    }

    @FXML
    private void MI_Producto_Eliminar(ActionEvent event) {
        if(util.Utility.getTreeProducts().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Productos para eliminar   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("productos/FXMLRemoverProducto");
    }

    @FXML
    private void MI_Producto_Mostrar(ActionEvent event) {
        if(util.Utility.getTreeProducts().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Productos para mostrar  :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("productos/FXMLMostrarProductos");
    }

    @FXML
    private void MI_Busqueda(ActionEvent event) {
        loadPage("Busqueda/FXMLBusqueda");
    }

    @FXML
    private void MI_Rep_Restaurantes_Supermercados(ActionEvent event) {
        if(util.Utility.getlGraphRestaurants_Supermarkets().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Establecimientos para ver   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("reporte/FXMLReporte_SupermercadosRestaurantes");
    }

    @FXML
    private void MI_Rep_Comidas_Productos(ActionEvent event) {
        if(util.Utility.getTreeProducts().isEmpty() && util.Utility.getTreeFood().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("No hay Productos o Comidas para ver   :c");
            a.setContentText("Agrega algunos!! :D ");
            a.showAndWait();
        }else
        loadPage("reporte/FXMLReporte_ProductosComidas");
    }

    @FXML
    private void MI_Rep_Busquedas(ActionEvent event) {
        if(util.Utility.getTreeProducts().isEmpty() && util.Utility.getTreeFood().isEmpty()){
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setHeaderText("Nadie ha hecho una busqueda T^T");
            a.setContentText("Pueder ser el Primero!!!! ~(*^*)~");
            a.showAndWait();
        }else
        loadPage("reporte/FXMLReporte_Historico");
    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
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
