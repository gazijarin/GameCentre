package fall2018.csc2017.games;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * An activity to display once the user has completed a game
 */
public class FinishedActivity extends AppCompatActivity implements Observer {
    /**
     * The display for the score achieved.
     */
    TextView currentScore;
    /**
     * The display for the user's previous high scores for this game, for this difficulty.
     */
    TextView prevScores;
    /**
     * The display for all user's previous high scores for this game, for this difficulty.
     */
    TextView topScores;
    /**
     * A Game object for the current game.
     */
    Game currentGame;
    /**
     * A list for for the user's previous high scores for this game, for this difficulty.
     */
    List<Score> prevScoreList = new ArrayList<>();
    /**
     * A list for all user's previous high scores for this game, for this difficulty.
     */
    List<Score> topScoreList = new ArrayList<>();
    /**
     * A ScoreboardManager to access scores.
     */
    ScoreboardManager scoreManager;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        int lastScore = getIntent().getIntExtra("SCORE", 0);
        currentGame = (Game) getIntent().getSerializableExtra("GAME");

        scoreManager = new ScoreboardManager(currentGame);
        scoreManager.addObserver(this);
        scoreManager.addUserScore(lastScore);

        currentScore = findViewById(R.id.current_score);
        prevScores = findViewById(R.id.prev_scores);
        topScores = findViewById(R.id.top_scores);

        currentScore.setText(lastScore + "");

        setupBackButton();
    }

    /**
     * Sets up the back button to bring it back to the menu
     */
    private void setupBackButton() {
        Button backButton = findViewById(R.id.BackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToGameSelect();
            }
        });
    }

    private void switchToGameSelect() {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        int end = GameScreenActivity.SAVE_FILENAME.indexOf("_");
        String username = GameScreenActivity.SAVE_FILENAME.substring(0, end);
        tmp.putExtra("USERNAME", username);
        startActivity(tmp);
    }


    @Override
    public void update(Observable observable, Object o) {
        if (o == ScoreboardManager.ADDED_USER_SCORE) {
            prevScoreList = scoreManager.getSortedUserScores();
            topScoreList = scoreManager.getTopScores();
        }
        if (o == ScoreboardManager.RETRIEVED_SCORES) {
            prevScores.setText(userConverter(prevScoreList));
        }
        if (o == ScoreboardManager.RETRIEVED_TOP_SCORES) {
            topScores.setText(overallConverter(topScoreList));
        }
    }

    /**
     * Creates a nice table to display overall top scores
     *
     * @param item the list of overall top scores
     * @return a string that is the overall table of scores
     */
    public static String overallConverter(List<Score> item) {
        int counter = 1;
        StringBuilder topScoresTable = new StringBuilder("Overall Top Scorers\n\n Rank    User    " +
                "Score + \n");

        for (Score m : item) {
            topScoresTable.append(counter).append("        ").append(m.getUsername()).append("         ")
                    .append(m.getScore()).append("\n");
            counter += 1;
        }

        return topScoresTable.toString();

    }

    /**
     * Creates a nice table to display the current user's top scores
     *
     * @param item the list of the current user's top scores
     * @return a string that is a table of the current user's top scores
     */
    public static String userConverter(List<Score> item) {
        StringBuilder topScoresTable = new StringBuilder("Your Top Scores\n\n Rank   Score + \n");

        for (int i = 0; i < item.size(); i++) {
            topScoresTable = new StringBuilder(" " + topScoresTable + (i + 1) + "          " +
                    Integer.toString(item.get(i).getScore()) + "\n");
        }
        return topScoresTable.toString();

    }

    //empty because we do not want the user to be able go go back to the completed game
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
