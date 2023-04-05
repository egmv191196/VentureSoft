package com.egmvdev.venturesoft.iu.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.egmvdev.venturesoft.R;
import com.egmvdev.venturesoft.clases.usuarioSinglenton;
import com.egmvdev.venturesoft.databinding.ActivityMenuBinding;
import com.egmvdev.venturesoft.iu.mainfragment.view.mainFragment;

public class main extends AppCompatActivity {

    private ActivityMenuBinding binding;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cargarVista();
        addActions();
    }

    private void cargarVista(){
        initTitle();
        initNavigationMenu();
        cargarFragment();
    }

    private void initTitle() {
        TextView titleHeader = binding.navigationView.getHeaderView(0).findViewById(R.id.title_header);
        titleHeader.setText(usuarioSinglenton.getInstance().nombreCompleto);
    }

    private void initNavigationMenu() {
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayoutMain, binding.toolbarMain, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        binding.drawerLayoutMain.addDrawerListener(toggle);
        binding.drawerLayoutMain.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void addActions(){
        binding.navigationView.setNavigationItemSelectedListener(item -> {
//            validaAccion(item.getItemId());
            binding.drawerLayoutMain.close();
            return true;
        });
        binding.toolbarMain.setNavigationOnClickListener(v->binding.drawerLayoutMain.openDrawer(GravityCompat.START));
    }

    public void cargarFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mainFragment mainFragment = new mainFragment();
        fragmentTransaction.add(binding.frmMain.getId(),mainFragment);
        fragmentTransaction.commit();
    }
}
