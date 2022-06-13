package com.app.nextoque.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.app.nextoque.Utils;
import com.app.nextoque.databinding.FragmentVerFotosBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.FotoBO;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class VerFotosFragment extends Fragment {
    private FragmentVerFotosBinding binding;
    private final Usuario usuario;
    private final String idProduto;
    private final NavigationView navigationView;
    private static final int SELECT_PICTURES = 200;
    private List<String> fotosPathList;

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

        fotosPathList = new ArrayList<>();

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

        Utils.verifyStoragePermission(getActivity());

        binding.btnAdicionarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(i, "Select Pictures"), SELECT_PICTURES);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURES) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.

                        for (int i = 0; i < count; i++) {
                            Uri imageUri = data.getClipData().getItemAt(i).getUri();

                            fotosPathList.add(Utils.getPathFromUri(getContext(), imageUri));

                            new FotoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).adicionarFotosProduto(fotosPathList, idProduto, navigationView);
                        }

                    } else if (data.getData() != null) {
                        Uri imageUri = data.getData();

                        fotosPathList.add(Utils.getPathFromUri(getContext(), imageUri));

                        new FotoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).adicionarFotosProduto(fotosPathList, idProduto, navigationView);
                    }
                }
            }
        }
    }

    private void esconderNavigationView() {
        if(navigationView.getVisibility() == View.VISIBLE) {
            navigationView.setVisibility(View.GONE);
        }
    }
}
