package com.example.ullar.golf.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.ullar.golf.handlers.FirebaseAuthenticationHandler;
import com.example.ullar.golf.models.Order;
import com.example.ullar.golf.handlers.OrderDatabaseHandler;
import com.example.ullar.golf.adapters.OrderListAdapter;
import com.example.ullar.golf.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity implements FirebaseAuthenticationHandler.AuthenticationHandlerStateChangedListener {
    private ListView orderList;
    private FirebaseAuthenticationHandler firebaseAuthenticationHandler;
    private ArrayList<Order> orders;
    private OrderListAdapter orderAdapter;
    private OrderDatabaseHandler orderDatabaseHandler;

    public AdminActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        orderList = (ListView) findViewById(R.id.order_listView);
        orderDatabaseHandler = new OrderDatabaseHandler();
        firebaseAuthenticationHandler = new FirebaseAuthenticationHandler(this);

        loadOrderList();
    }

    private void loadOrderList() {
        orderDatabaseHandler.getAllOrders(new OrderDatabaseHandler.AllOrdersReadyCallback() {

            @Override
            public void allOrdersReady(ArrayList<Order> ordersArray) {
                orders = ordersArray;
                initAdapter();
            }
        });
    }

    private void initAdapter() {
        orderAdapter = new OrderListAdapter(this, R.layout.activity_admin_list_item, orders);
        orderList.setAdapter(orderAdapter);
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
    }

    @Override
    public void onFailure(String message) {
        Intent intent = new Intent(this, AdminLoginActivity.class);
        startActivity(intent);
    }
}
