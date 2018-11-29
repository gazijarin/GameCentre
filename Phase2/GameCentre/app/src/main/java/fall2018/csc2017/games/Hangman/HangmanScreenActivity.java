package fall2018.csc2017.games.Hangman;

import android.content.Intent;
import android.os.Bundle;

import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;

/**
 * A class that represents the Hangman screen activity.
 */
public class HangmanScreenActivity extends GameScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = new HangmanManager("medium");
        String username = getIntent().getStringExtra("USERNAME");
        SAVE_FILENAME = username + "_" + game.getGameId() + "_save_file.ser";

        super.onCreate(savedInstanceState);
        gameImage.setImageResource(R.drawable.hangman_title_image);
        gameDescription.setText(getResources().getString(R.string.hangman_description));
    }

    /**
     * Switch to the SlidingTilesActivity view to play the game.
     */
    protected void switchToGame() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        saveToFile(SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the SlidingTilesActivity view to start a new game.
     */
    protected void switchToNewGame() {
        Intent tmp = new Intent(this, HangmanComplexityActivity.class);
        startActivity(tmp);
    }

}

