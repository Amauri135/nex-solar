package com.app.nextoque.model;

import android.content.Context;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.adapter.LiberarAcessosAdapter;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.enums.TipoUsuarioEnum;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsuarioBO {
    private Context context;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;
    private final DatabaseReference usuariosReference;

    public UsuarioBO(Context context, Usuario usuario, FragmentManager fragmentManager) {
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        usuariosReference  = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public void buscarNomeUsuario(TextView nomeUsuarioView, String idUsuario) {
        usuariosReference.child(idUsuario).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                    nomeUsuarioView.setText(usuario.getNome());
                }
            }
        });
    }

    public void buscarSolicitacoesDeAcesso(RecyclerView recyclerViewLiberarAcessos, NavigationView navigationView) {
        List<Usuario> usuarios = new ArrayList<>();

        LiberarAcessosAdapter liberarAcessosAdapter = new LiberarAcessosAdapter(usuarios, context, usuario, fragmentManager, navigationView);

        recyclerViewLiberarAcessos.setAdapter(liberarAcessosAdapter);

        usuariosReference.orderByChild("tipo_atual").equalTo("novo").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    for(DataSnapshot child : dataSnapshot.getChildren()) {
                        Usuario solicitacao = child.getValue(Usuario.class);

                        if(TipoUsuarioEnum.DEV.toString().toLowerCase().equals(usuario.getTipoAtual())){
                            usuarios.add(solicitacao);
                            liberarAcessosAdapter.notifyItemInserted(usuarios.size()-1);
                        } else if(TipoUsuarioEnum.DIRETOR.toString().equals(usuario.getTipoAtual())) {
                            if(TipoUsuarioEnum.ADMINISTRADOR.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())
                                || TipoUsuarioEnum.COLABORADOR.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())) {
                                usuarios.add(solicitacao);
                                liberarAcessosAdapter.notifyItemInserted(usuarios.size()-1);
                            }
                        } else {
                            if(TipoUsuarioEnum.COLABORADOR.toString().toLowerCase().equals(solicitacao.getTipoRequisicao())) {
                                usuarios.add(solicitacao);
                                liberarAcessosAdapter.notifyItemInserted(usuarios.size()-1);
                            }
                        }
                    }
                }
            }
        });
    }
}
