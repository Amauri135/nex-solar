package com.example.appnexsolar;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AcaoRepositorio {

    private Context context;

    public AcaoRepositorio(Context context) {
        this.context = context;
    }

    public void registrarRetirada(Produto produto, Usuario usuario, Integer quantidade) {
        Acao retirada = new Acao();

        Date dataAtual = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(dataAtual);
        String hora = timeFormat.format(dataAtual);

        retirada.setData(data);
        retirada.setHora(hora);
        retirada.setIdProduto(produto.getId());
        retirada.setRealizadaPor(usuario.getNome());
        retirada.setObservacao("");
        retirada.setTipo("Retirada");
        retirada.setQuantidade(quantidade.longValue());

        registrarAcao(retirada, usuario.getIdFilial());
    }

    public void registrarDevolucao(Produto produto, Usuario usuario, Integer quantidade) {
        Acao devolucao = new Acao();

        Date dataAtual = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(dataAtual);
        String hora = timeFormat.format(dataAtual);

        devolucao.setData(data);
        devolucao.setHora(hora);
        devolucao.setIdProduto(produto.getId());
        devolucao.setRealizadaPor(usuario.getNome());
        devolucao.setObservacao("");
        devolucao.setTipo("Devolução");
        devolucao.setQuantidade(quantidade.longValue());

        registrarAcao(devolucao, usuario.getIdFilial());
    }

    private void registrarAcao(Acao acao, String idFilialUsuario) {
        String acaoPath = "filiais/" + idFilialUsuario + "/estoque/acoes";
        DatabaseReference acaoReference = FirebaseDatabase.getInstance().getReference(acaoPath);

        acaoReference.push().setValue(acao).addOnSuccessListener(command -> {
            Toast.makeText(context, acao.getTipo() + " cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        });
    }
}
