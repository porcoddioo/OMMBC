package com.example.alessandro.testbottom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private FrameLayout main_content;
    private FragmentList fragmentList;
    private FragmentEntregados fragmentEntregados;
    private FragmentFavoritos fragmentFavoritos;
    private BottomNavigationView bottom_nav;
    private String Token;
    private ArrayList<Problema> problemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("OMMBC");
        Token = getIntent().getStringExtra("Token");
        bottom_nav = (BottomNavigationView) findViewById(R.id.navigation);
        main_content = (FrameLayout) findViewById(R.id.main_content);
        fragmentList = new FragmentList();
        fragmentEntregados = new FragmentEntregados();
        fragmentFavoritos = new FragmentFavoritos();
        setFragment(fragmentList);

        new bajaProblemas(Token,this).execute();

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_problemas:
                        setFragment(fragmentList);
                        return true;
                    case R.id.navigation_entregados:
                        setFragment(fragmentEntregados);
                        return true;
                    case R.id.navigation_favoritos:
                        setFragment(fragmentFavoritos);
                        return true;

                    default:
                        return false;
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


    class bajaProblemas extends AsyncTask<Void, Void, Void>  {

        String response = "No response";
        String token;
        Context mycont;
        ProgressDialog dialog;


        public bajaProblemas (String token, Context mycont) {
            this.token = token;
            this.mycont = mycont;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(mycont);
            dialog.setMessage("Bajando problemas...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            HttpURLConnection conn = null;
            Reader in = null;
            try {
                url = new URL("http://35.197.88.181/api/problems?qnty=100&page=1");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization",token);
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            try {
                for (int c; (c = in.read()) >= 0; )
                    sb.append((char) c);
                 response = sb.toString();
            } catch (IOException e) {e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            JSONArray jArray = null;
            problemas = new ArrayList<Problema>();
            try {
                jArray = new JSONArray(response);
            } catch (Exception e) {}
            for (int i=0; i < jArray.length(); i++)
            {
                try {
                    JSONObject oneObject = jArray.getJSONObject(i);
                    problemas.add(new Problema(oneObject.getString("tema"),oneObject.getString("pregunta")));
                } catch (JSONException e) {
                    // Oops
                }
            }
            fragmentList.actualiza();
            dialog.dismiss();
        }
    }

    public ArrayList<Problema> getProblemas() {
        return problemas;
    }
}