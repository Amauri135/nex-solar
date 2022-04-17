package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.nextoque.databinding.FragmentEditarProdutoBinding;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.google.android.material.navigation.NavigationView;

public class EditarProdutoFragment extends Fragment {
    private FragmentEditarProdutoBinding binding;
    private final Usuario usuario;
    private Produto produto;
    private final NavigationView navigationView;

    public EditarProdutoFragment(Usuario usuario, Produto produto, NavigationView navigationView) {
        this.usuario = usuario;
        this.produto = produto;
        this.navigationView = navigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditarProdutoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
