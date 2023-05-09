package com.example.androidwritefile.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.androidwritefile.entity.Mahasiswa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "mahasiswa.db";
    public static int VERSION = 1;


    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, DB_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS mhs (\n"
                + " id integer PRIMARY KEY autoincrement,\n"
                + " name text NOT NULL,\n"
                + " nim text unique,\n"
                + " password text\n"
                + ");";
        ContentValues contentValues = new ContentValues();

        db.execSQL(sql);
    }

    public void insertOne(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nim" , "e41212335");
        contentValues.put("name" , "zam");
        contentValues.put("password" , "rahasia");
        db.insert("mhs" ,null ,contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists mhs");
        onCreate(db);
    }

    public boolean addMhs(Mahasiswa mahasiswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", mahasiswa.getName());
        values.put("password", mahasiswa.getPassword());
        values.put("nim" , mahasiswa.getNim());
        try {
            db.insert("mhs", null, values);
            db.close();
            Log.i("SUCCESS" , "success insert data mahasiswa");
            return true;
        } catch (SQLiteException exception) {
            System.out.println("failed to insert data" + exception.getMessage());
        }
        return false;
    }
    @SuppressLint("Range")
    public List<Mahasiswa> getData(){
        List<Mahasiswa> data = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from mhs", null);
        try{
            if(cursor.moveToFirst()){
                do{
                    Mahasiswa mahasiswa = new Mahasiswa();
                    mahasiswa.setName(cursor.getString(cursor.getColumnIndex("name")));
                    mahasiswa.setNim(cursor.getString(cursor.getColumnIndex("nim")));
                    mahasiswa.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                    data.add(mahasiswa);
                }while(cursor.moveToNext());
            }
        }catch (Exception e){
            Log.e("e" , e.getMessage());
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        System.out.println(data);
        return data;
    }
}
