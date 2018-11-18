package fall2018.csc2017.games.SlidingTiles;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import fall2018.csc2017.games.FinishedActivity;
import fall2018.csc2017.games.SlidingTiles.BoardManager;


public class MovementController {

    private BoardManager boardManager = null;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void processTapMovement(Context context, int position, boolean display) {
        if (boardManager.isValidTap(position)) {
            boardManager.storeMove(position,boardManager.undoMoves);
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                //start next activity
                Intent i = new Intent(context, FinishedActivity.class);
                i.putExtra("SCORE", boardManager.getScore());
                i.putExtra("GAME", boardManager);
                context.startActivity(i);

            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}
