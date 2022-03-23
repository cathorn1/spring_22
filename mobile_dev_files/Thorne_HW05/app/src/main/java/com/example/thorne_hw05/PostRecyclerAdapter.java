package com.example.thorne_hw05;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.UserViewHolder> {

        ArrayList<Post> currPosts;
        //Post post;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d H:m:s", Locale.ENGLISH);
        LocalDateTime dateTime;

        static PostRecyclerAdapter.IPostRecycler listener;
        interface IPostRecycler{
            //void passBackSortDetails(String attribute, int orderType);
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            if(recyclerView.getContext() instanceof PostRecyclerAdapter.IPostRecycler){
                listener = (PostRecyclerAdapter.IPostRecycler) recyclerView.getContext();
            }else{
                throw new RuntimeException("HEY, implement IPostRecycler Recycle adapter");
            }
        }

        public PostRecyclerAdapter(ArrayList<Post> data){
            this.currPosts = data;
        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_items, parent, false);
            UserViewHolder userViewHolder = new UserViewHolder(view);
            return userViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            Post post = currPosts.get(position);
            holder.tvPostItemMessage.setText(post.post_text);
            holder.tvPostItemName.setText(post.created_by_name);
            String dateInString = post.created_at;
            dateTime = LocalDateTime.parse(dateInString, formatter);
            holder.tvPostItemDate.setText(dateTime.getMonthValue() + "/" + dateTime.getDayOfYear() + "/" +
                    dateTime.getYear() + " at " +dateTime.getHour() + ":" + dateTime.getMinute() + ":" + dateTime.getSecond());
            holder.position = position;

        }

        @Override
        public int getItemCount() {
            return this.currPosts.size();
        }

        public static class UserViewHolder extends RecyclerView.ViewHolder{
            TextView tvPostItemMessage;
            TextView tvPostItemName;
            TextView tvPostItemDate;
            //ImageButton btnTrash;
            View rootView;
            int position;
            String dateInString;
            Date date;

            public UserViewHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView;
                tvPostItemMessage = itemView.findViewById(R.id.tvPostItemMessage);
                tvPostItemName = itemView.findViewById(R.id.tvPostItemName);
                tvPostItemDate = itemView.findViewById(R.id.tvPostItemDate);

                itemView.findViewById(R.id.btnTrash).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("demo", "onClick: btnTrash click: " + position + " " + tvPostItemMessage.getText());
                        try {
                            //listener.passBackSortDetails(type, orderDesc);
                        } catch (Exception e){
                            Log.d("demo", "onClick: " + e.getMessage());
                        }
                    }
                });

            }
        }
    }
