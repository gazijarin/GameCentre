package fall2018.csc2017.games.Hangman;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Random;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

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
    private GridView letters;
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
        System.out.println("9");

        playGame();
    }

    /**
     * Adds all elements into the activity
     */
    private void playGame() {


        createNewWord();

        charViews = new TextView[currWord.length()];
        wordLayout.removeAllViews();

        createUnderlines();

        createHangman();


    }

    /**
     * pics a new word, ensure it's different from previous game
     */
    private void createNewWord() {
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

    //change to when textfield is submitted
    public void letterPressed(View view) {
        String ltr = ((TextView) view).getText().toString();
        char letterChar = ltr.charAt(0);
        view.setEnabled(false);
//        view.setBackgroundResource(R.drawable.letter_down);

        boolean correct = false;
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == letterChar) {
                correct = true;
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }

        if (correct) {
            //correct guess
            if (numCorr == numChars) {
                //user has won
                // Disable Buttons
                disableBtns();

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

                winBuild.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HangmanActivity.this.finish();
                            }
                        });

                winBuild.show();
            }
        } else if (currPart < numParts) {
            //some guesses left
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        } else {
            //user has lost
            disableBtns();

            // Display Alert Dialog
            AlertDialog.Builder loseBuild = new AlertDialog.Builder(this);
            loseBuild.setTitle("Oopsie");
            loseBuild.setMessage("You lose!\n\nThe answer was:\n\n" + currWord);
            loseBuild.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HangmanActivity.this.playGame();
                        }
                    });

            loseBuild.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HangmanActivity.this.finish();
                        }
                    });

            loseBuild.show();

        }
    }

    public void disableBtns() {
        int numLetters = letters.getChildCount();
        for (int l = 0; l < numLetters; l++) {
            letters.getChildAt(l).setEnabled(false);
        }
    }


}
