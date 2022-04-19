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
import com.app.nextoque.databinding.FragmentCadastrarProdutoBinding;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NovoProdutoFragment extends Fragment {
    private FragmentCadastrarProdutoBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;

    public NovoProdutoFragment(NavigationView navigationView, Usuario usuario) {
        this.navigationView = navigationView;
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCadastrarProdutoBinding.inflate(inflater, container, false);

        TextView titulo = (TextView) getActivity().findViewById(R.id.titulo);

        titulo.setText("NOVO PRODUTO");

        binding.formularioCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto();
            }
        });

        return binding.getRoot();
    }

    private void salvarProduto() {
        if (binding.descricao.getEditText().getText() == null ||
                binding.categoria.getEditText().getText() == null ||
                binding.unidadeMedida.getEditText().getText() == null ||
                binding.quantidade.getEditText().getText() == null) {

            Toast.makeText(getContext(), "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
        } else {
            Produto produto = new Produto();

            produto.setDescricao(binding.descricao.getEditText().getText().toString());
            produto.setCategoria(binding.categoria.getEditText().getText().toString());
            produto.setUnidadeMedida(binding.unidadeMedida.getEditText().getText().toString());

            Long quantidade = Long.valueOf(binding.quantidade.getEditText().getText().toString());

            produto.setQuantidadeInicial(quantidade);
            produto.setQuantidadeAtual(quantidade);

            produto.setIdUsuario(FirebaseAuth.getInstance().getUid());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

            Date data = Calendar.getInstance().getTime();

            produto.setData(dateFormat.format(data));
            produto.setHora(timeFormat.format(data));

            produto.setObs(binding.observacao.getEditText().getText() != null ? binding.observacao.getEditText().getText().toString() : null);

            new ProdutoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).salvarProduto(produto, navigationView);
        }
    }

}
