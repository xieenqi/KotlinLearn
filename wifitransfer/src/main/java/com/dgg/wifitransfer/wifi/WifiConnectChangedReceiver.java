package com.dgg.wifitransfer.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import org.simple.eventbus.EventBus;


/*WiFi 变化广播监听*/
public class WifiConnectChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
            Parcelable parcelableExtra = intent
                    .getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (null != parcelableExtra) {
                NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
//                RxBus.get().post(Constants.RxBusEventType.WIFI_CONNECT_CHANGE_EVENT, networkInfo.getState());
//                net.dgg.lib.base.RxBus.getInstance().post(networkInfo.getState());
                EventBus.getDefault().post(networkInfo.getState(),Constants.RxBusEventType.WIFI_CONNECT_CHANGE_EVENT);
            }
        }
    }
}
