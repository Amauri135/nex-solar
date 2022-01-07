package com.example.appnexsolar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoViewHolder> {

    private List<Produto> produtos;
    private Context context;
    private TextView viewMenuAcoes;

    private Usuario usuario;

    public ProdutoAdapter(Context context, List<Produto> produtos, Usuario usuario, TextView viewMenuAcoes) {
        this.context = context;
        this.produtos = produtos;
        this.usuario = usuario;
        this.viewMenuAcoes = viewMenuAcoes;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produto_sem_foto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {

        holder.textViewDescricaoProduto.setText(produtos.get(position).getDescricao());
        holder.textViewQtdAtual.setText(produtos.get(position).getQuantidadeAtual().toString());
        holder.textViewQtdInicial.setText(produtos.get(position).getQuantidadeInicial().toString());

        PopupMenu popupMenu = new PopupMenu(context, viewMenuAcoes);
        popupMenu.inflate(R.menu.menu_acoes_produto);

        popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            Produto produto = produtos.get(holder.getAdapterPosition());

            switch (menuItem.getTitle().toString()) {
                case "Retirar":
                    MaterialAlertDialogBuilder builderRetirada = new MaterialAlertDialogBuilder(context);

                    builderRetirada.setTitle("Retirar produto");
                    builderRetirada.setMessage("Informe a quantidade");

                    final NumberPicker numberPickerRetirada = new NumberPicker(context);
                    numberPickerRetirada.setMinValue(1);
                    numberPickerRetirada.setMaxValue(produto.getQuantidadeAtual().intValue());

                    builderRetirada.setView(numberPickerRetirada);

                    builderRetirada.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ProdutoRepositorio(context).retirarProduto(produto, numberPickerRetirada.getValue(), usuario);
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builderRetirada.create();
                    builderRetirada.show();

                    break;

                case "Devolver":
                    MaterialAlertDialogBuilder builderDevolucao = new MaterialAlertDialogBuilder(context);

                    builderDevolucao.setTitle("Devolver produto");
                    builderDevolucao.setMessage("Informe a quantidade");

                    final NumberPicker numberPickerDevolucao = new NumberPicker(context);
                    numberPickerDevolucao.setMinValue(1);
                    numberPickerDevolucao.setMaxValue(produto.getQuantidadeInicial().intValue() - produto.getQuantidadeAtual().intValue());

                    builderDevolucao.setView(numberPickerDevolucao);

                    builderDevolucao.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new ProdutoRepositorio(context).devolverProduto(produto, numberPickerDevolucao.getValue(), usuario);
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builderDevolucao.create();
                    builderDevolucao.show();

                    break;

                case "Ver Fotos":
                    StorageReference imageReference = FirebaseStorage.getInstance().getReference();
                    imageReference.child("filiais/" + usuario.getIdFilial() + "/produtos/" + produtos.get(position).getId() + "/").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                        @Override
                        public void onSuccess(ListResult listResult) {
                            if (listResult != null && !listResult.getItems().isEmpty()) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("listResult", (ArrayList<StorageReference>) listResult.getItems());

                                Fragment fragment = FragmentManager.findFragment(holder.itemView);
                                fragment.getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VerFotosFragment(listResult)).addToBackStack("fromEstoqueToVerFotos").commit();
                            } else {
                                Toast.makeText(context, "Não há fotos cadastradas para esse produto.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                default:
                    break;
            }

            return false;
        });

        holder.itemView.setOnClickListener(v -> popupMenu.show());
       /* if(TipoUsuarioEnum.ADMINISTRADOR.toString().equals(usuario.getTipoAtual())){
            popupMenu.getMenu().getItem(0).setVisible(false);
        }*/
        //holder.getImageButtonMenuProduto().setOnClickListener(view -> {
        //    popupMenu.show();
       // });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
