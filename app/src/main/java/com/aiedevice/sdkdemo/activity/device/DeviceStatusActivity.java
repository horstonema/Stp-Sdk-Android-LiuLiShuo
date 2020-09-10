package com.aiedevice.sdkdemo.activity.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.bean.User;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.device.DeviceManager;

import butterknife.BindView;

public class DeviceStatusActivity extends StpBaseActivity {
    private static final String TAG = DeviceStatusActivity.class.getSimpleName();

    // logic
    private DeviceManager mDeviceManager;

    // view
    @BindView(R.id.tv_play_status)
    public TextView playinfo;
    @BindView(R.id.tv_power)
    public TextView power;
    @BindView(R.id.tv_device_id)
    public TextView id;
    @BindView(R.id.tv_res_title)
    public TextView name;
    @BindView(R.id.tv_device_type)
    public TextView device_type;
    @BindView(R.id.tv_is_online)
    public TextView is_online;
    @BindView(R.id.tv_bind_user_list)
    public TextView user;
    @BindView(R.id.tv_battery)
    public TextView battery;

    public static void launch(Context context) {
        Intent intent = new Intent(context, DeviceStatusActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("设备状态");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.master_status_layout;
    }

    private void initLogic() {
        mDeviceManager = new DeviceManager(mContext);

        getMasterDetail();
    }

    private void getMasterDetail() {
        mDeviceManager.getDeviceDetail(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Gson gson = GsonUtils.getGson();
                DeviceDetail masterDetail = gson.fromJson(resultSupport.getModel("data").toString(), DeviceDetail.class);
                id.setText("设备ID：" + masterDetail.getId());
                device_type.setText("设备类型：" + masterDetail.getDeviceType());
                battery.setText(masterDetail.getBattery() + "%");
                is_online.setText(masterDetail.isOnline() + "");
                StringBuffer buffer = new StringBuffer();
                for (User user : masterDetail.getUsers()) {
                    buffer.append(user.getName());
                    buffer.append("\n");
                }
                user.setText(buffer.toString());
                name.setText("设备名称：" + masterDetail.getName());
                String text = masterDetail.isPower() ? "充电中" : "没有充电";
                power.setText("充电状态 :" + text);
                playinfo.setText("播放状态 :" + masterDetail.getPlayinfo().toString());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取设备详情失败");
            }
        });
    }

}
