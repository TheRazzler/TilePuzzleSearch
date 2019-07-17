/**
 * This program implements breadth first search on the sliding tile puzzle we covered in class
 * @author Spencer Yoder
 */
public class DepthFirstSearch {
  /**
   * The main method, starts the program
   * @param args command line arguments
   */
  public static void main(String[] args) {
    //This is the initial state of the game, think of this as the starting node
    int[] board = {2, 7, 4, 
                   1, 0, 8,
                   5, 3, 6};
    //This variable helps us keep track of the number of nodes we've expanded
    int nodesExpanded = 1;
    //Create the first node in the graph
    GameState start = new GameState(board);
    //This is the queue that will hold the states
    Stack q = new Stack();
    //This tracker keeps track of which Nodes we've visited
    StateTracker tracker = new StateTracker();
    q.add(start);
    GameState currentState = q.remove();
    tracker.add(currentState);
    
    //Think of this structure as an if/then. If the following code has an error, we have exhausted all our options
    try {
      //Go until we've removed the end state from the queue
      while(!currentState.isGoal()) {
        nodesExpanded++;
        //Every path you can take from the current state
        GameState[] children = currentState.children();
        for(int i = 0; i < children.length; i++) {
          //If we haven't visited this node before
          if(!tracker.contains(children[i])) {
            q.add(children[i]);
            //If we're adding it to the queue, we can add it to the tracker since we don't want to add the same state to the queue twice
            tracker.add(children[i]);
          }
        }
        currentState = q.remove();
      }
    } catch(NullPointerException e) {
      //We haven't gotten to the end goal, end the program
      System.out.println("Path not found.");
      System.out.println("Nodes expanded: " + nodesExpanded);
      System.exit(1);
    }
    
    String s = "";
    int pathLength = 0;
    //Backtrack through all the parents and print out the whole path from start to finish
    while(currentState != null) {
      s = currentState.toString() + "\n" + s;
      currentState = currentState.parent;
      pathLength++;
    }
    System.out.println(s);
    System.out.println("--------------------------------");
    System.out.println("Nodes expanded: " + nodesExpanded);
    System.out.println("Path length: " + pathLength);
  }
}