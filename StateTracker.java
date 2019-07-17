/**
 * A tree data structure to keep track of which states have been visited
 * @author Spencer Yoder
 */
public class StateTracker {
  /** The root of the tree */
  private Node root;
  
  /** Constructs a new empty StateTracker */
  public StateTracker() {
    root = new Node(-1);
  }
  
  /**
   * Marks the given state as visited in the tracker
   * @param state the given state
   */
  public void add(GameState state) {
    Node current = root;
    for(int i = 0; i < 9; i++) {
      int idx = state.pieceAt(i);
      Node next = current.children[idx];
      if(next == null) {
        next = new Node(i);
        current.children[idx] = next;
      }
      current = next;
    }
  }
  
  /**
   * Checks to see if the given state has been visited
   * @param state the given state
   * @return true if the given state is marked as having been visited by the StateTracker
   */
  public boolean contains(GameState state) {
    Node current = root;
    for(int i = 0; i < 9; i++) {
      int idx = state.pieceAt(i);
      current = current.children[idx];
      if(current == null)
        return false;
    }
    return true;
  }
  
  /**
   * A representation of a combination of the numbers in a state
   * @author Spencer Yoder
   */
  private class Node {
    /** The level of this Node in the tree */
    private int level;
    /** The children of this Node in the tree */
    private Node[] children;
    
    /**
     * Constructs a new Node with the given level in the tree
     * @param level the depth of this Node in the tree
     */
    private Node(int level) {
      this.level = level;
      children = new Node[9];
    }
  }
}