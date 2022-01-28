package com.example.appnexsolar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AcaoAdapter extends RecyclerView.Adapter<AcaoViewHolder> {
    private List<Acao> acoes;
    private Context context;
    private String idFilialUsuario;

    public AcaoAdapter(List<Acao> acoes, Context context, String idFilialUsuario)
    {
        this.acoes = acoes;
        this.context = context;
        this.idFilialUsuario = idFilialUsuario;
    }

    @NonNull
    @Override
    public AcaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ultimas_acoes, parent, false);
        return new AcaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcaoViewHolder holder, int position) {
        new ProdutoRepositorio(context, idFilialUsuario).buscarNomeProduto(acoes.get(position).getIdProduto(), holder.produto);
        holder.produto.setText(acoes.get(position).getIdProduto());

        holder.tipoAcao.setText(acoes.get(position).getTipo());

        Long quantidade = "retirada".equalsIgnoreCase(acoes.get(position).getTipo()) ? acoes.get(position).getQuantidadeRetirada() : acoes.get(position).getQuantidadeDevolvida();
        holder.quantidade.setText(quantidade.toString());

        holder.data.setText(acoes.get(position).getData());
        holder.hora.setText(acoes.get(position).getHora());

        holder.status.setText(acoes.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return acoes.size();
    }
}
