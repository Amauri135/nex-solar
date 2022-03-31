package com.app.nextoque.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class ProdutoViewHolder extends RecyclerView.ViewHolder {
    TextView listItemSpinner;

    public ProdutoViewHolder(@NonNull View itemView) {
        super(itemView);

        listItemSpinner = itemView.findViewById(R.id.list_item_spinner);
    }

}
