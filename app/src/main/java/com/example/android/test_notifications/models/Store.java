package com.example.android.test_notifications.models;

import com.google.firebase.database.Exclude;

/**
 * Created by Jehan on 17/05/2017.
 */

public enum  Store {
    multimedia(1, "Multimédia"),
    housing(2, "Maison"),
    hypermarket(3, "Hypermarché"),
    beauty(4, "Beauté et santé"),
    fashion(5, "Mode"),
    game(5, "Jeux"),
    services(5, "Services"),
    sport(5, "Sport");

    private int id;
    private String name;

    Store(int id, String name){
        this.id = id;
        this.name = name;
    }

    @Exclude
    public static Store getStoreFromId(int id){
        for(Store store: Store.values()){
            if (store.id == id){
                return store;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
