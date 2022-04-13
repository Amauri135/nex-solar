package com.app.nextoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.controller.DevolverProdutoFragment;
import com.app.nextoque.entity.Acao;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.enums.StatusRetiradaEnum;
import com.app.nextoque.model.ObraBO;
import com.app.nextoque.model.ProdutoBO;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MinhasRetiradasAdapter extends RecyclerView.Adapter<MinhasRetiradasViewHolder> {
    private List<Acao> minhasRetiradas;
    private Context context;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;
    private final NavigationView navigationView;

    public MinhasRetiradasAdapter(List<Acao> minhasRetiradas, Context context, Usuario usuario, FragmentManager fragmentManager, NavigationView navigationView){
        this.minhasRetiradas = minhasRetiradas;
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        this.navigationView = navigationView;
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

        new ProdutoBO(context, usuario, fragmentManager).buscarNomeProduto(retirada.getIdProduto(), holder.produto);

        Long qtRetirada =  retirada.getQuantidadeRetirada();
        Long qtDevolvida = retirada.getQuantidadeDevolvida();

        holder.qtRetirada.setText(qtRetirada != null ? qtRetirada.toString() : "");
        holder.qtDevolvida.setText(qtDevolvida != null ? qtDevolvida.toString() : "");

        new ObraBO(context, usuario).buscarNomeObra(retirada.getIdObra(), holder.obra);

        holder.data.setText(retirada.getData());
        holder.hora.setText(retirada.getHora());

        holder.obs.setText(retirada.getObservacao());

        if(retirada.getObservacao() == null || retirada.getObservacao().trim().isEmpty()){
            holder.obs.setText("-");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        if(StatusRetiradaEnum.PENDENTE.toString().equals(retirada.getStatus())) {
            holder.status.setText(StatusRetiradaEnum.PENDENTE.getLabel());
            holder.status.setTextColor(context.getResources().getColor(R.color.amarelo));
            holder.status.setBackgroundResource(R.drawable.border_background_amarelo);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(navigationView.getVisibility() == View.VISIBLE){
                        navigationView.setVisibility(View.GONE);
                    } else {
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, new DevolverProdutoFragment(usuario, retirada, navigationView))
                                .addToBackStack("fromMinhasRetiradasToDevolverProduto")
                                .commit();
                    }
                }
            });
        } else if(StatusRetiradaEnum.DEVOLVIDO.toString().equals(retirada.getStatus())){
            holder.status.setText(StatusRetiradaEnum.DEVOLVIDO.getLabel());
            holder.status.setTextColor(context.getResources().getColor(R.color.azul));
            holder.status.setBackgroundResource(R.drawable.border_background_azul);
        } else if(StatusRetiradaEnum.USADO_EM_OBRA.toString().equals(retirada.getStatus())){
            holder.status.setText(StatusRetiradaEnum.USADO_EM_OBRA.getLabel());
            holder.status.setTextColor(context.getResources().getColor(R.color.vermelho));
            holder.status.setBackgroundResource(R.drawable.border_background_vermelho);
        } else {
            holder.status.setText(StatusRetiradaEnum.INCONSISTENTE.getLabel());
            holder.status.setTextColor(context.getResources().getColor(R.color.vermelho));
            holder.status.setBackgroundResource(R.drawable.border_background_vermelho);
        }
    }

    @Override
    public int getItemCount() {
        return minhasRetiradas.size();
    }
}
