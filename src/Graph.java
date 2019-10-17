//CS2210 Assignment 5, Muhammad Salman Shaikh
//Student Number: 250959996
//Date: 2018/12/08

import java.util.*;

//class to create the graph
public class Graph implements GraphADT {


    //create and array to store the number of nodes and edgeMatrix.
    private GraphNode[] intersection;
    private GraphEdge[][] edgeMatrix;


    //constructor for the class
    public Graph(int nodes) {
        //create the graph with n number of nodes
        this.intersection = new GraphNode[nodes];
        this.edgeMatrix = new GraphEdge[nodes][nodes];

        //name each intersection the number it is.
        for (int i = 0; i < nodes; i++) {
            this.intersection[i] = new GraphNode(i);

        }

    }

    //method to insert the edgeMatrix in
    public void insertEdge(GraphNode u, GraphNode v, char busLine) throws GraphException {
        //create the edges
        GraphEdge newEdge = new GraphEdge(u, v, busLine);
        GraphEdge newEdge2 = new GraphEdge(v, u, busLine);
        //check if the node exists list
        if (u.getName() >= intersection.length || v.getName() >= intersection.length) {
            throw new GraphException("Node does not exist");
        } else if (edgeMatrix[u.getName()][v.getName()] != null) {
            throw new GraphException("Edge already exists");
        }
        //else add the edge to the specified
        else {
            //create the edge and insert it
            int row = u.getName();
            int col = v.getName();
            edgeMatrix[row][col] = newEdge;
            edgeMatrix[col][row] = newEdge2;
        }
    }

    //method to get the node
    public GraphNode getNode(int name) throws GraphException {
        //traverse through list of nodes and find node
        for (int i = 0; i < intersection.length; i++) {
            if (intersection[i].getName() == name) {
                return intersection[i];
            }
        }
        //if loop ends an exception is thrown
        throw new GraphException("Node not found");

    }

    //return an iterator that will iterate over a list of edgeMatrix incident to the node u
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {

        //first determine if node u is in  list
        /*if(intersection[u.getName()]==null){
            throw new GraphException("Node not in list");
        }*/

        boolean flag = true;
        //check if the node exists
        if (u.getName() >= intersection.length) {
            flag = false;
        } else if (flag == true) {

            //list to store incident edgeMatrix
            ArrayList<GraphEdge> incidentEdgeList = new ArrayList<GraphEdge>();

            for (int i = 0; i < intersection.length; i++) {

                if (edgeMatrix[u.getName()][i] != null) {
                    incidentEdgeList.add(edgeMatrix[u.getName()][i]);
                }
            }
            //creat the iterator and return it
            Iterator<GraphEdge> edgeIterator = incidentEdgeList.iterator();
            return edgeIterator;
        }
        throw new GraphException("Node not found");
    }

    //method to get the corresponding edge of specified nodes u and v
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        //first determine if edge is is in the matrix
        //for(int i =1; i< intersection.length; i++){
        if (u.getName() > intersection.length || v.getName() >= intersection.length) {
            throw new GraphException("Nodes do not eixt");
        }

        if (edgeMatrix[u.getName()][v.getName()] != null) {
            return edgeMatrix[u.getName()][v.getName()];

        }
        //}
        throw new GraphException("Edge not in matrix");

    }

    //check which nodes are adjacent to the specified node
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {

        //check to see if nodes are in matrix.
        if (u.getName() >= intersection.length || v.getName() >= intersection.length) {
            throw new GraphException("Node does not exist");
        }
        //for(int i =0; i<intersection.length; i++){
        if (edgeMatrix[u.getName()][v.getName()] != null) {
            return true;
        }

        //}
        return false;
    }
}
