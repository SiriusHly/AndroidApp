package com.javahly.ch4;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnReset;
    private EditText txtUserId;
    private EditText txtUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnReset = (Button) findViewById(R.id.reset);
        txtUserId = (EditText) findViewById(R.id.userId);
        txtUserPassword = (EditText) findViewById(R.id.userPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtUserId.getText().toString();
                String password = txtUserPassword.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("id", id);
                intent.putExtra("password", password);
                intent.setClass(LoginActivity.this, MainActivity.class);
                //startActivity(intent);
                //错误能返回
                startActivityForResult(intent,1);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUserId.setText("");
                txtUserPassword.setText("");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //得到新Activity 关闭后返回的数据
        String result = data.getExtras().getString("result");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }
}
