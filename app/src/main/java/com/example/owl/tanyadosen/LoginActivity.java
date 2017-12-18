package com.example.owl.tanyadosen;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.owl.tanyadosen.Ctrl.ctrl_volley;
import com.example.owl.tanyadosen.ServerDB.serverDB;

import java.util.Queue;

public class LoginActivity extends AppCompatActivity {
    private EditText tx_username;
    private EditText tx_password;
    private Button b_login;
    private Button b_register;

    //class
    private serverDB server;
    private ctrl_volley ctrlVolley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //test koneksi
        server = new serverDB();
        ctrlVolley = new ctrl_volley(getBaseContext());
        ctrlVolley.GetStringRequest(server.getServerconnection());

        //inisialisasi ctroll
        tx_username = findViewById(R.id.Login_txusername);
        tx_password = findViewById(R.id.Login_txpassword);
        b_login = findViewById(R.id.Login_blogin);
        b_register = findViewById(R.id.Login_bregister);

        //click event
        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest();
            }
        });
        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    private  void LoginRequest()
    {
        String url = "";
        url = "http://owlmu.com/bridgedb/login.php?usr="+tx_username.getText()+"&psw="+tx_password.getText()+"";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                Log.d("login", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Eroor : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
