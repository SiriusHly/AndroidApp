package com.javahly.ch2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditTextTestActivity extends AppCompatActivity {

    Button btnOk;
    EditText txtCongratulations;
    TextView txtHint;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_test);
        btnOk=(Button) findViewById(R.id.btnOK);
        txtCongratulations=(EditText)findViewById(R.id.txtCongratulations);
        txtHint=(TextView)findViewById(R.id.txtHint);

        txtCongratulations.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str="从"+start+"开始的"+before+"个旧字符被"+count+"个新字符所替换，新的字符串是："+s.toString();
                txtHint.setText(str+";  您已经输入了"+s.length()+"个字符");
                Toast.makeText(EditTextTestActivity.this,str,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(count==1) {
                    System.out.println("当前是active1.png");count=0;
                    btnOk.setBackgroundResource(R.mipmap.yes);
                    btnOk.setText("确定");
                }
                else {
                    System.out.println("当前是inactive.png");count=1;
                    btnOk.setBackgroundResource(R.mipmap.no);
                    btnOk.setText("取消");
                }
            }
        });
    }
}
