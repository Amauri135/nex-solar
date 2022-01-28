package com.example.appnexsolar;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcaoRepositorio {

    private Context context;
    private DatabaseReference acaoReference;
    private String idFilialUsuario;

    public AcaoRepositorio(Context context, String idFilialUsuario) {
        this.context = context;
        this.idFilialUsuario = idFilialUsuario;
        this.acaoReference = FirebaseDatabase.getInstance().getReference("filiais/" + idFilialUsuario + "/estoque/acoes");
    }

    public void registrarRetirada(Produto produto, String nomeUsuario, Integer quantidade) {
        Acao retirada = new Acao();

        Date dataAtual = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(dataAtual);
        String hora = timeFormat.format(dataAtual);

        retirada.setData(data);
        retirada.setHora(hora);
        retirada.setIdProduto(produto.getId());
        retirada.setRealizadaPor(nomeUsuario);
        retirada.setObservacao("");
        retirada.setTipo("Retirada");
        retirada.setQuantidadeRetirada(quantidade.longValue());

        registrarAcao(retirada);
    }

    public void registrarDevolucao(Produto produto, String nomeUsuario, Integer quantidade) {
        Acao devolucao = new Acao();

        Date dataAtual = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(dataAtual);
        String hora = timeFormat.format(dataAtual);

        devolucao.setData(data);
        devolucao.setHora(hora);
        devolucao.setIdProduto(produto.getId());
        devolucao.setRealizadaPor(nomeUsuario);
        devolucao.setObservacao("");
        devolucao.setTipo("Devolução");
        devolucao.setQuantidadeDevolvida(quantidade.longValue());

        registrarAcao(devolucao);
    }

    private void registrarAcao(Acao acao) {
        acaoReference.push().setValue(acao).addOnSuccessListener(command -> {
            Toast.makeText(context, acao.getTipo() + " cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        });
    }

    public void buscarRetiradasPendentes(TextView retiradasPendentesView) {
        List<Acao> retiradasPendentes = new ArrayList<>();

        acaoReference
                .orderByChild("tipo")
                .equalTo("retirada")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Acao retirada = child.getValue(Acao.class);

                                if ("pendente".equalsIgnoreCase(retirada.getStatus())) {
                                    retiradasPendentes.add(retirada);
                                }
                            }

                            retiradasPendentesView.setText(String.valueOf(retiradasPendentes.size()));
                        }
                    }
                });

    }

    public void buscarUltimasAcoes(RecyclerView ultimasAcoesView){
        List<Acao> ultimasAcoesList = new ArrayList<>();
        AcaoAdapter acaoAdapter = new AcaoAdapter(ultimasAcoesList, context, idFilialUsuario);

        ultimasAcoesView.setAdapter(acaoAdapter);

        acaoReference
                .orderByKey()
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.hasChildren()){
                            List<Acao> acoesList = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                acoesList.add(child.getValue(Acao.class));
                            }

                            for(int i = 0; i < 5 && (acoesList.size()-i > 0); i++){
                                ultimasAcoesList.add(acoesList.get(acoesList.size()-1-i));
                                acaoAdapter.notifyItemInserted(ultimasAcoesList.size()-1);
                            }
                        }
                    }
                });
    }
}
