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
import com.app.nextoque.databinding.FragmentListarObrasBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ObraBO;
import com.google.android.material.navigation.NavigationView;

public class ListarObrasFragment extends Fragment {
    private FragmentListarObrasBinding binding;
    private final Usuario usuario;
    private final NavigationView navigationView;

    public ListarObrasFragment(Usuario usuario, NavigationView navigationView) {
        this.usuario = usuario;
        this.navigationView = navigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListarObrasBinding.inflate(inflater, container, false);

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("OBRAS");

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerViewListarObras.setLayoutManager(layoutManager);

        new ObraBO(getContext(), usuario, getActivity().getSupportFragmentManager())
                .buscarObrasListarObras(binding.recyclerViewListarObras, navigationView);

        return binding.getRoot();
    }

    private void esconderNavigationView() {
        if (navigationView.getVisibility() == View.VISIBLE) {
            navigationView.setVisibility(View.GONE);
        }
    }
}
