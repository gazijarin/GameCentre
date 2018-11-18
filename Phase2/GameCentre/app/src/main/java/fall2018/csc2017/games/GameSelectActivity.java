package fall2018.csc2017.games;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import fall2018.csc2017.games.SlidingTiles.GameScreenActivity;
import fall2018.csc2017.slidingtiles.R;
import fall2018.csc2017.games.Ttt.TttActivity;

/**
 * An activity to select which game to play
 */
public class GameSelectActivity extends AppCompatActivity {

    /**
     * Sets up the sliding_tiles_button
     */
    @BindView(R.id.sliding_tiles_button)
    Button sliding_tiles_button;

    @BindView(R.id.ttt_button)
    Button ttt_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select);
        ButterKnife.bind(this);
        sliding_tiles_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame();
            }
        });

        ttt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTTTGame();
            }
        });


    }

    /**
     * Switches activity to the Sliding Tiles game
     */
    public void goToGame() {
        Intent tmp = new Intent(this, GameScreenActivity.class);
        tmp.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
        startActivity(tmp);
    }


    // sets up tic tac toe game
    public  void goToTTTGame(){
        Intent tmp = new Intent(this, TttActivity.class);
        tmp.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
        startActivity(tmp);
    }

}
