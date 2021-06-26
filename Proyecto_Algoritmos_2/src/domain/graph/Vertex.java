/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.graph;

import domain.list.SinglyLinkedList;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class Vertex {
    public Object data;
    public SinglyLinkedList edgesList; //funciona para AdjacencyListGraph
    private boolean visited; //para los recorridas dfs(), bfs()
    
    //Constructor
    public Vertex(Object data){
        this.data = data;
        this.visited = false;
        this.edgesList = new SinglyLinkedList();
    }

    public boolean isVisited() {
        return visited;
    }
    
    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}
