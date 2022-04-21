package com.app.nextoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.controller.EditarObraFragment;
import com.app.nextoque.entity.Obra;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.UsuarioBO;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ListarObrasAdapter extends RecyclerView.Adapter<ListarObrasViewHolder> {
    private List<Obra> obras;
    private Context context;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;
    private final NavigationView navigationView;

    public ListarObrasAdapter(Context context, List<Obra> obras, Usuario usuario, FragmentManager fragmentManager, NavigationView navigationView) {
        this.context = context;
        this.obras = obras;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        this.navigationView = navigationView;
    }

    @NonNull
    @Override
    public ListarObrasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_listar_obras, parent, false);

        return new ListarObrasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListarObrasViewHolder holder, int position) {
        if(position % 2 == 1) {
            ((CardView) holder.itemView.findViewById(R.id.cardViewListarObras)).setCardBackgroundColor(context.getResources().getColor(R.color.cinza_claro));
        } else {
            ((CardView) holder.itemView.findViewById(R.id.cardViewListarObras)).setCardBackgroundColor(context.getResources().getColor(R.color.cinza_escuro));
        }

        Obra obra = obras.get(position);

        holder.getNome().setText(obra.getNomeObra());
        holder.getEndereco().setText(obra.getEnderecoObra());
        holder.getCidade().setText(obra.getCidadeObra());
        holder.getResponsavel().setText(obra.getResponsavel());
        holder.getData().setText(obra.getData());
        holder.getHora().setText(obra.getHora());
        new UsuarioBO(context, usuario, fragmentManager).buscarNomeUsuario(holder.getCadastradaPor(), obra.getIdUsuario());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        holder.getBtnAlterarObra().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new EditarObraFragment(usuario, obra, navigationView))
                        .addToBackStack("fromListarObras(Editar)toEditarObra")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return obras.size();
    }
}
