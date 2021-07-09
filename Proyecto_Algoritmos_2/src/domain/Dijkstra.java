/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Anthony G.S
 */
public class Dijkstra {
    private static final int NO_PARENT = -1;
    private static ArrayList solution;
    private static int n;
 
    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented
    // using adjacency matrix
    // representation
    public static void dijkstra(int[][] adjacencyMatrix,
                                        int startVertex)
    {
        n =adjacencyMatrix[0].length-1;
        int nVertices = adjacencyMatrix[0].length;
 
        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];
 
        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];
 
        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
                                            vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
         
        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;
 
        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];
 
        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;
 
        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++)
        {
 
            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                     vertexIndex < nVertices;
                     vertexIndex++)
            {
                if (!added[vertexIndex] &&
                    shortestDistances[vertexIndex] <
                    shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
 
            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;
 
            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                     vertexIndex < nVertices;
                     vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                 
                if (edgeDistance > 0
                    && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                                                       edgeDistance;
                }
            }
        }
        
        solution = printSolution(startVertex, shortestDistances, parents);
        
    }
 
    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static ArrayList printSolution(int startVertex,
                                      int[] distances,
                                      int[] parents)
    {
        String result="";
        ArrayList<String> resultArray = new ArrayList<>();
        int nVertices = distances.length;
        //result+=("Vertex\t Distance\tPath");
         
        for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++)
        {
            if (vertexIndex != startVertex)
            {
                result+=(startVertex + "-");
                result+=(vertexIndex + ",");
                result+=(distances[vertexIndex] + ",");
                result+=printPath(vertexIndex, parents);
                resultArray.add(result);
                result="";
            }
        }
        sort(resultArray);
        return resultArray;
    }
 
    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static String printPath(int currentVertex,
                                  int[] parents)
    {
        String result="";
        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return "";
        }
        result+=printPath(parents[currentVertex], parents);
        result+=(currentVertex + "-");
        return result;
    }

    private static void sort(ArrayList<String> array) {
        
        for (int i = 1; i < array.size(); i++) {
            for (int j = 0; j < array.size()-1; j++) {
                int a1 = Integer.parseInt(array.get(j).split(",")[1]);
                int a2 = Integer.parseInt(array.get(j+1).split(",")[1]);
                if(a1>a2){
                    String aux = array.get(j);
                    
                    array.remove(j);
                    array.add(j, array.get(j));
                    
                    array.remove(j+1);
                    array.add(j+1, aux);
                }
            }
        }
    }

    public ArrayList getSolution() {
        return solution;
    }
}
