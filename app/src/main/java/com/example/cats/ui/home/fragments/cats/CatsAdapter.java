package com.example.cats.ui.home.fragments.cats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cats.R;
import com.example.cats.api.models.res.Cat;
import com.example.cats.ui.image.ImageDetails;

import org.jetbrains.annotations.NotNull;

public class CatsAdapter extends PagedListAdapter<Cat, CatsAdapter.ItemViewHolder> implements View.OnClickListener{
    private Context context;

    private static DiffUtil.ItemCallback<Cat> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Cat>() {
                @Override
                public boolean areItemsTheSame(Cat oldItem, Cat newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(Cat oldItem, @NotNull Cat newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public CatsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NotNull
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cat_item, parent, false);

        return new ItemViewHolder(view);
    }

    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Cat cat = getItem(position);
        assert cat != null;

        Glide.with(context)
                .load(cat.getUrl())
                .centerCrop()
                .into(holder.coverImage);

        holder.mView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(context, ImageDetails.class);
            intent.putExtra("url", cat.getUrl());
            intent.putExtra("id", cat.getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public void onClick(View view) {

    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView coverImage;
        View mView;

        public ItemViewHolder(View view) {
            super(view);
            mView = view;
            coverImage = view.findViewById(R.id.cat_image);
        }
    }

}