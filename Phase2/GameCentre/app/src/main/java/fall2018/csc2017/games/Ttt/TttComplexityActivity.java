package fall2018.csc2017.games.Ttt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import fall2018.csc2017.games.ComplexityActivity;
import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;

/**
 * The class representing Tic Tac Toe complexity activity.
 */
public class TttComplexityActivity extends ComplexityActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupNumUndoInput();
        setupDifficultyButtons();
        game = new TttManager("double");
    }

    @SuppressLint("SetTextI18n")
    private void setupDifficultyButtons() {
        Button singlePlayer = findViewById(R.id.button);
        Button obsolete = findViewById(R.id.button2);
        Button doublePlayer = findViewById(R.id.button3);
        obsolete.setVisibility(View.GONE);
        singlePlayer.setText("Single-Player");
        doublePlayer.setText("Multi-Player");

        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("single");
                toastDifficulty("Single Player");
            }
        });


        doublePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("double");
                toastDifficulty("Multi Player");
            }
        });
    }

    /**
     * method for switching to game
     */
    protected void switchToGame() {
        game.setNumUndos(Integer.parseInt(numUndos.getText().toString()));
        saveToFile(GameScreenActivity.SAVE_FILENAME);
        Intent tmp = new Intent(this, TttActivity.class);
        startActivity(tmp);
    }

    /**
     * Sets up the number of undos based on input
     */
    private void setupNumUndoInput() {
        numUndos = findViewById(R.id.numUndos);
        String text = 1 + "";
        numUndos.setText(text);
    }

}