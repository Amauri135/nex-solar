package com.app.nextoque.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class ListarProdutosViewHolder extends RecyclerView.ViewHolder{
    private final TextView produto;
    private final TextView categoria;
    private final TextView qtInicial;
    private final TextView qtAtual;
    private final TextView dataCadastro;
    private final TextView horaCadastro;
    private final TextView cadastradoPor;
    private final TextView obs;
    private final ImageButton btnVerFotos;
    private final ImageButton btnAlterarProduto;
    private final ImageButton btnExcluirProduto;


    public ListarProdutosViewHolder(@NonNull View itemView) {
        super(itemView);

        produto = itemView.findViewById(R.id.prod_listar_produtos);
        categoria = itemView.findViewById(R.id.categoria_listar_produtos);
        qtInicial = itemView.findViewById(R.id.qtInicial_listar_produtos);
        qtAtual = itemView.findViewById(R.id.qtAtual_listar_produtos);
        dataCadastro = itemView.findViewById(R.id.data_listar_produtos);
        horaCadastro = itemView.findViewById(R.id.hora_listar_produtos);
        cadastradoPor = itemView.findViewById(R.id.cadastrado_por_listar_produtos);
        obs = itemView.findViewById(R.id.obs_listar_produtos);
        btnVerFotos = itemView.findViewById(R.id.btnVerFotos);
        btnAlterarProduto = itemView.findViewById(R.id.btnAlterarProduto);
        btnExcluirProduto = itemView.findViewById(R.id.btnExcluirProduto);
    }

    public TextView getProduto() {
        return produto;
    }

    public TextView getCategoria() {
        return categoria;
    }

    public TextView getQtInicial() {
        return qtInicial;
    }

    public TextView getQtAtual() {
        return qtAtual;
    }

    public TextView getDataCadastro() {
        return dataCadastro;
    }

    public TextView getHoraCadastro() {
        return horaCadastro;
    }

    public TextView getCadastradoPor() {
        return cadastradoPor;
    }

    public TextView getObs() {
        return obs;
    }

    public ImageButton getBtnVerFotos() {
        return btnVerFotos;
    }

    public ImageButton getBtnAlterarProduto() {
        return btnAlterarProduto;
    }

    public ImageButton getBtnExcluirProduto() {
        return btnExcluirProduto;
    }
}
