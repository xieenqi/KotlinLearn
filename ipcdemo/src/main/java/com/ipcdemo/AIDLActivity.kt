package com.ipcdemo

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ComponentName
import android.os.IBinder
import android.content.ServiceConnection
import android.os.RemoteException


/**
 * Created by qiqi on 17/11/5.
 */
class AIDLActivity : AppCompatActivity() {

    var mIMyAidlInterface: IMyAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl)
        val intent = Intent()
        // 服务端AndroidManifest.xml文件该Service所配置的action
        intent.action = "com.ipcdemo"
        // Service所在的包名
        intent.`package` = "com.ipcdemo"
        bindService(intent, ConnectCallBack(), Context.BIND_AUTO_CREATE)
        startService(intent)
    }

    internal inner class ConnectCallBack : ServiceConnection {

        // 服务连接成功回调
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder)
            login()
        }

        // 服务断开连接回调
        override fun onServiceDisconnected(componentName: ComponentName) {
            mIMyAidlInterface = null
        }
    }

    private fun login() {
        try {
            mIMyAidlInterface!!.login("silencezwm", "123456")
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }
}