package com.dgg.wifitransfer.wifi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dgg.wifitransfer.R;
import com.dgg.wifitransfer.R2;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by masel on 2016/10/10.
 */

public class PopupMenuDialog {
    Unbinder mUnbinder;
    @BindView(R2.id.wifi_action)
    TextView wifi_action;
    @BindView(R2.id.wifi_on)
    LinearLayout wifi_on;
    @BindView(R2.id.wifi_off)
    LinearLayout wifi_off;

    @BindView(R2.id.shared_wifi_state)
    ImageView mImgLanState;
    @BindView(R2.id.wifi_conlection_hint)
    TextView wifi_conlection_hint;
    @BindView(R2.id.shared_wifi_address)
    TextView mTxtAddress;
    @BindView(R2.id.shared_wifi_settings)
    Button mBtnWifiSettings;
    WifiConnectChangedReceiver mWifiConnectChangedReceiver = new WifiConnectChangedReceiver();
    private Context context;
    private Dialog dialog;
    private Display display;
    private boolean WIFIisRun;

    public PopupMenuDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        EventBus.getDefault().register(this);
    }

    public PopupMenuDialog builder() {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_popup_menu_dialog, null);

        view.setMinimumWidth(display.getWidth());

        dialog = new Dialog(context, R.style.PopupMenuDialogStyle);
        dialog.setContentView(view);
        mUnbinder = ButterKnife.bind(this, dialog);
        dialog.setOnDismissListener(this::onDialogDismiss);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public PopupMenuDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public PopupMenuDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        checkWifiState(WifiUtils.getWifiConnectState(context));
        dialog.show();
        registerWifiConnectChangedReceiver();
    }

    @OnClick({R2.id.shared_wifi_cancel})
    public void onCancelClick(View view) {
        if (WIFIisRun) {
            checkOffWIFI();
        } else {
            dialog.dismiss();
        }
    }

    @OnClick({R2.id.shared_wifi_settings})
    public void onSettingClick(View view) {
        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    void registerWifiConnectChangedReceiver() {
        IntentFilter intentFilter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(mWifiConnectChangedReceiver, intentFilter);
    }

    void unregisterWifiConnectChangedReceiver() {
        context.unregisterReceiver(mWifiConnectChangedReceiver);
    }

    //WiFi网络变化
    @Subscriber(tag = Constants.RxBusEventType.WIFI_CONNECT_CHANGE_EVENT)
    public void onWifiConnectStateChanged2(NetworkInfo.State state) {
        checkWifiState(state);
    }

    void checkWifiState(NetworkInfo.State state) {
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            if (state == NetworkInfo.State.CONNECTED) {
                String ip = WifiUtils.getWifiIp(context);
                if (!TextUtils.isEmpty(ip)) {
                    onWifiConnected(ip);
                    return;
                }
            }
            onWifiConnecting();
            return;
        }
        onWifiDisconnected();
    }

    //启动HTTP服务失败
    void onWifiDisconnected() {
        wifi_on.setVisibility(View.GONE);
        wifi_off.setVisibility(View.VISIBLE);
        WIFIisRun = false;
    }

    //正在获取地址...
    void onWifiConnecting() {
        wifi_on.setVisibility(View.GONE);
        wifi_off.setVisibility(View.VISIBLE);
        wifi_conlection_hint.setText(R.string.retrofit_wlan_address);
        WIFIisRun = false;
    }

    //请在电脑浏览器地址栏完整输入
    void onWifiConnected(String ipAddr) {
        wifi_on.setVisibility(View.VISIBLE);
        wifi_off.setVisibility(View.GONE);
        mTxtAddress.setText(String.format(context.getString(R.string.http_address), ipAddr, Constants.HTTP_PORT));

        WIFIisRun = true;
    }

    void onDialogDismiss(DialogInterface dialog) {
        if (mUnbinder != null) {
            mUnbinder.unbind();
            unregisterWifiConnectChangedReceiver();
            WebService.stop(context);
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 开启WiFi传输关闭时的提示
     */
    public void checkOffWIFI() {
        final AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setTitle("关闭无线传输功能");
        ab.setMessage("如果您正在传输文件，关闭后将无法接收文件");
        ab.setCancelable(true);
        ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
            }
        });
        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface d, int which) {
                dialog.dismiss();
            }
        });
        ab.create();
        ab.show();
    }

    //文件操作
    /*wifi 传输文件的具体操作*/
    @Subscriber(tag = Constants.RxBusEventType.LOAD_BOOK_LIST)
    private void fileAction(FileAction fileAction) {
        if (fileAction.actionType == FileAction.ACTION_TYPE_DELETE) {
            wifi_action.setText("删除：" + fileAction.filePath);
        } else if (fileAction.actionType == FileAction.ACTION_TYPE_UPLOAD) {
            wifi_action.setText("传输：" + fileAction.filePath);
        } else if (fileAction.actionType == FileAction.ACTION_TYPE_DOWNLOAD) {
            wifi_action.setText("下载：" + fileAction.filePath);
        }
    }
}
