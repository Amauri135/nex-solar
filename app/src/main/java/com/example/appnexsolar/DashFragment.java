package com.example.appnexsolar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appnexsolar.databinding.FragmentDashBinding;
import com.google.android.material.navigation.NavigationView;

public class DashFragment extends Fragment {

    private FragmentDashBinding binding;
    private NavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashBinding.inflate(inflater,container, false);

        binding.navigationView.setVisibility(View.GONE);

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
               // if(binding.navigationView.getVisibility() == View.GONE){
                    binding.navigationView.setVisibility(View.VISIBLE);
                //} else {
                //    binding.navigationView.setVisibility(View.GONE);
                //}
            }
        });

        return binding.getRoot();
    }
}
