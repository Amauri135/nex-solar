package com.app.nextoque.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.FotoBO;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class VerFotosAdapter extends RecyclerView.Adapter<VerFotosViewHolder> {
    private List<Uri> urisFotos;
    private Context context;
    private final Usuario usuario;
    private final NavigationView navigationView;
    private final FragmentManager fragmentManager;
    private final RecyclerView recyclerView;

    public VerFotosAdapter(Context context, List<Uri> urisFotos, Usuario usuario, RecyclerView recyclerView, FragmentManager fragmentManager, NavigationView navigationView) {
        this.context = context;
        this.urisFotos = urisFotos;
        this.usuario = usuario;
        this.recyclerView = recyclerView;
        this.fragmentManager = fragmentManager;
        this.navigationView = navigationView;
    }

    @NonNull
    @Override
    public VerFotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_ver_fotos, parent, false);

        return new VerFotosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerFotosViewHolder holder, int position) {
        Uri uriFoto = urisFotos.get(holder.getAdapterPosition());

        Glide.with(context).load(uriFoto).into(holder.getFoto());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Deseja excluir essa foto? A ação não poderá ser desfeita.");

                builder.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new FotoBO(context, usuario, fragmentManager).excluirFoto(uriFoto, urisFotos, VerFotosAdapter.this, holder.getAdapterPosition());
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return urisFotos.size();
    }
}
