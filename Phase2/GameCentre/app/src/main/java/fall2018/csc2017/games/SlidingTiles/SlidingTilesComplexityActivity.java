package fall2018.csc2017.games.SlidingTiles;
import android.content.Intent;
import android.os.Bundle;
import fall2018.csc2017.games.ComplexityActivity;

/**
 * An activity to select settings for the game.
 */
public class SlidingTilesComplexityActivity extends ComplexityActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new BoardManager(4);
    }

    /**
     * Switches to the GameActivity view to play the game.
     */
    protected void switchToGame() {
        game.setNumUndos(Integer.parseInt(numUndos.getText().toString()));
        saveToFile(GameScreenActivity.SAVE_FILENAME);
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }

}
