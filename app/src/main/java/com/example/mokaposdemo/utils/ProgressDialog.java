package com.example.mokaposdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.mokaposdemo.R;

public class ProgressDialog extends Dialog {

    public ProgressDialog(Context context) {
        super(context, R.style.transparentDialogStyle);
        setContentView(LayoutInflater
                .from(context)
                .inflate(R.layout.progress_dialog_layout, null));
        setProperty();
    }

    private void setProperty() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

}
