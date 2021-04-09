package student_player;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

public class Minimax {	
  private int WIN = 100;
  private int DRAW = 5;
  private int LOSE = -100;

  public Move aBPruning(PentagoBoardState pbs, int player){
    int alpha = -Integer.MAX_VALUE; // At max nodes, update α only
    int beta = Integer.MAX_VALUE; // At min nodes, update β only

    Node root = new Node(pbs);
    toggleMinimax(root, alpha, beta, true, 0);
    Move myMove = root.getBestMove();
    if (myMove == null){
      System.out.println("Not found - Get random");
      myMove = pbs.getRandomMove();
    }
    return myMove;
  }

  public int toggleMinimax(Node node, int alpha, int beta, boolean isMax, int depth){
    int player = node.getBoardState().getTurnPlayer();

    if (depth == 2 || node.getBoardState().gameOver()) {
      return evaluate(node, player);
    }

    if (isMax){
      System.out.println("MAX");
      return maximize(node, alpha, beta, depth, isMax);
    } 
    else {
      System.out.println("MIN");
      return minimize(node, alpha, beta, depth, isMax);
    }
  }

  public int maximize(Node node, int alpha, int beta, int depth, boolean isMax){
    PentagoBoardState boardState = node.getBoardState();
    ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();

    for (PentagoMove pentagoMove : allMoves) {
      // Process child move on cloned board
      PentagoBoardState clonedBoard = (PentagoBoardState) boardState.clone();
      clonedBoard.processMove(pentagoMove);

      // Update game tree
      Node child = new Node(clonedBoard);
      child.setParent(node);
      child.setPentagoMove(pentagoMove);

      int value = toggleMinimax(child, alpha, beta, !isMax, depth+1);

      // Update alpha
      if(alpha < value){
        alpha = value;
        node.setBestMove(pentagoMove);
      }

      if (alpha >= beta) {
        break;
      }
    }
    return alpha;
  }

  public int minimize(Node node, int alpha, int beta, int depth, boolean isMax){
    PentagoBoardState boardState = node.getBoardState();
    ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();

    for (PentagoMove pentagoMove : allMoves) {
      // Process child move on cloned board
      PentagoBoardState clonedBoard = (PentagoBoardState) boardState.clone();
      clonedBoard.processMove(pentagoMove);

      // Update game tree
      Node child = new Node(clonedBoard);
      child.setParent(node);
      child.setPentagoMove(pentagoMove);

      int value = toggleMinimax(child, alpha, beta, !isMax, depth+1);

      // Update alpha
      if(beta > value){
        beta = value;
        node.setBestMove(pentagoMove);
      }

      if (alpha >= beta) break; // prune

    }
    return beta;
  }

  public int evaluate(Node node, int player){
    PentagoBoardState boardState = node.getBoardState();
    int score = 0;
    // If the node is the end of a game, 
    if (boardState.gameOver()) {
			if (boardState.getWinner() == player) {
				score += WIN;
			}
			else if (boardState.getWinner() == Board.DRAW) {
				score += DRAW;
			}
			else {
				score += LOSE;
				}
		} 
    // The node does not represent the end of a game, but we can earn some points for particular paths
    else {
      score += bonusPoints(boardState, player);
    }
    return score;
  }

  public int bonusPoints(PentagoBoardState boardState, int player){
    int bonus = 0;
    if (player == PentagoBoardState.WHITE){ // Note: this means student is playing first
      bonus += getAdjacentBonusPoints(boardState, "w");
    }
    else {
      bonus += getAdjacentBonusPoints(boardState, "b");
    }
    return bonus;
  }

  public int getAdjacentBonusPoints(PentagoBoardState boardState, String playerColor){
    int bonus = 0;
    bonus += getDiagonalPoints(boardState, playerColor);
    bonus += getRowPoints(boardState, playerColor);
    bonus += getColumnPoints(boardState, playerColor);
    return bonus;
  }

  public int getDiagonalPoints(PentagoBoardState boardState, String playerColor){
    int bonus = 0;
    int increment = 2;
    int lastConsecutive = -1;

    // left to right
    for (int i = 0; i <= 4; i++){ // up to 4 because at (4,4) it will also check piece at (5,5)
      String d1 = boardState.getPieceAt(i, i).toString();
      String d2 = boardState.getPieceAt((i+1)%6, (i+1)%6).toString(); // %6 to wrap around board limit
      if(d1.equals(playerColor) && d2.equals(playerColor)){
        lastConsecutive = i;
        if(lastConsecutive == (i-1)){
          increment *= 2; // The more consecutive pieces = the bigger the bonus
        }
      }
      bonus += increment; // bonus adjacent pieces along diagonal   
    }
    // right to left
    for (int i = 5; i <= 1; i--){ // down to 1 because at (1,4) it will also check piece at (0,5)
      String d1 = boardState.getPieceAt(i, 5-i).toString();
      String d2 = boardState.getPieceAt((i-1)%6, (i+1)%6).toString(); // %6 to wrap around board limit
      if(d1.equals(playerColor) && d2.equals(playerColor)){
        lastConsecutive = i;
        if(lastConsecutive == (i-1)){
          increment *= 2; // The more consecutive pieces = the bigger the bonus
        }
      }
      bonus += increment; // bonus adjacent pieces along diagonal   
    }
    return bonus;
  }

  public int getRowPoints(PentagoBoardState boardState, String playerColor){
    int bonus = 0;
    int increment = 2;
    int lastConsecutive = -1;

    for (int i = 0; i <= 4; i++){ // up to 4 adjacent because 5 would be game over
      String r1 = boardState.getPieceAt(i, i).toString();
      String r2 = boardState.getPieceAt((i+1)%6, i).toString(); // %6 to wrap around board limit
      if(r1.equals(playerColor) && r2.equals(playerColor)){
        lastConsecutive = i;
        if(lastConsecutive == (i-1)){
          increment *= 2; // The more consecutive pieces = the bigger the bonus
        }
      }
      bonus += increment; // bonus adjacent pieces along row   
    }
    return bonus;
  }


  public int getColumnPoints(PentagoBoardState boardState, String playerColor){
    int bonus = 0;
    int increment = 2;
    int lastConsecutive = -1;

    for (int i = 0; i <= 4; i++){ // up to 4 adjacent because 5 would be game over
      String c1 = boardState.getPieceAt(i, i).toString();
      String c2 = boardState.getPieceAt(i, (i+1)%6).toString(); // %6 to wrap around board limit
      if(c1.equals(playerColor) && c2.equals(playerColor)){
        lastConsecutive = i;
        if(lastConsecutive == (i-1)){
          increment *= 2; // The more consecutive pieces = the bigger the bonus
        }
      }
      bonus += increment; // bonus adjacent pieces along row   
    }
    return bonus;
  }
}