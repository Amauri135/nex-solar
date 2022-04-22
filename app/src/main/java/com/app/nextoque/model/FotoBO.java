package com.app.nextoque.model;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.adapter.VerFotosAdapter;
import com.app.nextoque.entity.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FotoBO {
    private Context context;
    private StorageReference fotoReference;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;

    public FotoBO(Context context, Usuario usuario, FragmentManager fragmentManager) {
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        this.fotoReference = FirebaseStorage.getInstance().getReference("filiais/" + usuario.getIdFilial());
    }

    public void buscarFotosProduto(RecyclerView recyclerViewVerFotos, String idProduto){
        StorageReference fotosProdutoReference = fotoReference.child("/produtos/" + idProduto);

        List<Uri> urisFotos = new ArrayList<>();

        VerFotosAdapter verFotosAdapter = new VerFotosAdapter(context, urisFotos);

        recyclerViewVerFotos.setAdapter(verFotosAdapter);

        fotosProdutoReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if(listResult != null && listResult.getItems() != null && !listResult.getItems().isEmpty()){

                    for(StorageReference storageReference : listResult.getItems()){
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urisFotos.add(uri);

                                verFotosAdapter.notifyItemInserted(urisFotos.size()-1);
                            }
                        });
                    }
                }
            }
        });
    }
}
