package com.app.nextoque.controller;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.nextoque.R;
import com.app.nextoque.databinding.FragmentCriarContaBinding;
import com.app.nextoque.entity.Filial;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.enums.TipoUsuarioEnum;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CriarContaFragment extends Fragment {

    private FragmentCriarContaBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentCriarContaBinding.inflate(inflater, container, false);

        Context context = getContext();

        getActivity().setTitle("Criar Conta");

        View root = binding.getRoot();

        final EditText editTextCriarNome = binding.editTextCriarNome.getEditText();
        final EditText editTextCriarUsername = binding.editTextCriarUsername.getEditText();
        final EditText editTextCriarSenha = binding.editTextCriarSenha.getEditText();
        final EditText editTextConfirmarCriarSenha = binding.editTextConfirmarCriarSenha.getEditText();
        final CheckBox checkBoxConfimarSenha = binding.checkboxConfirmarSenha;
        final Button buttonRegistrarConta = binding.buttonRegistrarConta;
        final Spinner spinnerFiliais = binding.spinnerFiliais;
        final Spinner spinnerTipoUsuario = binding.spinnerTipoUsuario;

        List<Object> listTipoUsuario = new ArrayList<>();

        listTipoUsuario.add("Tipo Usuário");
        listTipoUsuario.addAll(Arrays.asList(TipoUsuarioEnum.ADMINISTRADOR, TipoUsuarioEnum.COLABORADOR));

        ArrayAdapter<Object> tipoUsuarioAdapter = new ArrayAdapter<>(context, R.layout.list_item_spinner, listTipoUsuario);
        spinnerTipoUsuario.setAdapter(tipoUsuarioAdapter);

        DatabaseReference filialReference = FirebaseDatabase.getInstance().getReference("filiais");


        filialReference.get().addOnCompleteListener(runnable ->  {
                try{
                    if(runnable.isSuccessful()) {
                        List<Object> listFilial = new ArrayList<>();

                        listFilial.add("Filial da Nexsolar");

                        for (DataSnapshot child : runnable.getResult().getChildren()) {
                            Filial filial = child.getValue(Filial.class);
                            filial.setId(child.getKey());
                            listFilial.add(filial);
                        }

                        ArrayAdapter<Object> adapter = new ArrayAdapter<>(context, R.layout.list_item_spinner, listFilial);
                        spinnerFiliais.setAdapter(adapter);
                    } else if(runnable.isCanceled()){
                        Toast.makeText(context, "O carregamento das filiais foi interrompido.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Ocorreu uma falha no carregamento das filiais.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Ocorreu uma exceção ao carregar as filiais: " + e.getCause().toString(), Toast.LENGTH_LONG).show();
                }
        });

        editTextConfirmarCriarSenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                checkBoxConfimarSenha.setChecked(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals(editTextCriarSenha.getText().toString())){
                    checkBoxConfimarSenha.setChecked(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(editTextCriarSenha.getText().toString())){
                    checkBoxConfimarSenha.setChecked(true);
                } else{
                    checkBoxConfimarSenha.setChecked(false);
                }
            }
        });

        buttonRegistrarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();

                String textNome = editTextCriarNome.getText().toString();
                String textEmail = editTextCriarUsername.getText().toString();
                String textSenha = editTextCriarSenha.getText().toString();
                String textConfirmarSenha = editTextConfirmarCriarSenha.getText().toString();

                if(textNome.equals("") || textEmail.equals("") || textSenha.equals("") || textConfirmarSenha.equals("")){
                    Toast.makeText(context, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else if(!(spinnerFiliais.getSelectedItem() instanceof Filial)){
                    Toast.makeText(context, "Por favor, escolha alguma filial.", Toast.LENGTH_LONG).show();
                } else if(!(spinnerTipoUsuario.getSelectedItem() instanceof TipoUsuarioEnum)) {
                    Toast.makeText(context, "Por favor, escolha um tipo de usuário.", Toast.LENGTH_LONG).show();
                } else if(!checkBoxConfimarSenha.isChecked()) {
                    Toast.makeText(context, "Por favor, verifique sua senha.", Toast.LENGTH_LONG).show();
                } else {
                    Filial filial = (Filial) spinnerFiliais.getSelectedItem();
                    TipoUsuarioEnum tipoUsuario = (TipoUsuarioEnum) spinnerTipoUsuario.getSelectedItem();

                    Date dataAtual = new Date();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                    String data = dateFormat.format(dataAtual);
                    String hora = timeFormat.format(dataAtual);

                    usuario.setDataCriacao(data);
                    usuario.setHoraCriacao(hora);
                    usuario.setNome(textNome);
                    usuario.setEmail(textEmail);
                    usuario.setIdFilial(filial.getId());
                    usuario.setTipoRequisicao(tipoUsuario.toString().toLowerCase(Locale.ROOT));
                    usuario.setTipoAtual(TipoUsuarioEnum.NOVO.toString().toLowerCase(Locale.ROOT));

                    DatabaseReference usuarioReference = FirebaseDatabase.getInstance().getReference("usuarios");

                    //INSERIR O USUARIO NO FIREBASE AUTH
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    firebaseAuth.createUserWithEmailAndPassword(textEmail, textSenha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            usuarioReference.child(authResult.getUser().getUid()).setValue(usuario);
                            Toast.makeText(context, "Solicitação registrada com sucesso. Aguarde a liberação do cadastro por um administrador.", Toast.LENGTH_LONG).show();
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Ocorreu uma exceção ao registrar a solicitação: " + e.getCause().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        return root;
    }
}
