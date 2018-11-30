package fall2018.csc2017.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * A placeholder fragment containing a simple view.
 */
public class ScoreFragment extends Fragment implements Observer {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
     private static final String ARG_SECTION_NUMBER = "section_number";

     /**
     * The display for this page's difficulty label
     */
     TextView difficulty;
     /**
     * The display for the user's previous high scores for this game, for this difficulty.
     */
     TextView local;
        /**
         * The display for all user's previous high scores for this game, for this difficulty.
         */
        TextView global;

        /**
         * The scoreboardManager
         */
        ScoreboardManager scoreManager;

        /**
         * A list for for the user's previous high scores for this game, for this difficulty.
         */
        List<Score> prevScoreList = new ArrayList<>();
        /**
         * A list for all user's previous high scores for this game, for this difficulty.
         */
        List<Score> topScoreList = new ArrayList<>();

        public ScoreFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ScoreFragment newInstance(int sectionNumber, Game game) {
            ScoreFragment fragment = new ScoreFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putSerializable("GAME", (Serializable) game);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
            difficulty = (TextView) rootView.findViewById(R.id.difficulty);
            local = (TextView) rootView.findViewById(R.id.local);
            global = (TextView) rootView.findViewById(R.id.global);

            assert getArguments() != null;
            Game game = (Game) getArguments().getSerializable("GAME");

            assert game != null;
            difficulty.setText(game.getDifficulty());
            scoreManager = new ScoreboardManager(game);
            scoreManager.addObserver(this);

            prevScoreList = scoreManager.getSortedUserScores();
            topScoreList = scoreManager.getTopScores();

            return rootView;
        }

        @Override
        public void update(Observable observable, Object o) {
            if (o == ScoreboardManager.ADDED_USER_SCORE) {
                prevScoreList = scoreManager.getSortedUserScores();
                topScoreList = scoreManager.getTopScores();
            }
            if (o == ScoreboardManager.RETRIEVED_SCORES) {
                local.setText(FinishedActivity.userConverter(prevScoreList));
            }
            if (o == ScoreboardManager.RETRIEVED_TOP_SCORES) {
                global.setText(FinishedActivity.overallConverter(topScoreList));
            }
        }

}