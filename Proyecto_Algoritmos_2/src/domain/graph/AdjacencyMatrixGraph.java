/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.graph;

import domain.list.ListException;
import domain.queue.LinkedQueue;
import domain.queue.QueueException;
import domain.stack.LinkedStack;
import domain.stack.StackException;
import javafx.beans.binding.IntegerBinding;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class AdjacencyMatrixGraph implements Graph {
    private int n;
    //private IntegerBinding num;
    private Vertex vertexList[];
    private Object adjacencyMatrix[][];
    private int count; //vertex counter
    
    //para los recorridos dfs(), bfs()
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public AdjacencyMatrixGraph(int n){
        if(n<=0) System.exit(1);
        this.n = n;
        this.count = 0;
        this.vertexList = new Vertex[n];
        this.adjacencyMatrix = new Object[n][n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
        initMatrix();
    }
    
    private void initMatrix(){
        for (int i = 0; i < vertexList.length; i++) {
            for (int j = 0; j < vertexList.length; j++) {
                adjacencyMatrix[i][j] = 0; 
            }  
        }
    }

    @Override
    public int size() throws ListException {
        return count; //vertexes counter
    }

    @Override
    public void clear() {
        this.vertexList = new Vertex[n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
        this.count = 0;
        initMatrix();
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }

    @Override
    public boolean containsVertex(Object element) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Adjacency Matrix Graph is Empty");
        }
        for (int i = 0; i < count; i++) {
            if(util.Utility.equals(vertexList[i].data, element)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsEdge(Object a, Object b) throws GraphException, ListException {
        if(isEmpty()){
            throw new GraphException("Adjacency Matrix Graph is Empty");
        }
        return !util.Utility.equals(adjacencyMatrix[indexOf(a)][indexOf(b)], 0);
    }

    @Override
    public void addVertex(Object element) throws GraphException, ListException {
        if(count>=vertexList.length){
            throw new GraphException("Adjacency Matrix Graph is Full");
        }
        vertexList[count++] = new Vertex(element);
    }

    @Override
    public void addEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsVertex(a)||!containsVertex(b)){
            throw new GraphException("Can not add edge between vertexes ["
                    +a+"] and ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = "edge";
        adjacencyMatrix[indexOf(b)][indexOf(a)] = "edge";
    }
    @Override
    public int indexOf(Object data) {
        for (int i = 0; i < count; i++) {
           if(util.Utility.equals(vertexList[i].data, data)){
               return i; //retorna la pos
           }
        }
        return -1; //significa q no encontro el vertice con esa data
    }

    @Override
    public void addWeight(Object a, Object b, Object weight) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There is not edge between the vertexes "
                    +"["+a+"] and ["+b+"]");
        }
        adjacencyMatrix[indexOf(a)][indexOf(b)] = weight;
        adjacencyMatrix[indexOf(b)][indexOf(a)] = weight;
    }

    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        int index = indexOf(element);
        if(index!=-1){ //si existe el elemento en el grafo
            for (int i = index; i < count-1; i++) {
                vertexList[i] = vertexList[i+1];
                //movemos todas las filas una posicion hacia arriba
                for (int j = 0; j < count; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i+1][j];
                }
            }
            //movemos todas las cols una posicion hacia la izq
            for (int i = 0; i < count; i++) {
                 for (int j = index; j < count-1; j++) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[i][j+1];  
                 }
            }
            count--; 
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There's no edges");
        }
        int i = indexOf(a);
        int j = indexOf(b);
        if(i!=-1&&j!=-1){
            adjacencyMatrix[i][j] = 0;
            adjacencyMatrix[j][i] = 0;
        }
    }

    @Override
    public String dfs() throws GraphException, StackException {
        setVisited(false); //marca todos los vertices como no vistados
        // inicia en el vertice 0
        String info =vertexList[0].data+", ";
        vertexList[0].setVisited(true); // lo marca
        stack.clear();
        stack.push(0); //lo apila
        while(!stack.isEmpty()){
            // obtiene un vertice adyacente no visitado,
            //el que esta en el tope de la pila
            int index = adjacentVertexNotVisited((int)stack.top());
            if(index==-1) // no lo encontro
                stack.pop();
            else{
                vertexList[index].setVisited(true); // lo marca
                info+=vertexList[index].data+", "; //lo muestra
                stack.push(index); //inserta la posicion
            }
        }
        return info;
    }//dfs

    @Override
    public String bfs() throws GraphException, QueueException, ListException {
        setVisited(false); //marca todos los vertices como no visitados
        // inicia en el vertice 0
        String info =vertexList[0].data+", ";
        vertexList[0].setVisited(true); // lo marca
        queue.clear();
        queue.enQueue(0); // encola el elemento
        int v2;
        while(!queue.isEmpty()){
            int v1 = (int) queue.deQueue(); // remueve el vertice de la cola
            // hasta que no tenga vecinos sin visitar
            while((v2=adjacentVertexNotVisited(v1)) != -1 ){
                // obtiene uno
                vertexList[v2].setVisited(true); // lo marca
                info+=vertexList[v2].data+", "; //lo muestra
                queue.enQueue(v2); // lo encola
            }
        }
        return info;
    }
    
    private void setVisited(boolean value){
        for (int i = 0; i < count; i++) {
            vertexList[i].setVisited(value); //value==true/false
        }
    }
    
    private int adjacentVertexNotVisited(int i) {
        for (int j = 0; j < count; j++) {
           if(!util.Utility.equals(adjacencyMatrix[i][j], 0)&&!vertexList[j].isVisited()){
           //if(!adjacencyMatrix[i][j].equals(0)&&!vertexList[j].isVisited()){
               return j; //retorna la pos del vertice adjacente no visitado
           } 
        }
        return -1;
    }

    //@author: Anthony Gonzalez
    public String table(){
        String result = "TABLE\n";
        for (int i = 0; i < count; i++) {
            result += "\t"+vertexList[i].data;
        }
        result+="\n";
        for (int i = 0; i < count; i++) {
            result+=vertexList[i].data;
            for (int j = 0; j < count; j++) {
                result+="\t"+adjacencyMatrix[i][j];
                
            }
            result+="\n";
        }
        return result;
    }

    
    @Override
    public String toString() {
        String result = "ADJACENCY MATRIX GRAPH CONTENT...";
        for (int i = 0; i < count; i++) {
            result+="\nThe vertex in the position: "+i+" is: "+vertexList[i].data;  
        }
        result+="\n";
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
               if(!adjacencyMatrix[i][j].equals(0)){ //si existe una arista
                    result+="\nThere is edge between the vertexes: "
                           +vertexList[i].data+"......"+vertexList[j].data;
                    if(!adjacencyMatrix[i][j].equals("edge")){ //si tiene pesos
                       result+="____WEIGHT: "+adjacencyMatrix[i][j];
                     } 
                }    
            }
        }
        return result+"\n"+table();
    }

    public Vertex[] getVertexList() {
        return vertexList;
    }

    public int getCount() {
        return count;
    }

    @Override
    public Object[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }
    
    @Override
    public Vertex getVertexByIndex(int index) {
         return vertexList[index];
    }

    public void setAdjacencyMatrix(Object[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }
    
    public int getIndexOfVertex(Object obj) {
        for (int i = 0; i < vertexList.length; i++) {
            if(util.Utility.equals(vertexList[i].data, obj)){
                return i;
            }
        }
        return -1;
    }
    
}
