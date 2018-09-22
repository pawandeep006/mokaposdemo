package com.example.mokaposdemo.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.mokaposdemo.BaseApp;
import com.example.mokaposdemo.R;
import com.example.mokaposdemo.database.tables.ArticleTable;
import com.example.mokaposdemo.models.DiscountItem;
import com.example.mokaposdemo.models.ShoppingTable;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.mokaposdemo.utils.Constants.DISCOUNT_JSON;
import static com.example.mokaposdemo.utils.Constants.SHOPPING_CART_EVENT;
import static com.example.mokaposdemo.utils.Utils.getGson;
import static com.example.mokaposdemo.utils.Utils.toast;

public class AddToCartDialog extends Dialog implements View.OnClickListener {
    private final ArticleTable item;
    private final Context context;
    private Button cancel;
    private Button save;
    private TextView title;
    private TextView quantity;
    private TextView plusQuan;
    private TextView minusQuan;
    private ToggleButtonGroupTableLayout discountParent;
    private int totalQuantity = 1;

    public AddToCartDialog(Context context, ArticleTable item) {
        super(context);
        this.context = context;
        this.item = item;

        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.add_to_cart_dialog_layout, null);
        setContentView(view);
        initView(view);

        cancel.setOnClickListener(this);
        save.setOnClickListener(this);
        plusQuan.setOnClickListener(this);
        minusQuan.setOnClickListener(this);

        setUpRadio();

        title.setText(item.getName().trim() + " - $" + item.getPrice());
    }

    private void setUpRadio() {
        List<DiscountItem> discountItems = getItems();
        if (discountItems == null || discountItems.isEmpty())
            return;

        int size = discountItems.size();
        for (int i = 0; i < size; i = i + 2) {

            TableRow row = new TableRow(context);

            if (i < size) {
                DiscountItem discountItem1 = discountItems.get(i);
                RadioButton rb1 = new RadioButton(context);
                rb1.setId(100 + i);
                rb1.setTag(discountItem1);
                rb1.setText(discountItem1.getName() + " (" + discountItem1.getDiscount() + "%)");
                row.addView(rb1);
            }

            if (i + 1 < size) {
                DiscountItem discountItem2 = discountItems.get(i + 1);
                RadioButton rb2 = new RadioButton(context);
                rb2.setId(100 + i + 1);
                rb2.setTag(discountItem2);
                rb2.setText(discountItem2.getName() + " (" + discountItem2.getDiscount() + "%)");
                row.addView(rb2);
            }
            discountParent.addView(row);
        }
    }


    private List<DiscountItem> getItems() {
        return getGson().fromJson(DISCOUNT_JSON, new TypeToken<List<DiscountItem>>() {
        }.getType());
    }

    private void initView(View view) {
        cancel = view.findViewById(R.id.cancel);
        save = view.findViewById(R.id.save);
        title = view.findViewById(R.id.title);
        quantity = view.findViewById(R.id.quantity);
        plusQuan = view.findViewById(R.id.plus_quan);
        minusQuan = view.findViewById(R.id.minus_quan);
        discountParent = view.findViewById(R.id.discount_parent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.save:
                saveToCart();
                break;
            case R.id.plus_quan:
                updateQuantity(1);
                break;
            case R.id.minus_quan:
                updateQuantity(-1);
                break;
        }

    }

    private void updateQuantity(int i) {
        String quan = quantity.getText().toString().trim();

        int quanInt;
        try {
            quanInt = Integer.parseInt(quan);
        } catch (Exception e) {
            quanInt = 0;
        }

        quanInt += i;

        if (quanInt == 0)
            minusQuan.setEnabled(false);
        else
            minusQuan.setEnabled(true);

        if (quanInt == Integer.MAX_VALUE)
            plusQuan.setEnabled(false);
        else
            plusQuan.setEnabled(true);

        totalQuantity = quanInt;
        quantity.setText(quanInt + "");
    }

    private void saveToCart() {
        if (totalQuantity == 0) {
            toast("At least select one quantity");
            return;
        }

        double discount = getDiscount();

        if (discount == -1) {
            toast("At least select one discount");
            return;
        }

        ShoppingTable table = new ShoppingTable();
        table.setDiscount(discount);
        table.setPrice(item.getPrice());
        table.setQuantity(totalQuantity);
        table.setTitle(item.getName());
        table.setUniqueId(item.getArticleId() + "_" + discount);

        dismiss();

        Intent intent = new Intent(SHOPPING_CART_EVENT);
        // You can also include some extra data.
        intent.putExtra("data", table);

        LocalBroadcastManager.getInstance(BaseApp.getContext()).sendBroadcast(intent);
    }

    private double getDiscount() {
        try {
            return ((DiscountItem) findViewById(discountParent.getCheckedRadioButtonId()).getTag()).getDiscount();
        } catch (Exception e) {
            return -1;
        }
    }
}
