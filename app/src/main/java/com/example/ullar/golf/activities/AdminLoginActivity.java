package com.example.ullar.golf.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ullar.golf.handlers.FirebaseAuthenticationHandler;
import com.example.ullar.golf.R;
import com.google.firebase.auth.FirebaseUser;

public class AdminLoginActivity extends AppCompatActivity implements FirebaseAuthenticationHandler.AuthenticationHandlerStateChangedListener {
    private FirebaseAuthenticationHandler firebaseAuthenticationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        firebaseAuthenticationHandler = new FirebaseAuthenticationHandler(this);
    }

    public void signIn(View view) {
        EditText emailInput = (EditText) findViewById(R.id.email_input_editText);
        EditText passwordInput = (EditText) findViewById(R.id.password_input_editText);
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        firebaseAuthenticationHandler.attempSignInAdmin(email, password);
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuthenticationHandler.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuthenticationHandler.stopListening();
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        Log.d("UserSigned", "Signed in");
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
