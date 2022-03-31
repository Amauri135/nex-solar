package com.app.nextoque.controller;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.adapter.FotoAdapter;
import com.app.nextoque.databinding.FragmentVerFotosBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class VerFotosFragment extends Fragment {
    private FragmentVerFotosBinding binding;
    private ListResult listResult;
    private RecyclerView rvFotos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVerFotosBinding.inflate(inflater, container, false);

        rvFotos = binding.recyclerViewFotos;

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);

        rvFotos.setLayoutManager(layoutManager);

//        Toolbar toolbar = new Toolbar(getContext());
//
//        Toolbar teste = (Toolbar) inflater.inflate(R.layout.action_bar_fotos, container).findViewById(R.id.toolbar);
//        toolbar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        toolbar.setTitle("Fotos");
//        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setBackgroundColor(Color.parseColor("#201F1F"));
//        toolbar.inflateMenu(R.menu.menu_acoes_produto);
//
//        binding.getRoot().addView(teste);

        List<Uri> listUriFotos = new ArrayList<>();
        FotoAdapter fotoAdapter = new FotoAdapter(getContext(), listUriFotos);

        rvFotos.setAdapter(fotoAdapter);

        for(StorageReference fotoProduto : listResult.getItems()){
            fotoProduto.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    listUriFotos.add(uri);
                    fotoAdapter.notifyItemInserted(listUriFotos.size()-1);
//                    ImageView foto = new ImageView(getContext());
//                    tableVerFotos.setGravity(Gravity.CENTER_VERTICAL);
//                    foto.setPadding(10,10,10,10);
//                    foto.setAdjustViewBounds(true);
//                    foto.setBackgroundColor(Color.BLACK);
//
//                    tableVerFotos.addView(Glide.with(getContext()).load(uri).into(foto).getView());
                }
            });
        }



        return binding.getRoot();
    }

    public VerFotosFragment() {
    }

    public VerFotosFragment(ListResult listResult) {
        this.listResult = listResult;
    }
}
