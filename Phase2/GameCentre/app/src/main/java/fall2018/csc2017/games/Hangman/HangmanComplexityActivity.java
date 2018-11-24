package fall2018.csc2017.games.Hangman;

import android.content.Intent;
import android.os.Bundle;

import fall2018.csc2017.games.ComplexityActivity;

public class HangmanComplexityActivity extends ComplexityActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new HangmanManager("medium");
    }

    /**
     * Switches to the GameActivity view to play the game.
     */
    protected void switchToGame() {
        game.setNumUndos(Integer.parseInt(numUndos.getText().toString()));
        saveToFile(HangmanScreenActivity.SAVE_FILENAME);
        Intent tmp = new Intent(this, HangmanActivity.class);
        startActivity(tmp);
    }

}
