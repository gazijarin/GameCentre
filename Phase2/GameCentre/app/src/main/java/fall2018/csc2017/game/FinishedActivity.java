package fall2018.csc2017.game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * An activity to display once the user has completed a game
 */
public class FinishedActivity extends AppCompatActivity implements Observer {

    /**
     * The display for the score achieved
     */
    TextView currentScore;
    /**
     * the display for the user's previous high scores for this game, for this difficulty
     */
    TextView prevScores;
    /**
     * the display for all user's previous high scores for this game, for this difficulty
     */
    TextView topScores;
    /**
     * a Game object for the current game
     */
    Game currentGame;

    /**
     * a list for for the user's previous high scores for this game, for this difficulty
     */
    List<Integer> prevScoreList = new ArrayList<>();
    /**
     * a list for all user's previous high scores for this game, for this difficulty
     */
    List<String[]> topScoreList = new ArrayList<>();
    /**
     * a ScoreboardManager to access scores
     */
    ScoreboardManager dm;

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished);
        int lastScore = getIntent().getIntExtra("SCORE", 0);
        currentGame = (Game) getIntent().getSerializableExtra("GAME");

        dm = new ScoreboardManager(currentGame);
        dm.addObserver(this);
        dm.addUserScore(lastScore);

        currentScore = findViewById(R.id.current_score);
        prevScores = findViewById(R.id.prev_scores);
        topScores = findViewById(R.id.top_scores);

        currentScore.setText(lastScore + "");
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o == ScoreboardManager.ADDED_USER_SCORE) {
            prevScoreList = dm.getSortedUserScores();
            topScoreList = dm.getTopScores();
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
    private String overallConverter(List<String[]> item) {
        int counter = 1;
        StringBuilder topScoresTable = new StringBuilder("Overall Top Scorers\n\n Rank    User    " +
                "Score + \n");

        for (String[] m : item) {
            topScoresTable.append(counter).append("        ").append(m[0]).append("         ")
                    .append(m[1]).append("\n");
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
    private String userConverter(List<Integer> item) {
        StringBuilder topScoresTable = new StringBuilder("Your Top Scores\n\n Rank   Score + \n");

        for (int i = 0; i < item.size(); i++) {
            topScoresTable = new StringBuilder(" " + topScoresTable + (i + 1) + "          " +
                    Integer.toString(item.get(i)) + "\n");
        }
        return topScoresTable.toString();

    }

    //empty because we do not want the user to be able go go back to the completed game
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * a class that communicates with firebase to send and fetch scores
     */
    public static class ScoreboardManager extends Observable {

        /**
         * the tag for logging
         */
        private static final String TAG = "ScoreboardManager";
        /**
         * A FirebaseDatabase to access Firebase database
         */
        private FirebaseDatabase mDatabase;
        /**
         * the current user's username
         */
        private String currentUser;
        /**
         * the current Game
         */
        private Game currentGame;
        /**
         * the amount of scores to fetch when retrieving user scores
         */
        public static final int NUM_TOP_SCORES = 5;

        /**
         * The code signalling that an error has occured
         */
        private static final Integer ERROR = null;

        /**
         * The code signalling that adding a user score has completed
         */
        public static final Integer ADDED_USER_SCORE = 0;
        /**
         * The code signalling that retrieving the current user's
         * top scores for the current game has completed
         */
        public static final Integer RETRIEVED_SCORES = 1;
        /**
         * The code signalling that retrieving all users'
         * top scores for the current game has completed
         */
        public static final Integer RETRIEVED_TOP_SCORES = 2;

        /**
         * Creates a new ScoreboardManager
         *
         * @param currentGame the Game being played
         */
        public ScoreboardManager(Game currentGame) {
            mDatabase = FirebaseDatabase.getInstance();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            this.currentUser = Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName();
            this.currentGame = currentGame;
        }

        /**
         * Adds a single user to the database
         *
         * @param username username to add to the database
         */
        void addUser(String username) {
            DatabaseReference myRef = mDatabase.getReference("users").push();
            myRef.setValue(username);
        }

        /**
         * Adds a user score for the current user for the current game
         *
         * @param score the score the user got
         */
        public void addUserScore(final int score) {
            DatabaseReference myRef = mDatabase.getReference(
                    "scores/" + currentGame.getGameId() + "/"
                            + currentGame.getDifficulty() + "/" + currentUser);
            myRef.push().setValue(score);


            final DatabaseReference topScoresRef = mDatabase.getReference(
                    "scores/" + currentGame.getGameId() +
                            "/" + currentGame.getDifficulty() + "/@topscores@");

            topScoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int worstScore = score;
                    String worstKey = null;
                    int count = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        count++;
                        int currentScore = ((Long) Objects.requireNonNull(snapshot.child("score").
                                getValue())).intValue();
                        if ((currentScore < worstScore && currentGame.highTopScore()) ||
                                (currentScore > worstScore && !currentGame.highTopScore())) {
                            worstScore = currentScore;
                            worstKey = snapshot.getKey();
                        }
                    }

                    if (count < NUM_TOP_SCORES) {
                        String newKey = topScoresRef.push().getKey();
                        assert newKey != null;
                        topScoresRef.child(newKey).child("username").setValue(currentUser);
                        topScoresRef.child(newKey).child("score").setValue(score);
                    } else if (worstKey != null) {
                        topScoresRef.child(worstKey).child("username").setValue(currentUser);
                        topScoresRef.child(worstKey).child("score").setValue(score);
                    }

                    setChanged();
                    notifyObservers(ADDED_USER_SCORE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "addUserScore: Unsucessful");
                    setChanged();
                    notifyObservers(ERROR);
                }
            });


        }

        /**
         * Retrieves sorted user scores for the current user for the current game
         *
         * @return A list of sorted scores
         */
        public List<Integer> getSortedUserScores() {
            final List<Integer> scores = new ArrayList<>();
            DatabaseReference myRef = mDatabase.getReference(
                    "scores/" + currentGame.getGameId() + "/"
                            + currentGame.getDifficulty() + "/" + currentUser);
            myRef.orderByValue().limitToFirst(NUM_TOP_SCORES).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (!currentGame.highTopScore()) {
                            scores.add(((Long) Objects.requireNonNull(snapshot.getValue())).intValue());
                        } else { //add to the first position to reverse list
                            scores.add(0, ((Long) Objects.requireNonNull(snapshot.getValue()))
                                    .intValue());
                        }
                    }
                    setChanged();
                    notifyObservers(RETRIEVED_SCORES);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "getSortedUserScores: Unsucessful");
                    setChanged();
                    notifyObservers(ERROR);
                }
            });
            return scores;
        }

        /**
         * Returns the sorted top scores for all users in the current game
         *
         * @return an ordered List of String arrays of size 2 in the form [username, score]
         */
        public List<String[]> getTopScores() {
            final List<String[]> scores = new ArrayList<>();

            final DatabaseReference topScoresRef = mDatabase.getReference(
                    "scores/" + currentGame.getGameId() + "/"
                            + currentGame.getDifficulty() + "/@topscores@");

            topScoresRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String username = (String) snapshot.child("username").getValue();
                        String score = (Objects.requireNonNull(snapshot.child("score").getValue())).
                                toString();
                        String[] info = new String[2];
                        info[0] = username;
                        info[1] = score;

                        if (!currentGame.highTopScore()) {
                            scores.add(info);
                        } else { //add to the first position to reverse list
                            scores.add(0, info);
                        }
                    }

                    setChanged();
                    notifyObservers(RETRIEVED_TOP_SCORES);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "getTopScores: Unsucessful");
                    setChanged();
                    notifyObservers(ERROR);
                }
            });
            return scores;
        }


    }
}
