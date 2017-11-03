package com.ipcdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by qiqi on 17/11/3.
 */

public class AidlService extends Service {

    private static final String TAG = "AIDL";

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
            Log.d(TAG, "=====:login" + username + "==" + password);
        }
    }
}
