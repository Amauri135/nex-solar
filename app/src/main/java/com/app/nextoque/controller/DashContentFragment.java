package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.nextoque.R;
import com.app.nextoque.databinding.FragmentDashContentBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.AcaoBO;

public class DashContentFragment extends Fragment {
    private FragmentDashContentBinding binding;
    private final Usuario usuario;

    public DashContentFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentDashContentBinding.inflate(inflater, container, false);

        new AcaoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarRetiradasPendentes(binding.retiradasPendentes);

        binding.listUltimasAcoes.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getActivity().getDrawable(R.drawable.divider));

        binding.listUltimasAcoes.addItemDecoration(divider);

        new AcaoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarUltimasAcoes(binding.listUltimasAcoes);

        return binding.getRoot();
    }

}
