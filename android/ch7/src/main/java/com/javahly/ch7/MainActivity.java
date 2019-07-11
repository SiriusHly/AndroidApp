package com.javahly.ch7;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText txtContent,txtResult;
    MySumReceiver mySumReceiver;
    LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtContent=(EditText)findViewById(R.id.txtContent);
        txtResult=(EditText)findViewById(R.id.txtResult);
        mySumReceiver=new MySumReceiver();
        IntentFilter filter=new IntentFilter();
        filter.addAction("my.sum.action");
        localBroadcastManager=LocalBroadcastManager.getInstance(MainActivity.this);
        localBroadcastManager.registerReceiver(mySumReceiver,filter);
    }
    public void btnSendClick(View view){
        Intent intent=new Intent();
        intent.setAction("my.sum.action");
        intent.putExtra("number",txtContent.getText().toString());
        Toast.makeText(MainActivity.this,"number="+txtContent.getText().toString(),Toast.LENGTH_LONG).show();
        localBroadcastManager.sendBroadcast(intent);
        //System.out.println("广播已经发出，数字为"+txtContent.getText().toString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(mySumReceiver);
    }
    class MySumReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String number=intent.getStringExtra("number");//获得广播的消息
            Toast.makeText(MainActivity.this,"收到的number="+number,Toast.LENGTH_LONG).show();
            System.out.println("收到的数字为："+number);
            int intNumber=Integer.parseInt(number);//转化为数字，准备求和
            int sum=1;
            for(int i=1;i<=intNumber;i++){
                sum=sum*i;
            }
            Toast.makeText(MainActivity.this,"结果sum="+sum,Toast.LENGTH_LONG).show();
            txtResult.setText(number+"的阶乘是："+sum);
        }
    }
}
