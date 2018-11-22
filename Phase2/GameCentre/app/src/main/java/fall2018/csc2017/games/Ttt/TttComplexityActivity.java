package fall2018.csc2017.games.Ttt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import fall2018.csc2017.games.R;

public class TttComplexityActivity extends AppCompatActivity {

    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt_complexity);
        setupModeButtons();

    }

    private void setupModeButtons() {
        Button singleButton = findViewById(R.id.button_single);
        Button multiButton = findViewById(R.id.button_multi);

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 1;
                switchToGame();

            }
        });

        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = 2;
                switchToGame();
            }
        });

    }


    private void switchToGame() {
        Intent tmp = new Intent(this, TttActivity.class);
        tmp.putExtra("mode", mode);
        startActivity(tmp);
    }


}
