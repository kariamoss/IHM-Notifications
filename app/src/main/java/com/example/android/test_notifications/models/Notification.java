package com.example.android.test_notifications.models;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Jehan on 10/05/2017.
 */

public class Notification {
    private String content;
    private String title;
    private SimpleDateFormat dateFormat;
    private String date;
    private Category category;
    private String photoUrl;
    private Store store;
    private Store secondStore;


    public Notification() {
    }

    public Notification(String content, String title, int category, String date, String photoUrl, int store, int secondStore) {
        this.content = content;
        this.title = title;
        this.date = date;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.FRANCE);
        this.photoUrl = photoUrl;
        this.category = Category.getCategoryFromId(category);
        this.store = Store.getStoreFromId(store);
        this.secondStore = Store.getStoreFromId(secondStore);
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getStoreName() {
        return store.getName();
    }

    public Category getCategory() {
        return category;
    }

    public Store getStore() {
        return store;
    }

    public Store getSecondStore() {
        return secondStore;
    }

    public String getSecondStoreName() {
        return secondStore.getName();
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(int category) {
        this.category = Category.getCategoryFromId(category);
    }
}
