package fall2018.csc2017.games;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* Android login, signup and authentication activities adapted from Kam Low's github
* <github.com/sourcey/materiallogindemo
* ?fbclid=IwAR3vz3GG7AbyZzuz8Uswi2lfYSMTBDMLzQzy3ss4v0OsNvpcsKWy_C44fn0>
* Retrieved: October 2018
* Title: Android login and signup demo with material design
* Author: Kam Low
* Date of Last commit from author: 2015
*/

/**
 * An activity to log in a user
 */
public class LoginActivity extends AuthenticationActivity {
    /**
     * a TAG string for logging
     */
    private static final String TAG = "LoginActivity";

    /**
     * The text for entering email
     */
    @BindView(R.id.input_email)
    EditText _emailText;
    /**
     * The text for entering password
     */
    @BindView(R.id.input_password)
    EditText _passwordText;
    /**
     * The text switching to sign up
     */
    @BindView(R.id.link_signup)
    TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _enterButton = findViewById(R.id.btn_login);
        _enterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Attempts user log in, after the log in button is pressed
     */
    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onFailure();
            return;
        }

        _enterButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        login.signIn(email, password);

    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    /**
     * makes sure the input in the fields is valid
     *
     * @return whether input is valid
     */
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
