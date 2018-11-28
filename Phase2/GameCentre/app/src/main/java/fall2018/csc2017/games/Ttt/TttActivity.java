package fall2018.csc2017.games.Ttt;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.ObjectOutputStream;
import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;

//todo: AUTO SAVE, SAVE AND LOAD
//todo:  SCOREBOARD INTEGRATION
//todo: fix the yellows



public class TttActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * Player1 and 2 score displays on overhead
     */
    private TextView textViewp1;
    private TextView textViewp2;

    /**
     * Handler to track time
     */
    private Handler handler;

    /**
     * The game manager
     */
    TttManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt);

        textViewp1 = findViewById(R.id.text_view_p1);
        textViewp2 = findViewById(R.id.text_view_p2);
        Bundle b = getIntent().getExtras();
        manager = new TttManager(b.getString("mode"));

        buttonInitializer();

        Button buttonUndo = findViewById(R.id.button_undo);
        buttonUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.undo();
            }
        });

    }

    /**
     * Setting up buttons for the UI
     */
    private void buttonInitializer(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                manager.buttons[i][j] = findViewById(resID);
                manager.buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (manager.p1Turn) {
            ((Button) v).setText("X");

        } else {
            ((Button) v).setText("O");
        }

        manager.log.add((Button) v);
        manager.roundCount++;
        winActivities();


        if (!manager.p1Turn && manager.mode.equals("single")) {
            onClick(findViewById(manager.easyMode().getId()));
        }
    }

    /**
     * Displaying the appropriate message when game is over
     */
    private void announcement() {
        Toast.makeText(this, manager.winMessage, Toast.LENGTH_SHORT).show();
        updatePointsText();
        manager.resetBoard();
    }

    /**
     * Updating points after every round
     */
    private void updatePointsText() {
        textViewp1.setText("Player 1: " + manager.p1Points);
        textViewp2.setText("Player 2: " + manager.p2Points);
    }


    /**
     * Checking for a win after every round
     */
    private void winActivities() {
        if (manager.checkForWin() || manager.roundCount >= 9) {

            if (manager.roundCount >= 9) {
                manager.winMessage = "Draw";
            } else if (manager.p1Turn) {
                manager.winMessage = "Player 1 wins!";
                manager.p1Points++;
            } else {
                manager.winMessage = "Player 2 wins!";
                manager.p2Points++;
            }
            announcement();

        } else {
            manager.p1Turn = !manager.p1Turn;
        }

    }

    /**
     * Saves the game to a file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(manager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler();
        autoSaveTimer.run();
    }


    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(autoSaveTimer);
    }

    /**
     * Auto saving the game
     */
    public Runnable autoSaveTimer = new Runnable() {
        public void run() {
            saveToFile(GameScreenActivity.SAVE_FILENAME);

            handler.postDelayed(this, 30 * 1000);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", manager.roundCount);
        outState.putInt("p1Points", manager.p1Points);
        outState.putInt("p2Points", manager.p2Points);
        outState.putBoolean("p1Turn", manager.p1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        manager.roundCount = savedInstanceState.getInt("roundCount");
        manager.p1Points = savedInstanceState.getInt("p1Points");
        manager.p2Points = savedInstanceState.getInt("p2Points");
        manager.p1Turn = savedInstanceState.getBoolean("p1Turn");
    }
}