//CS2210 Assignment 5, Muhammad Salman Shaikh
//Student Number: 250959996
//Date: 2018/12/08

//class for graphNode
public class GraphNode {

    //declare instance vairables
    private int nodeName;
    private boolean nodeMark;

    //constructor
    public GraphNode(int name) {
        this.nodeName = name;
        this.nodeMark = false;
    }

    //method to set mark
    public void setMark(boolean mark) {
        this.nodeMark = mark;

    }

    //method to get mark
    public boolean getMark() {
        return this.nodeMark;
    }

    //method to get name
    public int getName() {
        return this.nodeName;
    }

}
