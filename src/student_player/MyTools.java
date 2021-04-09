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
}
