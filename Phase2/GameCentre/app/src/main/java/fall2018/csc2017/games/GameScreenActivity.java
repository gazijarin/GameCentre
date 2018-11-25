package fall2018.csc2017.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import fall2018.csc2017.games.R;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public abstract class GameScreenActivity extends FileActivity<Game> {

    protected ImageView gameImage;
    protected TextView gameDescription;

    public static String SAVE_FILENAME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starting);
        gameImage = findViewById(R.id.gameImage);
        gameDescription = findViewById(R.id.GameText);

        loadFromFile(SAVE_FILENAME);

        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToNewGame();
            }
        });

    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(SAVE_FILENAME);
                if (game != null) { //load is present
                    makeToastLoadedText();
                    switchToGame();
                }
            }
        });
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(SAVE_FILENAME);
    }

    /**
     * Switch to the Activity to play the game.
     */
    protected abstract void switchToGame();

    /**
     * Switch to the Activity to start a new game.
     */
    protected abstract void switchToNewGame();
}
