package com.app.nextoque;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.app.nextoque.controller.DashContentFragment;
import com.app.nextoque.controller.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private boolean deslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deslogar = false;

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Runnable inicialFragmentRunnable = new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
            }
        };

        new Thread(inicialFragmentRunnable).start();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            View nav_view = findViewById(R.id.navigation_view);

            if(nav_view != null && nav_view.getVisibility() == View.VISIBLE){
                nav_view.setVisibility(View.GONE);
            } else if(getSupportFragmentManager().getFragments()
                    .get(getSupportFragmentManager().getFragments().size()-1) instanceof DashContentFragment) {
                if(!deslogar) {
                    Toast.makeText(this, "Aperte novamente para sair!", Toast.LENGTH_SHORT).show();
                    deslogar = true;
                } else {
                    FirebaseAuth.getInstance().signOut();

                    FragmentManager.BackStackEntry entry = getSupportFragmentManager().getBackStackEntryAt(0);
                    getSupportFragmentManager().popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    deslogar = false;
                }
            } else {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }
}