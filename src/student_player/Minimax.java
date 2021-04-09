package student_player;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import pentago_twist.PentagoBoardState.Piece;

public class Minimax {	
  private int WIN = 100;
  private int DRAW = 50;
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
    // bonus += getMainDiagonalPoints(boardState, playerColor, oponentColor);
    bonus += getRowPoints(boardState, playerColor, oponentColor);
    bonus += getColumnPoints(boardState, playerColor, oponentColor);
    bonus += getCenterPoints(boardState, playerColor, oponentColor);
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
    for (int row = 0; row < 5; row++){
      for (int col = 0; col < 2; col++){
        Piece r1 = boardState.getPieceAt(row, col);
        Piece r2 = boardState.getPieceAt(row, col+1);
        Piece r3 = boardState.getPieceAt(row, col+2);
        Piece r4 = boardState.getPieceAt(row, col+3);
        Piece r5 = boardState.getPieceAt(row, col+4);
        if (r1 ==  playerColor && r2 == playerColor  && r3 == playerColor  && r4 == playerColor){
          if(r5 == Piece.EMPTY){
            bonus += 100; // if 4 in a row and 5th one is empty --> 100% DO THIS MOVE
          } 
        } 
        else if (r1 ==  playerColor && r2 == playerColor  && r3 == playerColor){
          if(r4 == Piece.EMPTY){
            bonus += 40; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r2 ==  playerColor && r3 == playerColor  && r4 == playerColor){
          if(r1 == Piece.EMPTY){
            bonus += 40; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r3 ==  playerColor && r4 == playerColor  && r5 == playerColor){
          if(r2 == Piece.EMPTY){
            bonus += 40; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r1 ==  playerColor && r2 == playerColor){
          if(r3 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r2 ==  playerColor && r3 == playerColor){
          if(r1 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
          else if(r4 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r3 ==  playerColor && r4 == playerColor){
          if(r2 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
          else if(r5 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r1 ==  oponentColor && r2 == oponentColor  && r3 == oponentColor  && r4 == oponentColor){
          if (r5 == Piece.EMPTY){
            bonus += 100; // OPONENT MIGHT WIN --> DEFENSE MOVE !!
          } 
        }  
      }
    }
    return bonus;
  }

  public int getColumnPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
    int bonus = 0;
    for (int col = 0; col < 5; col++){
      for (int row = 0; row < 2; row++){
        Piece r1 = boardState.getPieceAt(row, col);
        Piece r2 = boardState.getPieceAt(row+1, col);
        Piece r3 = boardState.getPieceAt(row+2, col);
        Piece r4 = boardState.getPieceAt(row+3, col);
        Piece r5 = boardState.getPieceAt(row+4, col);
        if (r1 ==  playerColor && r2 == playerColor  && r3 == playerColor  && r4 == playerColor){
          if(r5 == Piece.EMPTY){
            bonus += 100; // if 4 in a row and 5th one is empty --> 100% DO THIS MOVE
          } 
        } 
        else if (r1 ==  playerColor && r2 == playerColor  && r3 == playerColor){
          if(r4 == Piece.EMPTY){
            bonus += 40; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r2 ==  playerColor && r3 == playerColor  && r4 == playerColor){
          if(r1 == Piece.EMPTY){
            bonus += 40; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r3 ==  playerColor && r4 == playerColor  && r5 == playerColor){
          if(r2 == Piece.EMPTY){
            bonus += 40; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r1 ==  playerColor && r2 == playerColor){
          if(r3 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r2 ==  playerColor && r3 == playerColor){
          if(r1 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
          else if(r4 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r3 ==  playerColor && r4 == playerColor){
          if(r2 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
          else if(r5 == Piece.EMPTY){
            bonus += 20; // if 4 in a row and 5th one is empty --> DO THIS MOVE (otherwise its useless)
          } 
        } 
        else if (r1 ==  oponentColor && r2 == oponentColor  && r3 == oponentColor  && r4 == oponentColor){
          if (r5 == Piece.EMPTY){
            bonus += 100; // OPONENT MIGHT WIN --> DEFENSE MOVE !!
          } 
        }  
      }
    }
    return bonus;
  }

}