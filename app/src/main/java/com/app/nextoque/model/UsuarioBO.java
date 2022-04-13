package com.app.nextoque.model;

import com.app.nextoque.entity.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioBO {
    private DatabaseReference usuariosReference;

    public UsuarioBO() {
        super();
        usuariosReference = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public Usuario getUsuarioPorEmail(String email) {
        return new Usuario();
    }
}
