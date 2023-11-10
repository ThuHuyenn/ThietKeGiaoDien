package com.example.lab8.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public  DbHelper(Context context){

        super(context,"ASM",null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String monhoc   = "CREATE TABLE monhoc(id INTEGER PRIMARY KEY AUTOINCREMENT, mamh TEXT, tenmh TEXT,sotiet INTEGER)";
        db.execSQL(monhoc);
        String monhocc    = "INSERT INTO monhoc VALUES(1,'M1','Java',10)";
        db.execSQL(monhocc);
        String monhoccc   = "INSERT INTO monhoc VALUES(2,'M2','Web',5),(3,'M3','Web',7)";
        db.execSQL(monhoccc);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS monhoc");

            onCreate(db);
        }
    }
}
