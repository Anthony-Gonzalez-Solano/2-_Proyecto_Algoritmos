/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.places;

import domain.graph.AdjacencyListGraph;
import domain.graph.AdjacencyMatrixGraph;
import domain.graph.Graph;
import domain.graph.GraphException;
import domain.list.ListException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Callback;
import org.w3c.dom.Text;

/**
 * FXML Controller class
 *
 * @author nicole
 */
public class FXMLGrafoLugaresController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private CheckBox chbCartago;
    @FXML
    private CheckBox chbCaballoBlanco;
    @FXML
    private CheckBox chbTierraBlanca;
    @FXML
    private CheckBox chbSantaRosa;
    @FXML
    private CheckBox chbParaiso;
    @FXML
    private CheckBox chbOrosi;
    @FXML
    private CheckBox chbCachi;
    @FXML
    private CheckBox chbUjarras;
    @FXML
    private CheckBox chbCervantes;
    @FXML
    private CheckBox chbTurrialba;
    @FXML
    private Button btnGenerarGrafo;
    @FXML
    private AnchorPane apGraph;
    @FXML
    private TextField txtVertexGraph;
    @FXML
    private Button btnDistancias;
    
    private CheckBox[] checkList;
    
    Button button;
    float auxIndex;
    int longitud;
    int num, numVertex, numEdges;
    int cont, contEdges, contVertex, contDirections;
    Button buttonArray[];
    Line line = new Line();
    
    AdjacencyMatrixGraph grafoMatrix; /*= new AdjacencyMatrixGraph()*/
    
    public Text txtTitle;
    @FXML
    private TableView<List<String>> tblVDistancias;
    @FXML
    private Label lblDistancias;
    @FXML
    private TableColumn<List<String>, String> col_Orig_Dest;
    @FXML
    private TableColumn<List<String>, String> col_dist;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!util.Utility.getmGraphPlace().isEmpty()) {
            try {
                grafoMatrix = util.Utility.getmGraphPlace(); 
                num = grafoMatrix.size();
                drawGraph(grafoMatrix);
            } catch (ListException ex) {
                Logger.getLogger(FXMLGrafoLugaresController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GraphException ex) {
                Logger.getLogger(FXMLGrafoLugaresController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        checkList = new CheckBox[10];
        checkList[0] = chbTierraBlanca;
        checkList[1] = chbCaballoBlanco;
        checkList[2] = chbCachi;
        checkList[3] = chbCartago;
        checkList[4] = chbCervantes;
        checkList[5] = chbOrosi;
        checkList[6] = chbParaiso;
        checkList[7] = chbSantaRosa;
        checkList[8] = chbTurrialba;
        checkList[9] = chbUjarras;
        
        col_Orig_Dest.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(0)));
        col_dist.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().get(1)));
    }   

    private void fillGraph(){
        grafoMatrix = new AdjacencyMatrixGraph(10);
        try { 
            for (int i = 0; i < 10; i++) {
                if(checkList[i].isSelected()){
                    grafoMatrix.addVertex(checkList[i].getText());
                    System.out.println(checkList[i].getText());
                }
            }
            for (int k = 0; k < grafoMatrix.size(); k++) {
                for (int i = 0; i < grafoMatrix.size(); i++) {
                    if(!grafoMatrix.containsEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data)){
                        if(grafoMatrix.size()>4){
                            if(util.Utility.random(3)==1){
                                grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data);
                                grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data, util.Utility.random());                         
                            }
                        }else{
                            grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data);
                            grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data, util.Utility.random()); 
                        }
                    }
                }
            }
        } catch (ListException | GraphException ex) {
            Logger.getLogger(FXMLGrafoLugaresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void btnGenerarGrafo(ActionEvent event) throws ListException, GraphException {
        if (this.chbCaballoBlanco.isSelected() || this.chbCachi.isSelected() ||
            this.chbCartago.isSelected() || this.chbCervantes.isSelected() || 
            this.chbOrosi.isSelected() || this.chbParaiso.isSelected() ||
            this.chbSantaRosa.isSelected() || this.chbTierraBlanca.isSelected() ||
            this.chbTurrialba.isSelected() || this.chbUjarras.isSelected()) 
        {      
            int x = 0;
            for (int i = 0; i < 10; i++) {
                if(checkList[i].isSelected()){
                    x++;
                }
            }
            if(x>2){
                fillGraph();
                num = grafoMatrix.size();
                drawGraph(grafoMatrix);
                util.Utility.setmGraphPlace(grafoMatrix);
                loadTable(grafoMatrix.getAdjacencyMatrix());
            }else{
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Debe seleccionar almenos 2 \nlugares para al busqued");
                a.showAndWait();
            }
        }
    }

    @FXML
    private void btnDistancias(ActionEvent event) throws ListException, GraphException {
        if(!util.Utility.getmGraphPlace().isEmpty()){
            Object[][] m = new Object [grafoMatrix.size()][grafoMatrix.size()];
            for (int i = 0; i < grafoMatrix.size(); i++) {
                for (int j = 0; j < grafoMatrix.size(); j++) {
                    m[i][j] = 0; 
                }  
            }
            grafoMatrix.setAdjacencyMatrix(m);
            for (int k = 0; k < grafoMatrix.size(); k++) {
                for (int i = 0; i < grafoMatrix.size(); i++) {
                    if(!grafoMatrix.containsEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data)){
                        if(grafoMatrix.size()>4){
                            if(util.Utility.random(3)==1){
                                grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data);
                                grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data, util.Utility.random());                         
                            }
                        }else{
                            grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data);
                            grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(i).data, util.Utility.random()); 
                        }
                    }
                }
            }
            num = grafoMatrix.size();
            drawGraph(grafoMatrix);
            util.Utility.setmGraphPlace(grafoMatrix);
            loadTable(m);
        }
    }//btnDistancias
    
    private void drawGraph(Graph grafo) throws ListException, GraphException {
        apGraph.getChildren().clear();
        longitud = 180;
        cont = 0;
        buttonArray = new Button[grafo.size()];
        drawVertex(grafo);
        drawEdges(grafo);
        
    }//drawGraph

    private void drawVertex(Graph grafo) {
        for (float i = 0; i < 360f - 0.1; i += 360f / num) {
            button = new Button();
            buttonArray[cont] = button;
            button.setId(String.valueOf(i));
            apGraph.getChildren().add(button);
            button.setText(grafo.getVertexByIndex(cont).data.toString());
            cont++;

            if (i >= 0 && i < 90) {
                auxIndex = i;
                double x = Math.sin(Math.toRadians(auxIndex)) * longitud;
                double y = Math.cos(Math.toRadians(auxIndex)) * longitud;

                button.setLayoutX(txtVertexGraph.getLayoutX() + y * 1.5);
                button.setLayoutY(txtVertexGraph.getLayoutY() - x);
            }
            if (i >= 90 && i < 180) {
                auxIndex = i - 90;
                double x = Math.sin(Math.toRadians(auxIndex)) * longitud;
                double y = Math.cos(Math.toRadians(auxIndex)) * longitud;

                button.setLayoutX(txtVertexGraph.getLayoutX() - x * 1.5);
                button.setLayoutY(txtVertexGraph.getLayoutY() - y);
            }
            if (i >= 180 && i < 270) {
                auxIndex = i - 180;
                double x = Math.sin(Math.toRadians(auxIndex)) * longitud;
                double y = Math.cos(Math.toRadians(auxIndex)) * longitud;

                button.setLayoutX(txtVertexGraph.getLayoutX() - y * 1.5);
                button.setLayoutY(txtVertexGraph.getLayoutY() + x);
            }
            if (i >= 270 && i < 360) {
                auxIndex = i - 270;
                double x = Math.sin(Math.toRadians(auxIndex)) * longitud;
                double y = Math.cos(Math.toRadians(auxIndex)) * longitud;

                button.setLayoutX(txtVertexGraph.getLayoutX() + x * 1.5);
                button.setLayoutY(txtVertexGraph.getLayoutY() + y);
            }
        }
    }

    private void drawEdges(Graph grafo) throws GraphException, ListException {
        contEdges = 0;
        for (int i = 0; i < grafoMatrix.size(); i++) {
            for (int j = 0; j < grafoMatrix.size(); j++) {
                if (!buttonArray[j].getText().equals(buttonArray[i].getText())) {
                    //Edge hacia otra direccion   
                    if (grafo.containsEdge(buttonArray[i].getText(), buttonArray[j].getText())) {
                        //drawArrow(btnArray[j], btnArray[i]);
                        line = new Line();
                        apGraph.getChildren().add(line);
                        line.setLayoutX(buttonArray[j].getLayoutX());
                        line.setLayoutY(buttonArray[j].getLayoutY());
                        line.toBack();
                        line.setStartX(15);
                        line.setStartY(15);
                        line.setEndX((buttonArray[i]).getLayoutX() - buttonArray[j].getLayoutX() + 15);
                        line.setEndY((buttonArray[i]).getLayoutY() - buttonArray[j].getLayoutY() + 15);
                        line.setStrokeWidth(4);
                        line.setId(buttonArray[i].getText() + " , " + buttonArray[j].getText());
                        
                        javafx.scene.text.Text txt = new javafx.scene.text.Text(""+grafo.getAdjacencyMatrix()[grafo.indexOf(buttonArray[i].getText())][grafo.indexOf(buttonArray[j].getText())]+" km");
                        txt.setLayoutX(15+buttonArray[j].getLayoutX()+ (buttonArray[i].getLayoutX()-buttonArray[j].getLayoutX()+15)/2);
                        txt.setLayoutY(buttonArray[j].getLayoutY()+ (buttonArray[i].getLayoutY()-buttonArray[j].getLayoutY()+15)/2);
                        txt.setFill(Paint.valueOf("#ffffff"));
                        apGraph.getChildren().add(txt);
                        line.setOnMouseMoved(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Line line = (Line) event.getSource();
                                //txtTitle.setText(line.getId());
                            }
                        });
                    }
                } else {
                    
                }
            }
        }//for
        contEdges = contEdges / 2;
    }

    private void loadTable(Object[][] matrix) {
        tblVDistancias.getItems().clear();
        int dis = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i+1; j < matrix.length; j++) {
                if((int)matrix[i][j]!=0){
                    List<String> list = new ArrayList();
                    list.add(grafoMatrix.getVertexByIndex(i).data+", "+grafoMatrix.getVertexByIndex(j).data);
                    list.add(matrix[i][j]+" km");
                    dis = dis + (int)matrix[i][j];
                    tblVDistancias.getItems().add(list);
                }
            }
        }
        List<String> list = new ArrayList();
        list.add("----------------------");
        list.add("  ");
        tblVDistancias.getItems().add(list);
        list = new ArrayList();
        list.add("Distancia total");
        list.add(dis + " km");
        tblVDistancias.getItems().add(list);
    }//loadPage
}//FXMLGrafoLugaresController
