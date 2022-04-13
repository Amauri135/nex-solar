package com.app.nextoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.entity.Acao;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;

import java.util.List;

public class AcaoAdapter extends RecyclerView.Adapter<AcaoViewHolder> {
    private List<Acao> acoes;
    private Context context;
    private final Usuario usuario;
    private FragmentManager fragmentManager;

    public AcaoAdapter(List<Acao> acoes, Context context, Usuario usuario, FragmentManager fragmentManager)
    {
        this.acoes = acoes;
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AcaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ultimas_acoes, parent, false);
        return new AcaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcaoViewHolder holder, int position) {
        new ProdutoBO(context, usuario, fragmentManager).buscarNomeProduto(acoes.get(position).getIdProduto(), holder.produto);

        holder.tipoAcao.setText(acoes.get(position).getTipo());

        Long quantidade = "retirada".equalsIgnoreCase(acoes.get(position).getTipo()) ? acoes.get(position).getQuantidadeRetirada() : acoes.get(position).getQuantidadeDevolvida();

        if(quantidade == null){
            quantidade = acoes.get(position).getQuantidade();
        }

        holder.quantidade.setText(quantidade != null ? quantidade.toString() : "");

        holder.data.setText(acoes.get(position).getData());
        holder.hora.setText(acoes.get(position).getHora());

        holder.status.setText(acoes.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return acoes.size();
    }
}
