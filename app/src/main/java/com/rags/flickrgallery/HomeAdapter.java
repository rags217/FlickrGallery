package com.rags.flickrgallery;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rags.flickrgallery.model.Item;

import java.util.List;



public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<Item> list;

    static class ViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        TextView mTitle;
        TextView mAuthor;
        TextView mPublished;

        ViewHolder(View v, Context context) {
            super(v);
            mTitle = v.findViewById(R.id.title);
            mAuthor = v.findViewById(R.id.author);
            mPublished = v.findViewById(R.id.published);
            this.context = context;
        }
    }


    HomeAdapter(List<Item> list) {
        this.list = list;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View layout = LayoutInflater.from(context).inflate(R.layout.fg_list_item, parent, false);
        return new ViewHolder(layout, context);
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        Item it = list.get(position);
        holder.mTitle.setText(it.getTitle());
        holder.mAuthor.setText(it.getAuthor());
        holder.mPublished.setText(it.getPublished());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
