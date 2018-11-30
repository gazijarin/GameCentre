package fall2018.csc2017.games;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
 * A an activity for signing up a user
 */
public class SignupActivity extends AuthenticationActivity {
    /**
     * a TAG string for logging
     */
    private static final String TAG = "SignupActivity";
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
     * The text for reentering password
     */
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    /**
     * The text for entering username
     */
    @BindView(R.id.input_username)
    EditText _usernameText;
    /**
     * The text switching to log in
     */
    @BindView(R.id.link_login)
    TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        _enterButton = findViewById(R.id.btn_signup);
        _enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    /**
     * Attempts to sign in the user after the sign in button has been pressed
     */
    public void signUp() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onFailure();
            return;
        }

        _enterButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String username = _usernameText.getText().toString();

        login.createAccount(email, password, username);
    }

    /**
     * Validates the data inputted in the fields
     *
     * @return whether the input is valid or not
     */
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();
        String username = _usernameText.getText().toString();

        if (username.isEmpty() || username.contains("@") || username.contains("_")) {
            _emailText.setError("Username can not be empty or contain a '@' or '_' character");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            _passwordText.setError("between 6 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 ||
                reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }

}