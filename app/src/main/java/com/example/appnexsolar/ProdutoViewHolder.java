package com.example.appnexsolar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProdutoViewHolder extends RecyclerView.ViewHolder {
    TextView textViewDescricaoProduto;
    TextView textViewQtdAtual;
    TextView textViewQtdInicial;

    public ProdutoViewHolder(@NonNull View itemView) {
        super(itemView);

        textViewDescricaoProduto = itemView.findViewById(R.id.textViewDescricaoProduto);
        textViewQtdAtual = itemView.findViewById(R.id.textViewQtdAtual);
        textViewQtdInicial = itemView.findViewById(R.id.textViewQtdInicial);
    }

}
