package com.example.android.test_notifications.models;

import com.google.firebase.database.Exclude;

/**
 * Created by Jehan on 10/05/2017.
 */

public enum Category {
    shop_status(1, "Ouverture / Fermeture de magasin"),
    flash_offers(2, "Offres flash"),
    sales(3, "Période de soldes"),
    global_events(4, "Evènements globaux du centre"),
    product_news(5, "Nouveautés produits");

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
