package com.example.appnexsolar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UsuarioRepositorio {
    private DatabaseReference usuariosReference;

    public UsuarioRepositorio() {
        super();
        usuariosReference = FirebaseDatabase.getInstance().getReference("usuarios");
    }

    public Usuario getUsuarioPorEmail(String email) {
        return new Usuario();
    }
}
