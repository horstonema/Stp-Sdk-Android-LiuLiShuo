package com.aiedevice.sdkdemo.activity.device;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aiedevice.sdkdemo.AppConstant;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.R;
import com.google.gson.Gson;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.view.CustomDialog;
import com.aiedevice.appcommon.util.LogUtils;
import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.appcommon.util.GsonUtils;
import com.aiedevice.sdk.device.DeviceManager;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceSettingsActivity extends StpBaseActivity {
    private static final String TAG = DeviceSettingsActivity.class.getSimpleName();

    // logic
    private DeviceDetail mDevDetail;
    private DeviceManager mDeviceManager;
    private int mVolumeLevel = 0;

    // views
    @BindView(R.id.et_new_devname)
    public EditText etNewDevName;
    @BindView(R.id.tv_res_title)
    public TextView tvDeviceName;
    @BindView(R.id.tv_device_volume)
    public TextView tvDeviceVolume;

    public static void launch(Context context) {
        Intent intent = new Intent(context, DeviceSettingsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("设备设置");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_dev_settings;
    }

    @OnClick({R.id.btn_modify_device_name, R.id.volume_add, R.id.volume_reduce,
            R.id.btn_device_detail, R.id.btn_master_status})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_modify_device_name:
                modifyMasterName();
                break;

            case R.id.volume_add:
                addVolume();
                break;

            case R.id.volume_reduce:
                reduceVolume();
                break;

            case R.id.btn_device_detail:
                HardwareInfoActivity.launch(mContext);
                break;

            case R.id.btn_master_status:
                DeviceStatusActivity.launch(mContext);
                break;
        }
    }

    private void initLogic() {
        mDeviceManager = new DeviceManager(this);

        refreshDevInfo();
    }

    private void refreshDevInfo() {
        mDeviceManager.getDeviceDetail(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("获取设备信息成功");
                Gson gson = GsonUtils.getGson();
                mDevDetail = gson.fromJson(resultSupport.getModel("data").toString(), DeviceDetail.class);
                tvDeviceName.setText("设备名称：" + mDevDetail.getName());

                mVolumeLevel = AppConstant.getVolumeStep((int) mDevDetail.getVolume());
                tvDeviceVolume.setText("当前音量等级:" + mVolumeLevel + " 数值=" + mDevDetail.getVolume());
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("获取设备信息失败");
            }
        });
    }

    private void addVolume() {
        mVolumeLevel = mVolumeLevel + 1;
        if (mVolumeLevel >= AppConstant.VOLUME_VALUES.length)
            mVolumeLevel = AppConstant.VOLUME_VALUES.length - 1;

        mDeviceManager.changeDeviceVolume(AppConstant.VOLUME_VALUES[mVolumeLevel], new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("音量调整成功");
                tvDeviceVolume.setText("当前音量:" + mVolumeLevel);
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("音量调整失败");
            }
        });
    }

    private void reduceVolume() {
        mVolumeLevel = mVolumeLevel - 1;
        if (mVolumeLevel < 0)
            mVolumeLevel = 0;

        mDeviceManager.changeDeviceVolume(AppConstant.VOLUME_VALUES[mVolumeLevel], new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                tvDeviceVolume.setText("当前音量:" + mVolumeLevel);
                Toaster.show("音量调整成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("音量调整失败");
            }
        });
    }

    private void modifyMasterName() {
        String name = etNewDevName.getText().toString();
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setMessage(name);
        builder.setGetMessage(new CustomDialog.GetMessage() {
            @Override
            public void get(String msg) {
                LogUtils.d(msg);

                mDeviceManager.updateDeviceName(msg, new ResultListener() {
                    @Override
                    public void onSuccess(ResultSupport resultSupport) {
                        Toaster.show("修改成功");
                        refreshDevInfo();
                    }

                    @Override
                    public void onError(int code, String message) {
                        Log.d(TAG, "code = " + code + "；message = " + message);
                        Toaster.show("修改失败");
                    }
                });
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}