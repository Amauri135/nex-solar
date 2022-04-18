package com.app.nextoque.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class ListarObrasViewHolder extends RecyclerView.ViewHolder {
    private TextView nome;
    private TextView endereco;
    private TextView cidade;
    private TextView responsavel;
    private TextView data;
    private TextView hora;
    private TextView cadastradaPor;

    public ListarObrasViewHolder(@NonNull View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.obra_listar_obras);
        endereco = itemView.findViewById(R.id.endereco_listar_obras);
        cidade = itemView.findViewById(R.id.cidade_listar_obras);
        responsavel = itemView.findViewById(R.id.label_responsavel_listar_obras);
        data = itemView.findViewById(R.id.data_listar_obras);
        hora = itemView.findViewById(R.id.hora_listar_obras);
        cadastradaPor = itemView.findViewById(R.id.cadastrada_por_listar_obras);
    }

    public TextView getNome() {
        return nome;
    }

    public TextView getEndereco() {
        return endereco;
    }

    public TextView getCidade() {
        return cidade;
    }

    public TextView getResponsavel() {
        return responsavel;
    }

    public TextView getData() {
        return data;
    }

    public TextView getHora() {
        return hora;
    }

    public TextView getCadastradaPor() {
        return cadastradaPor;
    }
}
