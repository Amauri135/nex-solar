package com.example.appnexsolar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.appnexsolar.databinding.FragmentInicialBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginFragment extends Fragment {

    private FragmentInicialBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       binding = FragmentInicialBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        final EditText editTextUsername = binding.editTextUsername;
        final EditText editTextSenha = binding.editTextSenha;
        final Button buttonIniciarSessao = binding.buttonIniciarSessao;
        final Button buttonCriarConta = binding.buttonCriarConta;
        final TextView textViewEsqueciASenha = binding.textViewEsqueciASenha;

        textViewEsqueciASenha.setOnClickListener(view -> {
            Toast.makeText(getContext(), "Esqueci a senhaaa", Toast.LENGTH_LONG).show();
        });

        buttonCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new CriarContaFragment()).addToBackStack("fromInicialToCriarConta").commit();
            }
        });

        buttonIniciarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSessao(editTextUsername, editTextSenha);
            }
        });

        return root;
    }

    public void iniciarSessao(EditText editTextUsername, EditText editTextSenha) {
        if(editTextUsername.getText() == null || editTextUsername.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Digite o e-mail!", Toast.LENGTH_SHORT).show();
        }else if(editTextSenha.getText() == null || editTextSenha.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(), "Digite a senha!", Toast.LENGTH_SHORT).show();
        } else {
            String email = editTextUsername.getText().toString();
            String senha = editTextSenha.getText().toString();

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signInWithEmailAndPassword(email, senha).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    FirebaseUser firebaseUser = authResult.getUser();
                    DatabaseReference usuariosReference = FirebaseDatabase.getInstance().getReference("usuarios");
                    usuariosReference.child(firebaseUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                                Toast.makeText(getContext(), "Olá, " + usuario.getNome() + "!", Toast.LENGTH_SHORT).show();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DashboardFragment()).addToBackStack("fromLoginToDash").commit();
                            } else {
                                Toast.makeText(getContext(), "Usuário não encontrado no banco de dados! Peça ajuda ao suporte.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Falha ao buscar o usuário no banco de dados! Tente novamente em instantes ou peça ajuda ao suporte.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Falha ao iniciar a sessão. " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
