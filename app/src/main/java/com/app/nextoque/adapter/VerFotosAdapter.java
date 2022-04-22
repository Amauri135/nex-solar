package com.app.nextoque.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class VerFotosAdapter extends RecyclerView.Adapter<VerFotosViewHolder> {
    private List<Uri> urisFotos;
    private Context context;

    public VerFotosAdapter(Context context, List<Uri> urisFotos) {
        this.context = context;
        this.urisFotos = urisFotos;
    }

    @NonNull
    @Override
    public VerFotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ver_fotos, parent, false);

        return new VerFotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerFotosViewHolder holder, int position) {
        Uri uriFoto = urisFotos.get(position);

        Glide.with(context).load(uriFoto).into(holder.getFoto());
    }

    @Override
    public int getItemCount() {
        return urisFotos.size();
    }
}
