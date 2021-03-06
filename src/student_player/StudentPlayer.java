package student_player;
import boardgame.Move;
import pentago_twist.PentagoPlayer;
import pentago_twist.PentagoBoardState;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260681550");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + 1800; // maximum 1800 ms to find a move
        Minimax minimax = new Minimax();
        PentagoBoardState pbs = (PentagoBoardState) boardState.clone();

        Move myMove;
        int player = pbs.getTurnPlayer();
        myMove = minimax.aBPruning(pbs, player, endTime);

        // Return your move to be processed by the server.
        return myMove;
    }
}