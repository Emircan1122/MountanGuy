package com.example.mountanguy;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.mountanguy.DataBaseHelper.COL_5;
import static com.example.mountanguy.DataBaseHelper.COL_6;
import static com.example.mountanguy.DataBaseHelper.COL_7;
import static com.example.mountanguy.DataBaseHelper.COL_8;
import static com.example.mountanguy.DataBaseHelper.COL_9;
import static com.example.mountanguy.DataBaseHelper.COL_10;
import static com.example.mountanguy.DataBaseHelper.COL_11;

import androidx.fragment.app.Fragment;

public class ProfilFragment extends Fragment {

    // Declare your variables here
    private EditText alter;
    private EditText größe;
    private EditText gewicht;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Button aktualisierenbutton1;
    private DataBaseHelper db;
    private TextView profilUsername;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ProfilFragment() {
        // Required empty public constructor
    }

    public static ProfilFragment newInstance(String param1, String param2) {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profil, container, false);


        alter = view.findViewById(R.id.alterinput);
        größe = view.findViewById(R.id.größeinput);
        gewicht = view.findViewById(R.id.gewichtinput);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);
        spinner4 = view.findViewById(R.id.spinner4);
        aktualisierenbutton1 = view.findViewById(R.id.aktualisierenbutton);
        profilUsername = view.findViewById(R.id.profilUsername);
        db = DataBaseHelper.getInstance(getContext());


        String[] geschlechtItems = new String[]{"Wählen", "Männlich", "Weiblich"};
        ArrayAdapter<String> geschlechtAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, geschlechtItems);
        spinner1.setAdapter(geschlechtAdapter);


        String[] niveauItems = new String[]{"Wählen", "Niedrig", "Mittel", "Hoch"};
        ArrayAdapter<String> niveauAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, niveauItems);
        spinner2.setAdapter(niveauAdapter);

        String[] zielGewichtItems = new String[]{"Wählen","Schnell zunehmen", "Langsam zunehmen", "Gewicht halten", "Langsam abnehmen","Schnell abnehmen"};
        ArrayAdapter<String> zielGewichtAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, zielGewichtItems);
        spinner3.setAdapter(zielGewichtAdapter);

        String[] proteinItems = new String[]{"Wählen","1x Fach", "1,5x Fach", "2x Fach"};
        ArrayAdapter<String> proteinAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, proteinItems);
        spinner4.setAdapter(proteinAdapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String loggedInUser = sharedPreferences.getString("username", "");

        if (!loggedInUser.isEmpty()) {
            profilUsername.setText(loggedInUser);
            loadUserData(loggedInUser);
        }

        aktualisierenbutton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String userAlter = alter.getText().toString();
                String userGröße = größe.getText().toString();
                String userGewicht = gewicht.getText().toString();
                String userGeschlecht = spinner1.getSelectedItem().toString();
                String userNiveau = spinner2.getSelectedItem().toString();
                String userZielGewicht = spinner3.getSelectedItem().toString();
                String userZielProtein = spinner4.getSelectedItem().toString();

                if (userAlter.equals("") || userGröße.equals("") || userGewicht.equals("") || userGeschlecht.equals("Wählen")|| userNiveau.equals("Wählen")|| userZielGewicht.equals("Wählen") || userZielProtein.equals("Wählen"))
                    Toast.makeText(getActivity(), "Bitte geben sie ihre Daten vollständig ein", Toast.LENGTH_SHORT).show();
                else {
                    if (Integer.parseInt(userAlter) > 122) { //die älteste Person der Welt verstarb im Alter von 122
                        Toast.makeText(getActivity(), "geben sie ein realistisches Alter ein", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(userGröße) > 272) { //größter Mensch der Welt
                        Toast.makeText(getActivity(), "geben sie eine realistische Körpergröße ein", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(userGröße) < 54.6) { //kleinster Mnesch der Welt
                        Toast.makeText(getActivity(), "geben sie eine realistische Körpergröße ein", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(userGewicht) > 590) { // schwerster Mensch der Welt
                        Toast.makeText(getActivity(), "geben sie ein realistisches Gewicht ein", Toast.LENGTH_SHORT).show();
                    } else {

                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                        String loggedInUser = sharedPreferences.getString("username", "");
                        Log.d("Aktualisieren 1: ",
                                "funktioniert 1");

                        if (!loggedInUser.isEmpty()) {
                            Log.d("Aktualisieren 2: ",
                                    "funktioniert 2");
                            boolean isUpdated = db.updateUserInfo(loggedInUser, userGeschlecht, userAlter, userGröße, userGewicht, userNiveau, userZielGewicht, userZielProtein);
                            if (isUpdated) {
                                Toast.makeText(getActivity(), "Erfolgreich aktualisiert", Toast.LENGTH_SHORT).show();
                                Log.d("Aktualisieren der Daten Erfolgreich: ",
                                        "funktioniert");

                            } else {
                                Toast.makeText(getActivity(), "Fehler Beim Aktualisieren der Daten", Toast.LENGTH_SHORT).show();
                                Log.d("Aktualisieren der Daten nicht erfolgreich: ",
                                        "funktioniert nicht");
                            }
                        }
                    }
                }
                }
            });

        return view;
    }

    private void loadUserData(String username) {
        Cursor cursor = db.getUserData(username);
        if (cursor != null && cursor.moveToFirst()) {
            alter.setText(cursor.getString(cursor.getColumnIndexOrThrow(COL_6)));
            größe.setText(cursor.getString(cursor.getColumnIndexOrThrow(COL_7)));
            gewicht.setText(cursor.getString(cursor.getColumnIndexOrThrow(COL_8)));
            spinner1.setSelection(((ArrayAdapter<String>) spinner1.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndexOrThrow(COL_5))));
            spinner2.setSelection(((ArrayAdapter<String>) spinner2.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndexOrThrow(COL_9))));
            spinner3.setSelection(((ArrayAdapter<String>) spinner3.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndexOrThrow(COL_10))));
            spinner4.setSelection(((ArrayAdapter<String>) spinner4.getAdapter()).getPosition(cursor.getString(cursor.getColumnIndexOrThrow(COL_11))));
            cursor.close();
        }
    }

}
