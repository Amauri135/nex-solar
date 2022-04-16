package com.app.nextoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.model.UsuarioBO;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ListarProdutosAdapter extends RecyclerView.Adapter<ListarProdutosViewHolder> {
    private List<Produto> produtos;
    private Context context;
    private final NavigationView navigationView;

    public ListarProdutosAdapter(Context context, List<Produto> produtos, NavigationView navigationView) {
        this.context = context;
        this.produtos = produtos;
        this.navigationView = navigationView;
    }

    @NonNull
    @Override
    public ListarProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_listar_produtos, parent, false);
        return new ListarProdutosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListarProdutosViewHolder holder, int position) {
        if(position % 2 == 1) {
            ((CardView) holder.itemView.findViewById(R.id.cardViewListarProdutos)).setCardBackgroundColor(context.getResources().getColor(R.color.cinza_claro));
        } else {
            ((CardView) holder.itemView.findViewById(R.id.cardViewListarProdutos)).setCardBackgroundColor(context.getResources().getColor(R.color.cinza_escuro));
        }

        Produto produto = produtos.get(position);

        holder.getProduto().setText(produto.getDescricao());
        holder.getCategoria().setText(produto.getCategoria());
        holder.getQtInicial().setText(produto.getQuantidadeInicial() != null ? produto.getQuantidadeInicial().toString() : null);
        holder.getQtAtual().setText(produto.getQuantidadeAtual() != null ? produto.getQuantidadeAtual().toString() : null);
        holder.getDataCadastro().setText(produto.getData());
        holder.getHoraCadastro().setText(produto.getHora());
        new UsuarioBO().buscarNomeUsuario(holder.getCadastradoPor(), produto.getIdUsuario());
        holder.getObs().setText(produto.getObs() == null || produto.getObs().trim().isEmpty() ? "-" : produto.getObs());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
