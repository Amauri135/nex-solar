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
import com.app.nextoque.databinding.FragmentLiberarAcessosBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.UsuarioBO;
import com.google.android.material.navigation.NavigationView;

public class LiberarAcessosFragment extends Fragment {
    private FragmentLiberarAcessosBinding binding;
    private final Usuario usuario;
    private final NavigationView navigationView;

    public LiberarAcessosFragment(Usuario usuario, NavigationView navigationView) {
        this.usuario = usuario;
        this.navigationView = navigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLiberarAcessosBinding.inflate(inflater, container, false);

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("LIBERAR ACESSOS");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerViewLiberarAcessos.setLayoutManager(layoutManager);

        new UsuarioBO(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarSolicitacoesDeAcesso(binding.recyclerViewLiberarAcessos, navigationView);

        return binding.getRoot();
    }

    private void esconderNavigationView() {
        if(navigationView.getVisibility() == View.VISIBLE) {
            navigationView.setVisibility(View.GONE);
        }
    }
}
