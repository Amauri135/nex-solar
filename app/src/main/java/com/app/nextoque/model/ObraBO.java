package com.app.nextoque.model;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.app.nextoque.R;
import com.app.nextoque.controller.NovaObraFragment;
import com.app.nextoque.entity.Obra;
import com.app.nextoque.entity.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ObraBO {
    private Context context;
    private DatabaseReference obraReference;
    private final Usuario usuario;
    private final FragmentManager fragmentManager;

    public ObraBO(Context context, Usuario usuario, FragmentManager fragmentManager) {
        this.context = context;
        this.usuario = usuario;
        this.fragmentManager = fragmentManager;
        this.obraReference = FirebaseDatabase.getInstance().getReference("filiais/" + usuario.getIdFilial() + "/obras");
    }
    
    public void buscarObras(Spinner spinnerObras) {
        obraReference.get().addOnCompleteListener(runnable ->  {
            try{
                if(runnable.isSuccessful()) {
                    List<Object> listObra = new ArrayList<>();

                    Obra selecioneObra = new Obra();

                    selecioneObra.setNomeObra("Selecione a obra");
                    selecioneObra.setId("selecione");

                    listObra.add(selecioneObra);

                    for (DataSnapshot child : runnable.getResult().getChildren()) {
                        Obra obra = child.getValue(Obra.class);
                        obra.setId(child.getKey());
                        listObra.add(obra);
                    }

                    ArrayAdapter<Object> adapter = new ArrayAdapter<>(context, R.layout.list_item_spinner, listObra);
                    spinnerObras.setAdapter(adapter);
                } else if(runnable.isCanceled()){
                    Toast.makeText(context, "O carregamento das obras foi interrompido.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Ocorreu uma falha no carregamento das obras.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "Ocorreu uma exceção ao carregar as obras: " + e.getCause().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void buscarNomeObra(String idObra, TextView obra) {
        obraReference.child(idObra).child("nome_obra").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String nomeObra = dataSnapshot.getValue(String.class);

                obra.setText(nomeObra);
            }
        });
    }

    public void salvarObra(String nome, String endereco, String cidade, String responsavel, NavigationView navigationView) {
        if(nome == null || endereco == null || cidade == null || responsavel == null) {
            Toast.makeText(context, "Preencha todos os campos obrigatórios!", Toast.LENGTH_SHORT).show();
        } else {
            Obra obra = new Obra();

            obra.setNomeObra(nome);
            obra.setEnderecoObra(endereco);
            obra.setCidadeObra(cidade);
            obra.setResponsavel(responsavel);

            Date data = Calendar.getInstance().getTime();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

            obra.setData(dateFormat.format(data));
            obra.setHora(timeFormat.format(data));

            obra.setIdUsuario(usuario.getId());

            obraReference.push().setValue(obra).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Obra cadastrada com sucesso!", Toast.LENGTH_SHORT).show();

                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new NovaObraFragment(usuario, navigationView))
                            .commit();
                }
            });
        }
    }
}
