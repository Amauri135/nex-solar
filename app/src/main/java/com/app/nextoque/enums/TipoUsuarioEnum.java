package com.app.nextoque.enums;

public enum TipoUsuarioEnum {

    DIRETOR("Diretor"),
    ADMINISTRADOR("Administrador"),
    COLABORADOR("Colaborador"),
    NOVO("Novo"),
    DEV("Dev");

    private String descricao;

    TipoUsuarioEnum(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
