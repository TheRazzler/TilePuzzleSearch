import java.util.ArrayList;
import java.util.Arrays;

/**
 * This represents a state of the board
 * You can also think of it as a node in the graph we're searching
 * @author Spencer Yoder
 */
public class GameState implements Comparable<GameState> {
  /** We have a board with 9 numbers, this is the number for the empty space */
  public static final int EMPTY = 0;
  /** Adding 1 moves a piece to the right, 3 moves down, -1 moves left, -3 moves up */
  private static final int[] DIRECTIONS = {1, 3, -1, -3};
  
  /** The board of 9 numbers */
  private int[] board;
  /** The parent node for this GameState in the solution path (i.e. the move before this one) */
  public GameState parent;
  /** The location of the empty space on the board */
  private int emptyIdx;
  /** The guess of the distance to the goal */
  private int heuristic;
  /** This is the cumulative cost tracing back through each parent to the start */
  private int pathCost;
  
  /**
   * Creates a new GameState from the given board
   * @param board an array of integers representing the sliding tiles
   */
  public GameState(int[] board) {
    this(null, board);
  }
  
  /**
   * Constructs a new GameState which has the given parent and board
   * @param parent the GameState from which this one was reached
   * @param board an array of integers representing the sliding tiles
   */
  public GameState(GameState parent, int[] board) {
    if(parent == null)
      pathCost = 0;
    else
      pathCost = 1 + parent.pathCost;
    this.parent = parent;
    this.board = board;
    for(int i = 0; i < board.length; i++) {
      if(board[i] == EMPTY) {
        emptyIdx = i;
      }
      double dx = Math.abs(board[i] % 3 - i % 3);
      double dy = Math.abs(board[i] / 3 - i / 3);
      heuristic += (int) Math.ceil(Math.sqrt(dx * dx + dy * dy));
    }
    heuristic /= 2;
  }
  
  /**
   * @return true if this state is the goal state and false if not 
   */
  public boolean isGoal() {
    for(int i = 0; i < board.length; i++) {
      if(board[i] != i)
        return false;
    }
    return true;
  }
  
  /**
   * @return every state reachable from this state
   */
  public GameState[] children() {
    ArrayList<GameState> children = new ArrayList<GameState>();
    for(int i = 0; i < 4; i++) {
      int dir = emptyIdx + DIRECTIONS[i];
      if(dir >= 0 && dir < board.length) {
        if(Math.abs(DIRECTIONS[i]) == 3 || emptyIdx / 3 == dir / 3) {
          int[] newBoard = copyBoard();
          int copy = newBoard[dir];
          newBoard[dir] = EMPTY;
          newBoard[emptyIdx] = copy;
          children.add(new GameState(this, newBoard));
        }
      }
    }
    return children.toArray(new GameState[children.size()]);
  }
  
  
  /**
   * @return a copy of the board for use by each child
   */
  private int[] copyBoard() {
    int[] copy = new int[board.length];
    for(int i = 0; i < board.length; i++) {
      copy[i] = board[i];
    }
    return copy;
  }
  
  /**
   * @param idx an index in the board
   * @return the piece at the index
   */
  public int pieceAt(int idx) {
    return board[idx];
  }
  
  /**
   * @return the value of the heuristic function
   */
  public int heuristic() {
    return heuristic;
  }
  
  /**
   * @return the total path cost
   */
  public int pathCost() {
    return pathCost;
  }
  
  /**
   * Set the parent for this state to be the given state
   * @param state the state to be the new parent
   */
  public void setParent(GameState state) {
    this.parent = state;
  }
  
  /**
   * A function to help sort this GameState with other GameStates
   * @param other the GameState to compare this one against
   * @return a comparison of these two states
   */
  @Override
  public int compareTo(GameState other) {
    return (other.heuristic + other.pathCost) - (heuristic + pathCost);
  }
  
  /**
   * @return a String representation of this board
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int i = 0; i < 3; i++) {
      for(int j = 0; j < 3; j++) {
        sb.append(board[i * 3 + j] == EMPTY ? "-" : "" + board[i * 3 + j]);
      }
      sb.append("\n");
    }
    return sb.toString(); 
  }
  
  /**
   * @param o an object 
   * @return true if this state is the same as the given object
   */
  @Override
  public boolean equals(Object o) {
    if(o instanceof GameState) {
      GameState other = (GameState) o;
      return Arrays.equals(board, other.board);
    }
    return false;
  }
}