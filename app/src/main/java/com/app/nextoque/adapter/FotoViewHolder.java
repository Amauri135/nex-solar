package com.app.nextoque.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class FotoViewHolder extends RecyclerView.ViewHolder {
    private ImageView fotoProduto;
    private ProgressBar progressBarFoto;

    public FotoViewHolder(@NonNull View itemView) {
        super(itemView);

        fotoProduto = itemView.findViewById(R.id.foto_produto);
        progressBarFoto = itemView.findViewById(R.id.progressBarFoto);
    }

    public ImageView getFotoProduto() {
        return fotoProduto;
    }

    public void setFotoProduto(ImageView fotoProduto) {
        this.fotoProduto = fotoProduto;
    }

    public ProgressBar getProgressBarFoto() {
        return progressBarFoto;
    }

    public void setProgressBarFoto(ProgressBar progressBarFoto) {
        this.progressBarFoto = progressBarFoto;
    }
}
