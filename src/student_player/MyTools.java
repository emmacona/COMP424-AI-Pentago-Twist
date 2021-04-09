package student_player;

import java.util.ArrayList;

import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

public class MyTools {
    private Move bestMove;

    // Reminder : these strategies are played in the first 3 turns only, so one of these options must be available
    public Move playCenter(PentagoBoardState boardState, long endTime){
      bestMove = boardState.getAllLegalMoves().get(0);
      ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();
      while( System.currentTimeMillis() < endTime){
        // for the first rounds, place piece in the centers
        for (PentagoMove pentagoMove : allMoves) {
          int x = pentagoMove.getMoveCoord().getX();
          int y = pentagoMove.getMoveCoord().getY();
          if(x == 1 && y == 1){
            bestMove = pentagoMove;
            return bestMove;
          }
          else if(x == 4 && y == 1){
            bestMove = pentagoMove;
            return bestMove;
          }
          else if(x == 1 && y == 4){
            bestMove = pentagoMove;
            return bestMove;
          }
          else if(x == 4 && y == 4){
            bestMove = pentagoMove;
            return bestMove;
          }
        }
      }
      return playMiddle(boardState);
    }

    public Move playMiddle(PentagoBoardState boardState){
      ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();
      // for the first rounds, place piece in the middle pieces (if center not available)
      for (PentagoMove pentagoMove : allMoves) {
        int x = pentagoMove.getMoveCoord().getX();
        int y = pentagoMove.getMoveCoord().getY();
        if(x == 2 && y == 2){
          return pentagoMove;
        }
        else if(x == 3 && y == 2){
          return pentagoMove;
        }
        else if(x == 3 && y == 3){
          return pentagoMove;
        }
        else if(x == 2 && y == 3){
          return pentagoMove;
        }
      }
      return playCorners(boardState);
    }

    public Move playCorners(PentagoBoardState boardState){
      ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();
      // for the first rounds, place piece in the corners (if center not available)
      for (PentagoMove pentagoMove : allMoves) {
        int x = pentagoMove.getMoveCoord().getX();
        int y = pentagoMove.getMoveCoord().getY();
        if(x == 0 && y == 0){
          return pentagoMove;
        }
        else if(x == 0 && y == 5){
          return pentagoMove;
        }
        else if(x == 5 && y == 0){
          return pentagoMove;
        }
        else if(x == 5 && y == 5){
          return pentagoMove;
        }
      }
      return boardState.getAllLegalMoves().get(0);
    }

      // public int getMainDiagonalPoints(PentagoBoardState boardState, Piece playerColor, Piece oponentColor){
  //   int bonus = 0;

  //   // left to right
  //   List<String> mainDiagonal = new ArrayList();
  //   String d1 = boardState.getPieceAt(0, 0).toString();
  //   String d2 = boardState.getPieceAt(1, 1).toString();
  //   String d3 = boardState.getPieceAt(2, 2).toString();
  //   String d4 = boardState.getPieceAt(3, 3).toString();
  //   String d5 = boardState.getPieceAt(4, 4).toString();
  //   String d6 = boardState.getPieceAt(5, 5).toString();
  //   mainDiagonal.add(d1);
  //   mainDiagonal.add(d2);
  //   mainDiagonal.add(d3);
  //   mainDiagonal.add(d4);
  //   mainDiagonal.add(d5);
  //   mainDiagonal.add(d6);

  //   int nbConsecutives = 0;
  //   for (int index = 0; index < 5; index++) {
  //     if(mainDiagonal.get(index) ==  playerColor && mainDiagonal.get(index+1) == playerColor){
  //       nbConsecutives++;
  //       bonus += 10*nbConsecutives;
  //     }
  //   }
  //   nbConsecutives = 0; // reset for next diagonal
  //   // right to left
  //   List<String> LR_MainDiagonal = new ArrayList();
  //   String k1 = boardState.getPieceAt(0, 0).toString();
  //   String k2 = boardState.getPieceAt(1, 1).toString();
  //   String k3 = boardState.getPieceAt(2, 2).toString();
  //   String k4 = boardState.getPieceAt(3, 3).toString();
  //   String k5 = boardState.getPieceAt(4, 4).toString();
  //   String k6 = boardState.getPieceAt(5, 5).toString();
  //   LR_MainDiagonal.add(k1);
  //   LR_MainDiagonal.add(k2);
  //   LR_MainDiagonal.add(k3);
  //   LR_MainDiagonal.add(k4);
  //   LR_MainDiagonal.add(k5);
  //   LR_MainDiagonal.add(k6);

  //   for (int index = 0; index < 5; index++) {
  //     if(LR_MainDiagonal.get(index).equals(playerColor) && LR_MainDiagonal.get(index+1).equals(playerColor)){
  //       nbConsecutives++;
  //       bonus += 10*nbConsecutives;
  //     }
  //   }
  //   return bonus;
  // }
}
