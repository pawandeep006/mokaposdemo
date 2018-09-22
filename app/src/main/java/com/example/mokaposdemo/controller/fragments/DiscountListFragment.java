package com.example.mokaposdemo.controller.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.adapter.DiscountAdapter;
import com.example.mokaposdemo.models.DiscountItem;
import com.example.mokaposdemo.utils.Constants;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.mokaposdemo.utils.Utils.getGson;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscountListFragment extends BaseFragment {


    private RecyclerView list;

    public DiscountListFragment() {
        // Required empty public constructor
    }

    public static DiscountListFragment get() {
        return new DiscountListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discount_list, container, false);
    }

    @Override
    protected boolean isBackEnabled() {
        return true;
    }

    @Override
    protected int getScreenTitle() {
        return R.string.all_discounts;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setupList();
    }

    private void setupList() {
        list.setLayoutManager(new GridLayoutManager(getContext().getApplicationContext(), 3));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(new DiscountAdapter(getItems()));
        list.setHasFixedSize(true);
    }

    private List<DiscountItem> getItems() {
        return getGson().fromJson(Constants.DISCOUNT_JSON, new TypeToken<List<DiscountItem>>() {
        }.getType());
    }

    private void initView(View view) {
        list = view.findViewById(R.id.list);
    }
}
