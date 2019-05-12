package com.javahly.ch5;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//handler定义在子线程中的例子
//用一个子线程去计算某个数字内的所有素数，主程序负责输入这个上界数字，
// onCreate方法中创建子线程并让子线程运行起来。
//子线程的run方法中首先要调用Looper.prepare();来创建Looper对象，
// 然后创建handler对象，等待Looper对象给handler对象传递消息，接着调用
//Looper.loop()方法，反复的从消息队列中提取其他线程发过来的消息（loop方法是死循环），
// 提取到就发给handler，让handler调用handleMessage方法处理消息，
// 这里的处理消息，其实就是开始计算素数，即计算素数的代码是在子线程的handleMessage方法里的。
//这里是主线程给子线程的handler发送消息。
// 当单击开始计算按钮时，就把上界这个数字当做message发给子线程的handler
//handler在子线程中执行。
public class Handler2Activity extends AppCompatActivity {
    EditText txtNumber;
    int maxNumber;
    TextView txtResult;
    MyThread newThread;
    Handler mainHandler;//目的是把子线程的结果显示到txtResult里面，如果只是讲解handler定义在子线程中的用法，则不需要这个变量
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler2);
        txtNumber=(EditText)findViewById(R.id.txtNumber);
        txtResult=(TextView)findViewById(R.id.txtResult);
        mainHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==101){
                    Bundle bundle=msg.getData();
                    ArrayList<Integer> arrayList=bundle.getIntegerArrayList("nums");
                    txtResult.setText(arrayList.toString());
                }
            }
        };
        //先创建子线程，并启动子线程，从而让子线程的Looper对象和handler开始运行，
        // 等待接受主线程发送的计算素数的命令
        newThread=new MyThread();
        newThread.start();
    }
    public void btnCalculateClick(View v){
        maxNumber=Integer.parseInt(txtNumber.getText().toString());
        Message msg=new Message();
        msg.what=123;
        Bundle bundle=new Bundle();
        bundle.putInt("maxNumber",maxNumber);
        msg.setData(bundle);
        newThread.handler.sendMessage(msg);//给子线程发送消息，让子线程开始计算素数
    }
    class MyThread extends Thread{
        Handler handler;//定义handler对象
        public void run(){
            //线程启动后，从run方法开始执行
            //首先调用Looper的prepare()方法创建Looper的对象
            // （Looper的构造方法是private的，只能通过prepare方法创建对象，在prepare方法中调用了构造方法）
            //创建Looper的对象后，同时也为该线程创建了一个消息队列，
            // 其他线程通过handler对象的sendMessage方法发送的消息就是保存在消息队列里。
            //后面通过Looper.loop();来从消息队列读取消息，并发送给handler的handleMessage()方法。
            Looper.prepare();
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    //当handler收到消息时就会调用自己的handleMessage(Message msg)方法，消息是由
                    //Looper的对象通过loop方法把消息从消息队列中取出来发给handler对象的
                    if(msg.what==123){
                        int upper=msg.getData().getInt("maxNumber");
                        ArrayList<Integer> nums = new ArrayList<Integer>();
                        // 计算从2开始、到upper的所有质数
                        outer:
                        for (int i = 2 ; i <= upper ; i++)
                        {
                            // 用i处于从2开始、到i的平方根的所有数
                            for (int j = 2 ; j <= Math.sqrt(i) ; j++)
                            {
                                // 如果可以整除，表明这个数不是质数
                                if(i != 2 && i % j == 0)
                                {
                                    continue outer;
                                }
                            }
                            nums.add(i);
                        }
                        //txtResult.setText(nums.toString());这句话不行，因为子线程不允许访问UI主线程的控件
                        // 使用Toast显示统计出来的所有质数
                        Toast.makeText(Handler2Activity.this , nums.toString(),Toast.LENGTH_LONG).show();
                        Bundle bundle=new Bundle();
                        bundle.putIntegerArrayList("nums",nums);
                        Message msg1=new Message();
                        msg1.setData(bundle);
                        msg1.what=101;
                        mainHandler.sendMessage(msg1);
                        //txtResult.setText(nums.toString());//能显示结果，但是接着就报错了，因为子线程不允许修改主界面的控件的值
                    }
                }
            };
            Looper.loop();//loop方法从消息队列读取消息并发给handler对象，该方法里面是一个死循环，即一直在读取消息。
        }
    }
}
