package com.example.mountanguy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ANiveau extends AppCompatActivity {

    private String alter;
    private String größe;
    private String gewicht;
    private int geschlechtPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_aniveau);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getIntent().hasExtra("alter")) {
            alter = getIntent().getStringExtra("alter");
            größe = getIntent().getStringExtra("größe");
            gewicht = getIntent().getStringExtra("gewicht");
            geschlechtPosition = getIntent().getIntExtra("geschlecht_position", 0);
        }
    }

    int a = 0;
    int b = 0;
    int c =0;
    public void pressHoch(){

    }
    public void launchPersonalisierung(View v) {
        Intent i = new Intent(this, personalisierung.class);
       i.putExtra("alter", alter);
        i.putExtra("größe", größe);
        i.putExtra("gewicht", gewicht);
        i.putExtra("geschlecht_position", geschlechtPosition);
        startActivity(i);
    }

    public void launchZiel(View v) {
        Intent i = new Intent(this, ziel.class);
        i.putExtra("alter", alter);
        i.putExtra("größe", größe);
        i.putExtra("gewicht", gewicht);
        i.putExtra("geschlecht_position", geschlechtPosition);
        startActivity(i);
    }
}
