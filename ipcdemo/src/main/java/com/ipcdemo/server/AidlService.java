package com.ipcdemo.server;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.ipcdemo.IMyAidlInterface;

/**
 * Created by qiqi on 17/11/3.
 */

public class AidlService extends Service {

    private static final String TAG = "log";
    Handler handler;

    public AidlService() {
        handler = new Handler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            Log.d(TAG, "=====:basicTypes");
        }

        @Override
        public void login(String username, String password) throws RemoteException {
            Log.d(TAG, "==11===:loginAA " + username + "==" + password);
//handler 要在主线程new 对象
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "夸进程通讯成功--AIDL", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
