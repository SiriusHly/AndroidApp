package com.javahly.ch5;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtNumber;
    Button btnCalculate,btnShowImage;
    TextView txtResult;
    ImageView imageView;
    int[] images={R.mipmap.img_0,R.mipmap.img_3,R.mipmap.img_4};
    int index=0;//需要显示的图片索引
    //handler定义在主线程中，用于处理handler自己收到的消息msg。
    //有消息发过来的时候，自动会调用handleMessage(Message msg)方法，其中msg就是收到的消息
    //消息msg也是在别的线程中调用handler自己的sendEmptyMessage(0x101)或者sendMessage(message)发给他自己的
    //注意：实际上，在handler接受消息时是有一个叫做Looper的对象参与的，并不是handler自己拿到消息。Looper对象通过Looper.prepare()方法创建（他的构造方法是private的）
    //同时会为本程序所在的线程创建一个消息队列，用于保存其他线程传递给handler的消息。创建后，需要调用Looper.loop()方法
    //从消息队列中取出消息，并发给handler对象去处理。但是这个Looper参与的过程在这里是看不到的，
    // 因为这里handler定义在主线程中，主线程会自动创建Looper对象，并调用他的loop（）方法去取消息队列的消息，并发给handler
    //如果handler定义在子线程中，这个过程要手动创建，手工编写代码。
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //本方法在handler收到消息时自动调用的。
            //Message中主要包括四个属性 int arg1，int arg2，int what，Bundle bundle
            //其中what代表是哪个线程或任务发过来的，线程发消息时自己找一个int的数作为这个线程标识，放到what变量中
            //bundle用于保存从线程传过来的数据，arg1和arg2也是传过来的数据，但只能保存int数据
            if(msg.what==0x101){//0x101表示是从显示图片的子线程传过来的消息，所以要执行显示图片的功能
                imageView.setImageResource(images[(index++)%3]);
            }else if(msg.what==0x100){//0x100表示是从计算质数的子线程传过来的消息，所以要执行显示找到的质数的功能
                Bundle bundle=msg.getData();
                ArrayList<Integer> nums=(ArrayList<Integer>)bundle.getIntegerArrayList("nums");
                txtResult.setText(nums.toString());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtNumber=(EditText)findViewById(R.id.txtNumber);
        btnCalculate=(Button)findViewById((R.id.btnCalculate));
        btnShowImage=(Button)findViewById(R.id.btnShowImage);
        txtResult=(TextView)findViewById(R.id.txtResult);
        imageView=(ImageView)findViewById(R.id.imageView1);
    }
    //点击显示图片按钮，就创建显示图片的线程对象，从而执行线程中的run方法
    //本功能其实不用子线程也行，因为计算量小，不影响主线程的工作，这里只是为了演示handler的用法而已。
    public void btnShowImageClick(View view){
        new ShowImageThread().start();
    }
    //点击计算质数按钮，就创建找质数的线程对象，从而执行线程中的run方法
    //在run方法中把所有符合条件的质数找到，然后通过主线程的handler的sendMessage方法传给主线程的handler，让handler调用自己的handleMessage方法处理。
    public void btnCalculateClick(View view){
        new CalculateThread().start();
    }
    //CalculateThread是找质数的线程
    class CalculateThread extends Thread{
        public void run(){
            int upper=Integer.parseInt(txtNumber.getText().toString().trim());
            ArrayList<Integer> nums = new ArrayList<Integer>();
            // 计算从2开始、到upper的所有质数
            outer:
            for (int i = 2 ; i <= upper ; i++) {
                // 用i处于从2开始、到i的平方根的所有数
                for (int j = 2 ; j <= Math.sqrt(i) ; j++) {
                    // 如果可以整除，表明这个数不是质数
                    if(i != 2 && i % j == 0){
                        continue outer;
                    }
                }
                nums.add(i);
            }
            Bundle bundle=new Bundle();//相当于一个Map，里面存的是键值对
            bundle.putIntegerArrayList("nums",nums);//键nums，对应的值是集合nums
            Message message=new Message();
            message.setData(bundle);//把bundle放到消息message中
            message.what=0x100;//0x100表示是找质数的线程发过去的消息，这个数随便写，只要跟别的线程的数字不一样即可。
            handler.sendMessage(message);//发送消息给主线程的handler对象，让他处理（实际是发到消息队列里，由Looper对象的loop方法发给handler对象）
        }
    }
    //ShowImageThread是显示图片的线程，在run方法中发送10次消息给主线程的handler对象，
    // handler对象就执行10次处理消息的功能
    class ShowImageThread extends Thread{
        public void run(){
            for(int i=0;i<10;i++){
                //给handler发送10次消息
                handler.sendEmptyMessage(0x101);//0x101表示是显示图片的线程发过去的消息，这个数随便写，只要跟别的线程的数字不一样即可。
                try{Thread.sleep(1000);}//休息1000毫秒再继续往下执行
                catch(InterruptedException e){}
            }
        }
    }

}
