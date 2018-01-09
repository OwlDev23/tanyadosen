package com.example.owl.tanyadosen;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    EditText txnim, txemail, txpassword, txrepassword;
    Button bregister, breset, bcancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //inisialisasi control
        txnim = findViewById(R.id.sing_nim);
        txemail = findViewById(R.id.sing_email);
        txpassword = findViewById(R.id.sing_password);
        txrepassword = findViewById(R.id.sing_repass);
        bregister = findViewById(R.id.sing_bregister);
        breset = findViewById(R.id.sing_breset);
        bcancel = findViewById(R.id.sing_bcancel);

        //onclick
        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public static boolean isNumeric(String string) {
        return string.matches("^[-+]?\\d+(\\.\\d+)?$");
    }

    public boolean checking(){
        boolean result = false;
        String a ="";
        if(txnim.getText().toString().equals(""))
        {
            a += "NIM harus diisi dengan benar";
            txnim.setSelection(this.txnim.getSelectionStart(),this.txnim.length());
        }
        else if(txemail.getText().toString().equals(""))
        {
            a += "Email belum diisi";
            if(!txemail.getText().toString().matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
            {
                a+= "Bukan format email";
            }
            txemail.setSelection(this.txemail.getSelectionStart(),this.txemail.length());

        }
        else if(txpassword.getText().toString().equals(""))
        {
            a += "Password diisi dahulu";
            txpassword.setSelection(this.txpassword.getSelectionStart(),this.txpassword.length());

        }
        else if(!txrepassword.getText().toString().equals(txpassword.getText().toString()))
        {
            a += "Coba ulangi pasword degan benar";
            txrepassword.setSelection(this.txrepassword.getSelectionStart(),this.txrepassword.length());

        }
        if(!a.equals(""))
        {
            Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
            result = false;
        }
        else
        {
            result = true;
        }
        return result;
    }

    public void register()
    {
        if(checking())
        {
            try {
                String url = "http://owlmu.com/bridgedb/register.php?nim="+txnim.getText()+"&psw="+txpassword.getText()+"&email="+txemail.getText()+"";
                url = url.replaceAll("\\s","%20");
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("false"))
                        {
                            Toast.makeText(RegisterActivity.this, "Mohon diisi yang benar", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("1"))
                        {
                            Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Periksa Koneksi", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Register", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register procces Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(stringRequest);
            }catch (Exception e)
            {
                Toast.makeText(this, "Tidak ada Sambungan", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
