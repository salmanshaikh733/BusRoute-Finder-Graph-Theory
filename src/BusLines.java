//CS2210 Assignment 5, Muhammad Salman Shaikh
//Student Number: 250959996
//Date: 2018/12/08

import java.io.*;
import java.lang.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.*;

public class BusLines {
    private File file;
    private FileReader fileReader;
    private InputStreamReader isr;
    private BufferedReader reader;
    private String line;
    private int width;
    private int height;
    private int routeChanges;
    private int numNodes;
    private int numEdges;
    private int numeHouses;
    private int startingIndex;
    private int destinationIndex;
    private Graph mapGraph;
    private GraphNode[] nodeList;

    //create the map
    public BusLines(String inputFile) throws MapException {
        try {
            //get the file and start reading it
            this.file = new File(inputFile);
            this.fileReader = new FileReader(file);
            this.reader = new BufferedReader(this.fileReader);
            numNodes = 0;
            width = 0;
            height = 0;
            routeChanges = 0;
            numEdges = 0;
            numeHouses = 0;

            //read the first line of the line, get the width height and number of changes
            line = reader.readLine();
            Scanner firstLineScanner = new Scanner(line);
            String[] firstLine = firstLineScanner.nextLine().split(" ");
            width = Integer.parseInt(firstLine[1]);
            height = Integer.parseInt(firstLine[2]);
            routeChanges = Integer.parseInt(firstLine[3]);

            //initialize the map
            String newMap[][] = new String[height * 2 - 1][width * 2 - 1];
            int row = 0;
            line = reader.readLine();
            while (line != null) {
                for (int col = 0; col < width * 2 - 1; col++) {
                    //make the map put all chars of file into newMap array
                    newMap[row][col] = Character.toString(line.charAt(col));

                    //get the starting index
                    if (line.charAt(col) == 'S') {
                        startingIndex = numNodes;
                    }

                    //get the destination index.
                    if (line.charAt(col) == 'D') {
                        destinationIndex = numNodes;
                    }

                    //find the number of nodes, nodes are periods or S and D
                    if (line.charAt(col) == 'D' || line.charAt(col) == 'S' || line.charAt(col) == '.') {
                        numNodes++;
                    } else if (Character.isLetter(line.charAt(col)) || Character.isDigit(line.charAt(col))) {
                        numEdges++;
                    } else if (line.charAt(col) == ' ') {
                        numeHouses++;
                    }

                }
                //get the next row in the map array
                row++;
                line = reader.readLine();


                //read the next lin
            }

            //close the file when we are done with it
            this.reader.close();

            //create a graph of the size of nodes determined from reading the file
            mapGraph = new Graph(numNodes);

            //create a list of nodes of size numNodes
            nodeList = new GraphNode[numNodes];

            int counter = 0;
            //have a list of GraphNodes and add values to them by making new nodes, have a an array of GraphNodes to numNodes
            for (int i = 0; i < height * 2 - 1; i++) {
                for (int j = 0; j < width * 2 - 1; j++) {
                    if (newMap[i][j].charAt(0) == '.' || newMap[i][j].charAt(0) == 'D' || newMap[i][j].charAt(0) == 'S') {
                        nodeList[counter] = new GraphNode(counter);
                        counter++;
                    }
                }
            }

            //create the edges
            int toggle = 1;
            int nextRow = 0;
            int nextCol = width;
            //loop through edge and build them
            for (int r = 0; r < height * 2 - 1; r++) {
                if (toggle == 1) {
                    //build horizontally
                    for (int col = 1; col < width * 2 - 1; col = col + 2) {
                        //get the values before and after of the targeted node
                        char firstNode = newMap[r][col - 1].charAt(0);
                        char secondNode = newMap[r][col + 1].charAt(0);
                        char routeType = newMap[r][col].charAt(0);
                        if (firstNode == '.' || firstNode == 'S' || firstNode == 'D') {
                            if (secondNode == '.' || secondNode == 'S' || secondNode == 'D') {
                                if (routeType != ' ') {
                                    mapGraph.insertEdge(nodeList[nextRow], nodeList[nextRow + 1], routeType);
                                }
                                nextRow++;
                            }
                        }
                    }
                    nextRow++;
                    toggle *= -1;
                } else {
                    //build vertically
                    for (int col = 0; col < width * 2 - 1; col = col + 2) {
                        char firstNode = newMap[r - 1][col].charAt(0);
                        char secondNode = newMap[r + 1][col].charAt(0);
                        char routeType = newMap[r][col].charAt(0);
                        if (firstNode == '.' || firstNode == 'S' || firstNode == 'D') {
                            if (secondNode == '.' || secondNode == 'S' || secondNode == 'D') {
                                if (routeType != ' ') {
                                    mapGraph.insertEdge(nodeList[nextCol], nodeList[nextCol - width], routeType);
                                }
                                nextCol++;
                            }
                        }
                    }
                    toggle *= -1;
                }
            }
            //catch these exceptions
        } catch (IOException e) {
            throw new MapException("Map not found");
        } catch (GraphException e) {
            System.out.println("Graph Exception");
        }
    }

    //get the graph
    public Graph getGraph() {
        return mapGraph;

    }

    //helper method to return the correct path for the map
    private Iterator<GraphNode> tripHelper(GraphNode currentNode, GraphNode destination, Stack<GraphNode> path, int busChanges, char routeName, int changes) {
        boolean changeMarker = false;

        //set the node we are yet to true because we have visited it now
        currentNode.setMark(true);
        //add the current node to the stack
        path.push(currentNode);

        if (currentNode.getName() == destination.getName()) {
            return path.iterator();
        }

        //try and catch block to catch graph exceptions
        try {
            Iterator<GraphEdge> pathIterator = mapGraph.incidentEdges(currentNode);

            //while there are incident edges!
            while (pathIterator.hasNext()) {
                //get the next street. next to the node we are at
                GraphEdge street = pathIterator.next();
                //get the node we are trying to reach.
                GraphNode nextNode = street.secondEndpoint();

                //if the nextNode is marked false then continue and we are within the routeChange limit
                if (changes <= busChanges + 1 && !nextNode.getMark()) {
                    //if changes is within limit keep going and go to next node
                    if (routeName != street.getBusLine()) {
                        changes++;
                        changeMarker = true;
                    }
                    //go to the nextnode insert the .............(nextNode),(destination),(currentPath), (number of Bus changes), current routeName, and the changes we have already made.
                    Iterator<GraphNode> solutionPath = tripHelper(nextNode, destination, path, busChanges, street.getBusLine(), changes);

                    //if a path is returned return it.
                    if (solutionPath != null) {
                        return solutionPath;
                    }
                    //if not solution is found beyond then decrease the num changes and backtrack
                    else if (changeMarker == true) {
                        changes--;
                    }
                }
            }
        } catch (GraphException e) {
            System.out.println("Graph Exception");
        }
        //pop the lst node inserted and set it as unmarked
        GraphNode topNode = path.pop();
        topNode.setMark(false);
        return null;

    }

    //create the trip call teh helper method
    public Iterator<GraphNode> trip() {

        //get the starting node
        GraphNode startingNode = nodeList[startingIndex];
        GraphNode endingNode = nodeList[destinationIndex];
        Stack<GraphNode> gNodes = new Stack<GraphNode>();
        //call the helper method

        ArrayList<GraphNode> nlist = new ArrayList<GraphNode>();
        //nlist.remove(nlist.size()-1);
        return tripHelper(startingNode, endingNode, gNodes, routeChanges, '#', 0);
    }

}
