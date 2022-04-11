package com.app.nextoque.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;

public class Acao {

    @Exclude
    private String id;

    @PropertyName("data")
    private String data;

    @PropertyName("hora")
    private String hora;

    @PropertyName("id_prod")
    private String idProduto;

    @PropertyName("obs")
    private String observacao;

    @PropertyName("tipo")
    private String tipo;

    @PropertyName("quantidade_devolvida")
    private Long quantidadeDevolvida;

    @PropertyName("quantidade_retirada")
    private Long quantidadeRetirada;

    @PropertyName("status")
    private String status;

    @PropertyName("devolucoes")
    private HashMap<String,Acao> devolucoes;

    @PropertyName("id_usuario")
    private String idUsuario;

    @PropertyName("id_obra")
    private String idObra;

    @PropertyName("quantidade")
    private Long quantidade;

    public Acao() {}

    public Acao(String data, String hora, String idProduto, String observacao, String tipo, Long quantidadeDevolvida, Long quantidadeRetirada, String status, HashMap<String, Acao> devolucoes, String idUsuario, String idObra) {
        this.data = data;
        this.hora = hora;
        this.idProduto = idProduto;
        this.observacao = observacao;
        this.tipo = tipo;
        this.quantidadeDevolvida = quantidadeDevolvida;
        this.quantidadeRetirada = quantidadeRetirada;
        this.status = status;
        this.devolucoes = devolucoes;
        this.idUsuario = idUsuario;
        this.idObra = idObra;
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

    @PropertyName("tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @PropertyName("quantidade_devolvida")
    public Long getQuantidadeDevolvida() {
        return quantidadeDevolvida;
    }

    public void setQuantidadeDevolvida(Long quantidadeDevolvida) {
        this.quantidadeDevolvida = quantidadeDevolvida;
    }

    @PropertyName("quantidade_retirada")
    public Long getQuantidadeRetirada() {
        return quantidadeRetirada;
    }

    public void setQuantidadeRetirada(Long quantidadeRetirada) {
        this.quantidadeRetirada = quantidadeRetirada;
    }

    @PropertyName("status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @PropertyName("devolucoes")
    public HashMap<String, Acao> getDevolucoes() {
        return devolucoes;
    }

    public void setDevolucoes(HashMap<String, Acao> devolucoes) {
        this.devolucoes = devolucoes;
    }

    @PropertyName("id_usuario")
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    @PropertyName("id_obra")
    public String getIdObra() {
        return idObra;
    }

    public void setIdObra(String idObra) {
        this.idObra = idObra;
    }

    @PropertyName("quantidade")
    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
