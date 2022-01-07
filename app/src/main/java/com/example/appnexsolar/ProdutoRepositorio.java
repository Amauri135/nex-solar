package com.example.appnexsolar;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProdutoRepositorio {

    private Context context;

    public ProdutoRepositorio(Context context) {
        this.context = context;
    }

    public void retirarProduto(Produto produto, Integer quantidade, Usuario usuario) {
        String produtoPath = "filiais/" + usuario.getIdFilial() + "/estoque/produtos/" + produto.getId();
        DatabaseReference produtoReference = FirebaseDatabase.getInstance().getReference(produtoPath);

        produto.setQuantidadeAtual(produto.getQuantidadeAtual()-quantidade);

        produtoReference.setValue(produto);

        new AcaoRepositorio(context).registrarRetirada(produto, usuario, quantidade);
    }

    public void devolverProduto(Produto produto, Integer quantidade, Usuario usuario) {
        String produtoPath = "filiais/" + usuario.getIdFilial() + "/estoque/produtos/" + produto.getId();
        DatabaseReference produtoReference = FirebaseDatabase.getInstance().getReference(produtoPath);

        produto.setQuantidadeAtual(produto.getQuantidadeAtual()+quantidade);

        produtoReference.setValue(produto);

        new AcaoRepositorio(context).registrarDevolucao(produto, usuario, quantidade);
    }
}
