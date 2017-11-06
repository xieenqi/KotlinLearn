package com.ipcdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ipcdemo.IMyAidlInterface;
import com.ipcdemo.R;
import com.ipcdemo.server.AidlService;

/**
 * Created by qiqi on 17/11/5.
 */

public class AIDLActivity2 extends AppCompatActivity {
    private IMyAidlInterface mIMyAidlInterface;
    TextView aidl;
    ConnectCallBack callBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        aidl = (TextView) findViewById(R.id.aidl);
//        aidl.setOnClickListener(this);
        callBack = new ConnectCallBack();
        Intent intent = new Intent(this, AidlService.class);
        // 服务端AndroidManifest.xml文件该Service所配置的action
//        intent.setAction("com.ipcdemo");
//        // Service所在的包名
//        intent.setPackage("com.ipcdemo.server");
        bindService(intent, callBack, Context.BIND_AUTO_CREATE);
    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.aidl:
//                Intent intent = new Intent();
//                // 服务端AndroidManifest.xml文件该Service所配置的action
//                intent.setAction("com.ipcdemo");
//                // Service所在的包名
//                intent.setPackage("com.ipcdemo");
//                bindService(intent, new ConnectCallBack(), Context.BIND_AUTO_CREATE);
//                break;
//        }
//    }

    class ConnectCallBack implements ServiceConnection {

        // 服务连接成功回调
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            login();
            Log.d("log", "服务绑定成功---");
        }

        // 服务断开连接回调
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIMyAidlInterface = null;
            Log.d("log", "服务 解除绑定+++");
        }
    }

    private void login() {
        try {
            mIMyAidlInterface.login("+++silencezwm", "123456");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(callBack);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
