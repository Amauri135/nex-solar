package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.nextoque.databinding.FragmentDevolverProdutoBinding;
import com.app.nextoque.entity.Acao;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;
import com.google.android.material.navigation.NavigationView;

public class DevolverProdutoFragment extends Fragment {
    private FragmentDevolverProdutoBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;
    private final Acao retirada;

    public DevolverProdutoFragment(Usuario usuario, Acao retirada, NavigationView navigationView) {
        this.usuario = usuario;
        this.retirada = retirada;
        this.navigationView = navigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDevolverProdutoBinding.inflate(inflater, container, false);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.prodDevolverProduto.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.quantidade.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.observacao.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        new ProdutoBO(getContext(), usuario, getActivity().getSupportFragmentManager())
                .buscarNomeProduto(retirada.getIdProduto(), binding.prodDevolverProduto.getEditText());

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                } else {
                    Integer quantidade =
                            binding.quantidade.getEditText() == null || binding.quantidade.getEditText().toString().trim().isEmpty() ?
                                    Integer.parseInt(binding.quantidade.getEditText().getText().toString()) :
                                    null;

                    String obs =
                            binding.observacao.getEditText() != null ?
                                    binding.observacao.getEditText().getText().toString() :
                                    null;

                    new ProdutoBO(getContext(), usuario, getActivity().getSupportFragmentManager())
                            .devolverProduto(quantidade, obs, retirada);
                }
            }
        });

        return binding.getRoot();
    }
}
