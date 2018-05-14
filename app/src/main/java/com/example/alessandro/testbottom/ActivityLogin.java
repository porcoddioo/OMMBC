package com.example.alessandro.testbottom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import java.util.LinkedHashMap;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.pass);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Login(email.getText().toString(),password.getText().toString(),ActivityLogin.this).execute();
            }
        });
    }



    // Clase de conexión a la api

    private class Login extends AsyncTask<Void,Void,Void> {

        private String usr;
        private String pass;
        private Context c;
        private ProgressDialog pd;
        private String result;
        private Boolean success = false;

        public Login (String usr, String pass, Context c) {
            this.usr = usr;
            this.pass = pass;
            this.c = c;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(c);
            pd.setMessage("Entrando...");
            pd.setCancelable(false);
            pd.show();
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
                e.printStackTrace();
            }
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("email",usr);
            params.put("password",pass);
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, Object> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                try {
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                postData.append('=');
                try {
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            try {
                postDataBytes = postData.toString().getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                conn.setRequestMethod("POST");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            try {
                conn.getOutputStream().write(postDataBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            if(in!=null) {
            try {
                for (int c; (c = in.read()) >= 0; )
                    sb.append((char) c);
                result = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            success = true;
            }
            else {
                // No se pudo hacer el login
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String token = null;
            pd.dismiss();
            if(success) {
                try {
                    JSONObject mainObject = new JSONObject(result);
                    token = mainObject.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(c,MainActivity.class);
                i.putExtra("Token", token);
                startActivity(i);
                finish();
            }
            super.onPostExecute(aVoid);
        }
    }

}
