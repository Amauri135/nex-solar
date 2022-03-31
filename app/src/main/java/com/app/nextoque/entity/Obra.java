package com.app.nextoque.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class Obra {

    @PropertyName("cidade_obra")
    private String cidadeObra;

    @PropertyName("data")
    private String data;

    @PropertyName("endereco_obra")
    private String enderecoObra;

    @PropertyName("hora")
    private String hora;

    @PropertyName("id_usuario")
    private String idUsuario;

    @PropertyName("nome_obra")
    private String nomeObra;

    @PropertyName("responsavel")
    private String responsavel;

    @Exclude
    private String id;

    public Obra() {

    }

    public Obra(String cidadeObra, String data, String enderecoObra, String hora, String idUsuario, String nomeObra, String responsavel
    ) {
        this.cidadeObra = cidadeObra;
        this.data = data;
        this.enderecoObra = enderecoObra;
        this.hora = hora;
        this.idUsuario = idUsuario;
        this.nomeObra = nomeObra;
        this.responsavel = responsavel;
    }

    @PropertyName("cidade_obra")
    public String getCidadeObra() {
        return cidadeObra;
    }

    public void setCidadeObra(String cidadeObra) {
        this.cidadeObra = cidadeObra;
    }

    @PropertyName("data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @PropertyName("endereco_obra")
    public String getEnderecoObra() {
        return enderecoObra;
    }

    public void setEnderecoObra(String enderecoObra) {
        this.enderecoObra = enderecoObra;
    }

    @PropertyName("hora")
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @PropertyName("id_usuario")
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @PropertyName("nome_obra")
    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    @PropertyName("responsavel")
    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.nomeObra + " - " + this.enderecoObra;
    }
}
