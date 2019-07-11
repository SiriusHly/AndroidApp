package com.javahly.ch6;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentBpo {
    //往数据库student表中插入一个老师
    //public static void insert(Context context, String  studentId,String studnetName,){
    public static void insert(Context context, Teacher teacher){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TeacherDB",null,1);
        String insertSql="insert into teacher(teacherId,teacherName,sex,salary) values(?,?,?,?)";
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        db.execSQL(insertSql,new Object[]{teacher.getTeacherId(),teacher.getTeacherName(),teacher.getSex(),teacher.getSalary()});
        db.close();
        System.out.println("添加老师成功"+teacher.getTeacherId());
    }
    //把原来学号为oldStudentId的老师更新为student对象对应的老师
    public static void update(Context context, Teacher teacher,String oldTeacherId){
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(context,"TeacherDB",null,1);
        String updateSql="update teacher set teacherId=?,teacherName=?,sex=?,salary=? where teacherId=?";
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        db.execSQL(updateSql,new Object[]{teacher.getTeacherId(),teacher.getTeacherName(),teacher.getSex(),teacher.getSalary(),oldTeacherId});
        db.close();
        System.out.println("修改老师成功"+teacher.getTeacherId());
    }
    //根据学号删除老师，
    public static void delete(Context context,String teacherId){
        MyDatabaseHelper dbDelper=new MyDatabaseHelper(context,"TeacherDB",null,1);
        SQLiteDatabase db=dbDelper.getReadableDatabase();
        String deleteSql="delete from teacher where teacherId=?";
        db.execSQL(deleteSql,new String[]{teacherId});
        db.close();
        System.out.println("删除老师成功"+teacherId);
    }
    public static void delete1(Context context,String teacherId){
        MyDatabaseHelper dbDelper=new MyDatabaseHelper(context,"TeacherDB",null,1);
        SQLiteDatabase db=dbDelper.getReadableDatabase();
        db.delete("teacher","teacherId=?",new String[]{teacherId});
        db.close();
    }
    //按学号、姓名、性别查找符合条件的老师
    public static List<Map<String,String>> getAllTeacher(Context context, String teacherId, String teacherName, String sex){
        List<Map<String,String>> teacherList=new ArrayList<Map<String,String>>();
        MyDatabaseHelper dbDelper=new MyDatabaseHelper(context,"TeacherDB",null,1);
        SQLiteDatabase db=dbDelper.getReadableDatabase();
        String selectSql="select teacherId,teacherName,sex,salary  from teacher where teacherId like ? and teacherName like ? and sex like ?";
        Cursor cursor=db.rawQuery(selectSql,new String[]{"%"+teacherId+"%","%"+teacherName+"%","%"+sex+"%"});
        while(cursor.moveToNext()){
            Map<String,String> map=new HashMap<String,String>();
            map.put("teacherId",cursor.getString(0));
            map.put("teacherName",cursor.getString(1));
            map.put("sex",cursor.getString(2));
            map.put("salary",cursor.getString(3));
            //()Teacher(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));
            teacherList.add(map);
            System.out.println("teacherId="+map.get("teacherId"));
        }
        cursor.close();
        db.close();
        return teacherList;
    }
}
