package fall2018.csc2017.games.Ttt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import fall2018.csc2017.games.ComplexityActivity;
import fall2018.csc2017.games.R;

//ToDo: make it extend complexity activity

public class TttComplexityActivity extends AppCompatActivity {

    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt_complexity);
        setupModeButtons();
        setupNumUndoInput();
        setupStartButton();

    }

    private void setupModeButtons() {
        Button singleButton = findViewById(R.id.button_single);
        Button multiButton = findViewById(R.id.button_multi);

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "single";
            }
        });

        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "double";
            }
        });

    }

    public void setupStartButton() {
        Button startButton = findViewById(R.id.startButton2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }

    private void setupNumUndoInput() {
        EditText numUndos = findViewById(R.id.numUndos2);
        String text = 0 + "";
        numUndos.setText(text);
    }

    protected void switchToGame() {
        Intent tmp = new Intent(this, TttActivity.class);
        tmp.putExtra("mode", mode);
        startActivity(tmp);
    }

}