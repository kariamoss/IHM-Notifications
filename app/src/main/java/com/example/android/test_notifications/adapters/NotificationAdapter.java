package com.example.android.test_notifications.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.test_notifications.R;
import com.example.android.test_notifications.models.Notification;

import java.util.List;

/**
 * Created by Jehan on 05/04/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NewsCache> {

    private List<Notification> data;

    public NotificationAdapter(List<Notification> objects){
        this.data = objects;
    }

    @Override
    public NewsCache onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_news;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new NewsCache(view, context);
    }

    @Override
    public void onBindViewHolder(NewsCache holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NewsCache extends RecyclerView.ViewHolder{
        TextView title;
        TextView category;
        TextView date;
        TextView content;
        ImageView imageView;
        ProgressBar progressBar;


        public NewsCache(View itemView, Context context){
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);
            date = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.content);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            progressBar = (ProgressBar) itemView.findViewById(R.id.pb_loading_indicator);
        }

        void bind(Notification articleModel){
            title.setText(articleModel.getTitle());
            category.setText(articleModel.getCategoryName());

            /*SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM HH:mm", Locale.FRANCE);
            String time = "";
            try {
                time = myFormat.format(articleModel.getDateFormat().parse(articleModel.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/
            date.setText(articleModel.getDate());
            content.setText(articleModel.getContent());
            if(articleModel.getPhotoUrl() != null){
                imageView.setVisibility(View.VISIBLE);
                new AsyncTaskLoadImage(imageView, progressBar).execute(articleModel.getPhotoUrl());
            }
        }
    }
}