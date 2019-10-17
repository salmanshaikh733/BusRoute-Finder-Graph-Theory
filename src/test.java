/*


import java.io.*;
import java.util.*;

public class BusLines {
    private GraphNode nodes[];
    private Graph graph;
    private int mapWidth;
    private int mapLength;
    private int numRoute;
    private int start;
    private int destination;

    public BusLines(String inputFile) throws MapException {
        try {
            BufferedReader file = new BufferedReader(new FileReader(inputFile));
            String line;
            line = file.readLine();
            Scanner scanFirst = new Scanner(line);
            String[] firstLine = scanFirst.nextLine().split(" ");
            mapWidth = Integer.parseInt(firstLine[1]);
            mapLength = Integer.parseInt(firstLine[2]);
            numRoute = Integer.parseInt(firstLine[3]);
            String charMatrix[][] = new String[mapLength * 2 - 1][mapWidth * 2 - 1];
            int nodeCount = 0;
            int lengthCounter = 0;
            int widthCounter;
            while (lengthCounter < mapLength * 2 - 1) {
                line = file.readLine();
                widthCounter = 0;
                while (widthCounter < mapWidth * 2 - 1) {
                    char symbol = line.charAt(widthCounter);
                    charMatrix[lengthCounter][widthCounter] = Character.toString(line.charAt(widthCounter));
                    if (symbol == 'S') {
                        start = nodeCount;
                        nodeCount++;
                    }
                    if (symbol == 'D') {
                        destination = nodeCount;
                        nodeCount++;
                    }
                    if (symbol == '.') {
                        nodeCount++;
                    }
                    widthCounter++;
                }
                lengthCounter++;
            }
            graph = new Graph(nodeCount);
            nodes = new GraphNode[nodeCount];
            for (int j = 0; j < nodeCount; j++) {
                nodes[j] = new GraphNode(j);
            }
            int nodeRow = 0;
            int nodeCol = 0;
            for (int c = 0; c < mapLength * 2 - 1; c++) {
                if (c % 2 == 0) {
                    for (int d = 1; d < mapWidth * 2 - 1; d = d + 2) {
                        String index1 = charMatrix[c][d - 1];
                        String index2 = charMatrix[c][d + 1];
                        GraphNode node1 = null;
                        GraphNode node2 = null;
                        char routeName = (charMatrix[c][d]).charAt(0);
                        if (index1.equals("S") || index1.equals("D") || index1.equals(".")) {
                            node1 = nodes[nodeRow];
                            nodeRow++;
                            if (index2.equals("S") || index2.equals("D") || index2.equals(".")) {
                                node2 = nodes[nodeRow];
                            }
                            if (routeName != ' ') {
                                graph.insertEdge(node1, node2, routeName);
                            }
                        }
                    }
                }
                nodeCol = nodeRow + 1;
                if (c % 2 != 0) {
                    for (int d = 0; d < mapWidth * 2 - 1; d = d + 2) {
                        String index1 = charMatrix[c - 1][d];
                        String index2 = charMatrix[c + 1][d];
                        GraphNode node1 = null;
                        GraphNode node2 = null;
                        char routeName = (charMatrix[c][d]).charAt(0);
                        if (index1.equals("S") || index1.equals("D") || index1.equals(".")) {
                            node1 = nodes[nodeCol];
                            if (nodeCol + mapWidth < mapWidth * 2 - 1) {
                                nodeCol = nodeCol + mapWidth;
                                if (index2.equals("S") || index2.equals("D") || index2.equals(".")) {
                                    node2 = nodes[nodeCol];
                                }
                                if (routeName != ' ') {
                                    graph.insertEdge(node1, node2, routeName);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Invalid input.");
        } catch (GraphException e) {
            System.out.println("Edge not found.");
        }
    }

    public Graph getGraph() {
        return graph;
    }

    private Iterator<GraphNode> tripHelper(GraphNode currentNode, GraphNode endNode, int busChanges) throws GraphException {
        Stack<GraphNode> path = new Stack<GraphNode>();
        Iterator<GraphNode> iter = path.iterator();
        currentNode.setMark(true);
        path.push(currentNode);
        Iterator<GraphEdge> incidents = graph.incidentEdges(currentNode);
        if (currentNode.getName() == endNode.getName()) {
            iter = path.iterator();
            return iter;
        }
        while (incidents.hasNext()) {
            GraphEdge edge = incidents.next();
            GraphNode nextNode = edge.secondEndpoint();
            if (busChanges <= numRoute && nextNode.getMark() == true) {
                busChanges++;
                if (tripHelper(nextNode, endNode, busChanges) != null) {
                    return tripHelper(nextNode, endNode, busChanges);
                } else {
                    busChanges--;
                }
            }
        }
        return null;
    }

    public Iterator<GraphNode> trip() throws GraphException {
        Iterator<GraphNode> route = tripHelper(nodes[start], nodes[destination], 0);
        return route;
    }
}
*/
