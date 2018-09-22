package com.example.mokaposdemo.controller.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.adapter.ShoppingCartAdapter;
import com.example.mokaposdemo.models.ShoppingTable;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.mokaposdemo.utils.Constants.SHOPPING_CART_EVENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener, ShoppingCartAdapter.ShoppingCartListener {

    private final HashMap<String, ShoppingTable> shoppingItems = new HashMap<>();

    private Button charge;
    private Button clearSale;
    private RecyclerView list;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            ShoppingTable newItem = intent.getParcelableExtra("data");

            if (shoppingItems.containsKey(newItem.getUniqueId())) {
                ShoppingTable oldItem = shoppingItems.get(newItem.getUniqueId());
                oldItem.setQuantity(oldItem.getQuantity() + newItem.getQuantity());
                shoppingItems.put(newItem.getUniqueId(), oldItem);

                ((ShoppingCartAdapter) list.getAdapter())
                        .updateItems(new ArrayList<>(shoppingItems.values()));
            } else {
                shoppingItems.put(newItem.getUniqueId(), newItem);
                ((ShoppingCartAdapter) list.getAdapter())
                        .addItems(newItem);
            }
        }
    };


    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    public static ShoppingCartFragment get() {
        return new ShoppingCartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    protected boolean isBackEnabled() {
        return false;
    }

    @Override
    protected int getScreenTitle() {
        return R.string.shopping_cart;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setupList();
        charge.setOnClickListener(this);
        clearSale.setOnClickListener(this);
    }

    private void initView(View view) {
        charge = view.findViewById(R.id.charge);
        clearSale = view.findViewById(R.id.clear_sale);
        list = view.findViewById(R.id.list);
    }

    private void setupList() {
        list.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(new ShoppingCartAdapter(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(SHOPPING_CART_EVENT));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.charge:
                break;
            case R.id.clear_sale:
                ((ShoppingCartAdapter) list.getAdapter()).clearCart();
                shoppingItems.clear();
                charge.setText(getString(R.string.charge_0));
                break;
        }
    }

    @Override
    public void onUpdate(double v) {
        charge.setText(getString(R.string.charge) + v);
    }
}
