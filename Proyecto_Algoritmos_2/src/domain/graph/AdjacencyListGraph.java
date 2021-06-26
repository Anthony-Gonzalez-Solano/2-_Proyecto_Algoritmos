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

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class AdjacencyListGraph implements Graph {
    private int n;
    private Vertex vertexList[];
    private int count; //vertex counter
    
    //para los recorridos dfs(), bfs()
    private LinkedStack stack;
    private LinkedQueue queue;
    
    //Constructor
    public AdjacencyListGraph(int n){
        if(n<=0) System.exit(1);
        this.n = n;
        this.count = 0;
        this.vertexList = new Vertex[n];
        this.stack = new LinkedStack();
        this.queue = new LinkedQueue();
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
        return !vertexList[indexOf(a)].edgesList.isEmpty()
                ?vertexList[indexOf(a)].edgesList.contains(new EdgeWeight(b, null))
                :false;
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
        vertexList[indexOf(a)].edgesList.add(new EdgeWeight(b, null));
        vertexList[indexOf(b)].edgesList.add(new EdgeWeight(a, null));
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
        EdgeWeight ew = (EdgeWeight) vertexList[indexOf(a)].edgesList.getNode(
        vertexList[indexOf(a)].edgesList.indexOf(new EdgeWeight(b, null))).getData();
        ew.setWeight(weight); //setteamos el peso
        vertexList[indexOf(a)].edgesList.getNode(
        vertexList[indexOf(a)].edgesList.indexOf(new EdgeWeight(b, null))).setData(ew);
        
        //grafo no dirigido
        ew = (EdgeWeight) vertexList[indexOf(b)].edgesList.getNode(
        vertexList[indexOf(b)].edgesList.indexOf(new EdgeWeight(a, null))).getData();
        ew.setWeight(weight); //setteamos el peso
        vertexList[indexOf(b)].edgesList.getNode(
        vertexList[indexOf(b)].edgesList.indexOf(new EdgeWeight(a, null))).setData(ew);
    }

    @Override
    public void removeVertex(Object element) throws GraphException, ListException {
        for (int i = 0; i < count; i++) {
            if(util.Utility.equals(vertexList[i].data, element)){
                //se deben suprimir todas las aristas asociadas
                for (int j = 0; j < count; j++) {
                    if(containsEdge(vertexList[j].data, element)){
                        removeEdge(vertexList[j].data, element);
                    } 
                }
                //se debe suprimir el vertice
                for (int j = i; j < count-1; j++) {
                    vertexList[j] = vertexList[j+1];
                }
                count--;
            }
        }
    }

    @Override
    public void removeEdge(Object a, Object b) throws GraphException, ListException {
        if(!containsEdge(a, b)){
            throw new GraphException("There's no edges");
        }
        if(!vertexList[indexOf(a)].edgesList.isEmpty()){
            vertexList[indexOf(a)].edgesList.remove(new EdgeWeight(b, null));
        }
        //grafo no dirigido
        if(!vertexList[indexOf(b)].edgesList.isEmpty()){
            vertexList[indexOf(b)].edgesList.remove(new EdgeWeight(a, null));
        }
    }

    @Override
    public String dfs() throws GraphException, StackException, ListException {
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
    
     private int adjacentVertexNotVisited(int index) throws ListException {
        Object vertexData = vertexList[index].data;
        for(int i=0; i<count; i++)
	    if(!vertexList[i].edgesList.isEmpty()
              &&vertexList[i].edgesList
                .contains(new EdgeWeight(vertexData, null)) 
                && !vertexList[i].isVisited())
	             return i;
	     return -1;
    }
    
    @Override
    public String toString() {
        String result = "ADJACENCY LIST GRAPH CONTENT...";
        for (int i = 0; i < count; i++) {
            result+="\nThe vertex in the position: "+i+" is: "+vertexList[i].data;  
            if(!vertexList[i].edgesList.isEmpty()){
                result+="\n....EDGES AND WEIGHTS:"+vertexList[i].edgesList.toString();
            }
        }
        return result;
    }

    public int getN() {
        return n;
    }

    public Vertex[] getVertexList() {
        return vertexList;
    }

    public int getCount() {
        return count;
    }

    public LinkedStack getStack() {
        return stack;
    }

    public LinkedQueue getQueue() {
        return queue;
    }

    @Override
    public Vertex getVertexByIndex(int index) {
         return vertexList[index];
    }

    @Override
    public Object[][] getAdjacencyMatrix() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
