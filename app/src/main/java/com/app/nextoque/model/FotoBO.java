package com.app.nextoque.model;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.adapter.VerFotosAdapter;
import com.app.nextoque.controller.NovoProdutoFragment;
import com.app.nextoque.controller.VerFotosFragment;
import com.app.nextoque.entity.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    public void buscarFotosProduto(RecyclerView recyclerViewVerFotos, String idProduto, ProgressBar progressBar, NavigationView navigationView){
        StorageReference fotosProdutoReference = fotoReference.child("/produtos/" + idProduto);

        List<Uri> urisFotos = new ArrayList<>();

        VerFotosAdapter verFotosAdapter = new VerFotosAdapter(context, urisFotos, usuario, recyclerViewVerFotos, fragmentManager, navigationView);

        recyclerViewVerFotos.setAdapter(verFotosAdapter);

        fotosProdutoReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if(listResult != null && !listResult.getItems().isEmpty()){

                    for(StorageReference storageReference : listResult.getItems()){
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urisFotos.add(uri);

                                verFotosAdapter.notifyItemInserted(urisFotos.size()-1);
                            }
                        });
                    }

                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(context, "Esse produto não possui fotos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void salvarFotosProduto(List<String> fotosPathList, String idProduto, NavigationView navigationView, Button btnSalvar) {
        StorageReference fotosProdutoReference = fotoReference.child("/produtos/" + idProduto);

        for(String fotoPath : fotosPathList) {
            File file = new File(fotoPath);

            StorageReference imgReference = fotosProdutoReference.child(file.getName());

            try {
                InputStream stream = new FileInputStream(file);

                imgReference.putStream(stream).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Falha ao fazer upload do arquivo " + file.getName(), Toast.LENGTH_SHORT).show();
                        btnSalvar.setClickable(true);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "Produto cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, new NovoProdutoFragment(navigationView, usuario))
                                .commit();
                    }
                });
            } catch (FileNotFoundException e){
                Toast.makeText(context, "Arquivo " + file.getName() + " não encontrado!", Toast.LENGTH_SHORT).show();
                btnSalvar.setClickable(true);
            }

        }
    }

    public void adicionarFotosProduto(List<String> fotosPathList, String idProduto, NavigationView navigationView) {
        StorageReference fotosProdutoReference = fotoReference.child("/produtos/" + idProduto);

        for(String fotoPath : fotosPathList) {
            File file = new File(fotoPath);

            StorageReference imgReference = fotosProdutoReference.child(file.getName());

            try {
                InputStream stream = new FileInputStream(file);

                imgReference.putStream(stream).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Falha ao fazer upload do arquivo " + file.getName(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(context, "Foto(s) cadastrada(s) com sucesso!", Toast.LENGTH_SHORT).show();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_layout, new VerFotosFragment(usuario, idProduto, navigationView))
                                .commit();
                    }
                });
            } catch (FileNotFoundException e){
                Toast.makeText(context, "Arquivo " + file.getName() + " não encontrado!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void excluirFotosProduto(String idProduto) {
        fotoReference.child("/produtos/" + idProduto).listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                if(listResult != null && !listResult.getItems().isEmpty()) {
                    for(StorageReference reference : listResult.getItems()) {
                        reference.delete();
                    }
                }
            }
        });
    }

    public void excluirFoto(@NonNull Uri uri, List<Uri> uriList, VerFotosAdapter adapter, int position) {
        FirebaseStorage.getInstance().getReferenceFromUrl(uri.toString()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Foto excluída com sucesso!", Toast.LENGTH_SHORT).show();

                uriList.remove(position);

                adapter.notifyItemRemoved(position);
            }
        });
    }

}
