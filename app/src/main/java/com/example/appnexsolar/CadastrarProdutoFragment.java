package com.example.appnexsolar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnexsolar.databinding.FragmentCadastrarProdutoBinding;

public class CadastrarProdutoFragment extends Fragment {
    private FragmentCadastrarProdutoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCadastrarProdutoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}
