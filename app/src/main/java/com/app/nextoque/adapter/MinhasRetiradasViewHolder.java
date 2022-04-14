package com.app.nextoque.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;

public class MinhasRetiradasViewHolder extends RecyclerView.ViewHolder {
    private final TextView produto;
    private final TextView obra;
    private final TextView qtRetirada;
    private final TextView qtDevolvida;
    private final TextView data;
    private final TextView hora;
    private final TextView obs;
    private final TextView status;

    public MinhasRetiradasViewHolder(@NonNull View itemView) {
        super(itemView);

        produto = itemView.findViewById(R.id.prod_minhas_retiradas);
        obra = itemView.findViewById(R.id.obra_minhas_retiradas);
        qtRetirada = itemView.findViewById(R.id.qtRetirada_minhas_retiradas);
        qtDevolvida = itemView.findViewById(R.id.qtDevolvida_minhas_retiradas);
        data = itemView.findViewById(R.id.data_minhas_retiradas);
        hora = itemView.findViewById(R.id.hora_minhas_retiradas);
        obs = itemView.findViewById(R.id.obs_minhas_retiradas);
        status = itemView.findViewById(R.id.status_minha_retirada);
    }

    public TextView getProduto() {
        return produto;
    }

    public TextView getObra() {
        return obra;
    }

    public TextView getQtRetirada() {
        return qtRetirada;
    }

    public TextView getQtDevolvida() {
        return qtDevolvida;
    }

    public TextView getData() {
        return data;
    }

    public TextView getHora() {
        return hora;
    }

    public TextView getObs() {
        return obs;
    }

    public TextView getStatus() {
        return status;
    }
}
