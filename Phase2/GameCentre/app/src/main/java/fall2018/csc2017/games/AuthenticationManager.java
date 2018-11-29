package fall2018.csc2017.games;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Observable;

/**
 * A class that contains several methods that communicate with Firebase to authenticate and
 * register users
 */
public class AuthenticationManager extends Observable {
    /**
     * A FirebaseAuth object, involved with user authentication
     */
    private FirebaseAuth mAuth;

    /**
     * This class's tag, used for logging
     */
    private static final String TAG = "AuthenticationManager";

    /**
     * Initializes the authentication manager
     */
    AuthenticationManager() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Creates a user account with Firebase
     *
     * @param email    the user's email
     * @param password the user's password
     * @param username the user's username
     */
    void createAccount(String email, String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                setUsername(user, username);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            setChanged();
                            notifyObservers(false);
                        }
                    }
                });
    }

    /**
     * Sets the username of a user in Firebase
     *
     * @param user     the Firebase user who's name needs to be set
     * @param username the username to set to
     */
    private void setUsername(FirebaseUser user, String username) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        user.updateProfile(profileUpdates);
        //adds the user to the database
        ScoreboardManager dm = new ScoreboardManager(null);
        dm.addUser(username);
        setChanged();
        notifyObservers(username);
    }

    /**
     * Returns the current user's username
     *
     * @return the current user's username
     */
    //warning is suppressed because they are wrong. This method cannot be made private.
    @SuppressWarnings("WeakerAccess")
    public String getUser() {
        if (mAuth.getCurrentUser() != null)
            return mAuth.getCurrentUser().getDisplayName();
        else
            return null;
    }

    /**
     * Signs in a user
     *
     * @param email    the user's email
     * @param password the user's password
     */
    //warning is suppressed because they are wrong. This method cannot be made private.
    @SuppressWarnings("WeakerAccess")
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            setChanged();
                            notifyObservers(getUser());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            setChanged();
                            notifyObservers(false);
                        }

                        // ...
                    }
                });
    }

}
