package com.example.owl.tanyadosen;

import android.app.FragmentManager;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.owl.tanyadosen.classes.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LdosenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    List<mlvDosen> itemdosen = new ArrayList<mlvDosen>();
    ListView listViewdosen;
    SwipeRefreshLayout swipe;
    listdosenAdapter ListdosenAdapter;
    android.support.v4.app.FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ldosen, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentManager = getFragmentManager();
        listViewdosen = getActivity().findViewById(R.id.listviewdosen);
        swipe = getActivity().findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this);
        //isi dulu itemnya
        try {
            getjsonDosen();
        }catch (Exception ex)
        {
            Toast.makeText(getContext(), "Error : "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //baru masukkan ke adapter
        ListdosenAdapter = new listdosenAdapter(getContext(), itemdosen);
        listViewdosen.setAdapter(ListdosenAdapter);
        listViewdosen.setOnItemClickListener(this);

    }

    private  void getjsonDosen()
    {
        swipe.setRefreshing(true);
        itemdosen.clear();
    String urlowl = "http://owlmu.com/bridgedb/td_readdosen.php";
    RequestQueue queue = Volley.newRequestQueue(getContext());
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlowl, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            for(int i=0; i<response.length(); i++)
            {
                try {
                    mlvDosen mlvDosen = new mlvDosen();
                    JSONObject jsonObject = response.getJSONObject(i);
                    mlvDosen.setNik(jsonObject.getString("nik"));
                    mlvDosen.setNama(jsonObject.getString("nama"));
                    mlvDosen.setJurusan(jsonObject.getString("jurusan"));
                    mlvDosen.setMatakuliah(jsonObject.getString("matakuliah"));
                    itemdosen.add(mlvDosen);
                    Log.d("Read Tag : ", "Read Sukses");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Read Tag : ", e.getMessage().toString());

                }
            }
            ListdosenAdapter.notifyDataSetChanged();
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), "volley error "+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
        queue.add(jsonArrayRequest);
        swipe.setRefreshing(false);
}

    @Override
    public void onRefresh() {
        getjsonDosen();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mlvDosen mlv = itemdosen.get(position);
        mLogin.setNik(mlv.getNik());
        DetaildosenFragment detaildosenFragment = new DetaildosenFragment();
        fragmentManager.beginTransaction().replace(R.id.containerfragment, detaildosenFragment).addToBackStack(null).commit();
    }
}
