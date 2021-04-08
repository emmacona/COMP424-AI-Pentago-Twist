package pentago_twist;

import java.util.ArrayList;

import boardgame.Move;
import student_player.Minimax_Simple;

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
}
