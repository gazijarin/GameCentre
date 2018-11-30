package fall2018.csc2017.games;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
     * This fragment's section number;
     */
    int sectionNumber;

        /**
         * The scoreboardManager
         */
        ScoreboardManager scoreManager;

    /**
     * The game associated with this fragment.
     */
    Game game;

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
            args.putInt("NUMBER", sectionNumber);
            args.putSerializable("GAME", (Serializable) game);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            assert getArguments() != null;
            sectionNumber = getArguments().getInt("NUMBER");
            game = (Game) getArguments().getSerializable("GAME");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
            difficulty = (TextView) rootView.findViewById(R.id.difficulty);
            local = (TextView) rootView.findViewById(R.id.local);
            global = (TextView) rootView.findViewById(R.id.global);

            assert getArguments() != null;

            switch(sectionNumber){
                case 1:
                    game.setDifficulty("easy");
                    break;
                case 2:
                    game.setDifficulty("medium");
                    break;
                case 3:
                    game.setDifficulty("hard");
                    break;
            }

            difficulty.setText(game.getDifficulty());
            scoreManager = new ScoreboardManager(game);
            scoreManager.addObserver(this);

            prevScoreList = scoreManager.getSortedUserScores();
            topScoreList = scoreManager.getTopScores();

            return rootView;
        }

        @Override
        public void update(Observable observable, Object o) {
            if (o == ScoreboardManager.RETRIEVED_SCORES) {
                local.setText(FinishedActivity.userConverter(prevScoreList));
            }
            if (o == ScoreboardManager.RETRIEVED_TOP_SCORES) {
                global.setText(FinishedActivity.overallConverter(topScoreList));
            }
        }

        public void setGame(Game game){
           this.game = game;
        }

}