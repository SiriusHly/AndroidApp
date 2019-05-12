package com.javahly.ch5;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//handler定义在主程序中
//Looper:每个线程只能够有一个Looper,管理MessageQueue,不断地从中取出Message分发给对应的Handler处理！
//当我们的子线程想修改Activity中的UI组件时,我们可以新建一个Handler对象,通过这个对象向主线程发送信息;而我们发送的信息会先到主线程的MessageQueue进行等待,由Looper按先入先出顺序取出,再根据message对象的what属性分发给对应的Handler进行处理！
//Handler写在主线程中.在主线程中,因为系统已经初始化了一个Looper对象,所以我们直接创建Handler对象,就可以进行信息的发送与处理了！
public class Handler1Activity extends AppCompatActivity {

    int[] images={R.mipmap.img_0,R.mipmap.img_3,R.mipmap.img_4};
    ImageView imageView;
    EditText txtSecond;
    Button btnStart;
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler1);
        imageView=(ImageView)findViewById(R.id.imageView);
        txtSecond=(EditText)findViewById(R.id.txtSecond);
        btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyThread().start();
            }
        });
        //用自定义子线程的方式

        /*用Timer定时器方式
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        },0,1000);
        */
    }
    //定义一个handler对象，在其中的handleMessage方法中，根据msg的what值决定是否执行换图片的功能
    //当给该hangdler对象发送消息时，就会触发handleMessage方法。
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0x123){
                imageView.setImageResource(images[(index++)%3]);
            }
        }
    };
    class MyThread extends Thread{
        public void run(){
            int second=Integer.parseInt(txtSecond.getText().toString());
            for(int i=0;i<10;i++){
                //给handler发送10次消息
                handler.sendEmptyMessage(0x123);
                try{Thread.sleep(1000*second);}
                catch(InterruptedException e){}
            }
        }
    }
}
