package com.example.ullar.golf.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ullar.golf.handlers.FirebaseAuthenticationHandler;
import com.example.ullar.golf.models.Order;
import com.example.ullar.golf.handlers.OrderDatabaseHandler;
import com.example.ullar.golf.R;

public class OrderCompletedActivity extends AppCompatActivity {
    private FirebaseAuthenticationHandler firebaseAuthenticationHandler;
    private OrderDatabaseHandler orderDatabaseHandler;
    private TextView orderedBeers;
    private TextView orderedSandwiches;
    private TextView orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_completed);

        orderDatabaseHandler = new OrderDatabaseHandler();

        orderedBeers = (TextView) findViewById(R.id.textview_number_of_beers);
        orderedSandwiches = (TextView) findViewById(R.id.textview_number_of_sandwiches);
        orderId = (TextView) findViewById(R.id.order_id);

        orderedBeers.setText("0");
        orderedSandwiches.setText("0");
        orderId.setText("ID: ");

        loadUserOrder();
    }

    public void loadUserOrder() {
        orderDatabaseHandler.getOrder(FirebaseAuthenticationHandler.getCurrentUser(), new OrderDatabaseHandler.UserOrderReadyCallback() {
            @Override
            public void userOrderReady(Order order) {
                updateUI(order);
            }
        });
    }

    private void updateUI(Order order) {
        orderedBeers.setText(String.valueOf(order.getBeer()));
        orderedSandwiches.setText(String.valueOf(order.getSandwich()));
        orderId.setText("ID: " + order.getUid());
    }

    public void deleteOrder(View view) {
        orderDatabaseHandler.deleteOrder(FirebaseAuthenticationHandler.getCurrentUser().getUid());
    }
}
