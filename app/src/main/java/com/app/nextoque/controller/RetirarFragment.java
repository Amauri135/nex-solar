package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.nextoque.databinding.FragmentRetirarProdutoBinding;
import com.app.nextoque.entity.Obra;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ObraRepositorio;
import com.app.nextoque.model.ProdutoRepositorio;
import com.google.android.material.navigation.NavigationView;

public class RetirarFragment extends Fragment {
    private FragmentRetirarProdutoBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRetirarProdutoBinding.inflate(inflater, container, false);

        new ProdutoRepositorio(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarProdutos(binding.spinnerProdutos);

        new ObraRepositorio(getContext(), usuario).buscarObras(binding.spinnerObras);

        binding.spinnerProdutos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.formularioRetiradaProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProdutoRepositorio(getContext(), usuario, getActivity().getSupportFragmentManager()).retirarProduto(
                        (Produto) binding.spinnerProdutos.getSelectedItem(),
                        Integer.parseInt(binding.quantidade.getEditText().getText().toString()),
                        (Obra) binding.spinnerObras.getSelectedItem(),
                        binding.observacao.getEditText().getText().toString()
                );
            }
        });

        return binding.getRoot();
    }

    public RetirarFragment(NavigationView navigationView, Usuario usuario) {
        this.navigationView = navigationView;
        this.usuario = usuario;
    }
}
