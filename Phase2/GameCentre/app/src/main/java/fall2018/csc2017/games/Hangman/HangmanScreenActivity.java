package fall2018.csc2017.games.Hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;

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
     * Switch to the GameActivity view to play the game.
     */
    protected void switchToGame() {
        Intent tmp = new Intent(this, HangmanActivity.class);
        saveToFile(SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Switch to the GameActivity view to start a new game.
     */
    protected void switchToNewGame() {
        Intent tmp = new Intent(this, HangmanComplexityActivity.class);
        startActivity(tmp);
    }

}

