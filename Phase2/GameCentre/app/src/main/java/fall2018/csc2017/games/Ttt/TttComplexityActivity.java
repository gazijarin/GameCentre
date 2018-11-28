package fall2018.csc2017.games.Ttt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import fall2018.csc2017.games.ComplexityActivity;
import fall2018.csc2017.games.R;

public class TttComplexityActivity extends ComplexityActivity {

    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupDifficultyButtons();
        game = new TttManager("double");
    }


    private void setupDifficultyButtons() {
        Button singlePlayer = findViewById(R.id.button);
        View obsolete = findViewById(R.id.button2);
        Button doublePlayer = findViewById(R.id.button3);
        obsolete.setVisibility(View.GONE);
        singlePlayer.setText("Single-Player");
        doublePlayer.setText("Multi-Player");

        singlePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("single");
                toastDifficulty("Single Player");
                mode = "single";
            }
        });


        doublePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.setDifficulty("double");
                toastDifficulty("Multi Player");
                mode = "double";
            }
        });
    }

    protected void switchToGame() {
        Intent tmp = new Intent(this, TttActivity.class);
        tmp.putExtra("mode", mode);
        startActivity(tmp);
    }

}