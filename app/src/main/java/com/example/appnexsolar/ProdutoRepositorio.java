package com.example.appnexsolar;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProdutoRepositorio {

    private Context context;
    private DatabaseReference produtoReference;

    public ProdutoRepositorio(Context context, String idFilialUsuario) {
        this.context = context;
        this.produtoReference = FirebaseDatabase.getInstance().getReference("filiais/" + idFilialUsuario + "/estoque/produtos/");
    }

    public void retirarProduto(Produto produto, Integer quantidade, Usuario usuario) {
        String produtoPath = "filiais/" + usuario.getIdFilial() + "/estoque/produtos/" + produto.getId();
        DatabaseReference produtoReference = FirebaseDatabase.getInstance().getReference(produtoPath);

        produto.setQuantidadeAtual(produto.getQuantidadeAtual()-quantidade);

        produtoReference.setValue(produto);

        new AcaoRepositorio(context, usuario.getIdFilial()).registrarRetirada(produto, usuario.getNome(), quantidade);
    }

    public void devolverProduto(Produto produto, Integer quantidade, Usuario usuario) {
        String produtoPath = "filiais/" + usuario.getIdFilial() + "/estoque/produtos/" + produto.getId();
        DatabaseReference produtoReference = FirebaseDatabase.getInstance().getReference(produtoPath);

        produto.setQuantidadeAtual(produto.getQuantidadeAtual()+quantidade);

        produtoReference.setValue(produto);

        new AcaoRepositorio(context, usuario.getIdFilial()).registrarDevolucao(produto, usuario.getNome(), quantidade);
    }

    public void buscarNomeProduto(String idProduto, TextView nomeProdutoView){
        produtoReference.child(idProduto).child("descricao").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String nomeProduto = dataSnapshot.getValue(String.class);

                nomeProdutoView.setText(nomeProduto);
            }
        });
    }

    public void salvarProduto(Produto produto) {
        produtoReference.push().setValue(produto)
                .addOnSuccessListener(command -> Toast.makeText(context, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(command -> Toast.makeText(context, "Ocorreu uma falha ao salvar o produto.", Toast.LENGTH_SHORT).show());
    }
}
