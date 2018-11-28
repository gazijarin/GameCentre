package fall2018.csc2017.games.Ttt;

import android.content.Intent;
import android.os.Bundle;

import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.Hangman.HangmanActivity;
import fall2018.csc2017.games.Hangman.HangmanComplexityActivity;
import fall2018.csc2017.games.R;

public class TttScreenActivity extends GameScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = new TttManager("multi");
        String username = getIntent().getStringExtra("USERNAME");
        SAVE_FILENAME = username + "_" + game.getGameId() + "_save_file.ser";
        super.onCreate(savedInstanceState);
    }

    protected void switchToNewGame() {
        Intent tmp = new Intent(this, TttComplexityActivity.class);
        startActivity(tmp);
    }

    protected void switchToGame() {
        Intent tmp = new Intent(this, TttActivity.class);
        saveToFile(SAVE_FILENAME);
        startActivity(tmp);
    }
}