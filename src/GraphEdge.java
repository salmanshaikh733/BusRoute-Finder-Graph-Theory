//CS2210 Assignment 5, Muhammad Salman Shaikh
//Student Number: 250959996
//Date: 2018/12/08


//class for edges
public class GraphEdge {

    //declare instance variabls
    private GraphNode startingPoint;
    private GraphNode endPoint;
    private char busLine;

    //constructor
    public GraphEdge(GraphNode u, GraphNode v, char busLine) {
        this.startingPoint = u;
        this.endPoint = v;
        this.busLine = busLine;

    }

    //get the first endpoint
    public GraphNode firstEndpoint() {

        return this.startingPoint;
    }

    //get the secondEndpoint
    public GraphNode secondEndpoint() {

        return this.endPoint;
    }

    //get the busline
    public char getBusLine() {

        return this.busLine;
    }


}
