package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.adapter.ProdutoAdapter;
import com.app.nextoque.databinding.FragmentListarProdutosBinding;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListarProdutosFragment extends Fragment {

    private FragmentListarProdutosBinding binding;
    private RecyclerView recyclerView;
    private TextView viewMenuAcoes;
    private Usuario usuario;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentListarProdutosBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerViewEstoque.setLayoutManager(layoutManager);

//        recyclerView = binding.recyclerViewEstoque;
//        progressBar = binding.progressBar;
//        viewMenuAcoes = binding.ViewMenuAcoes;
//
//        progressBar.setVisibility(View.VISIBLE);
//
//        final DatabaseReference usuariosReference = FirebaseDatabase.getInstance().getReference("usuarios");
//
//        if(firebaseAuth.getCurrentUser() == null){
//            Toast.makeText(getContext(), "Não há usuário logado!", Toast.LENGTH_LONG).show();
//            requireActivity().getSupportFragmentManager().getFragments().clear();
//            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
//        }
//
//        List<Produto> produtos = new ArrayList<>();
//        ProdutoAdapter produtoAdapter = new ProdutoAdapter(getContext(), produtos, usuario, viewMenuAcoes);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(produtoAdapter);
//
//        usuariosReference.child(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    if (task.getResult() != null) {
//                        usuario = task.getResult().getValue(Usuario.class);
//                        produtoAdapter.setUsuario(usuario);
//                        if (usuario != null) {
//                            carregaItens(produtos, produtoAdapter);
//                        }
//                    }
//                }
//            }
//        });

        return binding.getRoot();
    }

    public void carregaItens(List<Produto> produtos, ProdutoAdapter produtoAdapter) {

        DatabaseReference estoqueReference = FirebaseDatabase.getInstance().getReference("filiais/-MlrWiX0mZJogkqzQJZ5/estoque/produtos");

        estoqueReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.hasChildren()){
                    for(DataSnapshot child : snapshot.getChildren()){
                        Produto produto = child.getValue(Produto.class);

                        if(produto != null){
                            produto.setId(child.getKey());
                            produtos.add(produto);
                            produtoAdapter.notifyItemInserted(produtos.size() - 1);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}
