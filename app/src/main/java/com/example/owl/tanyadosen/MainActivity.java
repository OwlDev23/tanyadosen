package com.example.owl.tanyadosen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar t;
    public static final String tagmsg = "main";
    private ImageView imgProfile, imgDosen, imgTanyados, imgPengaturan, imgAbout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t = findViewById(R.id.customToolbar);
        setSupportActionBar(t);
        //inisialize
        inisializerid();

    }

    private void inisializerid()
    {
        imgProfile = findViewById(R.id.macimg_profile);
        imgDosen = findViewById(R.id.macimg_dosen);
        imgTanyados = findViewById(R.id.macimg_tanyadosen);
        imgAbout = findViewById(R.id.macimg_about);
        imgPengaturan = findViewById(R.id.macimg_pengaturan);
        imgAbout.setOnClickListener(this);
        imgDosen.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgPengaturan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {
            case R.id.macimg_dosen :
                i = new Intent(getBaseContext(),SecondActivity.class);
                i.putExtra(tagmsg,"dosen");
                startActivity(i);
                break;
            case R.id.macimg_profile:
                i = new Intent(getBaseContext(), SecondActivity.class);
                i.putExtra(tagmsg, "profile");
                startActivity(i);
                break;
            case R.id.macimg_pengaturan:
                i = new Intent(getBaseContext(), SecondActivity.class);
                i.putExtra(tagmsg, "pengaturan");
                startActivity(i);
                break;
            case R.id.macimg_about:
                i = new Intent(getBaseContext(), SecondActivity.class);
                i.putExtra(tagmsg, "about");
                startActivity(i);
                break;
        }
    }
}
