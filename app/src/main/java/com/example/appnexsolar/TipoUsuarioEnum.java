package com.example.appnexsolar;

import androidx.annotation.NonNull;

public enum TipoUsuarioEnum {
    DIRETOR {
        @NonNull
        @Override
        public String toString() {
            return "Diretor";
        }
    },
    ADMINISTRADOR {
        @NonNull
        @Override
        public String toString() {
            return "Administrador";
        }
    },
    COLABORADOR {
        @NonNull
        @Override
        public String toString() {
            return "Colaborador";
        }
    },
    NOVO {
        @NonNull
        @Override
        public String toString() {
            return "Novo";
        }
    };
}
