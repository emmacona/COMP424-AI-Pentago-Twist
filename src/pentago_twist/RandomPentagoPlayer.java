package pentago_twist;

import java.util.ArrayList;

import boardgame.Move;

/**
 * @author mgrenander
 */
public class RandomPentagoPlayer extends PentagoPlayer {
    public RandomPentagoPlayer() {
        super("RandomPlayer");
    }

    public RandomPentagoPlayer(String name) {
        super(name);
    }

    @Override
    public Move chooseMove(PentagoBoardState boardState) {
        return boardState.getRandomMove();
    }
    // public Move chooseMove(PentagoBoardState boardState) {
    //     ArrayList<PentagoMove> allMoves = boardState.getAllLegalMoves();

    //     // for the first rounds, place piece in the centers
    //     for (PentagoMove pentagoMove : allMoves) {
    //       int x = pentagoMove.getMoveCoord().getX();
    //       int y = pentagoMove.getMoveCoord().getY();
    //       if(x == 1 && y == 1){
    //         System.out.println("FIRST QUAD CENTER");
    //         return pentagoMove;
    //       }
    //       else if(x == 4 && y == 1){
    //         System.out.println("2ND QUAD CENTER");
    //         return pentagoMove;
    //       }
    //       else if(x == 1 && y == 4){
    //         System.out.println("3RD QUAD CENTER");
    //         return pentagoMove;
    //       }
    //       else if(x == 4 && y == 4){
    //         System.out.println("4TH QUAD CENTER");
    //         return pentagoMove;
    //       }
    //     }
    //     return boardState.getRandomMove();
    // }
}
