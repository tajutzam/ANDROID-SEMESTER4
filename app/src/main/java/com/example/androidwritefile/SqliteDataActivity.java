package com.example.androidwritefile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidwritefile.adapter.MahasiswaAdapter;
import com.example.androidwritefile.entity.Mahasiswa;
import com.example.androidwritefile.helper.DbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteDataActivity extends AppCompatActivity {

    Button btn_tmbh;
    EditText nameEditText , nimEditText , passwordEditText;
    List<Mahasiswa> data;
    DbHelper dbHelper = new DbHelper(this , null , null);
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_data);
        getData();
        System.out.println(data.size());
        btn_tmbh = findViewById(R.id.btn_tmbh_mhs);
        recyclerView = findViewById(R.id.rycleMhs);
        nameEditText = findViewById(R.id.nameEditText);
        nimEditText = findViewById(R.id.nimEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        MahasiswaAdapter mahasiswaAdapter = new MahasiswaAdapter(data , this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mahasiswaAdapter);

        btn_tmbh.setOnClickListener(v -> {
            boolean isAdd = addData();
            if(isAdd){
                List<Mahasiswa> data = getData();
//                getData();
                mahasiswaAdapter.notifyDataSetChanged();
                Toast.makeText(this , "Success add mhs" , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this , "Success add mhs" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Mahasiswa> getData(){
        if(data!=null){
            data.clear();
        }
        data = dbHelper.getData();
        return data;
    }

    public boolean addData(){
        Mahasiswa mahasiswa = new Mahasiswa(nameEditText.getText().toString(), nimEditText.getText().toString() , passwordEditText.getText().toString());
        return dbHelper.addMhs(mahasiswa);
    }
}