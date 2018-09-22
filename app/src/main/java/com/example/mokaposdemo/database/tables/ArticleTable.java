package com.example.mokaposdemo.database.tables;

import com.orm.SugarRecord;

public class ArticleTable extends SugarRecord {

    private int articleId;
    private String name;
    private String image;
    private double price;

    public ArticleTable(int articleId, String name, String image, double price) {
        this.articleId = articleId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ArticleTable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
