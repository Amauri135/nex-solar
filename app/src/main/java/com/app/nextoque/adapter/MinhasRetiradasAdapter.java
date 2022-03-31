package com.app.nextoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.entity.Acao;
import com.app.nextoque.model.ObraRepositorio;
import com.app.nextoque.model.ProdutoRepositorio;
import com.app.nextoque.R;

import java.util.List;

public class MinhasRetiradasAdapter extends RecyclerView.Adapter<MinhasRetiradasViewHolder> {
    private List<Acao> minhasRetiradas;
    private Context context;
    private String idFilialUsuario;

    public MinhasRetiradasAdapter(List<Acao> minhasRetiradas, Context context, String idFilialUsuario){
        this.minhasRetiradas = minhasRetiradas;
        this.context = context;
        this.idFilialUsuario = idFilialUsuario;
    }

    @NonNull
    @Override
    public MinhasRetiradasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_minhas_retiradas, parent, false);
        return new MinhasRetiradasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MinhasRetiradasViewHolder holder, int position) {
        Acao retirada = minhasRetiradas.get(position);

        new ProdutoRepositorio(context, idFilialUsuario).buscarNomeProduto(retirada.getIdProduto(), holder.produto);

        Long qtRetirada =  retirada.getQuantidadeRetirada();
        Long qtDevolvida = retirada.getQuantidadeDevolvida();

        holder.qtRetirada.setText(holder.qtRetirada.getText().toString().concat(": " + qtRetirada != null ? qtRetirada.toString() : ""));
        holder.qtDevolvida.setText(qtDevolvida != null ? qtDevolvida.toString() : "");

        new ObraRepositorio(context, idFilialUsuario).buscarNomeObra(retirada.getIdObra(), holder.obra);

        holder.data.setText(retirada.getData());
        holder.hora.setText(retirada.getHora());
        holder.obs.setText(retirada.getObservacao());
    }

    @Override
    public int getItemCount() {
        return minhasRetiradas.size();
    }
}
