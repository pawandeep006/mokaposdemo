package com.example.mokaposdemo.controller.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.mokaposdemo.R;
import com.example.mokaposdemo.utils.ProgressDialog;

import static com.example.mokaposdemo.utils.Utils.log;

public abstract class BaseFragment extends Fragment {

    private Dialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            view.setOnClickListener(null);
            view.setBackgroundColor(getColor(R.color.off_white));
            setUpToolbar(view);
        } catch (Exception ignored) {
        }
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle(getScreenTitle());
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getScreenTitle());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(isBackEnabled());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(isBackEnabled());
        } else {
            log("toolbar not available");
        }
    }

    protected abstract boolean isBackEnabled();

    protected abstract int getScreenTitle();

    public final int getColor(int id) {
        if (getActivity() == null) {
            return 0;
        }

        if (getContext() == null) {
            return Color.BLACK;
        }

        return ContextCompat.getColor(getContext(), id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void hideKeyboard() {
        final Activity activity = getActivity();

        if (activity == null)
            return;

        View view = activity.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showLoader() {
        final Activity activity = getActivity();

        if (activity == null)
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideKeyboard();

                if (activity.isFinishing()) {
                    return;
                }

                if (progressDialog != null && progressDialog.isShowing()) {
                    return;
                }

                progressDialog = new ProgressDialog(activity);
                progressDialog.show();
            }
        });
    }

    public void hideLoader() {
        dismissDialog(progressDialog);
    }

    void dismissDialog(final Dialog dialog) {
        final Activity activity = getActivity();

        if (activity == null)
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog == null || !dialog.isShowing()) {
                    return;
                }
                dialog.dismiss();
            }
        });
    }
}
