package com.example.appnexsolar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnexsolar.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private ImageButton btnRetirar;
    private ImageButton btnDevolver;
    private ImageButton btnAlterar;
    private ImageButton btnCadastrar;
    private ImageButton btnVerEstoque;
    private ImageButton btnUltimasAcoes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container,false);

        btnRetirar = binding.imgBtnRetirar;
        btnDevolver = binding.imgBtnDevolver;
        btnAlterar = binding.imgBtnAlterar;
        btnCadastrar = binding.imgBtnCadastrar;
        btnVerEstoque = binding.imgBtnVerEstoque;
        btnUltimasAcoes = binding.imgBtnUltimasAcoes;

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CadastrarProdutoFragment())
                        .addToBackStack("fromDashToCadastrarProduto")
                        .commit();
            }
        });

        btnVerEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VerEstoqueFragment()).addToBackStack("fromDashToEstoque").commit();
            }
        });

        return binding.getRoot();
    }
}
