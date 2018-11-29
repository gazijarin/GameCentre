package fall2018.csc2017.games;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public abstract class ComplexityActivity extends FileActivity<Game> {
    /**
     * The input for the number of undos
     */
    protected EditText numUndos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complexity);
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
                game.setDifficulty("easy");
                toastDifficulty("Easy");
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("medium");
                toastDifficulty("Medium");
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("hard");
                toastDifficulty("Hard");
            }
        });
    }

    /**
     * Creates a toast saying the current board complexity
     *
     * @param difficulty the current board size, in the form "3x3"
     */
    public void toastDifficulty(String difficulty) {
        Toast.makeText(getApplicationContext(), difficulty +
                " selected", Toast.LENGTH_SHORT).show();
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
    private void setupStartButton() {
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    /**
     * Switches to the game activity view to play the game.
     */
    protected abstract void switchToGame();

}
