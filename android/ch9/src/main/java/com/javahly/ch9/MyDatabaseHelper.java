package com.javahly.ch9;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018-04-25.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MyDatabaseHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableStudent="create table student(studentid text primary key,studentname text,sex text,age integer)";
        String sqlCreateTableCourse="create table course(courseid text primary key,coursename text,credit integer)";
        String sqlCreateTableSc="create table sc(studentid text primary key,courseid text,score integer)";
        db.execSQL(sqlCreateTableStudent);
        db.execSQL(sqlCreateTableCourse);
        db.execSQL(sqlCreateTableSc);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlCreateTableTeacher="create table teacher(teacherid text primary key,teachername text,sex text,age integer)";
        if(oldVersion==1&&newVersion==2)//当是版本号是从1升级到2的时候执行下面的创建teacher表的操作
            db.execSQL(sqlCreateTableTeacher);
    }
}

