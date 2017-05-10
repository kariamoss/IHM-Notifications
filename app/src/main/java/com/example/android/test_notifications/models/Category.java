package com.example.android.test_notifications.models;

import com.google.firebase.database.Exclude;

/**
 * Created by Jehan on 10/05/2017.
 */

public enum Category {
    PROMOTION(1, "Promotions"),
    OUVERTURE(2, "Nouveau magasin");

    private int id;
    private String name;

    Category(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Exclude
    public static Category getCategoryFromId(int id){
        for(Category category: Category.values()){
            if (category.id == id){
                return category;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
