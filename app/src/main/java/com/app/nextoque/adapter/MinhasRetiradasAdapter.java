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
import com.app.nextoque.model.ObraRepositorio;
import com.app.nextoque.model.ProdutoRepositorio;

import java.util.List;

public class MinhasRetiradasAdapter extends RecyclerView.Adapter<MinhasRetiradasViewHolder> {
    private List<Acao> minhasRetiradas;
    private Context context;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;

    public MinhasRetiradasAdapter(List<Acao> minhasRetiradas, Context context, Usuario usuario, FragmentManager fragmentManager){
        this.minhasRetiradas = minhasRetiradas;
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
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

        new ProdutoRepositorio(context, usuario, fragmentManager).buscarNomeProduto(retirada.getIdProduto(), holder.produto);

        Long qtRetirada =  retirada.getQuantidadeRetirada();
        Long qtDevolvida = retirada.getQuantidadeDevolvida();

        holder.qtRetirada.setText(qtRetirada != null ? qtRetirada.toString() : "");
        holder.qtDevolvida.setText(qtDevolvida != null ? qtDevolvida.toString() : "");

        new ObraRepositorio(context, usuario).buscarNomeObra(retirada.getIdObra(), holder.obra);

        holder.data.setText(retirada.getData());
        holder.hora.setText(retirada.getHora());

        holder.obs.setText(retirada.getObservacao());

        if(retirada.getObservacao() == null || retirada.getObservacao().trim().isEmpty()){
            holder.obs.setText("-");
        }

        if("pendente".equals(retirada.getStatus())) {
            holder.status.setText("Devolver");
            holder.status.setTextColor(context.getResources().getColor(R.color.amarelo));
            holder.status.setBackgroundResource(R.drawable.border_background_amarelo);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new DevolverProdutoFragment(usuario, retirada))
                            .addToBackStack("fromMinhasRetiradasToDevolverProduto")
                            .commit();
                }
            });
        } else if("devolvido".equals(retirada.getStatus())){
            holder.status.setText("Devolvido");
            holder.status.setTextColor(context.getResources().getColor(R.color.azul));
            holder.status.setBackgroundResource(R.drawable.border_background_azul);
        } else if("usado_em_obra".equals(retirada.getStatus())){
            holder.status.setText("Usado em obra");
            holder.status.setTextColor(context.getResources().getColor(R.color.vermelho));
            holder.status.setBackgroundResource(R.drawable.border_background_vermelho);
        } else {
            holder.status.setText("Inconsistente");
            holder.status.setTextColor(context.getResources().getColor(R.color.vermelho));
            holder.status.setBackgroundResource(R.drawable.border_background_vermelho);
        }
    }

    @Override
    public int getItemCount() {
        return minhasRetiradas.size();
    }
}
