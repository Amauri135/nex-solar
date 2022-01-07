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
    private ImageButton imgBtnVerEstoque;
    private ImageButton imgBtnRetirar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container,false);

        imgBtnVerEstoque = binding.imgBtnVerEstoque;
        imgBtnRetirar = binding.imgBtnRetirar;

        imgBtnVerEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EstoqueFragment()).addToBackStack("fromDashToEstoque").commit();
            }
        });

        imgBtnRetirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new CadastrarProdutoFragment())
                        .addToBackStack("fromDashToCadastrarProduto")
                        .commit();
            }
        });

        return binding.getRoot();
    }
}
