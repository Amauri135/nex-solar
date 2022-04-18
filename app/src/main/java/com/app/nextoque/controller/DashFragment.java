package com.app.nextoque.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.nextoque.enums.TipoUsuarioEnum;
import com.app.nextoque.entity.Usuario;
import com.app.nextoque.R;
import com.app.nextoque.databinding.FragmentDashBinding;
import com.google.android.material.navigation.NavigationView;

public class DashFragment extends Fragment {

    private FragmentDashBinding binding;
    private NavigationView navigationView;
    private final Usuario usuario;

    public DashFragment(Usuario usuario) {
        this.usuario = usuario;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashBinding.inflate(inflater,container, false);

        navigationView = binding.navigationView;

        navigationView.setVisibility(View.GONE);

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.navigationView.getVisibility() == View.VISIBLE){
                    binding.navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.navigationView.getVisibility() == View.VISIBLE){
                    binding.navigationView.setVisibility(View.GONE);
                }
            }
        });

        binding.imgBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    binding.navigationView.setVisibility(View.VISIBLE);
            }
        });

        navigationView.getMenu().findItem(R.id.nav_dashboard).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new DashFragment(usuario));
                return false;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_retirar).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new RetirarProdutoFragment(navigationView, usuario));
                return false;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_devolver).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new MinhasRetiradasFragment(navigationView, usuario));
                return false;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_listar).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new ListarProdutosFragment(usuario, navigationView));
                return false;
            }
        }).setVisible(TipoUsuarioEnum.ADMINISTRADOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DIRETOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DEV.toString().equalsIgnoreCase(usuario.getTipoAtual()));

        navigationView.getMenu().findItem(R.id.nav_novo).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new NovoProdutoFragment(navigationView, usuario));
                return false;
            }
        });

        navigationView.getMenu().findItem(R.id.nav_liberar).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new LiberarAcessoFragment());
                return false;
            }
        }).setVisible(TipoUsuarioEnum.ADMINISTRADOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DIRETOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DEV.toString().equalsIgnoreCase(usuario.getTipoAtual()));

        navigationView.getMenu().findItem(R.id.nav_nova_obra).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                replaceFragment(new NovaObraFragment(usuario, navigationView));
                return false;
            }
        }).setVisible(TipoUsuarioEnum.ADMINISTRADOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DIRETOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DEV.toString().equalsIgnoreCase(usuario.getTipoAtual()));

        navigationView.getMenu().findItem(R.id.nav_listar_obras).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               // replaceFragment(new NovaObraFragment(usuario, navigationView));
                return false;
            }
        }).setVisible(TipoUsuarioEnum.ADMINISTRADOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DIRETOR.toString().equalsIgnoreCase(usuario.getTipoAtual())
                || TipoUsuarioEnum.DEV.toString().equalsIgnoreCase(usuario.getTipoAtual()));

        replaceFragment(new DashContentFragment(usuario));

        return binding.getRoot();
    }

    private void replaceFragment(Fragment fragment) {
        navigationView.setVisibility(View.GONE);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment).addToBackStack(fragment.getTag()).commit();
    }
}
