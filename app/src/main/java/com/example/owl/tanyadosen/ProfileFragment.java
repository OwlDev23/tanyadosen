package com.example.owl.tanyadosen;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.owl.tanyadosen.classes.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    Button b_edit, b_simpan, b_cancel;
    EditText text_nama,text_nim,text_email,text_nohp,text_pass;
    LinearLayout linearLayout;
    SwipeRefreshLayout refreshLayout;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait...");
        progressDialog.setCancelable(false);

        refreshLayout = getActivity().findViewById(R.id.pswipe_lay);
        b_edit = getActivity().findViewById(R.id.pbtn_edit);
        b_simpan = getActivity().findViewById(R.id.pbtn_simpan);
        b_cancel = getActivity().findViewById(R.id.pbtn_cancel);
        text_nama = getActivity().findViewById(R.id.ptxt_nama);
        text_pass = getActivity().findViewById(R.id.ptxt_pass);
        text_email = getActivity().findViewById(R.id.ptxt_email);
        text_nim = getActivity().findViewById(R.id.ptxt_nim);
        text_nohp = getActivity().findViewById(R.id.ptxt_nohp);
        linearLayout = getActivity().findViewById(R.id.pllyt_btncon);
        refreshLayout.setOnRefreshListener(this);
        b_simpan.setOnClickListener(this);
        b_cancel.setOnClickListener(this);
        b_edit.setOnClickListener(this);
        getReadProfile();
        unEnable();

    }

    public void unEnable()
    {
        try {
            linearLayout.setVisibility(View.INVISIBLE);
            text_nama.setEnabled(false);
            text_pass.setEnabled(false);
            text_nim.setEnabled(false);
            text_email.setEnabled(false);
            text_nohp.setEnabled(false);
        }catch (Exception e)
        {
            Log.d("Unable tAG", e.getMessage());
        }

    }

    public void Enable()
    {
        try {
            linearLayout.setVisibility(View.VISIBLE);
            text_nama.setEnabled(true);
            text_pass.setEnabled(true);
            text_email.setEnabled(true);
            text_nohp.setEnabled(true);
        }catch (Exception e)
        {
            Log.d("Unable tag", e.getMessage());
        }

    }

    @Override
    public void onRefresh() {
        getReadProfile();
    }

    private void getReadProfile()
    {
        try {
            String url = "http://owlmu.com/bridgedb/readmahasiswa.php?nim="+mLogin.getNim()+"";
            refreshLayout.setRefreshing(true);
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(0);
                        text_nama.setText(jsonObject.getString("nama"));
                        text_nim.setText(jsonObject.getString("nim"));
                        text_pass.setText(jsonObject.getString("pass"));
                        if(!jsonObject.getString("nohp").isEmpty())
                        {
                            text_nohp.setText(jsonObject.getString("nohp"));
                        }
                        text_email.setText(jsonObject.getString("email"));
                        Log.d("Read Tag", "\t"+jsonObject.getString("nim") + "\n" + jsonObject.getString("nama"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("Read Tag", e.getMessage()  + " : "+ response.length());
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Read Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonArrayRequest);
            refreshLayout.setRefreshing(false);
        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void getUpdateprofile()
    {
        String url = "http://owlmu.com/bridgedb/updatemahasiswa.php?nim="+mLogin.getNim();
        url = url.replaceAll("\\s","%20");
        progressDialog.show();
        Map<String,String> params = new HashMap<String, String>();
        params.put("nama", text_nama.getText().toString());
        params.put("psw", text_pass.getText().toString());
        params.put("email",text_email.getText().toString());
        params.put("nohp",text_nohp.getText().toString());
        RequestQueue queue = Volley.newRequestQueue(getContext());

        CustomRequest customRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int sukses = response.getInt("sukses");
                    Log.d("Update", response.getString("sukses"));
                    if(sukses == 1)
                    {
                        Toast.makeText(getContext(), "Update Sukses", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        getReadProfile();
                        unEnable();
                    }else
                    {
                        Toast.makeText(getContext(), "Update Gagal", Toast.LENGTH_SHORT).show();
                        Log.d("Update", response.getString("pesan"));
                        progressDialog.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Update error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.d("Error Volley", error.getMessage());
            }
        });
        queue.add(customRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.pbtn_simpan:
                try {
                    getUpdateprofile();
                }catch (Exception e)
                {
                    Log.d("Simpan Tag",e.getMessage());
                }
                break;
            case R.id.pbtn_cancel :
                unEnable();
                break;
            case  R.id.pbtn_edit:
                Enable();
                break;
        }
    }
}
