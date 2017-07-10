package com.example.ullar.golf.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ullar.golf.handlers.FirebaseAuthenticationHandler;
import com.example.ullar.golf.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void signInGuest(View view) {
        Intent intent = new Intent(this, GuestActivity.class);
        startActivity(intent);
    }

    public void signInStaff(View view) {
        if (FirebaseAuthenticationHandler.getCurrentUser() != null) {
            if (!FirebaseAuthenticationHandler.isThisAdmin()) {
                FirebaseAuthenticationHandler.signOut();
            }
        }
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }
}
