package com.example.mokaposdemo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingTable implements Parcelable {

    public static final Parcelable.Creator<ShoppingTable> CREATOR = new Parcelable.Creator<ShoppingTable>() {
        @Override
        public ShoppingTable createFromParcel(Parcel source) {
            return new ShoppingTable(source);
        }

        @Override
        public ShoppingTable[] newArray(int size) {
            return new ShoppingTable[size];
        }
    };
    private int quantity;
    private String title;
    private double price;
    private double discount;
    private String uniqueId;

    public ShoppingTable() {
    }

    protected ShoppingTable(Parcel in) {
        this.quantity = in.readInt();
        this.title = in.readString();
        this.price = in.readDouble();
        this.discount = in.readDouble();
        this.uniqueId = in.readString();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "ShoppingTable{" +
                "quantity=" + quantity +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.quantity);
        dest.writeString(this.title);
        dest.writeDouble(this.price);
        dest.writeDouble(this.discount);
        dest.writeString(this.uniqueId);
    }
}
