package com.javahly.ch8;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtNumber,txtResult;
    MathService.MyBinder binder;
    MathService mathService;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //本方法的作用就是为了把MathService中的onBind方法返回的binder对象赋值给本类中定义的binder对象，
            // 即他俩是一个对象，这样在本Activity中调用binder的方法，就相当于调用了MathService中的binder对象的方法了
            //当该Activity与MathService连接成功时会 回调该方法，即该方法在MathService的onBind方法之后执行
            System.out.println("onServiceConnected()");
            binder=(MathService.MyBinder)service;
            mathService=binder.getService();
            System.out.println("绑定后获得了MathService的binder对象");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //当该Activity与MathService断开连接时会回调该方法。
            //需要注意的是unbindService()方法成功后，系统并不会调用onServiceDisconnected ()，
            //因为onServiceDisconnected ()仅在意外断开绑定时才被调用
            System.out.println("onServiceDisconnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNumber=(EditText)findViewById(R.id.txtNumber);
        txtResult=(EditText)findViewById(R.id.txtResult);
    }
    public void btnStartClick(View view){
        //第一种启动方法，显式启动
        System.out.println("开始显式启动MathService");
        Intent intent=new Intent(this,MathService.class);
        bindService(intent,conn, Service.BIND_AUTO_CREATE);//Service.BIND_AUTO_CREATE表示自动创建Service；0表示不自动创建
        System.out.println("显式启动MathService完成");
    }
    public void btnStopClick(View view){
        //第一种停止方法，显式停止
        //Intent intent=new Intent(this,MathService.class);
        unbindService(conn);
    }
    public void btnSumClick(View view){
        if(txtNumber.getText().toString().trim().length()==0)
            return;
        int sum=binder.getSum(Integer.parseInt(txtNumber.getText().toString().trim()));
        txtResult.setText(String.valueOf(sum));
    }
    public void btnJieChengClick(View view){
        if(txtNumber.getText().toString().trim().length()==0)
            return;
        int sum=mathService.getJieCheng(Integer.parseInt(txtNumber.getText().toString().trim()));
        txtResult.setText(sum+" ");
    }
}
