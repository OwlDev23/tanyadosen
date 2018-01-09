package com.example.owl.tanyadosen;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;


public class SecondActivity extends AppCompatActivity {
    private Toolbar t;
    private FrameLayout fragContainner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        t = findViewById(R.id.customToolbar);
        setSupportActionBar(t);
        fragContainner = findViewById(R.id.containerfragment);
        //get string yang dikirim
        Intent i = getIntent();
        String msg = i.getStringExtra(MainActivity.tagmsg);
        fragmentmanegement(msg);
    }

    private void fragmentmanegement(String msg)
    {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (msg)
        {
            case "dosen":
                LdosenFragment ldosenFragment = new LdosenFragment();
                fragmentTransaction.add(R.id.containerfragment, ldosenFragment,"tagdosen");
                fragmentTransaction.commit();
                setTitle("List Dosen");
                break;
            case  "profile" :
                ProfileFragment profileFragment = new ProfileFragment();
                fragmentTransaction.add(R.id.containerfragment,profileFragment);
                fragmentTransaction.commit();
                setTitle("Profile");
                break;
            case "pengaturan":
                SettingFragment settingFragment = new SettingFragment();
                fragmentTransaction.add(R.id.containerfragment,settingFragment).commit();
                setTitle("Setting");
                break;
            case "about":
                AboutFragment aboutFragment = new AboutFragment();
                fragmentTransaction.add(R.id.containerfragment, aboutFragment).commit();
                setTitle("About");
                break;
        }
    }
}
