package com.javahly.ch8;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**MathService可以对外提供服务，即提供一些计算的方法。可以通过两种方式对外提供服务，一种是
 * 把方法写在Service类中，一种是把方法写在自定义的Binder类中。客户端连接到Service后可以自动获得Binder对象
 * 然后可以直接调用Binder对象中定义的方法，也可以通过Binder对象的getService方法来得到Service
 * 对象，从而可以调用Service的public的方法。getService方法需要在Binder类中定义。
 * Created by Administrator on 2018-05-14.
 */
public class MathService extends Service {
    MyBinder binder=new MyBinder();
    public MathService() {
        super();System.out.println("MathService()");
    }
    @Override
    public void onCreate() {
        super.onCreate();System.out.println("onCreate()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();System.out.println("onDestroy()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind() MathService is binded");
        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("onUnbind()");
        return true;
    }
    public class MyBinder extends Binder {
        //MyBinder类中的方法，也就是为其他程序提供服务的功能，
        // 这里编写了方法getSum，目的是计算1到maxNumber之间的和
        //客户端可以通过这个Binder对象来调用getSum。
        // 当然也可以通过getService方法得到当前的Service对象，从而调用Service的public方法。
        public int getSum(int maxNumber){
            int sum=0;
            for(int i=1;i<=maxNumber;i++)
                sum=sum+i;
            return sum;
        }

        //getService方法返回当前Service的对象，这样在客户端就可以通过binder对象得到这个Service对象，
        //从而可以调用Service 中定义的public的各种方法了。
        public MathService getService(){
            return MathService.this;
        }
        //getMaxFromArray方法就是本Service对外提供的服务，目的是找出数组中的最大值。
        public double getMaxFromArray(double[] myArray){
            if(myArray==null)
                return -1;
            double max=myArray[0];
            for(int i=1;i<myArray.length;i++){
                if(max<myArray[i])
                    max=myArray[i];
            }
            return max;
        }

    }
    //getJieCheng方法是本Service对外提供的另一个服务，即计算阶乘
    public int getJieCheng(int maxNumber){
        if(maxNumber>10)//阶乘容易超出int数的上届，所以这里限制为10，就是只计算10以内的阶乘
            return 0;
        int sum=1;
        for(int i=1;i<=maxNumber;i++)
            sum=sum*i;
        return sum;
    }
}
