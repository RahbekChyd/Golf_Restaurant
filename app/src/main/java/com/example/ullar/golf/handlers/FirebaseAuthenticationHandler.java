package com.example.ullar.golf.handlers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class FirebaseAuthenticationHandler {

    private AuthenticationHandlerStateChangedListener authenticationHandlerStateChangedListener;
    private static final String NOT_LOGGED_IN = "User not logged in";

    private FirebaseAuth.AuthStateListener authenticationStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                authenticationHandlerStateChangedListener.onSuccess(user);
            } else {
                authenticationHandlerStateChangedListener.onFailure(NOT_LOGGED_IN);
            }
        }
    };

    public FirebaseAuthenticationHandler(AuthenticationHandlerStateChangedListener authenticationHandlerStateChangedListener) {
        this.authenticationHandlerStateChangedListener = authenticationHandlerStateChangedListener;
    }

    private static FirebaseAuth getFirebaseAuthInstance () {
        return FirebaseAuth.getInstance();
    }

    public static FirebaseUser getCurrentUser () {
        return getFirebaseAuthInstance().getCurrentUser();
    }

    public void attempSignIn() {
        getFirebaseAuthInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    authenticationHandlerStateChangedListener.onSuccess(getFirebaseAuthInstance().getCurrentUser());
                }else {
                    authenticationHandlerStateChangedListener.onFailure("Authentication failed.");
                }
            }
        });
    }

    public static Boolean isThisAdmin() {
        if (getFirebaseAuthInstance().getCurrentUser().getEmail() == null) {}
        else if (getFirebaseAuthInstance().getCurrentUser().getEmail().equals("ho@staff.dk")) { return true; }
        return false;
    }

    public static void signOut(){
        getFirebaseAuthInstance().signOut();
    }

    public void attempSignInAdmin(String email, String password) {
        getFirebaseAuthInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    authenticationHandlerStateChangedListener.onSuccess(getFirebaseAuthInstance().getCurrentUser());
                }else {
                    authenticationHandlerStateChangedListener.onFailure("Authentication failed.");
                }
            }
        });
    }


    public void startListening() {
        getFirebaseAuthInstance().addAuthStateListener(authenticationStateListener);
    }

    public void stopListening() {
        if (getFirebaseAuthInstance() != null) {
            getFirebaseAuthInstance().removeAuthStateListener(authenticationStateListener);
        }
    }

    public interface AuthenticationHandlerStateChangedListener {
        void onSuccess(FirebaseUser user);
        void onFailure(String message);
    }
}