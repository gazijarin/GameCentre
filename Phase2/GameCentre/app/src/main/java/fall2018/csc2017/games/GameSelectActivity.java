package fall2018.csc2017.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import fall2018.csc2017.games.Hangman.HangmanScreenActivity;
import fall2018.csc2017.games.SlidingTiles.SlidingTilesScreenActivity;
import fall2018.csc2017.games.Ttt.TttScreenActivity;

/**
 * An activity to select which game to play
 */
public class GameSelectActivity extends AppCompatActivity {

    /**
     * Sets up the game buttons.
     */
    @BindView(R.id.sliding_tiles_button)
    Button sliding_tiles_button;

    @BindView(R.id.ttt_button)
    Button ttt_button;

    @BindView(R.id.hangman_button)
    Button hangman_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);
        ButterKnife.bind(this);

        sliding_tiles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame("slidingtiles");
            }
        });

        ttt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame("Ttt");
            }
        });

        hangman_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame("hangman");
            }
        });


    }

    /**
     * Progresses to the game activity specified
     * @param gameid the id of the game that the user wishes to switch to
     */
    public void goToGame(String gameid) {

        Intent tmp;

        switch (gameid) {
            case "slidingtiles":
                tmp = new Intent(this, SlidingTilesScreenActivity.class);
                break;
            case "hangman":
                tmp = new Intent(this, HangmanScreenActivity.class);
                break;
            case "Ttt":
                //tmp = new Intent(this, TttComplexityActivity.class);
                tmp = new Intent(this, TttScreenActivity.class);
                break;
            default:
                tmp = new Intent(this, SlidingTilesScreenActivity.class);
                break;
        }

        tmp.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
        startActivity(tmp);
    }


    //empty because we do not want the user to be able go go back to signing in
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
