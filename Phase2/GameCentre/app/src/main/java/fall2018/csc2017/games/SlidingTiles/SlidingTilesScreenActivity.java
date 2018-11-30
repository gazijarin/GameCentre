package fall2018.csc2017.games.SlidingTiles;

import android.content.Intent;
import android.os.Bundle;

import fall2018.csc2017.games.GameScreenActivity;
import fall2018.csc2017.games.R;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesScreenActivity extends GameScreenActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        game = new BoardManager(4);
        String username = getIntent().getStringExtra("USERNAME");
        SAVE_FILENAME = username + "_" + game.getGameId() + "_save_file.ser";

        super.onCreate(savedInstanceState);

        //gameImage.setImageResource(R.drawable.sliding_tiles_title_image);
        gameDescription.setText(getResources().getString(R.string.sliding_tiles_description));

    }

    @Override
    protected void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesActivity.class);
        saveToFile(SAVE_FILENAME);
        startActivity(tmp);
    }

    @Override
    protected void switchToNewGame() {
        Intent tmp = new Intent(this, SlidingTilesComplexityActivity.class);
        startActivity(tmp);
    }
}
