package com.example.androidwritefile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidwritefile.R;
import com.example.androidwritefile.entity.Mahasiswa;

import java.util.List;
import java.util.Map;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.MahasiswaViewHolder> {



    List<Mahasiswa> data;
    Context context;

    public MahasiswaAdapter(List<Mahasiswa> dataMahasiswa, Context context) {
        this.data = dataMahasiswa;
        this.context = context;
    }

    @NonNull
    @Override
    public MahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_mhs_card, parent, false);
        return new MahasiswaViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MahasiswaViewHolder holder, int position) {
        holder.nim.setText(data.get(position).getNim());
        holder.name.setText(data.get(position).getName());
        holder.password.setText(data.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        notifyDataSetChanged();
        return data.size();
    }

    public static class MahasiswaViewHolder extends RecyclerView.ViewHolder {

        TextView  name;
        TextView  nim;
        TextView  password;

        public MahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
           name =  itemView.findViewById(R.id.name_mhs);
            nim =  itemView.findViewById(R.id.nim_mhs);
            password =  itemView.findViewById(R.id.password_mhs);
        }
    }
}
