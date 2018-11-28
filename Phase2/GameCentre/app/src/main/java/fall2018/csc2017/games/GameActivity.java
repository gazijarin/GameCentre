package fall2018.csc2017.games;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class GameActivity extends FileActivity<Game> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Handler handler;

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(autoSaveTimer);
    }

    @Override
    protected void onStart() {
        super.onStart();
        handler = new Handler();
        autoSaveTimer.run();
    }

    /**
     * Runnable autoSaveTimer that saves the game every 30 seconds.
     */
    public Runnable autoSaveTimer = new Runnable() {
        public void run() {
            saveToFile(GameScreenActivity.SAVE_FILENAME);
            makeToastAutoSavedText();
            handler.postDelayed(this, 30 * 1000);
        }
    };

    /**
     * Display that a game was autosaved successfully.
     */
    private void makeToastAutoSavedText() {
        Toast.makeText(this, "Auto Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveToFile(GameScreenActivity.SAVE_FILENAME);
    }

}
