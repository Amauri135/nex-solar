package com.app.nextoque.model;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.nextoque.R;
import com.app.nextoque.adapter.AcaoAdapter;
import com.app.nextoque.adapter.MinhasRetiradasAdapter;
import com.app.nextoque.controller.DashContentFragment;
import com.app.nextoque.entity.Acao;
import com.app.nextoque.entity.Produto;
import com.app.nextoque.entity.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcaoBO {

    private final Context context;
    private DatabaseReference acaoReference;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;

    public AcaoBO(Context context, Usuario usuario, FragmentManager fragmentManager) {
        this.context = context;
        this.acaoReference = FirebaseDatabase.getInstance().getReference("filiais/" + usuario.getIdFilial() + "/estoque/acoes");
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
    }

    public void registrarRetirada(Produto produto, Integer quantidade, String idObra, String obs) {
        Acao retirada = new Acao();

        Date dataAtual = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(dataAtual);
        String hora = timeFormat.format(dataAtual);

        retirada.setData(data);
        retirada.setHora(hora);
        retirada.setIdProduto(produto.getId());
        retirada.setObservacao(obs);
        retirada.setTipo("retirada");
        retirada.setQuantidadeRetirada(quantidade.longValue());
        retirada.setQuantidadeDevolvida(0L);
        retirada.setIdUsuario(usuario.getId());
        retirada.setIdObra(idObra);
        retirada.setStatus("pendente");

        acaoReference.push().setValue(retirada).addOnSuccessListener(command -> {
            Toast.makeText(context, "Retirada cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, new DashContentFragment(usuario))
                    .addToBackStack("fromRetirarToDashContent")
                    .commit();
        });
    }

    public void registrarDevolucao(Produto produto, Integer quantidade, String obs, Acao retirada) {
        Acao devolucao = new Acao();

        Date dataAtual = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String data = dateFormat.format(dataAtual);
        String hora = timeFormat.format(dataAtual);

        devolucao.setData(data);
        devolucao.setHora(hora);
        devolucao.setIdProduto(produto.getId());
        devolucao.setIdUsuario(usuario.getId());
        devolucao.setObservacao(obs);
        devolucao.setTipo("devolucao");
        devolucao.setQuantidadeDevolvida(quantidade.longValue());

        DatabaseReference retiradaReference = acaoReference.child(retirada.getId());

        retiradaReference.child("devolucoes").push().setValue(devolucao).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                retiradaReference.child("quantidade_devolvida")
                        .setValue(retirada.getQuantidadeDevolvida()+quantidade.longValue())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(retirada.getQuantidadeRetirada().equals(retirada.getQuantidadeDevolvida()+quantidade.longValue())){
                                    retiradaReference.child("status").setValue("devolvido");
                                }

                                Toast.makeText(context, "Devolução cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

                                fragmentManager.popBackStack();
                            }
                        });
            }
        });

    }

    public void buscarRetiradasPendentes(TextView retiradasPendentesView) {
        List<Acao> retiradasPendentes = new ArrayList<>();

        acaoReference
                .orderByChild("tipo")
                .equalTo("retirada")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Acao retirada = child.getValue(Acao.class);

                                if ("pendente".equalsIgnoreCase(retirada.getStatus())) {
                                    retiradasPendentes.add(retirada);
                                }
                            }

                            retiradasPendentesView.setText(String.valueOf(retiradasPendentes.size()));
                        }
                    }
                });

    }

    public void buscarUltimasAcoes(RecyclerView ultimasAcoesView){
        List<Acao> ultimasAcoesList = new ArrayList<>();
        AcaoAdapter acaoAdapter = new AcaoAdapter(ultimasAcoesList, context, usuario, fragmentManager);

        ultimasAcoesView.setAdapter(acaoAdapter);

        acaoReference
                .orderByKey()
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists() && dataSnapshot.hasChildren()){
                            List<Acao> acoesList = new ArrayList<>();

                            for(DataSnapshot child : dataSnapshot.getChildren()){
                                acoesList.add(child.getValue(Acao.class));
                            }

                            for(int i = 0; i < 5 && (acoesList.size()-i > 0); i++){
                                ultimasAcoesList.add(acoesList.get(acoesList.size()-1-i));
                                acaoAdapter.notifyItemInserted(ultimasAcoesList.size()-1);
                            }
                        }
                    }
                });
    }

    public void buscarMinhasRetiradas(RecyclerView minhasRetiradasView, NavigationView navigationView) {
        List<Acao> minhasRetiradas = new ArrayList<>();
        MinhasRetiradasAdapter minhasRetiradasAdapter = new MinhasRetiradasAdapter(minhasRetiradas, context, usuario, fragmentManager, navigationView);

        minhasRetiradasView.setAdapter(minhasRetiradasAdapter);

        acaoReference
                .orderByChild("tipo")
                .equalTo("retirada")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            List<Acao> retiradasList = new ArrayList<>();

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                Acao retirada = child.getValue(Acao.class);

                                retirada.setId(child.getKey());

                                if (usuario.getId().equals(retirada.getIdUsuario())) {
                                    retiradasList.add(retirada);
                                }
                            }

                            for(int i = 0; i < retiradasList.size(); i++){
                                minhasRetiradas.add(retiradasList.get(retiradasList.size()-i-1));
                                minhasRetiradasAdapter.notifyItemInserted(i);
                            }
                        }
                    }
                });

    }
}
