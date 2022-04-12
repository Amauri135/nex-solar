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

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer quantidade =
                        binding.quantidade.getEditText() != null ?
                                Integer.parseInt(binding.quantidade.getEditText().getText().toString()) :
                                null;

                String obs =
                        binding.observacao.getEditText() != null ?
                                binding.observacao.getEditText().getText().toString() :
                                null;

                new ProdutoRepositorio(getContext(), usuario, getActivity().getSupportFragmentManager())
                        .devolverProduto(quantidade, obs, retirada);
            }
        });

        return binding.getRoot();
    }
}
