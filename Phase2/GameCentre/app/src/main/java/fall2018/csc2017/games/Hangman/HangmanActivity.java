package fall2018.csc2017.games.Hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import fall2018.csc2017.games.FileActivity;
import fall2018.csc2017.games.FinishedActivity;
import fall2018.csc2017.games.Game;
import fall2018.csc2017.games.GameActivity;
import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;
import fall2018.csc2017.games.SlidingTiles.SlidingTilesScreenActivity;

/*
 * HangmanActivity adapted from Sue Smith's tutorial in envatotuts+
 * <https://code.tutsplus.com/series/create-a-hangman-game-for-android--cms-702>
 * Retrieved: November 2018
 * Title: Create a Hangman Game for Android
 * Author: Sue Smith
 */

public class HangmanActivity extends GameActivity {

    private TextView current_word;

    //number correctly guessed
    private HangmanBody body;
    private HangmanManager manager;
    private Hangman hangman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(GameScreenActivity.SAVE_FILENAME);
        manager = (HangmanManager) game;
        hangman = manager.getHangman();

        //sets up all the body parts + some variables
        body = new HangmanBody();

        setContentView(R.layout.activity_hangman);
        //todo: fix this
        // i think it's to due with the manifest and how the class hierarchy is set up
        //i.e what the back button does
//        try {
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//        } catch (Exception e) {
//            System.out.print(e);
//        }

        //the Textview of the word being guessed
        current_word = findViewById(R.id.current_word);

        //Todo: Ew, too many arguments. Maybe create an array and pass in as single argument.
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
     * Gets the users guess from the input field
     *
     * @return user guess
     */
    private String getInput() {
        return ((EditText) findViewById(R.id.user_guess)).getText().toString();
    }

    /**
     * updates variables based on the valid guess
     */
    private void updateLetters(String guess) {
        // Update the colour of the correctly guessed letters
        // if game over go to gameover screen
        // if guess is wrong then add a body part
        char letterChar = guess.charAt(0);

        boolean found = hangman.makeVisible(letterChar);
        displayCurrentWord();

        if (manager.puzzleSolved()) {
            switchActivity();
        } else if (!found && !manager.puzzleLost()) {//some guesses left
            body.addPart();
        } else if (manager.puzzleLost()) { //user has lost rip
            //todo: update scoreboard?
            makeToastLost();
        } else {
            Log.i("orange", "updateLetters: " + Arrays.toString(hangman.getRevealedWord()));
        }
    }

    /**
     * Switches to the finished activity
     */
    private void switchActivity() {
        Intent i = new Intent(this, FinishedActivity.class);
        i.putExtra("SCORE", manager.getScore());
        i.putExtra("GAME", manager);
        startActivity(i);
    }

    /**
     * Displays the current word on the screen
     */
    private void displayCurrentWord() {
        char[] current = hangman.getRevealedWord();
        StringBuilder toDisplay = new StringBuilder();
        for (int i = 0; i < current.length; i++) {
            if (current[i] != '@') {
                toDisplay.append(current[i]);
                toDisplay.append(" ");
            } else {
                toDisplay.append("_");
                toDisplay.append(" ");
            }
        }

        current_word.setText(toDisplay.toString());
    }

    /**
     * Lets user know input was invalid, what is valid input
     */
    private void makeToastInvalid() {
        Toast.makeText(this, "Invalid guess, please guess a single letter", Toast.LENGTH_SHORT).show();
    }

    /**
     * Restarts game after a loss
     */
    private void makeToastLost() {
        Toast.makeText(this, "Ran out of guesses, try again", Toast.LENGTH_SHORT).show();
    }

}
