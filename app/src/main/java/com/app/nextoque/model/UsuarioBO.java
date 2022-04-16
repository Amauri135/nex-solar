package com.app.nextoque.model;

import android.widget.TextView;

import com.app.nextoque.entity.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioBO {
    private final DatabaseReference usuariosReference = FirebaseDatabase.getInstance().getReference("usuarios");
    private Usuario usuario;

    public UsuarioBO() {

    }

    public UsuarioBO(Usuario usuario) {
        this.usuario = usuario;
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
}
