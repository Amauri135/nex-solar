package com.example.appnexsolar;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.HashMap;

public class Filial {

    @Exclude
    private String id;

    @PropertyName("cnpj")
    private String cnpj;

    @PropertyName("data")
    private String data;

    @PropertyName("localizacao")
    private HashMap<String,String> localizacao;

    @PropertyName("responsavel_filial")
    private String responsavelFilial;

    public Filial() {

    }

    public Filial(String cnpj, String data, HashMap<String, String> localizacao, String responsavel_filial) {
        this.cnpj = cnpj;
        this.data = data;
        this.localizacao = localizacao;
        this.responsavelFilial = responsavel_filial;
    }

    @PropertyName("cnpj")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @PropertyName("data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @PropertyName("localizacao")
    public HashMap<String, String> getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(HashMap<String, String> localizacao) {
        this.localizacao = localizacao;
    }

    @PropertyName("responsavel_filial")
    public String getResponsavelFilial() {
        return responsavelFilial;
    }

    public void setResponsavelFilial(String responsavelFilial) {
        this.responsavelFilial = responsavelFilial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return localizacao.get("cidade") + ", " + localizacao.get("estado") + " - " + localizacao.get("pais");
    }
}
