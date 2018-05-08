package com.example.alessandro.testbottom;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterController extends AppCompatActivity {
    Spinner spinner;
    int iCurrentSelection;
    ArrayAdapter<CharSequence> adapter;

    Button validate;
    EditText email, password;
    TextView estado, municipio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinner = (Spinner)findViewById(R.id.spinner_estado);
        adapter= ArrayAdapter.createFromResource(this,R.array.estados_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        municipio = (TextView) findViewById(R.id.municipio);
        estado = (TextView) findViewById(R.id.estado);

       iCurrentSelection = spinner.getSelectedItemPosition();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (iCurrentSelection != 1){
                    municipio.setVisibility(View.VISIBLE);

                }
                municipio.setVisibility(View.INVISIBLE);            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        email = (EditText) findViewById(R.id.text_input_edit_email);
        password = (EditText) findViewById(R.id.text_input_edit_password);
        validate = (Button) findViewById(R.id.menu_button_register);

    }

    protected boolean validatePassword(String password) {
        EditText pwc = (EditText) findViewById(R.id.text_input_edit_confirm_password);

        if (!password.equals(pwc.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    //vali
    //validation of email
    protected boolean validateEmail(String email) {
        return !(email == null || TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void validate(View view) {
        // Do something in response to button
        if (!validateEmail(email.getText().toString())) {
            email.setError("Porfavor Escribe un Email Vàlido");
            email.requestFocus();

        } else if (validatePassword(password.getText().toString())) {
            password.setError("Las Constraseñas No Coinciden");
            password.requestFocus();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(RegisterController.this, "Se ha creado la cuenta!", Toast.LENGTH_LONG).show();
        }

    }
}
