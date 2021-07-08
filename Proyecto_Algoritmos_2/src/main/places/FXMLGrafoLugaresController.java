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
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private TableView<String[]> tvDistancias;
    @FXML
    private TableColumn<float[], String> columnOrigenDestino;
    @FXML
    private TableColumn<float[], String> columnDistancia;
    
    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

    private final int maxNumSelected =  10; 
    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    
    Button button;
    float auxIndex;
    int longitud;
    int num, numVertex, numEdges;
    Button buttonArray[];
    int cont, contEdges, contVertex, contDirections;
    Line line = new Line();
    
    public Text txtTitle;
    AdjacencyMatrixGraph grafo; /*= new AdjacencyMatrixGraph()*/
    AdjacencyListGraph aux;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        apGraph.setVisible(false);
        grafo = util.Utility.getmGraphPlace();
        try {
            for (int i = 0; i < grafo.size(); i++) {
                for (int j = 0; j < grafo.size(); j++) {
                    if (!grafo.containsEdge(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data)) {
                        if (!util.Utility.equals(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data)) {
                            grafo.addEdge(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data);
                            grafo.addWeight(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data, 5 + util.Utility.random(50));
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        if (grafo != null && !grafo.isEmpty()) {
            try {
                apGraph.getChildren().clear();
                drawGraph(aux);//aux
                //txtTitle = new Text(100, 100, "");
                //apGraph.getChildren().add(txtTitle);
            } catch (Exception e) {
            }
            
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
            //this.num = util.Utility
            grafo = new AdjacencyMatrixGraph(num);
            //fillGraph(grafo);
            loadTable(tvDistancias);
            drawGraph(aux);
        }
    }

    @FXML
    private void btnDistancias(ActionEvent event) throws ListException, GraphException {
        randomDistancias();  
    }//btnDistancias
/*
    private void fillGraph(AdjacencyMatrixGraph grafo) throws GraphException, ListException {
        for (int i = 0; i < num; i++) {
            Character character = util.Utility.randAlphabet();
            while (!grafo.isEmpty() && grafo.containsVertex(character)) {
                character = util.Utility.randAlphabet();
            }
            grafo.addVertex(character);
        }
    }
*/
    public void chb() {
        //Otra forma de llenar el grafo a partir de los chb seleccionados con un array de chb
    }//chb
    
    private void drawGraph(AdjacencyListGraph grafo) throws ListException, GraphException {
        apGraph.getChildren().clear();
        apGraph.setVisible(true);
        longitud = 200;
        buttonArray = new Button[grafo.size()];
        if (grafo != null && !grafo.isEmpty()) {
            drawVertex(grafo);
        }//if
        drawEdges(grafo);
        //txtTitle = new Text("");
        apGraph.getChildren().add(ap);
        ap.toBack();
        //apGraph.getChildren().add(txtTitle);
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

    private void drawEdges(AdjacencyListGraph grafo) throws GraphException, ListException {
        contEdges = 0;
        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                if (!buttonArray[j].getText().equals(buttonArray[i].getText())) {
                    //Edge hacia otra direccion   
                    if (grafo.containsEdge(buttonArray[i].getText().charAt(0), buttonArray[j].getText().charAt(0))) {
                        //drawArrow(btnArray[j], btnArray[i]);
                        //line = new Line();
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
                        line.setOnMouseMoved(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                Line line = (Line) event.getSource();
                                //txtTitle.setText(line.getId());
                            }
                        });
                    }
                } else {
                    //Arista hacia si mismo
                    if (grafo.containsEdge(buttonArray[i].getText().charAt(0), buttonArray[j].getText().charAt(0))) {
                        //drawItSelfEdge(btnArray[i]);
                    }
                }
            }
        }//for
        contEdges = contEdges / 2;
    }
    
    private void randomDistancias() throws ListException, GraphException{
        for (int i = 0; i < grafo.size(); i++) {
            for (int j = 0; j < grafo.size(); j++) {
                if (!(grafo.getVertexByIndex(i).data.equals(grafo.getVertexByIndex(i).data))) {
                    grafo.addEdge(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data);
                    grafo.addWeight(grafo.getVertexByIndex(i).data, grafo.getVertexByIndex(j).data, 5 + util.Utility.random(50));
                }//if
            }//for j
        }//for i
    }//randomDistancias

    private void loadTable(TableView<String[]> tvDistancias) {
        columnOrigenDestino.setCellValueFactory(col -> new SimpleStringProperty(String.valueOf(tvDistancias)));
        columnDistancia.setCellValueFactory(col -> new SimpleStringProperty(String.valueOf(tvDistancias)));
    }//loadPage
    
    
//            for (int k = 1; k <= n; k++) { 
//                for (int i = 0; i < 2; i++) {
//                    int aux = util.Utility.random(9)+1;
//                    while(g.containsEdge(g.getVertexByIndex(k).data, g.getVertexByIndex(aux).data)){
//                        aux = util.Utility.random(9)+1;
//                    }
//                    g.addEdge(g.getVertexByIndex(k).data, g.getVertexByIndex(aux).data);
//                    g.addWeight(g.getVertexByIndex(k).data, g.getVertexByIndex(aux).data, util.Utility.random());
//                }
//            }
    
    
    
}//FXMLGrafoLugaresController
