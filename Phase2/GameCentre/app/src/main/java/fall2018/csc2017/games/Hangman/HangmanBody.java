package fall2018.csc2017.games.Hangman;

import android.widget.ImageView;
import android.widget.TextView;

public class HangmanBody {
    /**
     * Body part images.
     */
    private ImageView[] bodyParts;
    /**
     * The number of body parts in total.
     */
    private int numParts = 6;
    /**
     * The current body part.
     */
    private int currPart;

    public ImageView[] getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(ImageView[] bodyParts) {
        this.bodyParts = bodyParts;
    }

    public int getNumParts() {
        return numParts;
    }

    public void setNumParts(int numParts) {
        this.numParts = numParts;
    }

    public int getCurrPart() {
        return currPart;
    }

    public void setCurrPart(int currPart) {
        this.currPart = currPart;
    }
}
