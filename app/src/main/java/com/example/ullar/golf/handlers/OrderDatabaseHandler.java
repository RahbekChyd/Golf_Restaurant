package com.example.ullar.golf.handlers;

import com.example.ullar.golf.models.Order;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by UllaR on 10-07-2017.
 */

public class OrderDatabaseHandler {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public OrderDatabaseHandler() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference();
    }

    public void addOrder(final Order currentOrder, final FirebaseUser currentUser) {
        Map<String, Object> orderValues = currentOrder.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/orders/" + currentUser.getUid(), orderValues);

        databaseReference.updateChildren(childUpdates);
    }

    public void getOrder(final FirebaseUser user, final UserOrderReadyCallback userOrderReadyCallback) {
        databaseReference.child("orders/" + user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = Order.mapToOrder((HashMap<String, Object>)dataSnapshot.getValue());
                userOrderReadyCallback.userOrderReady(order);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllOrders(final AllOrdersReadyCallback allOrdersReadyCallback) {
        databaseReference.child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, Object>> orderMaps = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
                ArrayList<Order> resultOrders = new ArrayList<Order>();
                if (orderMaps != null) {
                    for (HashMap<String, Object> orderMap : orderMaps.values()) {
                        resultOrders.add(Order.mapToOrder(orderMap));
                    }
                }
                allOrdersReadyCallback.allOrdersReady(resultOrders);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void deleteOrder(String uid) {
        databaseReference.child("orders/" + uid).removeValue();
    }

    public interface UserOrderReadyCallback {
        void userOrderReady(Order order);
    }

    public interface AllOrdersReadyCallback {
        void allOrdersReady(ArrayList<Order> orders);
    }
}
