package fall2018.csc2017.games.Hangman;

import android.content.Intent;
import android.os.Bundle;

import fall2018.csc2017.games.ComplexityActivity;
import fall2018.csc2017.games.GameScreenActivity;

/**
 * A class that represents the Hangman complexity activity.
 */
public class HangmanComplexityActivity extends ComplexityActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new HangmanManager("medium");
    }

    /**
     * Switches to the HangmanActivity view to play the game.
     */
    protected void switchToGame() {
        game.setNumUndos(Integer.parseInt(numUndos.getText().toString()));
        saveToFile(GameScreenActivity.SAVE_FILENAME);
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }

}
