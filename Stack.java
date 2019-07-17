/**
 * A stack in which data can be added and removed at the same end
 * @author Spencer Yoder
 */
public class Stack {
  /** The top of the stack */
  private Node head;
  /** The number of elements in the stack */
  private int size;
  
  /**
   * Constructs a new empty Stack
   */
  public Stack() {
    head = new Node(null);
  }
  
  /**
   * Put the given state on top of the stack
   * @param state the given state
   */
  public void add(GameState state) {
    Node n = new Node(state);
    n.next = head.next;
    head.next = n;
    size++;
  }
  
  /**
   * Take the topmost state off the stack
   * @return the topmost state
   */
  public GameState remove() {
    Node n = head.next;
    if(n == null)
      return null;
    head.next = head.next.next;
    size--;
    return n.state;
  }
  
  /**
   * @return the number of elements in the stack
   */
  public int size() {
    return size;
  }
  
  /**
   * A pointer to an element in the stack
   * @author Spencer Yoder
   */
  private class Node {
    /** The Node after this one in the stack */
    private Node next;
    /** The GameState this node points to */
    private GameState state;
    
    /**
     * Constructs a new Node which points to the given GameState
     * @param GameState the state this Node points to
     */
    private Node(GameState state) {
      this.state = state;
    }
  }
}