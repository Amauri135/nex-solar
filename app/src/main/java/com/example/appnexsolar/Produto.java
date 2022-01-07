package com.example.appnexsolar;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class Produto {

    @Exclude
    private String id;

    @PropertyName("data")
    private String data;

    @PropertyName("hora")
    private String hora;

    @PropertyName("cadastrado_por")
    private String cadastradoPor;

    @PropertyName("tipo")
    private String tipo;

    @PropertyName("descricao")
    private String descricao;

    @PropertyName("qt_inicial")
    private Long quantidadeInicial;

    @PropertyName("qt_atual")
    private Long quantidadeAtual;

    @PropertyName("categoria")
    private String categoria;

    @PropertyName("unidade_medida")
    private String unidadeMedida;

    public Produto() {

    }

    public Produto(String data, String hora, String cadastradoPor, String tipo, String descricao, Long quantidadeInicial, Long quantidadeAtual, String categoria, String unidadeMedida) {
        this.data = data;
        this.hora = hora;
        this.cadastradoPor = cadastradoPor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.quantidadeInicial = quantidadeInicial;
        this.quantidadeAtual = quantidadeAtual;
        this.categoria = categoria;
        this.unidadeMedida = unidadeMedida;
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

    @PropertyName("cadastrado_por")
    public String getCadastradoPor() {
        return cadastradoPor;
    }

    public void setCadastradoPor(String cadastradoPor) {
        this.cadastradoPor = cadastradoPor;
    }

    @PropertyName("tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @PropertyName("descricao")
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @PropertyName("qt_inicial")
    public Long getQuantidadeInicial() {
        return quantidadeInicial;
    }

    public void setQuantidadeInicial(Long quantidadeInicial) {
        this.quantidadeInicial = quantidadeInicial;
    }

    @PropertyName("qt_atual")
    public Long getQuantidadeAtual() {
        return quantidadeAtual;
    }

    public void setQuantidadeAtual(Long quantidadeAtual) {
        this.quantidadeAtual = quantidadeAtual;
    }

    @PropertyName("categoria")
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @PropertyName("unidade_medida")
    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
