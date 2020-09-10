package com.aiedevice.sdkdemo.activity.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.R;

import butterknife.OnClick;

public class ChatManagerActivity extends StpBaseActivity {
    private static final String TAG = ChatManagerActivity.class.getSimpleName();

    public static void launch(Context context, DeviceDetail deviceDetail) {
        Intent intent = new Intent(context, ChatManagerActivity.class);
        intent.putExtra("DeviceDetail", deviceDetail);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("微聊");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_chat_manager;
    }

    @OnClick({R.id.btn_wechat, R.id.btn_send_msg})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wechat:
                ImMessageActivity.launch(mContext);
                break;

            case R.id.btn_send_msg:
                DeviceDetail devDetail = getDeviceDetail();
                if (devDetail != null) {
                    SendMessageActivity.launch(mContext);
                } else {
                    Log.w(TAG, "devDetail is null");
                }
                break;
        }
    }

    private DeviceDetail getDeviceDetail() {
        Intent intent = getIntent();
        if (intent != null) {
            return (DeviceDetail) intent.getSerializableExtra("DeviceDetail");
        }

        return null;
    }

}
