package com.example.ullar.golf.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ullar.golf.handlers.FirebaseAuthenticationHandler;
import com.example.ullar.golf.models.Order;
import com.example.ullar.golf.handlers.OrderDatabaseHandler;
import com.example.ullar.golf.R;
import com.google.firebase.auth.FirebaseUser;

public class GuestActivity extends AppCompatActivity implements FirebaseAuthenticationHandler.AuthenticationHandlerStateChangedListener {
    private FirebaseAuthenticationHandler firebaseAuthenticationHandler;
    private OrderDatabaseHandler orderDatabaseHandler;
    private Order currentOrder;

    private Spinner beerSpinner;
    private Spinner sandwichSpinner;
    private Spinner holeSpinner;
    private TextView textView_beers;
    private TextView textView_sandwiches;
    private TextView textView_hole;

    private int numberOfBeers = 0;
    private int numberOfSandwiches = 0;
    private int currentHole = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        firebaseAuthenticationHandler = new FirebaseAuthenticationHandler(this);
        orderDatabaseHandler = new OrderDatabaseHandler();

        textView_beers = (TextView) findViewById(R.id.textview_number_of_beers);
        textView_sandwiches = (TextView) findViewById(R.id.textview_number_of_sandwiches);
        textView_hole = (TextView) findViewById(R.id.textview_current_hole);

        firebaseAuthenticationHandler.attempSignIn();
        currentOrder = new Order(0, 0, 0, "");
        initSpinners();
        initAndUpdateViews();
    }

    public void confirmOrder (View view) {
        if (currentOrder.getHole() == 0) {
            Toast.makeText(this, "Du skal vælge hvilket hul du er ved!", Toast.LENGTH_LONG).show();
        }
        else if (currentOrder.getBeer() == 0 && currentOrder.getSandwich() == 0) {
            Toast.makeText(this, "Din bestilling er tom.", Toast.LENGTH_LONG).show();

        }
        else {
            orderDatabaseHandler.addOrder(currentOrder, firebaseAuthenticationHandler.getCurrentUser());
            Intent navToOrderCompletedActivity = new Intent(this, OrderCompletedActivity.class);
            startActivity(navToOrderCompletedActivity);
        }
    }

    public void addToOrder(View view) {
        currentOrder = new Order(numberOfBeers, numberOfSandwiches, currentHole, firebaseAuthenticationHandler.getCurrentUser().getUid());
        initAndUpdateViews();
    }

    private void initAndUpdateViews() {
        if (currentOrder == null) {}

        textView_beers.setText("Antal øl: " + currentOrder.getBeer());
        textView_sandwiches.setText("Antal sandwiches: " + currentOrder.getSandwich());
        textView_hole.setText("Hul: " + currentOrder.getHole());
    }

    private void initSpinners () {
        beerSpinner = (Spinner) findViewById(R.id.beerSpinner);
        sandwichSpinner = (Spinner) findViewById(R.id.sandwichSpinner);
        holeSpinner = (Spinner) findViewById(R.id.holeSpinner);

        ArrayAdapter<CharSequence> beer_sandwich_adapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_beers_sandwiches, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> hole_adapter = ArrayAdapter.createFromResource(this,
                R.array.number_of_hole, android.R.layout.simple_spinner_item);

        beer_sandwich_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hole_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        beerSpinner.setAdapter(beer_sandwich_adapter);
        sandwichSpinner.setAdapter(beer_sandwich_adapter);
        holeSpinner.setAdapter(hole_adapter);

        beerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfBeers = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numberOfBeers = 0;
            }
        });

        sandwichSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numberOfSandwiches = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numberOfSandwiches = 0;
            }
        });

        holeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentHole = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentHole = 0;
            }
        });
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
        Log.d("UserSigned", "Signed in anonymously");
    }

    @Override
    public void onFailure(String message) {
        Log.d("UserSigned", message);
    }
}
