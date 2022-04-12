package com.app.nextoque.model;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.app.nextoque.R;
import com.app.nextoque.entity.Acao;
import com.app.nextoque.entity.Obra;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositorio {

    private final Context context;
    private DatabaseReference produtoReference;
    private final FragmentManager fragmentManager;
    private final Usuario usuario;

    public ProdutoRepositorio(Context context, Usuario usuario, FragmentManager fragmentManager) {
        this.context = context;
        this.usuario = usuario;
        this.produtoReference = FirebaseDatabase.getInstance().getReference("filiais/" + usuario.getIdFilial() + "/estoque/produtos/");
        this.fragmentManager = fragmentManager;
    }

    public void retirarProduto(Produto produto, Integer quantidade, Obra obra, String obs) {
        String produtoPath = "filiais/" + usuario.getIdFilial() + "/estoque/produtos/" + produto.getId();
        DatabaseReference produtoReference = FirebaseDatabase.getInstance().getReference(produtoPath);

        produto.setQuantidadeAtual(produto.getQuantidadeAtual()-quantidade);

        produtoReference.setValue(produto);

        new AcaoRepositorio(context, usuario, fragmentManager).registrarRetirada(produto, quantidade, obra.getId(), obs);
    }

    public void devolverProduto(Integer quantidade, String obs, Acao retirada) {
        if (quantidade == null || quantidade == 0L) {
            Toast.makeText(context, "A quantidade é obrigatória e deve ser maior do que 0 (zero)!", Toast.LENGTH_SHORT).show();
        } else if(quantidade > retirada.getQuantidadeRetirada() - retirada.getQuantidadeDevolvida()) {
            Toast.makeText(context, "A quantidade inserida é maior do que a permitida para devolução desse produto!", Toast.LENGTH_SHORT).show();
        } else {
            String produtoPath = "filiais/" + usuario.getIdFilial() + "/estoque/produtos/" + retirada.getIdProduto();
            DatabaseReference produtoReference = FirebaseDatabase.getInstance().getReference(produtoPath);

            produtoReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        Produto produto = dataSnapshot.getValue(Produto.class);

                        produto.setId(dataSnapshot.getKey());

                        produto.setQuantidadeAtual(produto.getQuantidadeAtual()+quantidade);

                        produtoReference.setValue(produto).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                new AcaoRepositorio(context, usuario, fragmentManager).registrarDevolucao(produto, quantidade, obs, retirada);
                            }
                        });
                    }
                }
            });
        }
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

    public void buscarProdutos(Spinner spinnerProdutos) {

        produtoReference.get().addOnCompleteListener(runnable ->  {
            try{
                if(runnable.isSuccessful()) {
                    List<Object> listProduto = new ArrayList<>();

                    listProduto.add("");

                    for (DataSnapshot child : runnable.getResult().getChildren()) {
                        Produto produto = child.getValue(Produto.class);
                        produto.setId(child.getKey());
                        listProduto.add(produto);
                    }

                    ArrayAdapter<Object> adapter = new ArrayAdapter<>(context, R.layout.list_item_spinner, listProduto);
                    spinnerProdutos.setAdapter(adapter);
                } else if(runnable.isCanceled()){
                    Toast.makeText(context, "O carregamento dos produtos foi interrompido.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Ocorreu uma falha no carregamento dos produtos.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Ocorreu uma exceção ao carregar os produtos: " + e.getCause().toString(), Toast.LENGTH_LONG).show();
            }
        });


//        List<Produto> produtos = new ArrayList<>();
//
//        ArrayAdapter produtoAdapter = new ArrayAdapter<String>(context, R.layout.list_item_spinner);

//        produtoAdapter.add("");
////        DatabaseReference estoqueReference = FirebaseDatabase.getInstance().getReference("filiais/-MlrWiX0mZJogkqzQJZ5/estoque/produtos");
//
//        produtoReference.orderByKey().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists() && snapshot.hasChildren()){
//                    for(DataSnapshot child : snapshot.getChildren()){
//                        Produto produto = child.getValue(Produto.class);
//
//                        if(produto != null){
//                            produto.setId(child.getKey());
//                            produtos.add(produto);
//                            produtoAdapter.add(produto.getDescricao()+" - "+produto.getQuantidadeAtual().toString()+" "+produto.getUnidadeMedida());
//                        }
//
//                    }
//
//                    spinnerProdutos.setAdapter(produtoAdapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(context, "O carregamento dos produtos foi interrompido.", Toast.LENGTH_LONG).show();
//            }
//        });
    }
}
