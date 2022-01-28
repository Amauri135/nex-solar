package com.example.appnexsolar;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AcaoViewHolder extends RecyclerView.ViewHolder {
    TextView produto;
    TextView tipoAcao;
    TextView quantidade;
    TextView data;
    TextView hora;
    TextView status;

    public AcaoViewHolder(@NonNull View itemView) {
        super(itemView);

        produto = itemView.findViewById(R.id.prod_ultima_acao);
        tipoAcao = itemView.findViewById(R.id.tipo_ultima_acao);
        quantidade = itemView.findViewById(R.id.qtd_ultima_acao);
        data = itemView.findViewById(R.id.data_ultima_acao);
        hora = itemView.findViewById(R.id.hora_ultima_acao);
        status = itemView.findViewById(R.id.status_ultima_acao);
    }
}
