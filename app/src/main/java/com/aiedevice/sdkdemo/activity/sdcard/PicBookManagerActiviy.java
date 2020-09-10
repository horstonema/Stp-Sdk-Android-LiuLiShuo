package com.aiedevice.sdkdemo.activity.sdcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;

import butterknife.OnClick;

public class PicBookManagerActiviy extends StpBaseActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, PicBookManagerActiviy.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("绘本管理");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_picbook_manager;
    }

    @OnClick({R.id.btn_device_manage, R.id.btn_picbook_upload})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_device_manage:
                SdcardManagerActivity.launch(mContext);
                break;

            case R.id.btn_picbook_upload:
                PicbookUploadActivity.launch(mContext);
                break;
        }
    }
}
