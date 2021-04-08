package student_player;

import java.util.ArrayList;

import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoCoord;
import pentago_twist.PentagoMove;

public class MyTools {

    // Reminder : these strategies are played in the first 3 turns only, so one of these options must be available
    public Move playCenter(PentagoBoardState boardState){
      
      ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();

      // for the first rounds, place piece in the centers
      for (PentagoMove pentagoMove : allMoves) {
        int x = pentagoMove.getMoveCoord().getX();
        int y = pentagoMove.getMoveCoord().getY();
        if(x == 1 && y == 1){
          System.out.println("FIRST QUAD CENTER");
          return pentagoMove;
        }
        else if(x == 4 && y == 1){
          System.out.println("2ND QUAD CENTER");
          return pentagoMove;
        }
        else if(x == 1 && y == 4){
          System.out.println("3RD QUAD CENTER");
          return pentagoMove;
        }
        else if(x == 4 && y == 4){
          System.out.println("4TH QUAD CENTER");
          return pentagoMove;
        }
      }
      return boardState.getAllLegalMoves().get(0);
    }

    public Move playMiddle(PentagoBoardState boardState, int playerId){
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
      return boardState.getAllLegalMoves().get(0);
    }

    public Move playCorners(PentagoBoardState boardState, int playerId){
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
