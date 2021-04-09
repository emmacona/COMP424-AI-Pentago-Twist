package student_player;

import java.util.ArrayList;

import boardgame.Board;
import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import pentago_twist.PentagoBoardState.Piece;

public class Minimax {	
  private int WIN = 100;
  private int DRAW = 5;
  private int LOSE = -100;

  public Move aBPruning(PentagoBoardState pbs, int player, long endTime){
    while(System.currentTimeMillis() < endTime){
      int alpha = -Integer.MAX_VALUE; // At max nodes, update α only
      int beta = Integer.MAX_VALUE; // At min nodes, update β only
  
      Node root = new Node(pbs);
      toggleMinimax(root, alpha, beta, true, 0);
      Move myMove = root.getBestMove();

      for(Node node : root.getChildren()) {
        if(node.getBoardState().getWinner() == pbs.getTurnPlayer()) {
          return node.pentagoMove;
        }
      }

      if (myMove == null){
        System.out.println("Get random");
        myMove = pbs.getRandomMove();
      }
      return myMove;
    }
    return pbs.getRandomMove(); // if times up -> get random
  }

  public int toggleMinimax(Node node, int alpha, int beta, boolean isMax, int depth){
    int player = node.getBoardState().getTurnPlayer();

    if (depth == 2 || node.getBoardState().gameOver()) {
      return evaluate(node, player);
    }

    if (isMax){
      return maximize(node, alpha, beta, depth, isMax);
    } 
    else {
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
      node.addChild(child);

      int value = toggleMinimax(child, alpha, beta, !isMax, depth+1);

      // Update alpha
      if(alpha < value){
        alpha = value;
        // node.setPentagoMove(pentagoMove);
        node.setBestMove(pentagoMove);
        node.setValue(value);
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
      node.addChild(child);

      int value = toggleMinimax(child, alpha, beta, !isMax, depth+1);

      // Update alpha
      if(beta > value){
        beta = value;
        // node.setPentagoMove(pentagoMove);
        node.setBestMove(pentagoMove);
        node.setValue(value);
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
      else if(boardState.getWinner() == Board.DRAW){
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
    int score = 0;
    if (player == PentagoBoardState.WHITE){
      score += getAdjacentBonusPoints(boardState, Piece.WHITE, Piece.BLACK);
    }
    else {
      score += getAdjacentBonusPoints(boardState, Piece.BLACK, Piece.WHITE);
    }
    return score;
  }


  public int getAdjacentBonusPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    bonus += getLRDiagonalPoints(boardState, playerColor, oponentColor);
    bonus += getRLDiagonalPoints(boardState, playerColor, oponentColor);
    bonus += getRowPoints(boardState, playerColor, oponentColor);
    bonus += getColumnPoints(boardState, playerColor, oponentColor);
    bonus += getCenterPoints(boardState, playerColor, oponentColor);
    return bonus;
  }

  public int getRLDiagonalPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    int[] diag1_X = {5, 4, 3, 2, 1, 0};
    int[] diag1_Y = {5, 4, 3, 2, 1, 0};
    int[] diag2_X = {4, 3, 2, 1, 0};
    int[] diag2_Y = {0, 1, 2, 3, 4};
    int[] diag3_X = {5, 4, 3, 2, 1};
    int[] diag3_Y = {1, 2, 3, 4, 5};

    for (int i = 0; i < 2; i++){
      if (i == 0) {
        Piece r1 = boardState.getPieceAt(diag1_X[0], diag1_Y[0]);
        Piece r2 = boardState.getPieceAt(diag1_X[1], diag1_Y[1]);
        Piece r3 = boardState.getPieceAt(diag1_X[2], diag1_Y[2]);
        Piece r4 = boardState.getPieceAt(diag1_X[3], diag1_Y[3]);
        Piece r5 = boardState.getPieceAt(diag1_X[4], diag1_Y[4]);
        Piece r6 = boardState.getPieceAt(diag1_X[5], diag1_Y[5]);
        
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);
        bonus += calculatePoints(playerColor, oponentColor, r2, r3, r4, r5, r6);    
      }
      if (i == 1) {
        Piece r1 = boardState.getPieceAt(diag2_X[0], diag2_Y[0]);
        Piece r2 = boardState.getPieceAt(diag2_X[1], diag2_Y[1]);
        Piece r3 = boardState.getPieceAt(diag2_X[2], diag2_Y[2]);
        Piece r4 = boardState.getPieceAt(diag2_X[3], diag2_Y[3]);
        Piece r5 = boardState.getPieceAt(diag2_X[4], diag2_Y[4]);
        
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);   
      }
      if (i == 2) {
        Piece r1 = boardState.getPieceAt(diag3_X[0], diag3_Y[0]);
        Piece r2 = boardState.getPieceAt(diag3_X[1], diag3_Y[1]);
        Piece r3 = boardState.getPieceAt(diag3_X[2], diag3_Y[2]);
        Piece r4 = boardState.getPieceAt(diag3_X[3], diag3_Y[3]);
        Piece r5 = boardState.getPieceAt(diag3_X[4], diag3_Y[4]);
        
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);   
      }

    }
    return bonus;
  }


  public int getLRDiagonalPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    int[] diag1_X = {0, 1, 2, 3, 4, 5};
    int[] diag1_Y = {0, 1, 2, 3, 4, 5};
    int[] diag2_X = {0, 1, 2, 3, 4};
    int[] diag2_Y = {1, 2, 3, 4, 5};
    int[] diag3_X = {1, 2, 3, 4, 5};
    int[] diag3_Y = {0, 1, 2, 3, 4};

    for (int i = 0; i < 2; i++){
      if (i == 0) {
        Piece r1 = boardState.getPieceAt(diag1_X[0], diag1_Y[0]);
        Piece r2 = boardState.getPieceAt(diag1_X[1], diag1_Y[1]);
        Piece r3 = boardState.getPieceAt(diag1_X[2], diag1_Y[2]);
        Piece r4 = boardState.getPieceAt(diag1_X[3], diag1_Y[3]);
        Piece r5 = boardState.getPieceAt(diag1_X[4], diag1_Y[4]);
        Piece r6 = boardState.getPieceAt(diag1_X[5], diag1_Y[5]);
        
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);
        bonus += calculatePoints(playerColor, oponentColor, r2, r3, r4, r5, r6);    
      }
      if (i == 1) {
        Piece r1 = boardState.getPieceAt(diag2_X[0], diag2_Y[0]);
        Piece r2 = boardState.getPieceAt(diag2_X[1], diag2_Y[1]);
        Piece r3 = boardState.getPieceAt(diag2_X[2], diag2_Y[2]);
        Piece r4 = boardState.getPieceAt(diag2_X[3], diag2_Y[3]);
        Piece r5 = boardState.getPieceAt(diag2_X[4], diag2_Y[4]);
        
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);   
      }
      if (i == 2) {
        Piece r1 = boardState.getPieceAt(diag3_X[0], diag3_Y[0]);
        Piece r2 = boardState.getPieceAt(diag3_X[1], diag3_Y[1]);
        Piece r3 = boardState.getPieceAt(diag3_X[2], diag3_Y[2]);
        Piece r4 = boardState.getPieceAt(diag3_X[3], diag3_Y[3]);
        Piece r5 = boardState.getPieceAt(diag3_X[4], diag3_Y[4]);
        
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);   
      }

    }
    return bonus;
  }

  public int calculatePoints(Piece playerColor, Piece oponentColor, Piece r1, Piece r2, Piece r3, Piece r4, Piece r5){
    int bonus = 0;
    if (r1 ==  playerColor && r2 == playerColor  && r3 == playerColor  && r4 == playerColor){
      if(r5 == Piece.EMPTY){
        bonus += 500; // if 4 in a row and 5th one is empty --> 100% DO THIS MOVE
      } 
    } 
    else if (r1 ==  playerColor && r2 == playerColor  && r3 == playerColor){
      if(r4 == Piece.EMPTY){
        bonus += 40;
      } 
    } 
    else if (r2 ==  playerColor && r3 == playerColor  && r4 == playerColor){
      if(r1 == Piece.EMPTY || r5 == Piece.EMPTY){
        bonus += 40;
      } 
    } 
    else if (r3 ==  playerColor && r4 == playerColor  && r5 == playerColor){
      if(r2 == Piece.EMPTY){
        bonus += 40;
      } 
    } 
    else if (r1 ==  playerColor && r2 == playerColor){
      if(r3 == Piece.EMPTY){
        bonus += 20;
      } 
    } 
    else if (r2 ==  playerColor && r3 == playerColor){
      if(r1 == Piece.EMPTY || r4 == Piece.EMPTY){
        bonus += 20;
      } 
    } 
    else if (r3 ==  playerColor && r4 == playerColor){
      if(r2 == Piece.EMPTY || r5 == Piece.EMPTY){
        bonus += 20;
      } 
    } 
    else if (r1 ==  oponentColor && r2 == oponentColor  && r3 == oponentColor  && r4 == oponentColor){
      if (r5 == Piece.EMPTY){
        bonus += 500; // OPONENT MIGHT WIN --> DEFENSE MOVE !!
      } 
    } 
    return bonus;
  }

  public int getCenterPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    int[] centers = {1,4};
    for (int i = 0; i < 2; i++){
      for (int j = 0; i < 2; i++){
        if (boardState.getPieceAt(centers[i], centers[j]) == playerColor){
          bonus += 1; // give points for center
        }
        else if (boardState.getPieceAt(centers[i], centers[j]) == oponentColor){
          bonus -= 1; // if center alreay used, not so good
        }
        else { // its empty
          bonus += 10; // potentially a good move
        }
      } 
    }
    return bonus;
  }

  public int getRowPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    for (int row = 0; row <= 5; row++){
      for (int col = 0; col < 2; col++){
        Piece r1 = boardState.getPieceAt(row, col);
        Piece r2 = boardState.getPieceAt(row, col+1);
        Piece r3 = boardState.getPieceAt(row, col+2);
        Piece r4 = boardState.getPieceAt(row, col+3);
        Piece r5 = boardState.getPieceAt(row, col+4);
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);
      }
    }
    return bonus;
  }

  public int getColumnPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    for (int col = 0; col <= 5; col++){
      for (int row = 0; row < 2; row++){
        Piece r1 = boardState.getPieceAt(row, col);
        Piece r2 = boardState.getPieceAt(row+1, col);
        Piece r3 = boardState.getPieceAt(row+2, col);
        Piece r4 = boardState.getPieceAt(row+3, col);
        Piece r5 = boardState.getPieceAt(row+4, col);     
        bonus += calculatePoints(playerColor, oponentColor, r1, r2, r3, r4, r5);
      }
    }
    return bonus;
  }

}