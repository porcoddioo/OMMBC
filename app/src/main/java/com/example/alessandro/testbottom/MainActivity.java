package com.example.alessandro.testbottom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FrameLayout main_content;
    private FragmentList fragmentList;
    private FragmentEntregados fragmentEntregados;
    private FragmentFavoritos fragmentFavoritos;
    private BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("OMMBC");
        bottom_nav = (BottomNavigationView)findViewById(R.id.navigation);
        main_content = (FrameLayout)findViewById(R.id.main_content);
        fragmentList = new FragmentList();
        fragmentEntregados = new FragmentEntregados();
        fragmentFavoritos = new FragmentFavoritos();
        setFragment(fragmentList);

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_problemas:    setFragment(fragmentList); return true;
                    case R.id.navigation_entregados:   setFragment(fragmentEntregados); return true;
                    case R.id.navigation_favoritos:    setFragment(fragmentFavoritos); return true;

                    default: return false;
                }
            }


        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
