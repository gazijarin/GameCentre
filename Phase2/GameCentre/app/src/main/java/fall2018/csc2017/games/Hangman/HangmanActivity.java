package fall2018.csc2017.games.Hangman;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import fall2018.csc2017.games.FinishedActivity;
import fall2018.csc2017.games.GameActivity;
import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;

/*
 * HangmanActivity adapted from Sue Smith's tutorial in envatotuts+
 * <https://code.tutsplus.com/series/create-a-hangman-game-for-android--cms-702>
 * Retrieved: November 2018
 * Title: Create a Hangman Game for Android
 * Author: Sue Smith
 */

/**
 * A class that represents the main Hangman activity.
 */
public class HangmanActivity extends GameActivity {
    /**
     * The current word being guessed.
     */
    private TextView currentWord;
    /**
     * The Hangman body.
     */
    private HangmanBody body;
    /**
     * The Hangman manager.
     */
    private HangmanManager manager;
    /**
     * The current Hangman.
     */
    private Hangman hangman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(GameScreenActivity.SAVE_FILENAME);
        manager = (HangmanManager) game;
        hangman = manager.getHangman();

        // Sets up all the body parts + some variables.
        body = new HangmanBody();

        setContentView(R.layout.activity_hangman);
        currentWord = findViewById(R.id.current_word);

        body.initBodyParts(findViewById(R.id.head), findViewById(R.id.body),
                findViewById(R.id.arm1), findViewById(R.id.arm2), findViewById(R.id.leg1),
                findViewById(R.id.leg2));

        body.createHangman(hangman.getCurrentGuesses());
        displayCurrentWord();
        addSubmitButton();
    }

    /**
     * Activate the submit button.
     */
    private void addSubmitButton() {
        Button submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = getInput();
                if (!manager.isValid(guess)) {
                    makeToastInvalid();
                } else {
                    updateLetters(guess);
                }

            }
        });
    }

    /**
     * Gets the users guess from the input field.
     *
     * @return user guess
     */
    private String getInput() {
        return ((EditText) findViewById(R.id.user_guess)).getText().toString();
    }

    /**
     * Updates variables based on the valid guess.
     */
    private void updateLetters(String guess) {
        char letterChar = guess.charAt(0);

        boolean found = hangman.makeVisible(letterChar);
        displayCurrentWord();

        if (manager.puzzleSolved()) {
            switchActivity();
        } else if (!found && !manager.puzzleLost()) {
            body.addPart();
        } else if (manager.puzzleLost()) {
            makeToastLost();
        } else {
            Log.i("orange", "updateLetters: " + Arrays.toString(hangman.getRevealedWord()));
        }
    }

    /**
     * Switches to the finished activity.
     */
    private void switchActivity() {
        Intent i = new Intent(this, FinishedActivity.class);
        i.putExtra("SCORE", manager.getScore());
        i.putExtra("GAME", manager);
        startActivity(i);
    }

    /**
     * Displays the current word on the screen.
     */
    private void displayCurrentWord() {
        char[] current = hangman.getRevealedWord();
        StringBuilder toDisplay = new StringBuilder();
        for (char aCurrent : current) {
            if (aCurrent != '@') {
                toDisplay.append(aCurrent);
                toDisplay.append(" ");
            } else {
                toDisplay.append("_");
                toDisplay.append(" ");
            }
        }

        currentWord.setText(toDisplay.toString());
    }

    /**
     * Lets user know input was invalid, what is valid input.
     */
    private void makeToastInvalid() {
        Toast.makeText(this, "Invalid guess, please guess a single letter", Toast.LENGTH_SHORT).show();
    }

    /**
     * Restarts game after a loss.
     */
    private void makeToastLost() {
        Toast.makeText(this, "Ran out of guesses, try again", Toast.LENGTH_SHORT).show();
    }

}
