package com.javahly.ch5;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

public class Handler3Activity extends AppCompatActivity {
    EditText txtNumber;
    int maxNumber;
    TextView txtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler2);
        txtNumber=(EditText)findViewById(R.id.txtNumber);
        txtResult=(TextView)findViewById(R.id.txtResult);
    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==123){
                List<Integer> list=msg.getData().getIntegerArrayList("nums");
                txtResult.setText(list.toString());
            }
        }
    };
    public void btnCalculateClick(View view){
        new MyThread().start();
    }
    class MyThread extends Thread{
        public void run(){
            int upper=Integer.parseInt(txtNumber.getText().toString().trim());
            ArrayList<Integer> nums = new ArrayList<Integer>();
            // 计算从2开始、到upper的所有质数
            outer:
            for (int i = 2 ; i <= upper ; i++) {
                // 用i除以从2开始、到i的平方根的所有数
                for (int j = 2 ; j <= Math.sqrt(i) ; j++) {
                    // 如果可以整除，表明这个数不是质数
                    if(i != 2 && i % j == 0){
                        continue outer;
                    }
                }
                nums.add(i);
            }
            Bundle bundle=new Bundle();
            bundle.putIntegerArrayList("nums",nums);
            Message message=new Message();
            message.setData(bundle);
            message.what=123;
            handler.sendMessage(message);
            //txtResult.setText(nums.toString());//能显示结果，但是接着就报错了，因为子线程不允许修改主界面的控件的值
        }
    }
}

