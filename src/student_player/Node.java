package student_player;

import java.util.List;

import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

public class Node {
    private int value;
    public PentagoMove pentagoMove; // Move used to get into current state
    private PentagoBoardState pentagoBoardState; // current state at this point
    private List<Node> children;
    private Node parent;
    private boolean isMax;
    public Move bestMove;
    
    public Node(int value) {
        this.value = value;
        this.isMax = true;
    }

    public Node(PentagoBoardState pentagoBoardState) {
      this.pentagoBoardState = pentagoBoardState;
      this.isMax = true;
    } 

    List<Node> getChildren() {
      return children;
    }

    void addChild(Node child) {
      children.add(child);
    }

    void setChildren(List<Node> allChildren) {
      this.children = allChildren;
    }

    public void setMaxStatus(boolean isMax) {
      this.isMax = isMax;
    } 

    public boolean getMaxStatus() {
      return this.isMax;
    } 

    public void setParent(Node parent) {
      this.parent = parent;
    } 

    public Node getParent() {
      return this.parent;
    } 
  
    public int getValue() {
      return this.value;
    }
  
    public void setValue(int value) {
      this.value = value;
    }

    public Move getBestMove() {
      return this.bestMove;
    }
    
    public void setBestMove(Move bestMove) {
      this.bestMove = bestMove;
    }
    
    public PentagoMove getPentagoMove() {
      return this.pentagoMove;
    }
    
    public void setPentagoMove(PentagoMove pentagoMove) {
      this.pentagoMove = pentagoMove;
    }

    public PentagoBoardState getBoardState() {
      return this.pentagoBoardState;
    }
    
    public void setBoardState(PentagoBoardState pentagoBoardState) {
      this.pentagoBoardState = pentagoBoardState;
    }
}
