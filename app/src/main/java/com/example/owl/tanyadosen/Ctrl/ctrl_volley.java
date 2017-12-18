package com.example.owl.tanyadosen.Ctrl;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.owl.tanyadosen.ServerDB.serverDB;

/**
 * Created by Owl on 12/12/2017.
 */

public class ctrl_volley {
    private Context context;
    public ctrl_volley(Context context) {
        this.context = context;
    }

    public void GetStringRequest(String url)
    {
        String geturl = url;
        //url action
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, geturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.equals(""))
                {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Koneksi Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

}
