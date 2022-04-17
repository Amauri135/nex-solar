package com.app.nextoque.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.controller.EditarProdutoFragment;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.model.ProdutoBO;
import com.app.nextoque.model.UsuarioBO;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ListarProdutosAdapter extends RecyclerView.Adapter<ListarProdutosViewHolder> {
    private List<Produto> produtos;
    private Context context;
    private final NavigationView navigationView;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;

    public ListarProdutosAdapter(Context context, List<Produto> produtos, Usuario usuario, FragmentManager fragmentManager, NavigationView navigationView) {
        this.context = context;
        this.produtos = produtos;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        this.navigationView = navigationView;
    }

    @NonNull
    @Override
    public ListarProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_listar_produtos, parent, false);
        return new ListarProdutosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListarProdutosViewHolder holder, int position) {
        if(position % 2 == 1) {
            ((CardView) holder.itemView.findViewById(R.id.cardViewListarProdutos)).setCardBackgroundColor(context.getResources().getColor(R.color.cinza_claro));
        } else {
            ((CardView) holder.itemView.findViewById(R.id.cardViewListarProdutos)).setCardBackgroundColor(context.getResources().getColor(R.color.cinza_escuro));
        }

        Produto produto = produtos.get(position);

        holder.getProduto().setText(produto.getDescricao());
        holder.getCategoria().setText(produto.getCategoria());
        holder.getQtInicial().setText(produto.getQuantidadeInicial() != null ? produto.getQuantidadeInicial().toString() : null);
        holder.getQtAtual().setText(produto.getQuantidadeAtual() != null ? produto.getQuantidadeAtual().toString() : null);
        holder.getDataCadastro().setText(produto.getData());
        holder.getHoraCadastro().setText(produto.getHora());
        new UsuarioBO().buscarNomeUsuario(holder.getCadastradoPor(), produto.getIdUsuario());
        holder.getObs().setText(produto.getObs() == null || produto.getObs().trim().isEmpty() ? "-" : produto.getObs());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(navigationView.getVisibility() == View.VISIBLE){
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        holder.getBtnExcluirProduto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("Essa ação não poderá ser desfeita! Deseja mesmo excluir?");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new ProdutoBO(context, usuario, fragmentManager).excluirProduto(produto.getId(), navigationView);
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
            }
        });

        holder.getBtnAlterarProduto().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new EditarProdutoFragment(usuario, produto, navigationView))
                        .addToBackStack("fromListarProdutos(Editar)toEditarProduto")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }
}
