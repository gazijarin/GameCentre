package fall2018.csc2017.games.Hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import fall2018.csc2017.slidingtiles.R;

//todo: implement game
//implements View.OnClickListener
public class HangmanActivity extends AppCompatActivity {

    //Todo: MINIMIZE THE # OF INSTANCE VARIABLES; ONLY 5 OR LESS PER CLASS.
    private String[] words;
    private Random rand;
    private String currWord;
    private LinearLayout wordLayout;
    private TextView[] charViews;

    //body part images
    private ImageView[] bodyParts;

    //number of body parts total
    private int numParts = 6;

    //current part - will increment when wrong answers are chosen
    private int currPart;

    //number of characters in current word
    private int numChars;

    //number correctly guessed
    private int numCorr;

    //hangmanManager
    private HangmanManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //sets up all the body parts + some variables


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        //todo: fix this
        // i think it's to due with the manifest and how the class hierarchy is set up
        //i.e what the back button does
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            System.out.print(e);
        }
        Resources res = getResources();
        words = res.getStringArray(R.array.easywords);
        rand = new Random();
        currWord = "";
        wordLayout = (LinearLayout) findViewById(R.id.word);

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView) findViewById(R.id.head);
        bodyParts[1] = (ImageView) findViewById(R.id.body);
        bodyParts[2] = (ImageView) findViewById(R.id.arm1);
        bodyParts[3] = (ImageView) findViewById(R.id.arm2);
        bodyParts[4] = (ImageView) findViewById(R.id.leg1);
        bodyParts[5] = (ImageView) findViewById(R.id.leg2);

        playGame();
    }

    /**
     * Adds all elements into the activity
     */
    private void playGame() {


        pickNewWord();
        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();
        createUnderlines();
        createHangman();
        addSubmitButton();


    }

    /**
     * pics a new word, ensure it's different from previous game
     */
    private void pickNewWord() {
        String newWord = words[rand.nextInt(words.length)];
        while (newWord.equals(currWord)) {
            newWord = words[rand.nextInt(words.length)];
        }
        currWord = newWord;
    }

    /**
     * Sets up the hidden word + underlines based on current word
     */
    private void createUnderlines() {
        for (int i = 0; i < currWord.length(); i++) {
            //sets up the amount of underlines
            charViews[i] = new TextView(this);
            charViews[i].setText("" + currWord.charAt(i));

            charViews[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            charViews[i].setGravity(Gravity.CENTER);
            //colour set to white to hide letter on white background, will reveal to black
            charViews[i].setTextColor(Color.WHITE);
            charViews[i].setBackgroundResource(R.drawable.letter_underline);
            //add to layout
            wordLayout.addView(charViews[i]);

        }
    }

    /**
     * Draws the hangman on the activity
     */
    private void createHangman() {
        //sets up the hangman with body parts
        currPart = 0;
        numChars = currWord.length();
        numCorr = 0;

        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Activate the submit button.
     */
    private void addSubmitButton() {
        Button undoButton = findViewById(R.id.submit);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = getInput();
                if (!isValid(guess)) {
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
        String guess = ((EditText) findViewById(R.id.user_guess)).getText().toString();
        return guess.toUpperCase();
    }

    /**
     * @param guess, the users guess
     * @return if guess is a valid guess, single char from english alphabet
     */
    private boolean isValid(String guess) {
        return guess.matches("[a-zA-Z]");
    }

    /**
     * updates variables based on the valid guess
     */
    private void updateLetters(String guess) {

        char letterChar = guess.charAt(0);
        boolean correct = false;

        //updates the correct letters to black from white
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == letterChar) {
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
        if (correct) {
            //correct guess
            if (numCorr == numChars) { //user has won
                //bring up scoreboard


                // Display Alert Dialog
                AlertDialog.Builder winBuild = new AlertDialog.Builder(this);
                winBuild.setTitle("Yay, well done!");
                winBuild.setMessage("You won!\n\nThe answer was:\n\n" + currWord);
                winBuild.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HangmanActivity.this.playGame();
                            }
                        });

                winBuild.setNegativeButton("Play a different game",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HangmanActivity.this.finish();
                            }
                        });

                winBuild.show();
            }
        } else if (currPart < numParts) {//some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        } else { //user has lost rip
            //todo: update scoreboard?
            makeToastLost();
        }


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
        HangmanActivity.this.playGame();

    }

}
