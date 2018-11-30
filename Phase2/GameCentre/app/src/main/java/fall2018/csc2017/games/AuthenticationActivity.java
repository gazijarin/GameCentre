package fall2018.csc2017.games;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Observable;
import java.util.Observer;

/**
 * An activity that involves authenticating/registering a user
 */
public class AuthenticationActivity extends AppCompatActivity implements Observer {
    /**
     * The progress dialog for this activity
     */
    protected ProgressDialog progressDialog;
    /**
     * The button to authenticate
     */
    protected Button _enterButton;
    /**
     * This class's authentication manager
     */
    protected AuthenticationManager login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login = new AuthenticationManager();
        login.addObserver(this);
    }

    /**
     * Updates the screen to bring it to the game select screen
     *
     * @param user the username of the current user.
     */
    private void updateUI(String user) {
        Intent tmp = new Intent(this, GameSelectActivity.class);
        tmp.putExtra("USERNAME", user);
        startActivity(tmp);
    }

    /**
     * Perform the necessary operations should authentication succeed
     *
     * @param username the username of user that just authenticated
     */
    protected void onSuccess(String username) {
        _enterButton.setEnabled(true);
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        updateUI(username);
        finish();
    }

    /**
     * Perform the necessary operations should authentication fail
     */
    protected void onFailure() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        _enterButton.setEnabled(true);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            onSuccess((String) arg);
        } else {
            onFailure();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        //start with signing out for login testing purposes
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        String user = login.getUser();
        if (user != null) {
            updateUI(user);
        }
    }
}
