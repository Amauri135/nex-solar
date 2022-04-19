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
import com.app.nextoque.databinding.FragmentMinhasRetiradasBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.AcaoBO;
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

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("MINHAS RETIRADAS");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.listMinhasRetiradas.setLayoutManager(layoutManager);

        new AcaoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarMinhasRetiradas(binding.listMinhasRetiradas, navigationView);

        return binding.getRoot();
    }
}
