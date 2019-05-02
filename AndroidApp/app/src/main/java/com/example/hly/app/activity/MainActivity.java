package com.example.hly.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hly.app.R;
import com.example.hly.app.thread.LoginThread;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText txtUserId;
    private EditText txtUserPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        txtUserId = (EditText)findViewById(R.id.userId);
        txtUserPassword =(EditText)findViewById(R.id.userPassword);
        //progressBar = (ProgressBar)findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.GONE);

        //登录事件监听
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progressBar.setVisibility(View.VISIBLE);

                try {
                    LoginThread loginThread = new LoginThread(txtUserId.getText().toString(),txtUserPassword.getText().toString());
                    Thread t = new Thread(loginThread);
                    t.start();
                    t.join();
                    //progressBar.setVisibility(View.GONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(LoginThread.message.equals("1")) {
                    Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Index.class);
                    MainActivity.this.startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_SHORT);
                }


            }
        });


    }


}
