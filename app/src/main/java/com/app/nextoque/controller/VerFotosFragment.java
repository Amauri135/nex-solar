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
import com.app.nextoque.databinding.FragmentVerFotosBinding;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.FotoBO;
import com.google.android.material.navigation.NavigationView;

public class VerFotosFragment extends Fragment {
    private FragmentVerFotosBinding binding;
    private final Usuario usuario;
    private final String idProduto;
    private final NavigationView navigationView;
   // private ListResult listResult;

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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerViewVerFotos.setLayoutManager(layoutManager);

        new FotoBO(getContext(), usuario, getActivity().getSupportFragmentManager()).buscarFotosProduto(binding.recyclerViewVerFotos, idProduto);

//        new
//
////        Toolbar toolbar = new Toolbar(getContext());
////
////        Toolbar teste = (Toolbar) inflater.inflate(R.layout.action_bar_fotos, container).findViewById(R.id.toolbar);
////        toolbar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////        toolbar.setTitle("Fotos");
////        toolbar.setTitleTextColor(Color.WHITE);
////        toolbar.setBackgroundColor(Color.parseColor("#201F1F"));
////        toolbar.inflateMenu(R.menu.menu_acoes_produto);
////
////        binding.getRoot().addView(teste);
//
//        List<Uri> listUriFotos = new ArrayList<>();
//        FotoAdapter fotoAdapter = new FotoAdapter(getContext(), listUriFotos);
//
//        for(StorageReference fotoProduto : listResult.getItems()){
//            fotoProduto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    listUriFotos.add(uri);
//                    fotoAdapter.notifyItemInserted(listUriFotos.size()-1);
////                    ImageView foto = new ImageView(getContext());
////                    tableVerFotos.setGravity(Gravity.CENTER_VERTICAL);
////                    foto.setPadding(10,10,10,10);
////                    foto.setAdjustViewBounds(true);
////                    foto.setBackgroundColor(Color.BLACK);
////
////                    tableVerFotos.addView(Glide.with(getContext()).load(uri).into(foto).getView());
//                }
//            });
//        }
//


        return binding.getRoot();
    }
}
