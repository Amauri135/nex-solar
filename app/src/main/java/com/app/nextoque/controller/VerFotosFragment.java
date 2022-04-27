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
import com.app.nextoque.databinding.FragmentVerFotosBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.FotoBO;
import com.google.android.material.navigation.NavigationView;

public class VerFotosFragment extends Fragment {
    private FragmentVerFotosBinding binding;
    private final Usuario usuario;
    private final String idProduto;
    private final NavigationView navigationView;

    public VerFotosFragment(Usuario usuario, String idProduto, NavigationView navigationView) {
        this.usuario = usuario;
        this.navigationView = navigationView;
        this.idProduto = idProduto;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVerFotosBinding.inflate(inflater, container, false);

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("VER FOTOS");

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.progressBarVerFotos.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerViewVerFotos.setLayoutManager(layoutManager);

        new FotoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarFotosProduto(binding.recyclerViewVerFotos, idProduto, binding.progressBarVerFotos, navigationView);

        return binding.getRoot();
    }

    private void esconderNavigationView() {
        if(navigationView.getVisibility() == View.VISIBLE) {
            navigationView.setVisibility(View.GONE);
        }
    }
}
