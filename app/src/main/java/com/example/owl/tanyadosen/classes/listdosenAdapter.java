package com.example.owl.tanyadosen.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.owl.tanyadosen.R;

import java.util.List;

/**
 * Created by Owl on 27/12/2017.
 */

public class listdosenAdapter extends BaseAdapter {
    Context context;
    List<mlvDosen> itemsdosen;
    public listdosenAdapter(Context c, List<mlvDosen> items) {
        this.context = c;
        this.itemsdosen = items;

    }

    @Override
    public int getCount() {
        return itemsdosen.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsdosen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_listdosen, parent, false);
        TextView tx_nik = view.findViewById(R.id.lvdstx_nik);
        TextView tx_nama = view.findViewById(R.id.lvdstx_nama);
        TextView tx_jurusan = view.findViewById(R.id.lvdstx_jurusan);
        TextView tx_matakuliah = view.findViewById(R.id.lvdstx_matakuliah);
        mlvDosen mlvDosen =itemsdosen.get(position);
        tx_nik.setText(mlvDosen.getNik());
        tx_nama.setText(mlvDosen.getNama());
        tx_jurusan.setText(mlvDosen.getJurusan());
        tx_matakuliah.setText(mlvDosen.getMatakuliah());
        return view;
    }
}
