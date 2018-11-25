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
    private final int NUMPARTS = 6;
    /**
     * The current body part.
     */
    private int currPart;

    /**
     * Draws the hangman on the activity
     */
    void createHangman() {
        currPart = 0;

        for (int p = 0; p < NUMPARTS; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }
    }
    /**
     * Increments hangman body part.
     */
    void addPart() {
        bodyParts[currPart].setVisibility(View.VISIBLE);
        if (currPart < NUMPARTS) {
            currPart++;
        }
    }

    /**
     * Initializes hangman body parts.
     */
    void initBodyParts(View head, View body, View arm1,
                       View arm2, View leg1, View leg2) {
        bodyParts = new ImageView[NUMPARTS];
        bodyParts[0] = (ImageView) head;
        bodyParts[1] = (ImageView) body;
        bodyParts[2] = (ImageView) arm1;
        bodyParts[3] = (ImageView) arm2;
        bodyParts[4] = (ImageView) leg1;
        bodyParts[5] = (ImageView) leg2;
    }
}
