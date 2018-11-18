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

import fall2018.csc2017.games.SlidingTiles.BoardManager;
import fall2018.csc2017.games.SlidingTiles.GameActivity;
import fall2018.csc2017.games.SlidingTiles.GameScreenActivity;
import fall2018.csc2017.slidingtiles.R;

/**
 * An activity to select settings for the game.
 */
public class GameComplexityActivity extends AppCompatActivity {

    /**
     * The BoardManager for the game
     */
    private BoardManager board;
    /**
     * The input for the number of undos
     */
    private EditText numUndos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_complexity);
        board = new BoardManager(4);
        setupDifficultyButtons();
        setupNumUndoInput();
        setupStartButton();

    }

    /**
     * Sets up the buttons that allows the user to change difficulty
     */
    private void setupDifficultyButtons() {
        Button easyButton = findViewById(R.id.button);
        Button mediumButton = findViewById(R.id.button2);
        Button hardButton = findViewById(R.id.button3);

        easyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board = new BoardManager(3);
                toastDifficulty("3x3");
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board = new BoardManager(4);
                toastDifficulty("4x4");
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board = new BoardManager(5);
                toastDifficulty("5x5");
            }
        });
    }

    /**
     * Creates a toast saying the current board complexity
     *
     * @param difficulty the current board size, in the form "3x3"
     */
    private void toastDifficulty(String difficulty) {
        Toast.makeText(getApplicationContext(), difficulty +
                " board selected", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets up the input for the number of undos
     */
    private void setupNumUndoInput() {
        numUndos = findViewById(R.id.numUndos);
        String text = 3 + "";
        numUndos.setText(text);
    }

    /**
     * Sets up the button that allows the user to start the game
     */
    public void setupStartButton() {
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     * Switches to the GameActivity view to play the game.
     */
    private void switchToGame() {
        board.setNumUndos(Integer.parseInt(numUndos.getText().toString()));
        saveToFile(GameScreenActivity.SAVE_FILENAME);
        Intent tmp = new Intent(this, GameActivity.class);
        startActivity(tmp);
    }


    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(board);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
