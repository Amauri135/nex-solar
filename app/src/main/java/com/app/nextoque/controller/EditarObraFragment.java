package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.nextoque.R;
import com.app.nextoque.databinding.FragmentEditarObraBinding;
import com.app.nextoque.entity.Obra;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ObraBO;
import com.google.android.material.navigation.NavigationView;

public class EditarObraFragment extends Fragment {
    private FragmentEditarObraBinding binding;
    private final Usuario usuario;
    private final NavigationView navigationView;
    private Obra obra;

    public EditarObraFragment(Usuario usuario, Obra obra, NavigationView navigationView) {
        this.usuario = usuario;
        this.obra = obra;
        this.navigationView = navigationView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditarObraBinding.inflate(inflater, container, false);

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("EDITAR OBRA");

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.formularioEditarObra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.endereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.cidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.responsavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.observacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esconderNavigationView();
            }
        });

        binding.nome.getEditText().setText(obra.getNomeObra());
        binding.endereco.getEditText().setText(obra.getEnderecoObra());
        binding.cidade.getEditText().setText(obra.getCidadeObra());
        binding.responsavel.getEditText().setText(obra.getResponsavel());

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = binding.nome.getEditText().getText() != null
                        && !binding.nome.getEditText().getText().toString().isEmpty() ?
                        binding.nome.getEditText().getText().toString()
                        : null;

                String endereco = binding.endereco.getEditText().getText() != null
                        && !binding.endereco.getEditText().getText().toString().isEmpty() ?
                        binding.endereco.getEditText().getText().toString() :
                        null;

                String cidade = binding.cidade.getEditText().getText() != null
                        && !binding.cidade.getEditText().getText().toString().isEmpty() ?
                        binding.cidade.getEditText().getText().toString() :
                        null;

                String responsavel = binding.responsavel.getEditText().getText() != null
                        && !binding.responsavel.getEditText().getText().toString().isEmpty() ?
                        binding.responsavel.getEditText().getText().toString() :
                        null;

                String obsAlteracao = binding.observacao.getEditText().getText() != null ?
                        binding.observacao.getEditText().getText().toString() :
                        null;

                if(nome == null || endereco == null || cidade == null || responsavel == null) {
                    Toast.makeText(getContext(), "Informe todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
                } else {
                    obra.setNomeObra(nome);
                    obra.setEnderecoObra(endereco);
                    obra.setCidadeObra(cidade);
                    obra.setResponsavel(responsavel);

                    new ObraBO(getContext(), usuario, getActivity().getSupportFragmentManager()).editarObra(obra, obsAlteracao);
                }
            }
        });

        return binding.getRoot();
    }

    private void esconderNavigationView() {
        if(navigationView.getVisibility() == View.VISIBLE) {
            navigationView.setVisibility(View.GONE);
        }
    }
}
