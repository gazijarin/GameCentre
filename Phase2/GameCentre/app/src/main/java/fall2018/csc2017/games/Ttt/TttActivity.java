package fall2018.csc2017.games.Ttt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc2017.games.FinishedActivity;
import fall2018.csc2017.games.GameActivity;
import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;
/*
 * TttActivity adapted from tutorial in Coding in Flow
 * <https://codinginflow.com/tutorials/android/tic-tac-toe/>
 * Retrieved: November 2018
 * Author: Florian Walther
 */

/**
 * The Tic Tac Toe Activity.
 */
public class TttActivity extends GameActivity implements View.OnClickListener {
    /**
     * Player1 and 2 score displays on overhead
     */
    private TextView p1Score;
    private TextView p2Score;
    /**
     * The game manager
     */
    TttManager manager;
    /**
     * The button log of all the moves made in a game
     */
    public ArrayList<Button> log = new ArrayList<>();
    /**
     * Tacks the contents of the buttons
     */
    public Button[][] buttons = new Button[3][3];
    /**
     * A message to display after the game has ended.
     */
    String message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt);

        p1Score = findViewById(R.id.text_view_p1);
        p2Score = findViewById(R.id.text_view_p2);

        loadFromFile(GameScreenActivity.SAVE_FILENAME);
        manager = (TttManager) game;

        buttonDraw();

        Button buttonUndo = findViewById(R.id.button_undo);
        buttonUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.undo();
                buttonDraw();
            }
        });

        Button finish = findViewById(R.id.finishButton);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity();
            }
        });

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
     * Setting up buttons for the UI
     */
    private void buttonDraw() {
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "");
        map.put(1, "X");
        map.put(2, "O");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
                buttons[i][j].setText(map.get(manager.board[i][j]));
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        int[] cords = identifier((Button) v);
        if (manager.p1Turn) {
            ((Button) v).setText("X");
            manager.play(cords[0], cords[1], 1);


        } else {
            ((Button) v).setText("O");
            manager.play(cords[0], cords[1], 2);
        }

        log.add((Button) v);
        winActivities();


        if (!manager.p1Turn && manager.mode.equals("single")) {
            int[] chosen = manager.computerPlay();
            onClick(findViewById(buttons[chosen[0]][chosen[1]].getId()));
        }
    }

    /**
     * Returns the coordinate of the specified button.
     *
     * @param btn the button
     * @return coordinates of button
     */
    private int[] identifier(Button btn) {
        int[] coord = new int[2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (btn.getId() == buttons[i][j].getId()) {
                    coord[0] = i;
                    coord[1] = j;
                }
            }
        }
        return coord;
    }


    /**
     * Displaying the appropriate message when game is over
     */
    private void announcement() {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        updatePointsText();
        log = new ArrayList<>();
        clearScreen();
    }

    /**
     * Updating points after every round
     */
    @SuppressLint("SetTextI18n")
    private void updatePointsText() {
        p1Score.setText("You: " + manager.points.get("p1"));
        p2Score.setText("Player 2: " + manager.points.get("p2"));
    }

    /**
     * Clears the screen and resets the board.
     */
    private void clearScreen() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        manager.resetBoard();
    }


    /**
     * Checking for a win after every round
     */
    private void winActivities() {
        if (manager.checkHorizontals() || manager.checkDiagonals() ) {
            if (manager.p1Turn) {
                message = "Player 1 wins!";
                manager.points.put("p1", manager.points.get("p1") + 1);
            } else {
                message = "Player 2 wins!";
                manager.points.put("p2", manager.points.get("p2") + 1);
            }
            announcement();
        }
        else if (manager.roundCount >= 9){
            message = "Draw";
            announcement();
        }
        else {
            manager.p1Turn = !manager.p1Turn;
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", manager.roundCount);
        outState.putInt("p1Points", manager.points.get("p1"));
        outState.putInt("p2Points", manager.points.get("p2"));
        outState.putBoolean("p1Turn", manager.p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        manager.roundCount = savedInstanceState.getInt("roundCount");
        manager.points.put("p1", savedInstanceState.getInt("p1Points"));
        manager.points.put("p2", savedInstanceState.getInt("p2Points"));
        manager.p1Turn = savedInstanceState.getBoolean("p1Turn");
    }
}