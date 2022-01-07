package com.example.appnexsolar;

import com.google.firebase.database.PropertyName;

public class Acao {

    @PropertyName("data")
    private String data;

    @PropertyName("hora")
    private String hora;

    @PropertyName("id_prod")
    private String idProduto;

    @PropertyName("obs")
    private String observacao;

    @PropertyName("realizada_por")
    private String realizadaPor;

    @PropertyName("tipo")
    private String tipo;

    @PropertyName("quantidade")
    private Long quantidade;

    public Acao() {}

    public Acao(String data, String hora, String idProduto, String observacao, String realizadaPor, String tipo, Long quantidade) {
        this.data = data;
        this.hora = hora;
        this.idProduto = idProduto;
        this.observacao = observacao;
        this.realizadaPor = realizadaPor;
        this.tipo = tipo;
        this.quantidade = quantidade;
    }

    @PropertyName("data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @PropertyName("hora")
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    @PropertyName("id_prod")
    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    @PropertyName("obs")
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @PropertyName("realizada_por")
    public String getRealizadaPor() {
        return realizadaPor;
    }

    public void setRealizadaPor(String realizadaPor) {
        this.realizadaPor = realizadaPor;
    }

    @PropertyName("tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @PropertyName("quantidade")
    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
