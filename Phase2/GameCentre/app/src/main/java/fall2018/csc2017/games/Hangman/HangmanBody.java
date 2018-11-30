package fall2018.csc2017.games.Hangman;

import android.view.View;
import android.widget.ImageView;

/**
 * The Hangman Body.
 */
class HangmanBody {
    /**
     * Body part images.
     */
    private ImageView[] bodyParts;
    /**
     * The number of body parts in total.
     */
    private final int NUM_PARTS = 6;
    /**
     * The current body part.
     */
    private int currPart;

    /**
     * Draws the hangman on the activity
     * @param incorrectGuesses the number of body parts showing on the hangman
     */
    void createHangman(int incorrectGuesses) {
        currPart = incorrectGuesses;

        for (int p = 0; p < NUM_PARTS; p++) {
            if (p < incorrectGuesses) {
                bodyParts[p].setVisibility(View.VISIBLE);
            } else {
                bodyParts[p].setVisibility(View.INVISIBLE);
            }
        }
    }
    /**
     * Increments hangman body part.
     */
    void addPart() {
        bodyParts[currPart].setVisibility(View.VISIBLE);
        if (currPart < NUM_PARTS) {
            currPart++;
        }
    }

    /**
     * Initializes hangman body parts.
     */
    void initBodyParts(View head, View body, View arm1,
                       View arm2, View leg1, View leg2) {
        bodyParts = new ImageView[NUM_PARTS];
        bodyParts[0] = (ImageView) head;
        bodyParts[1] = (ImageView) body;
        bodyParts[2] = (ImageView) arm1;
        bodyParts[3] = (ImageView) arm2;
        bodyParts[4] = (ImageView) leg1;
        bodyParts[5] = (ImageView) leg2;
    }
}
