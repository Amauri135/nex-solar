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
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class VerFotosAdapter extends RecyclerView.Adapter<VerFotosViewHolder> {
    private List<Uri> urisFotos;
    private Context context;
    private final NavigationView navigationView;

    public VerFotosAdapter(Context context, List<Uri> urisFotos, NavigationView navigationView) {
        this.context = context;
        this.urisFotos = urisFotos;
        this.navigationView = navigationView;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return urisFotos.size();
    }
}
