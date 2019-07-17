/**
 * This program runs A* search on the sliding tile puzzle to find an optimal solution
 * @author Spencer Yoder
 */
public class AStar {
  /** 
   * The main method, starts the program
   * @author Spencer Yoder
   */
  public static void main(String[] args) {
    //The initial configuration of the board
    int[] board = {2, 7, 4, 
                   1, 0, 8,
                   5, 3, 6};
    //This variable is to keep track of how many nodes we've removed from the priority queue
    int nodesExpanded = 1;
    //You can think of this start GameState as the first node in the graph 
    GameState start = new GameState(board);
    //This is the priority queue, it will automatically sort the states by their path cost + heuristic
    PriorityQueue q = new PriorityQueue();
    //This tracker is how we check to see which nodes we've visited
    StateTracker tracker = new StateTracker();
    q.add(start);
    GameState currentState = q.remove();
    //We add states to the tracker once they've been visited
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
            //Add it to the queue which will automatically update parents and change costs
            q.update(children[i]);
          }
        }
        currentState = q.remove();
        //Since we've removed the next state from the queue, we've visited it.
        tracker.add(currentState);
      }
    } catch(NullPointerException e) {
      //We couldn't find the goal, stop the program
      System.out.println("Path not found.");
      System.out.println("Nodes expanded: " + nodesExpanded);
      System.exit(1);
    }
    
    String s = "";
    int pathLength = 0;
    //This part of the code prints all the states we visit by backtracking through their parents
    while(currentState != null) {
//      s = currentState.toString() + "\n" + s;
      s = currentState.toString() + "\n" + currentState.heuristic() + "\n----------------------\n" + s;
      currentState = currentState.parent;
      pathLength++;
    }
    System.out.println(s);
    System.out.println("--------------------------------");
    System.out.println("Nodes expanded: " + nodesExpanded);
    System.out.println("Path length: " + pathLength);
  }
}