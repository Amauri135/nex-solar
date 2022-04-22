package com.app.nextoque.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class VerFotosViewHolder extends RecyclerView.ViewHolder {
    private ImageView foto;

    public VerFotosViewHolder(@NonNull View itemView) {
        super(itemView);

        foto = itemView.findViewById(R.id.foto_ver_fotos);
    }

    public ImageView getFoto() {
        return foto;
    }
}
