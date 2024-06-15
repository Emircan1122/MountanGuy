package com.example.mountanguy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class personalisierung extends AppCompatActivity {

    private DataBaseHelper DB;
    private EditText Alter;
    private EditText Größe;
    private EditText Gewicht;
    private RadioGroup Geschlecht;
    private RadioButton selectedRadiobutton;
    private Button weiterButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personalisierung);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Alter = findViewById(R.id.alterinput);
        Größe = findViewById(R.id.größeinput);
        Gewicht = findViewById(R.id.gewichtinput);
        Geschlecht = findViewById(R.id.geschlechtinput);
        weiterButton = findViewById(R.id.personalisierungsbutton);
        int selectedId = Geschlecht.getCheckedRadioButtonId();
        selectedRadiobutton= findViewById(selectedId);
        DB = DataBaseHelper.getInstance(this);
    }


    public void applyChange(View v) {

                DB.insertIntoDatabase("3",DataBaseHelper.COL_5,Alter.getText().toString());
                DB.insertIntoDatabase("3",DataBaseHelper.COL_6,Größe.getText().toString());
                DB.insertIntoDatabase("3",DataBaseHelper.COL_7,Gewicht.getText().toString());
                DB.insertIntoDatabase("3",DataBaseHelper.COL_8,selectedRadiobutton.getText().toString());
                Log.d("Tag","Erfolgreich eingefügt");


    }

    public void launchANiveau(View v) {

        Intent i = new Intent(this, ANiveau.class);
        startActivity(i);
    }
}