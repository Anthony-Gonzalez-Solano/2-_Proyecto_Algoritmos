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
    private TableView<String[]> tvDistancias;
    private TableColumn<float[], String> columnOrigenDestino;
    private TableColumn<float[], String> columnDistancia;
    
    private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();
    private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

    private final int maxNumSelected =  10; 
    private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
    private CheckBox[] checkList;
    Button button;
    float auxIndex;
    int longitud;
    int num, numVertex, numEdges;
    int cont, contEdges, contVertex, contDirections;
    Button buttonArray[];
    Line line = new Line();
    
    AdjacencyMatrixGraph grafoMatrix; /*= new AdjacencyMatrixGraph()*/
    AdjacencyListGraph grafoList;
    
    public Text txtTitle;
    
    @FXML
    private TableView<String[]> tblVDistancias;
    @FXML
    private Label lblDistancias;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                for (int i = 0; i < 1; i++) {
                    int aux = util.Utility.random(grafoMatrix.size()-1);
                    while(grafoMatrix.containsEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(aux).data)){
                        aux = util.Utility.random(grafoMatrix.size()-1);
                    }
                    grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(aux).data);
                    grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(aux).data, util.Utility.random()); 
                    System.out.println("aaaa");
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
            fillGraph();
            num = grafoMatrix.size();
            //this.num = util.Utility
            //grafoMatrix = new AdjacencyMatrixGraph(num);
            //fillGraph(grafo);
            //loadTable(tvDistancias);
            drawGraph(grafoMatrix);
            util.Utility.setmGraphPlace(grafoMatrix);
        }
    }

    @FXML
    private void btnDistancias(ActionEvent event) throws ListException, GraphException {
        Object[][] m = new Object [grafoMatrix.size()][grafoMatrix.size()];
        for (int i = 0; i < grafoMatrix.size(); i++) {
            for (int j = 0; j < grafoMatrix.size(); j++) {
                m[i][j] = 0; 
            }  
        }
        grafoMatrix.setAdjacencyMatrix(m);
        for (int k = 0; k < grafoMatrix.size(); k++) {
            for (int i = 0; i < 1; i++) {
                int aux = util.Utility.random(grafoMatrix.size()-1);
                while(grafoMatrix.containsEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(aux).data)){
                    aux = util.Utility.random(grafoMatrix.size()-1);
                }
                grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(aux).data);
                grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(k).data, grafoMatrix.getVertexByIndex(aux).data, util.Utility.random());
                System.out.println("aaaa");
            }
        }
        /*
        //Para la tabla
        String[][] tableMatrix = new String[grafoMatrix.size() * grafoMatrix.size()][2];
        tableMatrix[0][1] = "Origen, Destino";
        tableMatrix[0][0] = "Distancia";
        int contDirecciones = 1;
        
        grafoList = new AdjacencyListGraph(grafoMatrix.size());
        for (int i = 0; i < grafoMatrix.size(); i++) {
            if (checkList != null) {
                if (checkList[i].isSelected()) {
                    //Place place;
                    //place = (Place) grafoMatrix.getVertexByIndex(i).add;
                    grafoMatrix.addVertex(checkList[i].getText());
                }
            }
        }
        */
        num = grafoMatrix.size();
        drawGraph(grafoMatrix);
        util.Utility.setmGraphPlace(grafoMatrix);
        loadTable(tvDistancias, m);
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
        
        chbCaballoBlanco.setOnAction(e -> handleButtonAction(e));
        chbCachi.setOnAction(e -> handleButtonAction(e));
        chbCartago.setOnAction(e -> handleButtonAction(e));
        chbCervantes.setOnAction(e -> handleButtonAction(e));
        chbOrosi.setOnAction(e -> handleButtonAction(e));
        chbParaiso.setOnAction(e -> handleButtonAction(e));
        chbSantaRosa.setOnAction(e -> handleButtonAction(e));
        chbTierraBlanca.setOnAction(e -> handleButtonAction(e));
        chbTurrialba.setOnAction(e -> handleButtonAction(e));
        chbUjarras.setOnAction(e -> handleButtonAction(e));
        
    }//chb
    
    private void drawGraph(Graph grafo) throws ListException, GraphException {
        apGraph.getChildren().clear();
        longitud = 180;
        cont = 0;
        buttonArray = new Button[grafo.size()];
        drawVertex(grafo);
        drawEdges(grafo);
        //txtTitle = new Text("");
        //apGraph.getChildren().add(ap);
        //ap.toBack();
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
    
    private void randomDistancias() throws ListException, GraphException{
        for (int i = 0; i < grafoMatrix.size(); i++) {
            for (int j = 0; j < grafoMatrix.size(); j++) {
                if (!(grafoMatrix.getVertexByIndex(i).data.equals(grafoMatrix.getVertexByIndex(i).data))) {
                    grafoMatrix.addEdge(grafoMatrix.getVertexByIndex(i).data, grafoMatrix.getVertexByIndex(j).data);
                    grafoMatrix.addWeight(grafoMatrix.getVertexByIndex(i).data, grafoMatrix.getVertexByIndex(j).data, 5 + util.Utility.random(50));
                }//if
            }//for j
        }//for i
    }//randomDistancias

    private void loadTable(TableView<String[]> table, Object[][] distanceMatrix) {
        //columnOrigenDestino.setCellValueFactory(col -> new SimpleStringProperty(String.valueOf(tvDistancias)));
        //columnDistancia.setCellValueFactory(col -> new SimpleStringProperty(String.valueOf(tvDistancias)));
    
        distanceMatrix = cleanTable(distanceMatrix);
        
        table.getColumns().clear();
        
        ObservableList<String[]> data = FXCollections.observableArrayList();
        //data.addAll(Arrays.asList(distanceMatrix));
        data.remove(0);
        int x = 180;
        for (int i = 0; i < distanceMatrix[0].length; i++) {
            TableColumn tableCol = new TableColumn((String) distanceMatrix[0][i]);
            tableCol.setEditable(false);
            tableCol.setSortable(false);
            final int numCol = i;
            tableCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> arg0) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    //return new SimpleStringProperty((Place.getValue()[numCol]));
                    //return [i][numCol];
                }
            });
            table.getColumns().add(tableCol);
            tableCol.setPrefWidth(x);
            x = 65;
        }
        table.setItems(data);
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

    @FXML
    private void handleButtonAction(ActionEvent e) {
        int cont = 0;
        String choices = "";
        if (chbCaballoBlanco.isSelected()) {
            cont++;
            choices+=chbCaballoBlanco.getText() + "\n";
        }
        if (chbCachi.isSelected()) {
            cont++;
            choices+=chbCachi.getText() + "\n";
        }
        if (chbCartago.isSelected()) {
            cont++;
            choices+=chbCartago.getText() + "\n";
        }
        if (chbCervantes.isSelected()) {
            cont++;
            choices+=chbCervantes.getText() + "\n";
        }
        if (chbOrosi.isSelected()) {
            cont++;
            choices+=chbOrosi.getText() + "\n";
        }
        if (chbParaiso.isSelected()) {
            cont++;
            choices+=chbParaiso.getText() + "\n";
        }
        if (chbSantaRosa.isSelected()) {
            cont++;
            choices+=chbSantaRosa.getText() + "\n";
        }
        if (chbTierraBlanca.isSelected()) {
            cont++;
            choices+=chbTierraBlanca.getText() + "\n";
        }
        if (chbTurrialba.isSelected()) {
            cont++;
            choices+=chbTurrialba.getText() + "\n";
        }
        if (chbUjarras.isSelected()) {
            cont++;
            choices+=chbUjarras.getText() + "\n";
        }
    }
    
    private Object[][] cleanTable(Object[][] m) {
        int cont = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[i][0] != null && !m[i][0].equals("")) {
                cont++;
            }
        }
        
        Object auxM[][] = new Object[cont][2];
        cont = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[i][0] != null && !m[i][0].equals("")) {
                auxM[cont][0] = (String) m[i][0];
                auxM[cont++][1] = (String) m[i][1];
            }
        }
        return auxM;
    }
    
}//FXMLGrafoLugaresController
