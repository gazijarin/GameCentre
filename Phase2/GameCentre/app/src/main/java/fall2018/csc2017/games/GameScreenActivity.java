package fall2018.csc2017.games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public abstract class GameScreenActivity extends FileActivity<Game> {
    /**
     * The game image.
     */
    protected ImageView gameImage;
    /**
     * The game description.
     */
    protected TextView gameDescription;
    /**
     * The game save filename.
     */
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
        addScoreButtonListener();
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
     * Activate the score button
     */
    private void addScoreButtonListener() {
        Button scoreButton = findViewById(R.id.score_button);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToScores();
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
    public void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFromFile(SAVE_FILENAME);
    }

    /**
     * An activity that switches to the ScoreActivity
     */
    private void switchToScores(){
        Intent tmp = new Intent(this, ScoreboardActivity.class);
        tmp.putExtra("GAME", (Serializable) game);
        startActivity(tmp);
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
