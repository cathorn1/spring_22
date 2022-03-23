package com.example.thorne_hw05;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class PageRecyclerAdapter extends RecyclerView.Adapter<PageRecyclerAdapter.UserViewHolder>{
    ArrayList<Integer> pageNums;
    //Post post;

    static PageRecyclerAdapter.IPageRecycler listener;
    interface IPageRecycler{
        //void passBackSortDetails(String attribute, int orderType);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(recyclerView.getContext() instanceof PageRecyclerAdapter.IPageRecycler){
            listener = (PageRecyclerAdapter.IPageRecycler) recyclerView.getContext();
        }else{
            throw new RuntimeException("HEY, implement IPageRecycler Recycle adapter");
        }
    }

    public PageRecyclerAdapter(ArrayList<Integer> data){
        this.pageNums = data;
    }

    @NonNull
    @Override
    public PageRecyclerAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_number_item, parent, false);
        PageRecyclerAdapter.UserViewHolder userViewHolder = new PageRecyclerAdapter.UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PageRecyclerAdapter.UserViewHolder holder, int position) {
        int page = pageNums.get(position) + 1;
        holder.tvPageNum.setText(Integer.toString(page));
        holder.position = position;

    }

    @Override
    public int getItemCount() {
        return this.pageNums.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView tvPageNum;
        View rootView;
        int position;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            tvPageNum = itemView.findViewById(R.id.tvCardPageNum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("demo", "onClick: pagenum click click: " + position + 1);
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