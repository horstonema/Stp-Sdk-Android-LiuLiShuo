package com.aiedevice.sdkdemo.activity.device;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.aiedevice.appcommon.util.Toaster;
import com.aiedevice.basic.bean.ResultSupport;
import com.aiedevice.basic.net.ResultListener;
import com.aiedevice.sdk.AccountUtil;
import com.aiedevice.sdk.account.AccountManager;
import com.aiedevice.sdk.device.DeviceManager;
import com.aiedevice.sdkdemo.activity.StpBaseActivity;
import com.aiedevice.sdkdemo.activity.blufi.PreConnectActivity;
import com.aiedevice.sdkdemo.bean.DeviceDetail;
import com.aiedevice.sdkdemo.bean.User;
import com.aiedevice.sdkdemo.activity.LoginActivity;
import com.aiedevice.sdkdemo.R;

import java.util.List;

import butterknife.OnClick;

public class DeviceManageActivity extends StpBaseActivity {
    private static final String TAG = DeviceManageActivity.class.getSimpleName();

    // logic
    private DeviceDetail mDevDetail;
    private DeviceManager mDeviceManager;
    private AccountManager mAccountManager;

    public static void launch(Context context, DeviceDetail devDetail) {
        Intent intent = new Intent(context, DeviceManageActivity.class);
        intent.putExtra("DeviceDetail", devDetail);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("设备管理");
        initLogic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.act_device_manager;
    }

    private void initLogic() {
        mDevDetail = (DeviceDetail) getIntent().getSerializableExtra("DeviceDetail");
        if (mDevDetail != null && mDevDetail.getDetail() != null)
            mDevDetail = mDevDetail.getDetail();

        Log.d(TAG, "[onCreate] mDevDetail=" + mDevDetail);
        mDeviceManager = new DeviceManager(mContext);
        mAccountManager = new AccountManager(mContext);
    }

    @OnClick({R.id.btn_set_network, R.id.btn_bind_device, R.id.btn_unbind_device, R.id.btn_device_setting})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_network:
                PreConnectActivity.launch(mContext, true);
                break;

            case R.id.btn_bind_device:
                PreConnectActivity.launch(mContext, false);
                break;

            case R.id.btn_unbind_device:
                if (mDevDetail != null && mDevDetail.getUsers() != null) {
                    showDeleteMasterDialog();
                }
                break;

            case R.id.btn_device_setting:
                DeviceSettingsActivity.launch(mContext);
                break;
        }
    }

    private void showDeleteMasterDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("确定要解除设备吗？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<User> users = mDevDetail.getUsers();
                for (User user : users) {
                    if (user.isManager()) {
                        if (user.getUserId().equals(AccountUtil.getUserId()) && users.size() > 1) {
                            showShiftMasterDialog(users);
                            return;
                        }
                    }
                }
                deleteMaster();
            }
        });
        dialog.setNegativeButton("取消", null);
        dialog.show();
    }

    private void showShiftMasterDialog(final List<User> users) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("请将管理员权限移交至：");
        String[] userName = new String[users.size() - 1];
        final String[] userID = new String[userName.length];
        int j = 0;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserId().equals(AccountUtil.getUserId())) {
                continue;
            }
            userName[j] = user.getName();
            userID[j] = user.getUserId();
            j++;
        }
        dialog.setItems(userName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                transManager(userID[i]);
            }
        });
        dialog.show();
    }

    // 解除绑定
    private void deleteMaster() {
        mDeviceManager.deleteDevice(new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("解除绑定成功");
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("解除绑定失败");
            }
        });
    }

    // 变更管理员
    private void transManager(String userID) {
        mAccountManager.changeManager(userID, new ResultListener() {
            @Override
            public void onSuccess(ResultSupport resultSupport) {
                Toaster.show("转移管理员成功");
            }

            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "code = " + code + "；message = " + message);
                Toaster.show("转移管理员失败");
            }
        });
    }
}
