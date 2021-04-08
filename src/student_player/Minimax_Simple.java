package student_player;

import java.util.ArrayList;

import boardgame.Board;
import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

public class Minimax_Simple {
  private int alpha = -Integer.MAX_VALUE; // At max nodes, update α only
  private int beta = Integer.MAX_VALUE; // At min nodes, update β only
  private int WIN = 10;
  private int DRAW = 5;
  private int LOSE = -10;
  private MinmaxNode bestMove = new MinmaxNode(0);

  private class MinmaxNode {	
      private int value;
      private PentagoMove pentagoMove;
      
      public MinmaxNode(int value) {
          this.value = value;
      }
 
      public int getValue() {
        return this.value;
      }

      public void setValue(int value) {
      this.value = value;
    }
      
      public PentagoMove getPentagoMove() {
        return this.pentagoMove;
      }
      
      public void setPentagoMove(PentagoMove pentagoMove) {
        this.pentagoMove = pentagoMove;
      }
  }

   public Move alphaBetaPruning(PentagoBoardState boardState){
      boolean isMaximize = true;
      bestMove = minimaxDecision(boardState, 0, isMaximize, alpha, beta);
      PentagoMove bestPentagoMove = bestMove.getPentagoMove();
      if(bestPentagoMove == null) {
        System.out.println("NOT FOUND");
        return boardState.getRandomMove();
      } else {
        return bestMove.getPentagoMove();
      }  
   }

   public MinmaxNode minimaxDecision(PentagoBoardState boardState, int depth, boolean isMaximize, int alpha, int beta){
    ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();
    PentagoBoardState clonedBoardState;
    MinmaxNode move;

      if (depth == 2 || boardState.gameOver()) {
        int value = minimaxValue(boardState);
        return new MinmaxNode(value);
      }
  
      if (isMaximize){
        bestMove = new MinmaxNode(-Integer.MAX_VALUE);
        for (PentagoMove pentagoMove : allMoves) {    
          clonedBoardState = (PentagoBoardState) boardState.clone();
          clonedBoardState.processMove(pentagoMove);
          move = minimaxDecision(clonedBoardState, depth+1, !isMaximize, alpha, beta); // !isMaximize to go into minimize
  
          // Update bestMove
          if(bestMove.getValue() < move.getValue()){
            // new move is better
            bestMove = move;
            bestMove.setPentagoMove(pentagoMove);
          }
  
          // At max nodes, update α only
          if(move.getValue() > alpha){
            alpha = move.getValue(); // update to max(alpha, bestValue);
          }
  
          // Prune in the event of inconsistency (α ≥ β)
          if (beta <= alpha) {
            // bestMove.setValue(beta);
            // bestMove.setPentagoMove(pentagoMove);
            return bestMove;
          }
        }
        return bestMove;
      }
      else {
        bestMove = new MinmaxNode(Integer.MAX_VALUE);
        for (PentagoMove pentagoMove : allMoves) {    
          clonedBoardState = (PentagoBoardState) boardState.clone();
          clonedBoardState.processMove(pentagoMove);
          move = minimaxDecision(clonedBoardState, depth+1, !isMaximize, alpha, beta); // !isMaximize to go into maximize
  
          // Update bestMove
          if(bestMove.getValue() > move.getValue()){
            // new move is better
            bestMove = move;
            bestMove.setPentagoMove(pentagoMove);
          }
  
          // At max nodes, update β only
          if(move.getValue() < beta){
            beta = move.getValue(); // update to min(beta, bestValue);
          }
  
            // Prune in the event of inconsistency (α ≥ β)
          if (beta <= alpha) {
            // bestMove.setValue(alpha);
            // bestMove.setPentagoMove(pentagoMove);
            return bestMove;
          }
        }
        return bestMove;
     }
  }

  public int minimaxValue(PentagoBoardState boardState){
    int player = boardState.getTurnPlayer();
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
    return score;
  }
}
