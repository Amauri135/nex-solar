package com.app.nextoque.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.enums.TipoUsuarioEnum;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class LiberarAcessosAdapter extends RecyclerView.Adapter<LiberarAcessosViewHolder> {
    private List<Usuario> usuarios;
    private Context context;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;
    private final NavigationView navigationView;

    public LiberarAcessosAdapter(List<Usuario> usuarios, Context context ,Usuario usuario, FragmentManager fragmentManager, NavigationView navigationView) {
        this.usuarios = usuarios;
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        this.navigationView = navigationView;
    }

    @NonNull
    @Override
    public LiberarAcessosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_liberar_acessos, parent, false);

        return new LiberarAcessosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiberarAcessosViewHolder holder, int position) {
        Usuario solicitacao = usuarios.get(position);

        holder.getNome().setText(solicitacao.getNome());

        if(TipoUsuarioEnum.COLABORADOR.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())) {
            holder.getTipoRequisicao().setText(TipoUsuarioEnum.COLABORADOR.toString());
        }

        if(TipoUsuarioEnum.ADMINISTRADOR.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())) {
            holder.getTipoRequisicao().setText(TipoUsuarioEnum.ADMINISTRADOR.toString());
        }

        if(TipoUsuarioEnum.DIRETOR.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())) {
            holder.getTipoRequisicao().setText(TipoUsuarioEnum.DIRETOR.toString());
        }

        if(TipoUsuarioEnum.DEV.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())) {
            holder.getTipoRequisicao().setText(TipoUsuarioEnum.DEV.toString());
        }

        holder.getEmail().setText(usuario.getEmail());

        holder.getAceitar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Liberar o acesso
                    // - fazer tipo_atual := tipo_requisicao
                    // - permanecer no fragment de liberar acessos
            }
        });

        holder.getRecusar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //excluir a solicitacao do banco de dados
                //permanecer no fragment de liberar acessos
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
