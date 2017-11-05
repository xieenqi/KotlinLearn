package com.ipcdemo.server;

import android.app.Service;
import android.content.Intent;
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

    public AidlService() {

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
            Log.d(TAG, "=====:login " + username + "==" + password);
            Toast.makeText(AidlService.this, "夸进程通讯成功--AIDL", Toast.LENGTH_SHORT).show();
        }
    }
}
