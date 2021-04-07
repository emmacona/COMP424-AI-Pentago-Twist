package student_player;

import boardgame.Move;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoCoord;
import pentago_twist.PentagoMove;

public class MyTools {

    // Reminder : these strategies are played in the first 3 turns only, so one of these options must be available
    public Move playCenter(PentagoBoardState boardState, int playerId){
      
        // for the first rounds, place piece in the centers
        PentagoCoord c1 = new PentagoCoord(1, 1);
        PentagoCoord c2 = new PentagoCoord(4, 1);
        PentagoCoord c3 = new PentagoCoord(1, 4);
        PentagoCoord c4 = new PentagoCoord(4, 4);
    
        if(boardState.isPlaceLegal(c1)){
          return new PentagoMove(c1, 1, 0, playerId);
        }
        if(boardState.isPlaceLegal(c2)){
          return new PentagoMove(c2, 1, 0, playerId);
        }
        if(boardState.isPlaceLegal(c3)){
          return new PentagoMove(c3, 1, 0, playerId);
        }
        if(boardState.isPlaceLegal(c4)){
          return new PentagoMove(c4, 1, 0, playerId);
        }
        return playMiddle(boardState, playerId);
      }

    public Move playMiddle(PentagoBoardState boardState, int playerId){
      PentagoCoord c1 = new PentagoCoord(2, 2);
      PentagoCoord c2 = new PentagoCoord(3, 2);
      PentagoCoord c3 = new PentagoCoord(3, 3);
      PentagoCoord c4 = new PentagoCoord(2, 3);
      
      // for the first rounds, place piece in the middle pieces (if center not available)
      if(boardState.isPlaceLegal(c1)){
        return new PentagoMove(c1, 1, 0, playerId);
      }
      if(boardState.isPlaceLegal(c2)){
        return new PentagoMove(c2, 1, 0, playerId);
      }
      if(boardState.isPlaceLegal(c3)){
        return new PentagoMove(c3, 1, 0, playerId);
      }
      if(boardState.isPlaceLegal(c4)){
        return new PentagoMove(c4, 1, 0, playerId);
      }
      return boardState.getRandomMove();

    }

    public Move playCorners(PentagoBoardState boardState, int playerId){
      PentagoCoord c1 = new PentagoCoord(0, 0);
      PentagoCoord c2 = new PentagoCoord(0, 5);
      PentagoCoord c3 = new PentagoCoord(5, 0);
      PentagoCoord c4 = new PentagoCoord(5, 5);
      
      // for the first rounds, place piece in the corners (if center not available)
      if(boardState.isPlaceLegal(c1)){
        return new PentagoMove(c1, 1, 0, playerId);
      }
      if(boardState.isPlaceLegal(c2)){
        return new PentagoMove(c2, 1, 0, playerId);
      }
      if(boardState.isPlaceLegal(c3)){
        return new PentagoMove(c3, 1, 0, playerId);
      }
      if(boardState.isPlaceLegal(c4)){
        return new PentagoMove(c4, 1, 0, playerId);
      }
      return boardState.getRandomMove();

    }
}