package com.javahly.ch4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");
        if (id.equals("张三") && password.equals("123")) {
            result.setText("账号：" + id + "密码：" + password);
        }else{
            intent.putExtra("result","用户名或密码错误");
            setResult(1,intent);
            finish();
        }
    }
}
