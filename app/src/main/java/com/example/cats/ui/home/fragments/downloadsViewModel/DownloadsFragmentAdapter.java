package com.example.cats.ui.home.fragments.downloadsViewModel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cats.R;
import com.example.cats.api.models.res.Downloads;
import com.example.cats.ui.home.fragments.downloadsViewModel.deleteViewModel.DeleteDownloadActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DownloadsFragmentAdapter extends RecyclerView.Adapter<DownloadsFragmentAdapter.CustomViewHolder>
        implements View.OnLongClickListener {

    public static String idImageDownloads;
    private List<Downloads> dataList;
    private Context context;

    public DownloadsFragmentAdapter(Context context, List<Downloads> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        private ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            coverImage = mView.findViewById(R.id.download_image);
        }
    }

    @NotNull
    @Override
    public DownloadsFragmentAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.download_item, parent, false);

        return new DownloadsFragmentAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Picasso.with(context)
                .load(dataList.get(position).getUrl())
                .resize(500, 500)
                .centerCrop()
                .error(R.drawable.image_background)
                .into(holder.coverImage);

        holder.mView.setOnLongClickListener(view -> {
            idImageDownloads = dataList.get(position).getId();
            Intent intent = new Intent();
            intent.setClass(context, DeleteDownloadActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
