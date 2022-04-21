package com.app.nextoque.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class LiberarAcessosViewHolder extends RecyclerView.ViewHolder {
    private TextView nome;
    private TextView tipoRequisicao;
    private TextView email;
    private TextView aceitar;
    private TextView recusar;

    public LiberarAcessosViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.nome_liberar_acessos);
        tipoRequisicao = itemView.findViewById(R.id.tipo_requisicao_liberar_acessos);
        email = itemView.findViewById(R.id.email_liberar_acessos);
        aceitar = itemView.findViewById(R.id.aceitar_liberar_acessos);
        recusar = itemView.findViewById(R.id.recusar_liberar_acessos);
    }

    public TextView getNome() {
        return nome;
    }

    public TextView getTipoRequisicao() {
        return tipoRequisicao;
    }

    public TextView getEmail() {
        return email;
    }

    public TextView getAceitar() {
        return aceitar;
    }

    public TextView getRecusar() {
        return recusar;
    }
}
