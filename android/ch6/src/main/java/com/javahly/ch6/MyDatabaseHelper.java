package com.javahly.ch6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableTeacher="create table teacher(teacherId varchar(30) primary key,teacherName varchar(30),sex varchar(30),salary integer)";
        db.execSQL(sqlCreateTableTeacher);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlCreateTableTeacher="create table teacher(teacherId varchar(30) primary key,teacherName varchar(30),sex varchar(30),salary integer)";
        if(oldVersion==1&&newVersion==2)//当是版本号是从1升级到2的时候执行下面的创建teacher表的操作
            db.execSQL(sqlCreateTableTeacher);
    }
}

