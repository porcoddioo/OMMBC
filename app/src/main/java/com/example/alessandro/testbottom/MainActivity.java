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
import java.util.LinkedHashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private FrameLayout main_content;
    private FragmentList fragmentList;
    private FragmentEntregados fragmentEntregados;
    private FragmentFavoritos fragmentFavoritos;
    private BottomNavigationView bottom_nav;
    private String Token;

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
        Allertami(Token);

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


    public void Allertami(String msg) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Token de sesión")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /*class callAPI extends AsyncTask<Void, Void, Void>  {

        String response = "No response";
        String usr;
        String pwd;
        Context mycont;
        ProgressDialog dialog;


        public callAPI(String usr, String pwd, Context mycont) {
            this.usr = usr;
            this.pwd = pwd;
            this.mycont = mycont;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(mycont);
            dialog.setMessage("Logging In");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            HttpURLConnection conn = null;
            byte[] postDataBytes = null;
            Reader in = null;
            try {
                url = new URL("http://35.197.88.181/signin");
            } catch (MalformedURLException e) {
                Allertami(e.getMessage());
            }
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("email", usr);
            params.put("password", pwd);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                try {
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Allertami(e.getMessage());
                }
                postData.append('=');
                try {
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Allertami(e.getMessage());
                }
            }
            try {
                postDataBytes = postData.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                Allertami(e.getMessage());
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                Allertami(e.getMessage());
            }
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                Allertami(e.getMessage());
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            try {
                conn.getOutputStream().write(postDataBytes);
            } catch (IOException e) {
                Allertami(e.getMessage());
            }
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                Allertami(e.getMessage());
            }
            StringBuilder sb = new StringBuilder();
            try {
                for (int c; (c = in.read()) >= 0; )
                    sb.append((char) c);
                 response = sb.toString();
            } catch (IOException e) {Allertami(e.getMessage());}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            dialog.dismiss();
        }
    }*/


}