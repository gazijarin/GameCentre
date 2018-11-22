package fall2018.csc2017.games.SlidingTiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.games.ComplexityActivity;
import fall2018.csc2017.games.SlidingTiles.BoardManager;
import fall2018.csc2017.games.SlidingTiles.GameActivity;
import fall2018.csc2017.games.SlidingTiles.GameScreenActivity;
import fall2018.csc2017.games.R;

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
