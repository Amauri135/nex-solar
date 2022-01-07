package com.example.appnexsolar;

import com.google.firebase.database.PropertyName;

public class Usuario {

    @PropertyName("data_criacao")
    private String dataCriacao;

    @PropertyName("email")
    private String email;

    @PropertyName("hora_criacao")
    private String horaCriacao;

    @PropertyName("id_filial")
    private String idFilial;

    @PropertyName("name")
    private String nome;

    @PropertyName("tipo_atual")
    private String tipoAtual;

    @PropertyName("tipo_requisicao")
    private String tipoRequisicao;

    public Usuario() {

    }

    public Usuario(String dataCriacao, String email, String horaCriacao, String idFilial, String nome, String tipoAtual, String tipoRequisicao) {
        this.dataCriacao = dataCriacao;
        this.email = email;
        this.horaCriacao = horaCriacao;
        this.idFilial = idFilial;
        this.nome = nome;
        this.tipoAtual = tipoAtual;
        this.tipoRequisicao = tipoRequisicao;
    }

    @PropertyName("data_criacao")
    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("hora_criacao")
    public String getHoraCriacao() {
        return horaCriacao;
    }

    public void setHoraCriacao(String horaCriacao) {
        this.horaCriacao = horaCriacao;
    }

    @PropertyName("id_filial")
    public String getIdFilial() {
        return idFilial;
    }

    public void setIdFilial(String idFilial) {
        this.idFilial = idFilial;
    }

    @PropertyName("nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @PropertyName("tipo_atual")
    public String getTipoAtual() {
        return tipoAtual;
    }

    public void setTipoAtual(String tipoAtual) {
        this.tipoAtual = tipoAtual;
    }

    @PropertyName("tipo_requisicao")
    public String getTipoRequisicao() {
        return tipoRequisicao;
    }

    public void setTipoRequisicao(String tipoRequisicao) {
        this.tipoRequisicao = tipoRequisicao;
    }
}
