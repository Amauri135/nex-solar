package com.app.nextoque.controller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.app.nextoque.Utils;
import com.app.nextoque.databinding.FragmentCadastrarProdutoBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NovoProdutoFragment extends Fragment {
    private static final int SELECT_PICTURES = 200;
    private FragmentCadastrarProdutoBinding binding;
    private final NavigationView navigationView;
    private final Usuario usuario;
    private List<String> fotosPathList;

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

        fotosPathList = new ArrayList<>();

        Utils.verifyStoragePermission(getActivity());

        binding.formularioCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (navigationView.getVisibility() == View.VISIBLE) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.btnSelecionarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                i.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(i, "Select Pictures"), SELECT_PICTURES);
            }
        });

        binding.salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.salvar.setClickable(false);
                salvarProduto();
            }
        });

        return binding.getRoot();
    }

    private void salvarProduto() {
        if (binding.descricao.getEditText().getText() == null ||
                binding.descricao.getEditText().getText().toString().trim().isEmpty() ||
                binding.categoria.getEditText().getText() == null ||
                binding.categoria.getEditText().getText().toString().trim().isEmpty() ||
                binding.unidadeMedida.getEditText().getText() == null ||
                binding.unidadeMedida.getEditText().getText().toString().trim().isEmpty() ||
                binding.quantidade.getEditText().getText() == null ||
                binding.quantidade.getEditText().getText().toString().trim().isEmpty()) {

            Toast.makeText(getContext(), "Preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> params =  new HashMap<>();

            params.put("descricao", binding.descricao.getEditText().getText().toString());
            params.put("categoria", binding.categoria.getEditText().getText().toString());
            params.put("unidadeMedida", binding.unidadeMedida.getEditText().getText().toString());

            Long quantidade = Long.valueOf(binding.quantidade.getEditText().getText().toString());

            params.put("quantidade", quantidade);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

            Date data = Calendar.getInstance().getTime();

            params.put("data", dateFormat.format(data));
            params.put("hora", timeFormat.format(data));
            params.put("obs", binding.observacao.getEditText().getText() != null ? binding.observacao.getEditText().getText().toString() : null);

            params.put("idUsuario", FirebaseAuth.getInstance().getUid());

            new ProdutoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).salvarProduto(params, fotosPathList, navigationView, binding.salvar);

        }
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
                        }

                        binding.fotosSelecionadas.setText(count + " fotos selecionadas");
                    } else if (data.getData() != null) {
                        Uri imageUri = data.getData();

                        fotosPathList.add(Utils.getPathFromUri(getContext(), imageUri));

                        binding.fotosSelecionadas.setText("1 foto selecionada");
                    }
                }
            }
        }
    }
}
