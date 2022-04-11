package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.nextoque.databinding.FragmentMinhasRetiradasBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.AcaoRepositorio;
import com.google.android.material.navigation.NavigationView;

public class MinhasRetiradasFragment extends Fragment {
    private FragmentMinhasRetiradasBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;

    public MinhasRetiradasFragment(NavigationView navigationView, Usuario usuario) {
        this.navigationView = navigationView;
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMinhasRetiradasBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        binding.listMinhasRetiradas.setLayoutManager(layoutManager);

        new AcaoRepositorio(getContext(), usuario.getIdFilial()).buscarMinhasRetiradas(binding.listMinhasRetiradas, usuario.getId());

        return binding.getRoot();
    }
}
