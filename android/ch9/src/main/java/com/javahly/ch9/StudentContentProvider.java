package com.javahly.ch9;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.sql.PreparedStatement;

public class StudentContentProvider extends ContentProvider {
    private UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);//常量UriMatcher.NO_MATCH表示Uri不匹配时的返回码
    private MyDatabaseHelper dbHelper;
    final String AUTHORITY="weili.org.myapplication.student";
    //注意：AUTHORITY不包括content://，写uri时要加上content://
    //Authority是URI的标识（一般写主机名或者域名，或者写任何一个字符串，不重复即可），
    // Authority唯一定义了是哪个ContentProvider提供这些数据，
    //系统是根据这个部分找到操作哪个ContentProvider，
    // 这个标识在AndroidManifest.xml文件里的authorities属性中说明

    public StudentContentProvider() {
        System.out.println("StudentContentProvider 构造方法() 被调用");
    }
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        //第一次调用StudentContentProvider时会执行该方法，只执行一次
        //获得数据库dbHelper对象，将来用它得到SQLLiteDatabase对象
        dbHelper=new MyDatabaseHelper(this.getContext(),"studentDB",null,1);
        //给matcher添加所有要匹配的uri，将来在各个功能模块中通过匹配来决定该执行什么功能
        matcher.addURI(AUTHORITY,"studentall",1);//查询所有学生记录
        matcher.addURI(AUTHORITY,"students/*",2);//根据学号和姓名模糊查询获得多个记录。*代表匹配任何字符，#代表匹配任何数字
        matcher.addURI(AUTHORITY,"student/#",3);//单个学生，需要根据studentid来判断,#是数字通配符，将来可以匹配student/001,student/002等
        matcher.addURI(AUTHORITY,"insertstudent",1);//插入学生
        matcher.addURI(AUTHORITY,"deletestudent/#",1);//删除单个学生，需要根据studentid来判断，#将来调用时实参使用学号来代替
        matcher.addURI(AUTHORITY,"deleteallstudent",2);//删除所有学生
        matcher.addURI(AUTHORITY,"updatestudent",1);//修改学生信息
        System.out.println("mather"+matcher.toString());
        System.out.println("StudentContentProvider  onCreate() 被调用");
        return true;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        //throw new UnsupportedOperationException("Not yet implemented");
        //selection：是where条件语句，不包括where这个单词
        //selectionArgs是where语句中各个？对应的值
        System.out.println("delete uri="+uri);
        int ret=-1;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case 1://删除单个学生，需要根据studentid来判断
                //可以通过ContentUris.parseId(uri)来得到uri最后的id部分的值，当然这里不需要，可以直接通过selection和selectionArgs来完成删除功能
                //String studentId= String.valueOf(ContentUris.parseId(uri));
                //实际上，case 1 和case 2，用一个即可，只要调用是把selection 和selectionArgs设为null即可
                ret = db.delete("student", selection, selectionArgs);break;
            case 2:
                ret = db.delete("student", null, null);break;
            default:
                throw new IllegalArgumentException("未知的uri");
        }
        db.close();
        //可以用下面语句通知那些ContentObserver，该uri对应的数据已经变化
        this.getContext().getContentResolver().notifyChange(Uri.parse("content://weili.org.myapplication.student/studentall"),null);
        return ret;
    }
    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        //db.insert(String table,String nullcoloumn,ContentValues values)
        //table:表名  nullcoloumn：当values为空或里面的key的个数为0时，nullcolumn表示要插入null的那一列的列名
        //目的是为了生成合法的insert 语句，例如 insert into table(nullcolumn) values(null)，防止出现insert into table() value()这样的sql语句
        //values:相当于一个map对象，里面是一些键值对的形式，键是字段名，值是字段的值
        System.out.println("insert uri="+uri.getPath());
        System.out.println("StudentContentProvider内的insert 方法被调用");
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            case 1:db.insert("student",null,values);//null表示没有强制输入null的列。
                db.close();break;
            default:
                //throw new UnsupportedOperationException("Not yet implemented");
                throw new IllegalArgumentException("未知的uri");
        }
        db.close();
        this.getContext().getContentResolver().notifyChange(Uri.parse("content://weili.org.myapplication.student/studentall"),null);
        return uri;

    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
        //projection：是select语句中的字段列表
        //selection：是where条件语句，不包括where这个单词
        //selectionArgs是where语句中各个？对应的值

        System.out.println("uri.getHost()="+uri.getHost());
        System.out.println("uri.getAuthority()="+uri.getAuthority());
        System.out.println("uri.getPath()="+uri.getPath());
        System.out.println("uri.getQuery()="+uri.getQuery());
        System.out.println("StudentContentProvider内的query 方法被调用");
        Cursor cursor=null;
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        switch (matcher.match(uri)){
            //下面3种情况其实代码是一样的，完全可以合并成一个，因为我是通过实参来控制的。
            // 当查询所有学生时，selection（where语句的内容）和selectionArgs（where条件中的参数）其实传的都是null
            //查询单个学生时，selection=" studentid=?"，selectionArgs={"001"},即单个的学生编号
            //按姓名和学号模糊查询时，selection=" studentid like ? and studentname like ?"，selectionArgs={"%001%","%zhang%"}的形式
            //当然，也可以在这里取得uri的内容，从而根据情况判断来决定执行什么功能。
            case 1://查询所有学生
                System.out.println("case 1 查询所有学生");
                /*创建与mysql的连接，
                String sql="select "+projection+" where "+selection+" order by "+sortOrder;
                PreparedStatement pst=new PreparedStatement(sql);
                for(int i=0;i<selectionArgs.length;i++){
                    pst.setString(selectionArgs[i]);
                }*/
                cursor=db.query("student",projection,selection,selectionArgs,"","",sortOrder);
                break;
            case 2://根据学号、姓名 模糊查询
                System.out.println("case 2 根据学号、姓名 模糊查询");
                cursor=db.query("student",projection,selection,selectionArgs,"","",sortOrder);
                break;
            case 3://单个学生
                System.out.println("case 3 单个学生");
                cursor=db.query("student",projection,selection,selectionArgs,"","","");
                break;
            default:System.out.println("调用query方法时，没有收到合适的uri");
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        //values:相当于一个map对象，里面是一些键值对的形式，键是字段名，值是字段的值
        //selection：是where条件语句，不包括where这个单词
        //selectionArgs是where语句中各个？对应的值
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        int ret=-1;
        switch (matcher.match(uri)){
            case 1:
                ret=db.update("student",values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("未知的uri");
        }
        db.close();
        this.getContext().getContentResolver().notifyChange(Uri.parse("content://weili.org.myapplication.student/studentall"),null);
        return ret;
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
