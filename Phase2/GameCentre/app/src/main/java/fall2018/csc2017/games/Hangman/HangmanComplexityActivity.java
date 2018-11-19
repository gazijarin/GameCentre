package fall2018.csc2017.games.Hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;

import fall2018.csc2017.games.Hangman.HangmanManager;
import fall2018.csc2017.games.Hangman.HangmanActivity;
import fall2018.csc2017.slidingtiles.R;

public class HangmanComplexityActivity extends AppCompatActivity {
    /**
     * The HangmanManager for the game
     */
    private HangmanManager hangman;
    /**
     * The input for the number of undos
     */
    private EditText numUndos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_complexity);
        hangman = new HangmanManager("medium");
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
                hangman = new HangmanManager("easy");
                toastDifficulty("Easy");
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangman = new HangmanManager("medium");
                toastDifficulty("Medium");
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hangman = new HangmanManager("hard");
                toastDifficulty("Hard");
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
                " Hangman game selected", Toast.LENGTH_SHORT).show();
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
        hangman.setNumUndos(Integer.parseInt(numUndos.getText().toString()));
        saveToFile(HangmanScreenActivity.SAVE_FILENAME);
        Intent tmp = new Intent(this, HangmanActivity.class);
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
            outputStream.writeObject(hangman);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}
