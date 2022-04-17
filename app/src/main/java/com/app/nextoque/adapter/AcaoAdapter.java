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
import com.app.nextoque.enums.StatusRetiradaEnum;
import com.app.nextoque.enums.TipoAcaoEnum;
import com.app.nextoque.model.ProdutoBO;

import java.util.List;
import java.util.Locale;

public class AcaoAdapter extends RecyclerView.Adapter<AcaoViewHolder> {
    private List<Acao> acoes;
    private Context context;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;

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

        holder.tipoAcao.setText(TipoAcaoEnum.valueOf(acoes.get(position).getTipo().toUpperCase(Locale.ROOT)).getLabel());

        Long quantidade = "retirada".equalsIgnoreCase(acoes.get(position).getTipo()) ? acoes.get(position).getQuantidadeRetirada() : acoes.get(position).getQuantidadeDevolvida();

        if(quantidade == null){
            quantidade = acoes.get(position).getQuantidade();
        }

        holder.quantidade.setText(quantidade != null ? quantidade.toString() : "");

        holder.data.setText(acoes.get(position).getData());
        holder.hora.setText(acoes.get(position).getHora());

        String status = acoes.get(position).getStatus();

        if(status != null) {
            holder.status.setText(StatusRetiradaEnum.valueOf(status.toUpperCase()).getLabel());
        }
    }

    @Override
    public int getItemCount() {
        return acoes.size();
    }
}
