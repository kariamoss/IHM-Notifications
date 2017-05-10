package com.example.android.test_notifications.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.android.test_notifications.R;

import java.io.IOException;
import java.net.URL;

class AsyncTaskLoadImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView ivNews;
    private ProgressBar pbNews;

    AsyncTaskLoadImage(ImageView imageView, ProgressBar progressBar){
        ivNews = imageView;
        pbNews = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ivNews.setImageResource(R.drawable.placeholder);
        pbNews.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap image = null;
        try {
            URL url = new URL(params[0]);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        pbNews.setVisibility(View.INVISIBLE);
        if(bitmap != null){
            ivNews.setImageBitmap(bitmap);
        }
    }
}