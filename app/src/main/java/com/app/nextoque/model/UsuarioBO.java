package com.app.nextoque.model;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.adapter.LiberarAcessosAdapter;
import com.app.nextoque.controller.LiberarAcessosFragment;
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
        this.usuariosReference  = FirebaseDatabase.getInstance().getReference("usuarios");
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

                        solicitacao.setId(child.getKey());

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

    public void liberarAcesso(Usuario solicitacao, NavigationView navigationView) {
        solicitacao.setTipoAtual(solicitacao.getTipoRequisicao());

        usuariosReference.child(solicitacao.getId()).setValue(solicitacao).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Acesso liberado com sucesso!", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new LiberarAcessosFragment(usuario, navigationView))
                        .commit();
            }
        });
    }

    public void recusarAcesso(Usuario solicitacao, NavigationView navigationView) {
        usuariosReference.child(solicitacao.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Acesso recusado com sucesso!", Toast.LENGTH_SHORT).show();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new LiberarAcessosFragment(usuario, navigationView))
                        .commit();
            }
        });
    }
}
