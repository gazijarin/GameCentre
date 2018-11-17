package fall2018.csc2017.game;

/*
 * This code is from StackOverflow user Calvin
 * Original code found here: https://stackoverflow.com/questions/7727919/creating-a-fixed-size-stack
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import fall2018.csc2017.game.SlidingTiles.GameScreenActivity;

/**
 * A stack that always has a fixed max size
 * Removes the older item when adding to a full size FixedStack
 * Other works as a regular stack
 *
 * @param <T>, the type of objects to be stored in the FixedStack
 */
public class FixedStack<T> extends Stack<T> {
    /**
     * The maximum size of this FixedStack
     */
    private int maxSize;

    /**
     * Initializes this FixedStack
     *
     * @param size the size of this FixedStack
     */
    public FixedStack(int size) {
        super();
        this.maxSize = size;
    }

    @Override
    public T push(T object) {
        if (maxSize == 0) {
            return null;
        }
        while (this.size() >= maxSize) {
            this.remove(0);
        }
        return super.push(object);
    }

    /**
     * An activity to select which game to play
     */
    public static class GameSelectActivity extends AppCompatActivity {

        /**
         * Sets up the sliding_tiles_button
         */
        @BindView(R.id.sliding_tiles_button)
        Button sliding_tiles_button;

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

        }

        /**
         * Switches activity to the Sliding Tiles game
         */
        public void goToGame() {
            Intent tmp = new Intent(this, GameScreenActivity.class);
            tmp.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
            startActivity(tmp);
        }

    }
}
