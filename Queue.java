/**
 * A queue in which one can add GameStates at one end and remove them at the other
 * @author Spencer Yoder
 */
public class Queue {
  /** The front of the queue */
  private Node head;
  /** The end of the queue */
  private Node tail;
  /** The number of elements in the queue */
  private int size;
  
  /**
   * Constructs a new empty queue
   */
  public Queue() {
    head = new Node(null);
    tail = new Node(null);
    head.next = tail;
    tail.prev = head;
  }
  
  /**
   * Adds a new GameState to the end of the queue
   * @param state the GameState to add
   */
  public void add(GameState state) {
    Node n = new Node(state);
    n.next = head.next;
    n.next.prev = n;
    head.next = n;
    n.prev = head;
    size++;
  }
  
  /**
   * Remove the last element in the queue
   * @return the element you removed
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
   * A pointer to an element in the queue
   * @author Spencer Yoder
   */
  private class Node {
    /** The GameState this Node points to */
    private GameState state;
    /** The Node after this one in the queue */
    private Node next;
    /** The Node before this one in the queue */
    private Node prev;
    
    /**
     * Constructs a new Node which points to the given GameState
     * @param state the given GameState
     */
    private Node(GameState state) {
      this.state = state;
    }
  }
}