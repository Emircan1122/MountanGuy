package com.example.mountanguy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class registration extends AppCompatActivity {

    private EditText nameregister , emailregister , passwordregister;
    private Button buttonregister;
    private DataBaseHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



        });

        nameregister = findViewById(R.id.registername);
        emailregister = findViewById(R.id.registeremail);
        passwordregister = findViewById(R.id.registerpasswort);

        buttonregister = findViewById(R.id.registerbutton);

        myDB = new DataBaseHelper(this);
        insertUser();
    }
    private void insertUser(){
        buttonregister.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                boolean var = myDB.registerUser(nameregister.getText().toString() , emailregister.getText().toString() , passwordregister.getText().toString());
                if(var){
                    Toast.makeText(registration.this, "Benutzer Erfolgreich Registriert!", Toast.LENGTH_SHORT).show();
                    Log.d("neue Registration: " ,
                                "Name: " +nameregister.getText().toString()+
                                    " Email: "+emailregister.getText().toString()+
                                    " Passwort: "+passwordregister.getText().toString());
                    launchPersonalisierung(v);
                }
                else
                    Toast.makeText(registration.this, "Fehler Bei Der Registration!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void launchPersonalisierung(View v) {

        Intent i = new Intent(this, personalisierung.class);
        startActivity(i);
    }


    public void launchAnmeldung(View v) {

        Intent r = new Intent(this, MainActivity.class);
        startActivity(r);
    }
}