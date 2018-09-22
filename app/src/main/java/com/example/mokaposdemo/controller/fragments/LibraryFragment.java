package com.example.mokaposdemo.controller.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.controller.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends BaseFragment implements View.OnClickListener {


    private TextView discounts;
    private TextView items;

    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment get() {
        return new LibraryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    protected boolean isBackEnabled() {
        return false;
    }

    @Override
    protected int getScreenTitle() {
        return R.string.library;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        discounts.setOnClickListener(this);
        items.setOnClickListener(this);
    }


    private void initView(View view) {
        discounts = view.findViewById(R.id.discounts);
        items = view.findViewById(R.id.items);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.discounts:
                ((MainActivity) getActivity()).openDiscountScreen();
                break;
            case R.id.items:
                ((MainActivity) getActivity()).openItemsScreen();
                break;
        }
    }
}
