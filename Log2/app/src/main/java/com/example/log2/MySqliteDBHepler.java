package com.example.log2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.jetbrains.annotations.NotNull;

public class MySqliteDBHepler extends SQLiteOpenHelper {
    public MySqliteDBHepler(Context context) {
        super(context, "User_Foods", null, 1);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        db.execSQL("create table  User(_id integer primary key autoincrement,user varchar(20),password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

