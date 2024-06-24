package com.example.mountanguy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
        }

        return view;
    }



}
