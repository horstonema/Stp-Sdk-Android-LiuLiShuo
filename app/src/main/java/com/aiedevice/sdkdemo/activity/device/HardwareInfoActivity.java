package com.aiedevice.sdkdemo.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.bean.InfoItem;
import com.aiedevice.sdkdemo.bean.PuddingInfoData;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.device.DeviceManager;

import java.util.List;

public class HardwareInfoActivity extends StpBaseActivity {
    private static final String TAG = HardwareInfoActivity.class.getSimpleName();

    private LinearLayout llDeviceInfo;
    private DeviceManager mDeviceManager;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HardwareInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initLogic();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_hardware_info;
    }

    private void initLogic() {
        mDeviceManager = new DeviceManager(mContext);

        refreshHardwareInfo();
    }

    private void refreshHardwareInfo() {
        mDeviceManager.getDeviceHardwareInfo(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                PuddingInfoData loginData = gson.fromJson(resultSupport.getModel("data").toString(), PuddingInfoData.class);
                List<InfoItem> infoItems = loginData.getList();
                llDeviceInfo = (LinearLayout) findViewById(R.id.ll_master_info);
                if (infoItems != null && infoItems.size() > 0) {
                    for (int i = 0; i < infoItems.size(); i++) {
                        InfoItem item = infoItems.get(i);
                        TextView textView = new TextView(getBaseContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        textView.setLayoutParams(params);
                        textView.setTextColor(getResources().getColor(android.R.color.black));
                        textView.setText(item.getKey() + " : " + item.getVal());
                        llDeviceInfo.addView(textView);
                    }
                }
                Toaster.show("获取信息成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取信息失败 错误:" + message);
            }
        });
    }

}
