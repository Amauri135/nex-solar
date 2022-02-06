package com.example.appnexsolar;

import com.google.firebase.database.PropertyName;

public class Obra {

    @PropertyName("cidade_obra")
    private String cidadeObra;

    @PropertyName("data")
    private String data;

    @PropertyName("email")
    private String email;

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

    @PropertyName("telefone")
    private String telefone;

    public Obra() {

    }

    public Obra(String cidadeObra, String data, String email, String enderecoObra, String hora, String idUsuario, String nomeObra, String responsavel, String telefone) {
        this.cidadeObra = cidadeObra;
        this.data = data;
        this.email = email;
        this.enderecoObra = enderecoObra;
        this.hora = hora;
        this.idUsuario = idUsuario;
        this.nomeObra = nomeObra;
        this.responsavel = responsavel;
        this.telefone = telefone;
    }

    public String getCidadeObra() {
        return cidadeObra;
    }

    public void setCidadeObra(String cidadeObra) {
        this.cidadeObra = cidadeObra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnderecoObra() {
        return enderecoObra;
    }

    public void setEnderecoObra(String enderecoObra) {
        this.enderecoObra = enderecoObra;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
