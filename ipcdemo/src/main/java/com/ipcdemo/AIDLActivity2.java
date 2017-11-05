import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

import com.ipcdemo.IMyAidlInterface;
import com.ipcdemo.R;

/**
 * Created by qiqi on 17/11/5.
 */

public class AIDLActivity2 extends AppCompatActivity {
    private IMyAidlInterface mIMyAidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
//        mBtnLogin = (Button) findViewById(R.id.btn_login);
//        mBtnLogin.setOnClickListener(this);
        Intent intent = new Intent();
                // 服务端AndroidManifest.xml文件该Service所配置的action
                intent.setAction("com.ipcdemo");
                // Service所在的包名
                intent.setPackage("com.ipcdemo");
                bindService(intent, new ConnectCallBack(), Context.BIND_AUTO_CREATE);
    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_login:
//                Intent intent = new Intent();
//                // 服务端AndroidManifest.xml文件该Service所配置的action
//                intent.setAction("com.silencezwm.ipcdemo");
//                // Service所在的包名
//                intent.setPackage("com.silencezwm.ipcdemo");
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
        }

        // 服务断开连接回调
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIMyAidlInterface = null;
        }
    }

    private void login() {
        try {
            mIMyAidlInterface.login("+++silencezwm", "123456");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
