package com.javahly.ch10;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtPassword;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //得到界面的3个控件
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnOk = (Button) findViewById(R.id.btnOk);
        //得到SharedPreferences对象，他类似于Map集合，将数据以键值对的形式保存至xml文件中。
        final SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        //SharedPreferences需要在内部类中使用，所以必须定义成final类型的
        //user是文件名，即user.xml,保存在/data/data/weili.org.myapplication/shared_prefs/user.xml
        //weili.org.myapplication是应用程序的包名
        String userName = sharedPreferences.getString("userName", "");//从user.xml中读取userName键的值，如果读不到，就把空字符串付给他
        String userPassword = sharedPreferences.getString("userPassword", "");
        if (!userName.equals(""))//如果不为空字符串，即读到数据了，就把读到的数据显示到用户名输入框上
            txtName.setText(userName);
        if (!userPassword.equals(""))
            txtPassword.setText(userPassword);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", txtName.getText().toString());
                editor.putString("userPassword", txtPassword.getText().toString());
                editor.commit();
            }
        });
    }
}
