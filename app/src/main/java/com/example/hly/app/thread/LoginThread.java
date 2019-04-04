package com.example.hly.app.thread;

import android.util.Log;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by hly on 2018/8/19.
 */

public class LoginThread implements Runnable {
    private String userId ;
    private String userPassword ;
    public static String message =" ";

    public static final  String IP = "520a8cbd.nat123.cc";
    public static final  int PORT = 80;

    public LoginThread() {
    }
    public LoginThread(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }
    @Override
    public void run() {

        try {
            Log.e("开始连接","Start");
            Socket socket = new Socket(IP,PORT);
            Log.e("成功连接","success");
            //获取输入流
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataInputStream in = new DataInputStream(socket.getInputStream());
            //获取输出流
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(userId+" "+userPassword);
            //刷新发送
            out.flush();
            message = in.readUTF();
            System.out.print("接收的数据为:"+in.readUTF());
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
