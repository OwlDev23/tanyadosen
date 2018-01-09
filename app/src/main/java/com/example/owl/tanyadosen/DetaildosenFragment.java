package com.example.owl.tanyadosen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.owl.tanyadosen.R;
import com.example.owl.tanyadosen.classes.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DetaildosenFragment extends Fragment {

    TextView nama, nik, email, jk, almatrumah, alamatkantor, sinopsis;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detaildosen, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //inisialisasi
        nama = getView().findViewById(R.id.detaild_namadosen);
        nik = getView().findViewById(R.id.detaild_nik);
        email = getView().findViewById(R.id.detaild_email);
        almatrumah = getView().findViewById(R.id.detaild_almatrumah);
        alamatkantor = getView().findViewById(R.id.detaild_alamatkantor);
        sinopsis = getView().findViewById(R.id.detaild_sinopsis);
        jk = getView().findViewById(R.id.detaild_jk);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        getdetaidosen();
    }

    public void getdetaidosen()
    {
        String url = "http://owlmu.com/bridgedb/read_detaildosen.php?nik="+mLogin.getNik();
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0; i<response.length(); i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        nama.setText(jsonObject.getString("nama"));
                        nik.setText(jsonObject.getString("nik"));
                        email.setText(jsonObject.getString("email"));
                        almatrumah.setText(jsonObject.getString("alamatrumah"));
                        alamatkantor.setText(jsonObject.getString("alamatkantor"));
                        sinopsis.setText(jsonObject.getString("sinopsis"));
                        jk.setText(jsonObject.getString("jk"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Er", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);


        progressDialog.dismiss();
    }
}
