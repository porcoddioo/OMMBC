package com.example.alessandro.testbottom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FrameLayout main_content;
    private FragmentList fragmentList;
    private FragmentEntregados fragmentEntregados;
    private FragmentFavoritos fragmentFavoritos;
    private FragmentAccount fragmentAccount;
    private BottomNavigationView bottom_nav;
    private SharedPreferences mPrefs;
    private String Token;
    private ArrayList<Problema> problemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("OMMBC");
        mPrefs = getSharedPreferences("label", 0);
        Token = mPrefs.getString("token",null);
        bottom_nav = (BottomNavigationView) findViewById(R.id.navigation);
        main_content = (FrameLayout) findViewById(R.id.main_content);
        fragmentList = new FragmentList();
        fragmentEntregados = new FragmentEntregados();
        fragmentFavoritos = new FragmentFavoritos();
        fragmentAccount = new FragmentAccount();
        setFragment(fragmentList);
        new BottomNavigationViewHelper().disableShiftMode(bottom_nav);
        new bajaProblemas(Token,this).execute();


        SharedPreferences mPrefs = getSharedPreferences("label", 0);
        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("tag", "Ciao").commit();


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
                    case R.id.navigation_account:
                        setFragment(fragmentAccount);
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
                    //problemas.add(new Problema(oneObject.getString("tema"),oneObject.getString("pregunta")));
                    problemas.add(new Problema(oneObject.getString("_id"),oneObject.getString("area"), oneObject.getString("tema"),
                            oneObject.getString("pregunta"),oneObject.getString("nivel")));
                    /*problemas.add(new Problema(oneObject.getString("area"),
                            oneObject.getString("tema"),
                            oneObject.getString("nivel"),
                            oneObject.getString("pregunta"),
                            oneObject.getString("imagen"),
                            oneObject.getString("solucion"),
                            oneObject.getString("tip"),
                            oneObject.getString("origen"),
                            oneObject.getString("siguiente"),
                            oneObject.getString("anterior"),
                            oneObject.getString("comentario"),
                            oneObject.getString("puntaje")));
                            */
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

    public class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public  void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
                Log.e("BNVHelper", "Unable to get shift mode field", e);
            } catch (IllegalAccessException e) {
                Log.e("BNVHelper", "Unable to change value of shift mode", e);
            }
        }
    }
}

