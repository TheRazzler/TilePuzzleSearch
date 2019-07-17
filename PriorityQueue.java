/**
 * This is a data structure which holds a collection of GameStates in sorted order based on 
 * path cost and heuristic
 * @author Spencer Yoder
 */
public class PriorityQueue {
  /** The front of the queue */
  private Node head;
  /** The end of the queue */
  private Node tail;
  /** The number of items in the queue */
  private int size;
  
  /**
   * Constructs a new, empty, priority queue
   */
  public PriorityQueue() {
    head = new Node(null);
    tail = new Node(null);
    head.next = tail;
    tail.prev = head;
  }
  
  /**
   * Adds the given state to the priority queue in sorted order
   * @param state the given state
   */
  public void add(GameState state) {
    Node current = head;
    while(current.next != tail && state.compareTo(current.next.state) > 0) {
      current = current.next;
    }
    Node n = new Node(state);
    n.next = current.next;
    n.next.prev = n;
    current.next = n;
    n.prev = current;
    size++;
  }
  
  /**
   * Remove the GameState closest to the end of the queue
   * @return the GameState you removed
   */
  public GameState remove() {
    Node n = tail.prev;
    if(n == head)
      return null;
    GameState state = n.state;
    tail.prev = tail.prev.prev;
    tail.prev.next = tail;
    size--;
    return state;
  }
  
  /**
   * This method adds GameStates to the queue if they aren't in the queue OR
   * if they are, updates them if neccessary
   * @param state the state you want to add to the queue
   */
  public void update(GameState state) {
    Node current = head.next;
    while(current != tail) {
      if(current.state.equals(state)) {
        if(current.state.pathCost() > state.pathCost()) {
          current.prev.next = current.next;
          current.next.prev = current.prev;
          break;
        } else {
          return;
        }
      }
      current = current.next;
    }
    add(state);
  }
  
  /**
   * @return a String representation of this queue for testing purposes
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node current = head.next;
    while(current != tail) {
      sb.append(current.state.toString() + ", ");
      current = current.next;
    }
    sb.append("]");
    return sb.toString();
  }
  
  /**
   * @return the number of elements in the queue
   */
  public int size() {
    return size;
  }
  
  /**
   * A class for a pointer in memory to each element in the queue
   * @author Spencer Yoder
   */
  private class Node {
    /** The state this Node points to */
    private GameState state;
    /** The node after this one in the queue */
    private Node next;
    /** The node before this one in the queue */
    private Node prev;
    
    /**
     * Constructs a new Node which points to the given state
     * @param state the given state
     */
    private Node(GameState state) {
      this.state = state;
    }
  }
}