package com.example.mokaposdemo.database;


import com.example.mokaposdemo.database.tables.ArticleTable;
import com.example.mokaposdemo.models.Article;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrmHelper {
    private static final Object lock = new Object();
    private static OrmHelper mOrmHelper;
    private final Random rand;

    private OrmHelper() {
        rand = new Random();
    }

    public static OrmHelper get() {
        OrmHelper ormHelper = mOrmHelper;
        if (ormHelper == null) {
            synchronized (lock) {
                ormHelper = mOrmHelper;
                if (ormHelper == null) {
                    ormHelper = new OrmHelper();
                    mOrmHelper = ormHelper;
                }
            }
        }
        return ormHelper;
    }


    public void saveArticle(List<Article> articles) {
        for (Article article : articles) {
            new ArticleTable(article.getId(), article.getTitle()
                    , article.getThumbnailUrl()
                    , article.getId() * (rand.nextInt(90) + 10)).save();
        }
    }

    public List<ArticleTable> getAllItems() {
        try {
            return ArticleTable.listAll(ArticleTable.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
