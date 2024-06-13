package com.example.mountanguy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

private EditText namelogin , passwortlogin;
private Button buttonlogin;
private DataBaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
            namelogin = findViewById(R.id.loginname);
            passwortlogin = findViewById(R.id.loginpasswort);
            buttonlogin = findViewById(R.id.loginbutton);

            myDB = DataBaseHelper.getInstance(this);

                myDB.openDatabase();

loginUser();

        }
        private void loginUser(){
        buttonlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean var = myDB.checkUser(namelogin.getText().toString() , passwortlogin.getText().toString());
                if(var){
                    Toast.makeText(MainActivity.this, "Anmeldung Erfolgreich", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this , Home.class));
                    Log.d("Anmeldung: " ,
                            "Name: " +namelogin.getText().toString()+
                                 " Passwort: "+passwortlogin.getText().toString());
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Fehler bei der Anmeldung", Toast.LENGTH_SHORT).show();
                    Log.d("Fehler Anmeldung: " ,
                            "Name: " +namelogin.getText().toString()+
                                    " Passwort: "+passwortlogin.getText().toString());
                }
            }
        });


        }
        public void launchRegistration(View v){

            Intent i = new Intent(this, registration.class);
            startActivity(i);
    }
    public void launchAngemeldet(View v) {

        Intent h = new Intent(this, Home.class);
        startActivity(h);
    }


}