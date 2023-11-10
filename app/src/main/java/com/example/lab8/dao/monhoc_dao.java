package com.example.lab8.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lab8.database.DbHelper;
import com.example.lab8.monhoc;

import java.util.ArrayList;

public class monhoc_dao {
    private DbHelper dbHelper;
    public  monhoc_dao(Context context){
        dbHelper = new DbHelper(context);
    }
    //lấy danh sách SQL
    public ArrayList<monhoc> getDs(){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<monhoc> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM monhoc",null);//sửa table
        if(cursor.getCount() >0){
            cursor.moveToFirst();
            do{
                list.add(new monhoc(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    //xóa
    public  boolean delete(int id){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int check = sqLiteDatabase.delete("monhoc", "id = ?",new String[]{String.valueOf(id)});//sửa table
        if(check<=0) return  false;
        return true;
    }

    //add
    public boolean add(monhoc monhoc){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("mamh",monhoc.getMamh());
        contentValues.put("tenmh",monhoc.getTenmh());
        contentValues.put("sotiet",monhoc.getSotiet());

        long check = sqLiteDatabase.insert("monhoc",null,contentValues);//sủa table
        if(check == -1) return false;
        return true;

    }
    //update
    public boolean update(monhoc monhoc){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mamh",monhoc.getMamh());
        contentValues.put("tenmh",monhoc.getTenmh());
        contentValues.put("sotiet",monhoc.getSotiet());

        int check = sqLiteDatabase.update("monhoc",contentValues,"id = ?",new String[]{String.valueOf(monhoc.getId())});
        if(check <= 0) return false;
        return true;

    }
}
