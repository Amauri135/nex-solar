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

import com.app.nextoque.R;
import com.app.nextoque.databinding.FragmentListarProdutosBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;
import com.google.android.material.navigation.NavigationView;

public class ListarProdutosFragment extends Fragment {

    private FragmentListarProdutosBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;

    public ListarProdutosFragment(Usuario usuario, NavigationView navigationView) {
        this.usuario = usuario;
        this.navigationView = navigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListarProdutosBinding.inflate(inflater, container, false);

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("PRODUTOS");

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.recyclerViewEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerViewEstoque.setLayoutManager(layoutManager);

        new ProdutoBO(getContext(), usuario, getActivity().getSupportFragmentManager())
                .buscarProdutosListarProdutos(binding.recyclerViewEstoque, navigationView);

        return binding.getRoot();
    }

}
