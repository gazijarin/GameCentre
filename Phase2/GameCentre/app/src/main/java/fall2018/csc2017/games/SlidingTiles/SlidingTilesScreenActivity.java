package fall2018.csc2017.games.SlidingTiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

        //todo get an image for sliding tiles
        //gameImage.setImageResource(R.drawable.sliding_tiles_title_image);
        gameDescription.setText(getResources().getString(R.string.sliding_tiles_description));

    }

    @Override
    protected void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(SAVE_FILENAME);
        startActivity(tmp);
    }

    @Override
    protected void switchToNewGame() {
        Intent tmp = new Intent(this, SlidingTilesComplexityActivity.class);
        startActivity(tmp);
    }
}
