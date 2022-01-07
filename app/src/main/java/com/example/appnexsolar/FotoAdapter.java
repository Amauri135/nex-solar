package com.example.appnexsolar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class FotoAdapter extends RecyclerView.Adapter<FotoViewHolder> {

    private Context context;
    private List<Uri> uriList;

    public FotoAdapter(Context context, List<Uri> uriList){
        this.context = context;
        this.uriList = uriList;
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_foto_produto, parent, false);
        return new FotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoViewHolder holder, int position) {
        holder.getProgressBarFoto().setVisibility(View.VISIBLE);
        Glide.with(context).load(uriList.get(position)).into(holder.getFotoProduto()).onResourceReady(holder.getFotoProduto().getDrawable(), new Transition<Drawable>() {
            @Override
            public boolean transition(Drawable current, ViewAdapter adapter) {
                holder.getProgressBarFoto().setVisibility(View.GONE);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return uriList.size();
    }
}
