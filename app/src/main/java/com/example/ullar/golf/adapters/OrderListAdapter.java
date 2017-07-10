package com.example.ullar.golf.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ullar.golf.models.Order;
import com.example.ullar.golf.R;

import java.util.ArrayList;

/**
 * Created by UllaR on 10-07-2017.
 */

public class OrderListAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<Order> orders;

    private TextView orderId;
    private TextView numberOfBeers;
    private TextView numberOfSandwich;
    private TextView currentHole;

    public OrderListAdapter(Context context, int resource, ArrayList orders) {
        super(context, resource, orders);
        this.context = context;
        this.resource = resource;
        this.orders = orders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Order order = orders.get(position);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null) {
            view = inflater.inflate(resource, parent, false);
        }
        else {
            view = convertView;
        }

        numberOfBeers = (TextView) view.findViewById(R.id.textview_number_of_beers);
        numberOfSandwich = (TextView) view.findViewById(R.id.textview_number_of_sandwiches);
        currentHole = (TextView) view.findViewById(R.id.textview_current_hole);
        orderId = (TextView) view.findViewById(R.id.order_id);

        numberOfBeers.setText("Øl: " + String.valueOf(order.getBeer()));
        numberOfSandwich.setText("Sandwich: " + String.valueOf(order.getSandwich()));
        currentHole.setText("Hul: " + String.valueOf(order.getHole()));
        orderId.setText(order.getUid());

        return view;
    }
}
