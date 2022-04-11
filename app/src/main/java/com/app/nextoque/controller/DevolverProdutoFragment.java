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
import com.app.nextoque.model.ProdutoRepositorio;

public class DevolverProdutoFragment extends Fragment {
    private FragmentDevolverProdutoBinding binding;
    private final Usuario usuario;
    private final Acao retirada;

    public DevolverProdutoFragment(Usuario usuario, Acao retirada) {
        this.usuario = usuario;
        this.retirada = retirada;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDevolverProdutoBinding.inflate(inflater, container, false);

        new ProdutoRepositorio(getContext(), usuario, getActivity().getSupportFragmentManager())
                .buscarNomeProduto(retirada.getIdProduto(), binding.prodDevolverProduto.getEditText());

        return binding.getRoot();
    }
}
