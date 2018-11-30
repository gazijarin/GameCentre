package fall2018.csc2017.games;

import android.support.annotation.NonNull;
import android.util.Log;

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

/**
 * a class that communicates with firebase to send and fetch scores
 */
class ScoreboardManager extends Observable {
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
    private static final int NUM_TOP_SCORES = 5;

    /**
     * The code signalling that an error has occured
     */
    private static final Integer ERROR = null;

    /**
     * The code signalling that adding a user score has completed
     */
    static final Integer ADDED_USER_SCORE = 0;
    /**
     * The code signalling that retrieving the current user's
     * top scores for the current game has completed
     */
    static final Integer RETRIEVED_SCORES = 1;
    /**
     * The code signalling that retrieving all users'
     * top scores for the current game has completed
     */
    static final Integer RETRIEVED_TOP_SCORES = 2;

    /**
     * Creates a new ScoreboardManager
     *
     * @param currentGame the Game being played
     */
    ScoreboardManager(Game currentGame) {
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
    void addUserScore(final int score) {
        final DatabaseReference topScoresRef = getDatabaseReference(score);

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

                setScoreValues(worstKey, count, topScoresRef, score);
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
     * Sets the score values for current user.
     *
     * @param worstKey     the worst key
     * @param count        the count
     * @param topScoresRef the top scores reference
     * @param score        the score
     */
    private void setScoreValues(String worstKey, int count, DatabaseReference topScoresRef, int score) {
        if (count < NUM_TOP_SCORES) {
            String newKey = topScoresRef.push().getKey();
            assert newKey != null;
            topScoresRef.child(newKey).child("username").setValue(currentUser);
            topScoresRef.child(newKey).child("score").setValue(score);
        } else if (worstKey != null) {
            topScoresRef.child(worstKey).child("username").setValue(currentUser);
            topScoresRef.child(worstKey).child("score").setValue(score);
        }
    }

    /**
     * Returns the database reference.
     *
     * @param score the score
     * @return the database reference
     */
    private DatabaseReference getDatabaseReference(int score) {
        DatabaseReference myRef = mDatabase.getReference(
                "scores/" + currentGame.getGameId() + "/"
                        + currentGame.getDifficulty() + "/" + currentUser);
        myRef.push().setValue(score);


        return mDatabase.getReference(
                "scores/" + currentGame.getGameId() +
                        "/" + currentGame.getDifficulty() + "/@topscores@");
    }

    /**
     * Retrieves sorted user scores for the current user for the current game
     *
     * @return A list of sorted scores
     */
    List<Score> getSortedUserScores() {
        final List<Score> scores = new ArrayList<>();
        DatabaseReference myRef = mDatabase.getReference(
                "scores/" + currentGame.getGameId() + "/"
                        + currentGame.getDifficulty() + "/" + currentUser);
        myRef.orderByValue().limitToFirst(NUM_TOP_SCORES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (!currentGame.highTopScore()) {
                        scores.add(new Score(((Long) Objects.requireNonNull(snapshot.getValue())).intValue()));
                    } else { //add to the first position to reverse list
                        scores.add(0, new Score(((Long) Objects.requireNonNull(snapshot.getValue()))
                                .intValue()));
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
     * @return an ordered List of Scores
     */
    List<Score> getTopScores() {
        final List<Score> scores = new ArrayList<>();

        final DatabaseReference topScoresRef = mDatabase.getReference(
                "scores/" + currentGame.getGameId() + "/"
                        + currentGame.getDifficulty() + "/@topscores@");

        topScoresRef.orderByChild("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateScoresData(dataSnapshot, scores);

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

    /**
     * Updates scores and changes data according to recent top scores.
     *
     * @param dataSnapshot the data snapshot
     * @param scores       scores
     */
    private void updateScoresData(@NonNull DataSnapshot dataSnapshot, List<Score> scores) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String username = (String) snapshot.child("username").getValue();
            int score = ((Long) Objects.requireNonNull(snapshot.child("score").
                    getValue())).intValue();
            Score info = new Score(username, score);

            if (!currentGame.highTopScore()) {
                scores.add(info);
            } else { //add to the first position to reverse list
                scores.add(0, info);
            }
        }
    }


}