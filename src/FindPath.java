import java.util.*;
import java.io.*;

public class FindPath {

    public static void main (String[] args) {
        GraphNode u, v;
        DisplayMap display;
        int delay = 0;


        if (args.length != 1 && args.length != 2)
            System.out.println("Usage: java FindPath inputFile");
        else {
            if (args.length == 2) {
                delay = Integer.parseInt(args[1]); // Delay between
            }
            // drawing edges
            display = new DisplayMap(args[0]);  // Draw the map
            try {
                BusLines streetMap = new BusLines(args[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(System.in));
                System.out.println("Press a key to continue");
                String line = in.readLine();
                if (line.length() != 0) display.drawRoads(); // Re-draw the map

		/* Here is where the solution is computed */
                Iterator<GraphNode> solution = streetMap.trip();

                // Display the solution
                if (solution != null) {
                    if (solution.hasNext()) u = solution.next();
                    else return;
                    while (solution.hasNext()) {
                        v = solution.next();
                        Thread.sleep(delay);
                        display.drawLine(u,v);
                        u = v;
                    }
                }
                else {
                    System.out.println("No solution was found");
                    System.out.println("");
                }

                in = new BufferedReader(
                        new InputStreamReader(System.in));
                System.out.println("Press a key to finish");
                line = in.readLine();

            }
            catch (MapException e) {
                System.out.println("Error reading input file");
            }
            catch (IOException in) {
                System.out.println("Error reading from keyboard");
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            display.dispose();
            System.exit(0);
        }
    }
}
